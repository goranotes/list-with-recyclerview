package com.goranotes.listwithrecyclerview.view

import com.goranotes.listwithrecyclerview.model.DataItemCarResponse

interface MainActivityContract {

    interface View{
        fun showError(data: String?)
        fun showResultGetData(data: List<DataItemCarResponse>)
    }

    interface Presenter{
        fun onViewDetached()
        fun getData()
    }
}