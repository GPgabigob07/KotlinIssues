package com.nta.githubkotlinissueapp.api

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

object RxHandler {

    sealed class RxResult<out T> {
        object Loading : RxResult<Nothing>()
        data class Success<T>(val data: T) : RxResult<T>()
        data class Error<T>(val throwable: Throwable) : RxResult<T>()
    }


    @SuppressLint("CheckResult")
    fun <T, L, O> emitOn(
        emitter: L,
        observableProducer: () -> O
    ) where T : Any, L : MutableLiveData<RxResult<T>>, O : Observable<T> {
        emitter.postValue(RxResult.Loading)
        observableProducer()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { emitter.postValue(RxResult.Success(it)) },
                onError = { emitter.postValue(RxResult.Error(it)) }
            )
    }

}