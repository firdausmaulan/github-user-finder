package com.fd.guf.features.searchUser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.fd.guf.R
import com.fd.guf.base.BaseViewHolder
import com.fd.guf.databinding.AdapterLoadingBinding
import com.fd.guf.databinding.AdapterUserBinding
import com.fd.guf.models.User
import com.fd.guf.utils.State


class UserAdapter : RecyclerView.Adapter<BaseViewHolder>() {

    companion object {
        private const val VIEW_TYPE_LOADING = 0
        private const val VIEW_TYPE_CONTENT = 1
    }

    private var listUser = ArrayList<User>()
    private var listener: Listener? = null

    interface Listener {
        fun onItemClick(user: User)
    }

    fun setListener(listener: Listener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return if (viewType == VIEW_TYPE_CONTENT) {
            val binding: AdapterUserBinding = DataBindingUtil.inflate(
                layoutInflater, R.layout.adapter_user, parent, false
            )
            UserViewHolder(binding)
        } else {
            val loadBinding: AdapterLoadingBinding = DataBindingUtil.inflate(
                layoutInflater, R.layout.adapter_loading, parent, false
            )
            LoadingViewHolder(loadBinding)
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
        holder.itemView.setOnClickListener {
            listener?.onItemClick(listUser[position])
        }
    }

    inner class UserViewHolder(private val binding: AdapterUserBinding) :
        BaseViewHolder(binding.root) {
        override fun onBind(position: Int) {
            super.onBind(position)
            binding.user = listUser[position]
        }
    }

    inner class LoadingViewHolder(loadBinding: AdapterLoadingBinding) :
        BaseViewHolder(loadBinding.root)

    override fun getItemViewType(position: Int): Int {
        return if (listUser[position].id == 0) {
            VIEW_TYPE_LOADING
        } else {
            VIEW_TYPE_CONTENT
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

    private fun getItem(position: Int): User? {
        return if (position >= 0) {
            listUser[position]
        } else {
            return null
        }
    }

    fun clear() {
        listUser.clear()
        notifyDataSetChanged()
    }

    fun add(users: List<User>) {
        for (user in users) {
            if (!listUser.contains(user)) {
                listUser.add(user)
                notifyItemInserted(listUser.size - 1)
            }
        }
    }

    fun setState(state: String) {
        if (state == State.LOAD_MORE) {
            addLoading()
        } else if (state == State.SUCCESS_LOAD_MORE || state == State.ERROR_LOAD_MORE) {
            removeLoading()
        }
    }

    private fun addLoading() {
        listUser.add(User())
        notifyItemInserted(listUser.size - 1)
    }

    private fun removeLoading() {
        val position: Int = listUser.size - 1
        val item: User? = getItem(position)
        listUser.remove(item)
        notifyItemRemoved(position)
    }

}