package com.goranotes.listwithrecyclerview.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.goranotes.listwithrecyclerview.adapter.CarListAdapter
import com.goranotes.listwithrecyclerview.databinding.ActivityMainBinding
import com.goranotes.listwithrecyclerview.model.DataItemCarResponse
import com.goranotes.listwithrecyclerview.presenter.MainActivityPresenter

class MainActivity : AppCompatActivity(), MainActivityContract.View {

    lateinit var binding: ActivityMainBinding

    private lateinit var presenter: MainActivityContract.Presenter
    private lateinit var adapterCarList: CarListAdapter
    private var itemsCarList: MutableList<DataItemCarResponse> = mutableListOf()

    override fun showError(data: String?) {
        Toast.makeText(this, data, Toast.LENGTH_SHORT).show()
    }

    override fun showResultGetData(data: List<DataItemCarResponse>) {

        itemsCarList.clear()
        itemsCarList.addAll(data)
        adapterCarList.notifyDataSetChanged()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter =MainActivityPresenter(this, this)

        adapterCarList = CarListAdapter(this, itemsCarList){ data->
            Toast.makeText(this, data.carName, Toast.LENGTH_SHORT).show()
        }
        binding.rvList.layoutManager = LinearLayoutManager(this)
        binding.rvList.setHasFixedSize(true)
        binding.rvList.adapter = adapterCarList

        loadData()
    }

    private fun loadData(){
        presenter.getData()
    }
}