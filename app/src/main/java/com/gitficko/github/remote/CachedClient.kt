package com.gitficko.github.remote

import android.app.Application
import android.util.Log
import androidx.room.Room
import com.gitficko.github.model.*
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

object CachedClient {
    var database: ApplicationDatabase? = null

    fun init(context: Application) {
        this.database = Room.databaseBuilder(
            context,
            ApplicationDatabase::class.java,
            "application_database"
        ).build()
    }

    fun getRepositoriesOf(user: Owner): Single<List<Repository>> {
        val compositeDisposable = CompositeDisposable()

        return Single.create { emitter ->
            compositeDisposable.add(
                Networking.githubApi.getRepositoriesByOwnerName(user.login)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe({
                        emitter.onSuccess(it.map(RepositoryDto::toEntity))
                    }, {
                        try {
                            emitter.onSuccess(database!!.repositoryDao()
                                .getAllRepositoriesByOwnerId(user.id))
                        } catch (t: Throwable) {
                            emitter.onError(t)
                        }
                    })
            )
        }.doOnDispose {
            compositeDisposable.dispose()
        }
    }

    fun getPullRequestsOf(user: Owner, repository: Repository): Single<List<PullRequest>> {
        val compositeDisposable = CompositeDisposable()

        return Single.create { emitter ->
            compositeDisposable.add(
                Networking.githubApi.getPullRequests(user.login, repository.name)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe({
                        Log.i("FICKO_PULL", it.map(PullRequestDto::toEntity).toString())
                        emitter.onSuccess(it.map(PullRequestDto::toEntity))
                    }, {
                        try {
                            emitter.onSuccess(database!!.pullRequestDao()
                                .getAllPullRequestsByRepositoryId(repository.id))
                        } catch (t: Throwable) {
                            emitter.onError(t)
                        }
                    })
            )
        }.doOnDispose {
            compositeDisposable.dispose()
        }
    }

    fun getPullRequestsOf(user: Owner): Single<List<PullRequest>> {
        return getRepositoriesOf(user).flatMap { repositories ->
            val requests = repositories.map { repository -> getPullRequestsOf(user, repository) }
            Single.zip(requests) { pullRequestsList ->
                pullRequestsList.flatMap { pullRequests -> pullRequests as List<PullRequest> }
            }
        }
    }
}