package com.fd.guf.features.searchUser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.fd.guf.R
import com.fd.guf.databinding.AdapterUserBinding
import com.fd.guf.models.User

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private var users = ArrayList<User>()
    private var listener : Listener? = null

    interface Listener{
        fun onItemClick(user: User)
    }

    fun setListener(listener: Listener){
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: AdapterUserBinding = DataBindingUtil.inflate(
            layoutInflater, R.layout.adapter_user, parent, false
        )
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position])
        holder.itemView.setOnClickListener {
            listener?.onItemClick(users[position])
        }
    }

    inner class UserViewHolder(private val binding: AdapterUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.user = user
        }
    }

    override fun getItemCount(): Int {
        return users.size
    }

    fun clear() {
        users.clear()
        notifyDataSetChanged()
    }

    fun add(users: List<User>) {
        for (user in users) {
            this.users.add(user)
            notifyItemInserted(users.size - 1)
        }
    }
}