package com.example.restaurants.presentation.common.ext

import androidx.annotation.NonNull
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

internal fun <T> LiveData<T>.observeOnce(
    @NonNull owner: LifecycleOwner,
    @NonNull observer: Observer<T>
) {
    observe(owner, object : Observer<T> {
        override fun onChanged(t: T?) {
            observer.onChanged(t)
            removeObserver(this)
        }
    })
}
