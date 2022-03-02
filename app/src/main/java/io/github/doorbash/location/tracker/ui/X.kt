package io.github.doorbash.location.tracker.ui

import androidx.lifecycle.MutableLiveData

operator fun <T> MutableLiveData<MutableList<T>>.plusAssign(item: T) {
    val value = this.value ?: mutableListOf()
    value.add(0, item)
    this.value = value
}

operator fun <T> MutableLiveData<MutableList<T>>.plusAssign(items: List<T>) {
    val value = this.value ?: mutableListOf()
    value.addAll(0, items)
    this.value = value
}