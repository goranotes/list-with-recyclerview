package com.goranotes.listwithrecyclerview.presenter

import android.app.Activity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.goranotes.listwithrecyclerview.model.DataItemCarResponse
import com.goranotes.listwithrecyclerview.view.MainActivityContract
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.InputStream

class MainActivityPresenter(
    private val activity: Activity,
    val view: MainActivityContract.View
): MainActivityContract.Presenter {

    private val disposable = CompositeDisposable()
    val gson = Gson()

    override fun onViewDetached() {
        disposable.dispose()
    }


    private fun getDataFromJson(): Single<List<DataItemCarResponse>>{

        return Single.create { emitter ->
            try {
                val inputStream: InputStream = activity.assets.open("data_list_car.json")
                val inputString = inputStream.bufferedReader().use{it.readText()}
                val data: List<DataItemCarResponse> = Gson().fromJson(
                    inputString,
                    object : TypeToken<List<DataItemCarResponse?>?>() {}.type
                )
                emitter.onSuccess(data)
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }
    }

    override fun getData() {

        val getDataRequest = getDataFromJson()
        getDataRequest.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<DataItemCarResponse>>{
                override fun onSubscribe(d: Disposable) {
                    disposable.add(d)
                }

                override fun onError(e: Throwable) {
                    view.showError(e.localizedMessage)
                }

                override fun onSuccess(t: List<DataItemCarResponse>) {
                    view.showResultGetData(t)
                }
            })
    }
}