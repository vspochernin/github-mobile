package com.gitficko.github.ui.repositories_list

import com.gitficko.github.model.Repository

interface RepositoryClickListener {
    fun onRepositoryClick(repository: Repository)
}
