package com.ayratis.frogogo.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ayratis.frogogo.R
import com.ayratis.frogogo.entity.User
import com.ayratis.frogogo.extension.inflate
import com.bumptech.glide.Glide
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_user.*

class UserListAdapter(
    private val onItemClickListener: (User) -> Unit
) : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    private val items = ArrayList<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent.inflate(R.layout.item_user))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun setItems(data: List<User>) {

        val oldItems = items.toList()
        items.clear()
        items.addAll(data)

        DiffUtil
            .calculateDiff(DiffCallback(oldItems, data))
            .dispatchUpdatesTo(this)
    }

    inner class DiffCallback(
        private val oldItems: List<User>,
        private val newItems: List<User>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldItems.size
        override fun getNewListSize(): Int = newItems.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItems[oldItemPosition].id == newItems[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItems[oldItemPosition] == newItems[newItemPosition]
        }
    }

    inner class ViewHolder(
        override val containerView: View
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        private lateinit var user: User

        init {
            containerView.setOnClickListener { onItemClickListener.invoke(user) }
        }

        fun bind(user: User) {
            this.user = user

            nameTextView.text = nameTextView.context.getString(
                R.string.user_name_s_s,
                user.firstName,
                user.lastName
            )

            emailTextView.text = user.email

            Glide.with(avatarImageView.context)
                .load(user.avatarUrl)
                .placeholder(R.drawable.ic_person)
                .into(avatarImageView)
        }
    }
}