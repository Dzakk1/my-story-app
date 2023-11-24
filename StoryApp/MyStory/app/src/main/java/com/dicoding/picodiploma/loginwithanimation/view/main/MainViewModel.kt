package com.dicoding.picodiploma.loginwithanimation.view.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.dicoding.picodiploma.loginwithanimation.data.UserRepository
import com.dicoding.picodiploma.loginwithanimation.data.api.ApiConfig
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserModel
import com.dicoding.picodiploma.loginwithanimation.data.response.ListStoryItem
import kotlinx.coroutines.launch


class MainViewModel(private val repository: UserRepository) : ViewModel() {
    private val _story = MutableLiveData<List<ListStoryItem>>()
    val story : LiveData<PagingData<ListStoryItem>> =
        repository.getStory().cachedIn(viewModelScope)

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _toast = MutableLiveData<String>()
    val toast : LiveData <String> get() = _toast

    fun setStory(token: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val stories = ApiConfig.getApiService(token).getStories()
                if (stories.listStory.isNotEmpty()) {
                    _story.value = stories.listStory
                } else {
                    _toast.value = "Upss, belum ada story yang ditambahkan"
                }
            } catch (e: Exception) {
                Log.e(TAG, e.toString())
            }
            _isLoading.value = false
        }
    }

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}