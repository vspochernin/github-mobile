package com.gitficko.github.model

interface ListComparatorAdapter<T> {
    fun submitList(list: List<T>);
}