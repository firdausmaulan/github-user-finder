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
import com.fd.guf.base.BaseApp
import com.fd.guf.custom.action.DelayedTextWatcher
import com.fd.guf.custom.action.PaginationListener
import com.fd.guf.dataSource.remote.Repository
import com.fd.guf.databinding.ActivitySearchUserBinding
import com.fd.guf.models.User
import com.fd.guf.utils.KeyboardUtil
import com.fd.guf.utils.State
import javax.inject.Inject

class SearchUsersActivity : BaseActivity() {

    @Inject
    lateinit var repository: Repository

    private lateinit var binding: ActivitySearchUserBinding
    private lateinit var viewModel: SearchUsersViewModel
    private val userAdapter = UserAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as BaseApp).getComponent().inject(this)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_user)
        viewModel = ViewModelProvider(this, SearchUsersFactory(repository))
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
            userAdapter.setState(state)
            if (viewModel.query.isNotEmpty()) KeyboardUtil.hide(binding.container)
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
                if (data != viewModel.query) {
                    userAdapter.clear()
                    viewModel.searchUsers(data)
                }
            }
        })

        binding.rvUser.addOnScrollListener(object :
            PaginationListener(binding.rvUser.layoutManager) {
            override fun loadMoreItems() {
                if (viewModel.hasNexPage()) {
                    viewModel.loadMoreUsers(viewModel.query)
                }
            }

            override val isLoading: Boolean
                get() = viewModel.stateLiveData.value.equals(State.LOAD_MORE)

            override val hasNextPage: Boolean
                get() = userAdapter.itemCount < viewModel.totalCount
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