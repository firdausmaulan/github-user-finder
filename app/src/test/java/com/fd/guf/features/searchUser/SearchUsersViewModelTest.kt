package com.fd.guf.features.searchUser

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fd.guf.dataSource.remote.Repository
import com.fd.guf.dataSource.remote.RepositoryCallback
import com.fd.guf.models.Users
import com.fd.guf.utils.State
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.verify
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class SearchUsersViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: Repository


    @Mock
    private lateinit var viewModel: SearchUsersViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = SearchUsersViewModel(repository)
    }

    @Test
    fun `State Success`() {
        // Params
        val q = "firdaus"
        val page = 1
        val response = Users()
        viewModel.searchUsers(q)
        Assert.assertEquals(viewModel.stateLiveData.value, State.LOADING)
        argumentCaptor<RepositoryCallback<Users>>().apply {
            verify(repository).searchUsers(eq(q), eq(page), capture())
            firstValue.onDataLoaded(response)
        }
        Assert.assertEquals(viewModel.stateLiveData.value, State.SUCCESS)
    }

    @Test
    fun `State Success Load More`() {
        // Params
        val q = "firdaus"
        val page = 2
        // Response
        val response = Users()
        viewModel.loadMoreUsers(q, page)
        Assert.assertEquals(viewModel.stateLiveData.value, State.LOAD_MORE)
        argumentCaptor<RepositoryCallback<Users>>().apply {
            verify(repository).searchUsers(eq(q), eq(page), capture())
            firstValue.onDataLoaded(response)
        }
        Assert.assertEquals(viewModel.stateLiveData.value, State.SUCCESS_LOAD_MORE)
    }

    @Test
    fun `State Error`() {
        // Params
        val q = "firdaus"
        val page = 1
        // Response
        val errorMessage = "Error search"
        viewModel.searchUsers(q)
        Assert.assertEquals(viewModel.stateLiveData.value, State.LOADING)
        argumentCaptor<RepositoryCallback<Users>>().apply {
            verify(repository).searchUsers(eq(q), eq(page), capture())
            firstValue.onDataError(errorMessage)
        }
        Assert.assertEquals(viewModel.stateLiveData.value, State.ERROR)
    }

    @Test
    fun `State Error Load More`() {
        // Params
        val q = "firdaus"
        val page = 2
        // Response
        val errorMessage = "Error load more"
        viewModel.loadMoreUsers(q, page)
        Assert.assertEquals(viewModel.stateLiveData.value, State.LOAD_MORE)
        argumentCaptor<RepositoryCallback<Users>>().apply {
            verify(repository).searchUsers(eq(q), eq(page), capture())
            firstValue.onDataError(errorMessage)
        }
        Assert.assertEquals(viewModel.stateLiveData.value, State.ERROR_LOAD_MORE)
    }

    @Test
    fun `State Query Empty`() {
        // Params
        val q = ""
        viewModel.searchUsers(q)
        Assert.assertEquals(viewModel.stateLiveData.value, State.SUCCESS)
    }

    @Test
    fun `State List Empty`() {
        // Params
        val q = "@#$%^&"
        val page = 1
        // Response
        val errorMessage = "Empty list"
        viewModel.searchUsers(q)
        Assert.assertEquals(viewModel.stateLiveData.value, State.LOADING)
        argumentCaptor<RepositoryCallback<Users>>().apply {
            verify(repository).searchUsers(eq(q), eq(page), capture())
            firstValue.onDataError(errorMessage)
        }
        Assert.assertEquals(viewModel.stateLiveData.value, State.ERROR)
    }
}