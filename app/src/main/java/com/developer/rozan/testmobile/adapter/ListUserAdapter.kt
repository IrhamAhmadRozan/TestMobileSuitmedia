package com.developer.rozan.testmobile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.developer.rozan.testmobile.R
import com.developer.rozan.testmobile.data.response.DataItem
import com.developer.rozan.testmobile.databinding.ItemUsersBinding

class ListUserAdapter : PagingDataAdapter<DataItem, ListUserAdapter.ViewHolder>(DIFF_CALLBACK) {

    var listener: OnUserClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemUsersBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val items = getItem(position)
        if (items != null) {
            holder.bind(items)

            holder.clUser.setOnClickListener {
                listener?.onItemClicked(items)
            }
        }
    }

    class ViewHolder(private val view: ItemUsersBinding) : RecyclerView.ViewHolder(view.root) {
        val clUser = view.clUser

        fun bind(dataItem: DataItem) {
            Glide.with(view.imageUser)
                .load(dataItem.avatar)
                .placeholder(R.drawable.ic_image)
                .error(R.drawable.ic_image_not_found)
                .into(view.imageUser)
            view.tvUser.text = "${dataItem.firstName} ${dataItem.lastName}"
            view.tvEmail.text = dataItem.email
        }
    }

    interface OnUserClickListener {
        fun onItemClicked(dataItem: DataItem)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: DataItem,
                newItem: DataItem
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}