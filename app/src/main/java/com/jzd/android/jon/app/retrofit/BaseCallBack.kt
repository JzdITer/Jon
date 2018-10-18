package com.jzd.android.jon.app.retrofit

import com.jzd.android.jon.utils.JLog
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

abstract class BaseCallBack<T : ResponseBean<*>> : Observer<T>
{
    abstract fun onNextResponse(t: T)
    open fun onErrorResponse(e: Throwable)
    {
        JLog.e(e)
        onResponseFinish()
    }

    open fun onResponseFinish()
    {

    }


    override fun onComplete()
    {
        onResponseFinish()
    }

    override fun onSubscribe(d: Disposable)
    {
    }

    override fun onNext(t: T)
    {
        try
        {
            onNextResponse(t)
        } catch(e: Throwable)
        {
            onErrorResponse(e)
        }
    }

    override fun onError(e: Throwable)
    {
        onErrorResponse(e)
    }
}