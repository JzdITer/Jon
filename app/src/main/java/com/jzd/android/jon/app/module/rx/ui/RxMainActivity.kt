package com.jzd.android.jon.app.module.rx.ui

import android.os.Bundle
import android.view.View
import com.jzd.android.jon.app.R
import com.jzd.android.jon.app.base.BaseActivity
import com.jzd.android.jon.utils.JLog
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_rx_main.*
import java.util.concurrent.TimeUnit

class RxMainActivity : BaseActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rx_main)

        mBtnJust.setOnClickListener(this)
        mBtnCreate.setOnClickListener(this)
        mBtnFrom.setOnClickListener(this)
        mBtnRepeat.setOnClickListener(this)
        mBtnDefer.setOnClickListener(this)
        mBtnTime.setOnClickListener(this)
        mBtnInterval.setOnClickListener(this)
    }

    override fun onClick(v: View?)
    {
        when (v?.id)
        {
            R.id.mBtnJust ->
            {
                Observable.just("1", "2", "3", "4", "5")
                        .subscribe({
                            JLog.d("onNext:$it")
                        }, {
                            JLog.e("onError:$it")
                        }, {
                            JLog.d("onComplete")
                        }, {
                            JLog.d("onSubscribe")
                        })

            }
            R.id.mBtnCreate ->
            {
                Observable.create(ObservableOnSubscribe<Int> {
                    try
                    {
                        if (!it.isDisposed)
                        {
                            for (i in 0..5)
                            {
                                it.onNext(i)
                            }
                            it.onComplete()
                        }
                    } catch (e: Exception)
                    {
                        it.onError(e)
                    }
                }).subscribe({
                    JLog.d("onNext->$it")
                }, {
                    JLog.e("onError->$it")
                }, {
                    JLog.e("onSubscribe")
                })
            }
            R.id.mBtnFrom ->
            {
                Observable.fromArray("a", "b", "c")
                        .subscribe({
                            JLog.d(it)
                        })

                val items = arrayListOf("1", "2", "3")
                Observable.fromIterable(items)
                        .subscribe({
                            JLog.d(it)
                        })
            }
            R.id.mBtnRepeat ->
            {
                Observable.just("1", "2", "3")
                        .repeat(2)
                        .subscribe({ JLog.d(it) })
                JLog.d("---------------------------")
                Observable.just("1")
                        .repeatWhen { Observable.timer(3, TimeUnit.SECONDS) }.subscribe({ JLog.d(it) })

                JLog.d("---------------------------")

                var count = 0
                Observable.just("测试")
                        .repeatUntil { count >= 5 }
                        .subscribe({

                            JLog.d("第${count++}次：$it")

                        })
            }
            R.id.mBtnDefer ->
            {
                var a = 1
                val defer = Observable.defer {
                    Observable.just(a)
                }
                val just = Observable.just(a)
                a++
                just.subscribe({ JLog.d("just:" + it.toString()) })
                defer.subscribe({
                    JLog.d("defer:" + it.toString())
                })
            }
            R.id.mBtnTime ->
            {
                Observable.timer(5, TimeUnit.SECONDS)
                        .subscribe(object : Observer<Long>
                        {
                            override fun onComplete()
                            {
                            }

                            override fun onSubscribe(d: Disposable)
                            {
                                JLog.d("onSubscribe")
                            }

                            override fun onNext(t: Long)
                            {
                                JLog.d("onNext")
                                JLog.d("onNext发射结果:$t")
                            }

                            override fun onError(e: Throwable)
                            {
                                JLog.e(e.toString())
                            }
                        })
            }
            R.id.mBtnInterval ->
            {
                Observable.intervalRange(10, 5, 1, 1, TimeUnit.SECONDS)
                        .subscribe({ JLog.d(it.toString()) })
            }
        }
    }
}