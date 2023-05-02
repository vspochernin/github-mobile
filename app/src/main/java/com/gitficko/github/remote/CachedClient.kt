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

    fun getRepositoriesOf(ownerLogin: String): Single<List<Repository>> {
        val compositeDisposable = CompositeDisposable()

        return Single.create { emitter ->
            compositeDisposable.add(
                Networking.githubApi.getRepositoriesByOwnerName(ownerLogin)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .doOnSuccess {
                        database!!.repositoryDao().clear(ownerLogin)
                        database!!.repositoryDao().insert(it.map(RepositoryDto::toEntity))
                    }
                    .subscribe({
                        emitter.onSuccess(it.map(RepositoryDto::toEntity))
                    }, {
                        try {
                            emitter.onSuccess(database!!.repositoryDao()
                                .getAllByOwnerLogin(ownerLogin))
                        } catch (t: Throwable) {
                            emitter.onError(t)
                        }
                    })
            )
        }.doOnDispose {
            compositeDisposable.dispose()
        }
    }

    fun getPullRequestsOf(ownerLogin: String, repositoryName: String): Single<List<PullRequest>> {
        val compositeDisposable = CompositeDisposable()

        return Single.create { emitter ->
            compositeDisposable.add(
                Networking.githubApi.getPullRequests(ownerLogin, repositoryName)
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .doOnSuccess {
//                        database!!.pullRequestDao().clear(ownerLogin, repositoryName)
                        database!!.pullRequestDao().insert(it.map(PullRequestDto::toEntity))
                    }
                    .subscribe({
                        Log.i("some_list", it.toString())
                        emitter.onSuccess(it.map(PullRequestDto::toEntity))
                    }, {
                        Log.e("some_throwable", "fick", it)
                        try {
                            emitter.onSuccess(database!!.pullRequestDao().getAllByOwnerLoginAndRepositoryName(ownerLogin, repositoryName))
                        } catch (t: Throwable) {
                            emitter.onError(t)
                        }
                    })
            )
        }.doOnDispose {
            compositeDisposable.dispose()
        }.doOnSuccess {
            Log.i("some_list", it.toString())
        }
    }

    fun getPullRequestsOf(ownerLogin: String): Single<List<PullRequest>> {
        return getRepositoriesOf(ownerLogin).flatMap { repositories ->
            val requests = repositories.map { repository -> getPullRequestsOf(ownerLogin, repository.name) }
            Single.zip(requests) { pullRequestsList ->
                pullRequestsList.flatMap { pullRequests -> pullRequests as List<PullRequest> }
            }
        }
    }
}