package com.fd.guf.features.searchUser

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fd.guf.R
import com.fd.guf.base.BaseActivity
import com.fd.guf.custom.action.DelayedTextWatcher
import com.fd.guf.custom.action.PaginationListener
import com.fd.guf.dataSource.remote.Repository
import com.fd.guf.databinding.ActivitySearchUserBinding
import com.fd.guf.models.User
import com.fd.guf.utils.Constants
import com.fd.guf.utils.KeyboardUtil
import com.fd.guf.utils.State


class SearchUsersActivity : BaseActivity() {

    private lateinit var binding: ActivitySearchUserBinding
    private lateinit var viewModel: SearchUsersViewModel
    private val userAdapter = UserAdapter()
    private var q = ""
    private var isLoadMore = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_user)
        viewModel = ViewModelProvider(this, SearchUsersFactory(Repository()))
            .get(SearchUsersViewModel::class.java)
        initView()
        initAction()
    }

    private fun initView() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
        binding.rvUser.adapter = userAdapter
        viewModel.stateLiveData.observe(this, Observer { state ->
            binding.state = state
            isLoadMore = state == State.LOAD_MORE || state == State.LOADING
            if (q.isNotEmpty()) KeyboardUtil.hide(binding.container)
        })
        viewModel.usersLiveData.observe(this, Observer { users ->
            userAdapter.add(users)
        })
        viewModel.errorLiveData.observe(this, Observer { error ->
            binding.errorMessage = error
            if (binding.state == State.ERROR_LOAD_MORE) {
                showErrorMessage(error)
            }
        })
        binding.etSearch.requestFocus()
    }

    private fun initAction() {
        val searchTextWatcher = DelayedTextWatcher(this)
        binding.etSearch.addTextChangedListener(searchTextWatcher)
        searchTextWatcher.setListener(object : DelayedTextWatcher.Listener {
            override fun onTextChanged(data: String) {
                userAdapter.clear()
                q = data
                viewModel.searchUsers(q, 1)
            }
        })

        binding.rvUser.addOnScrollListener(object :
            PaginationListener(binding.rvUser.layoutManager) {
            override fun loadMoreItems() {
                viewModel.searchUsers(q, userAdapter.itemCount / Constants.PER_PAGE + 1)
            }

            override val isLoading: Boolean
                get() = isLoadMore
        })

        userAdapter.setListener(object : UserAdapter.Listener {
            override fun onItemClick(user: User) {
                openWebView(user.htmlUrl)
            }
        })
    }

    private fun openWebView(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

}