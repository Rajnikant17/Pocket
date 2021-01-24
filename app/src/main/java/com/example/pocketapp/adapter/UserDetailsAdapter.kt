package com.example.pocketapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.example.apiservicesmodule.models.UsersPostList
import com.example.apiservicesmodule.models.UserDetailsApiResponse
import com.example.pocketapp.databinding.UserDetailsAdapterBinding

class UserDetailsAdapter(
    val context: Context,
    private val userEmailAndName: UserDetailsApiResponse,
    private val itemList: List<UsersPostList>
) : RecyclerView.Adapter<UserDetailsAdapter.Myhandler>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Myhandler {
        val userDetailsAdapterBinding =
            UserDetailsAdapterBinding.inflate(LayoutInflater.from(context), parent, false)
        return Myhandler(userDetailsAdapterBinding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: Myhandler, position: Int) {
        holder.bind(itemList.get(position), userEmailAndName)
    }

    class Myhandler(val binding: UserDetailsAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(titleAndBodyResponse: UsersPostList, userEmailAndName: UserDetailsApiResponse) {
            binding.titleAndBody = titleAndBodyResponse
            binding.userNameAndEmail = userEmailAndName
            binding.setVariable(BR.titleAndBody, titleAndBodyResponse)
            binding.setVariable(BR.userNameAndEmail, userEmailAndName)
            binding.executePendingBindings()
        }
    }
}