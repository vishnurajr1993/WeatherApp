package com.imperium.weatherapplication.UI.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.imperium.weatherapplication.R
import com.imperium.weatherapplication.Room.UserEntity
import kotlinx.android.synthetic.main.row_item.view.*

/*
class UserListRecyclerAdapter {
}*/
class UserListRecyclerAdapter : RecyclerView.Adapter<UserListRecyclerAdapter.VH>() {
    private  var users= mutableListOf<UserEntity>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(parent)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int = users.size


    fun setData(list: List<UserEntity>) {
        users.addAll(list)
        notifyItemInserted(users.size)
    }

    fun removeAt(position: Int) {
        users.removeAt(position)
        notifyItemRemoved(position)
    }

    class VH(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false)) {

        fun bind(name: UserEntity) = with(itemView) {
            first_name.text = name.firstName
        }
    }
}