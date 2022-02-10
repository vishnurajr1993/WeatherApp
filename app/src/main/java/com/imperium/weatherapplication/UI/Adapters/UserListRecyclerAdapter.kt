package com.imperium.weatherapplication.UI.Adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.imperium.weatherapplication.R
import com.imperium.weatherapplication.Room.UserEntity
import kotlinx.android.synthetic.main.row_item.view.*
import java.util.*


/*
class UserListRecyclerAdapter {
}*/
class UserListRecyclerAdapter(private val onItemTouchListener: OnItemTouchListener): RecyclerView.Adapter<UserListRecyclerAdapter.VH>() {
    private  var users= mutableListOf<UserEntity>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(parent)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(users[position])
        holder.itemView.setOnClickListener {
            onItemTouchListener.onItemViewTap()
        }
    }

    override fun getItemCount(): Int = users.size


    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<UserEntity>) {
        if(users.isNotEmpty()) {
            users.clear()
        notifyDataSetChanged()
        }
        users= list.toMutableList()
        notifyItemInserted(users.size)
    }

    fun removeAt(position: Int) {
       // users.removeAt(position)
        notifyItemRemoved(position)
    }

    class VH(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.row_item, parent, false)) {

        fun bind(entity: UserEntity) = with(itemView) {
            first_name.text = entity.firstName
            last_name.text = entity.lastName
            email_id.text=entity.email
            avtar.text=entity.firstName.uppercase().substring(0,1)



        }
    }

}
interface OnItemTouchListener {
    fun onItemViewTap()

}

