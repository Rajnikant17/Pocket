package com.example.pocketapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.example.apiservicesmodule.models.ModelClassGroupedByUserId

import com.example.pocketapp.R
import com.example.pocketapp.databinding.HomePageAdapterBinding

class HomePageAdapter (val context: Context, private val itemList: List<ModelClassGroupedByUserId>, private val itemClickIterface: ItemClickInterface) : RecyclerView.Adapter<HomePageAdapter.Myhandler>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myhandler {
        val itemRowAdapterBinding =
            HomePageAdapterBinding.inflate(LayoutInflater.from(context), parent, false)
        return Myhandler(itemRowAdapterBinding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: Myhandler, position: Int) {

        holder.bind(itemList.get(position))
        holder.ll_parent.setOnClickListener {
            itemClickIterface.itemClickInterfaceMethod(position)
        }
    }

    class Myhandler(val binding: HomePageAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val ll_parent = binding.root.findViewById<LinearLayoutCompat>(R.id.ll_parent)
        fun bind(modelClassGroupedByUserId: ModelClassGroupedByUserId) {
            binding.noOfPostAndUserdIdData = modelClassGroupedByUserId
            binding.setVariable(BR.noOfPostAndUserdIdData, modelClassGroupedByUserId)
            binding.executePendingBindings()
        }
    }

    interface ItemClickInterface {
        fun itemClickInterfaceMethod(position: Int)
    }
}