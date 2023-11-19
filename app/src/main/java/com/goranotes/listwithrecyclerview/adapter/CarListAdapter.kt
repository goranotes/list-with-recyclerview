package com.goranotes.listwithrecyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.goranotes.listwithrecyclerview.databinding.ItemDataListBinding
import com.goranotes.listwithrecyclerview.model.DataItemCarResponse

class CarListAdapter(
    private val context: Context,
    private val items: MutableList<DataItemCarResponse>,
    private val listener: (DataItemCarResponse) -> Unit
): RecyclerView.Adapter<CarListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = ItemDataListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(items[position], context, listener)
    }

    class ViewHolder(val binding: ItemDataListBinding): RecyclerView.ViewHolder(binding.root){
        fun bindItem(
            items: DataItemCarResponse,
            context: Context,
            listener: (DataItemCarResponse) -> Unit){

            binding.root.setOnClickListener {
                listener(items)
            }

            binding.tvName.text = items.carName
            binding.tvDetail.text = items.carDetail
        }
    }
}