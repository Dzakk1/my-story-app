package com.dicoding.picodiploma.loginwithanimation.view.map

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.loginwithanimation.data.UserRepository
import com.dicoding.picodiploma.loginwithanimation.data.api.ApiConfig
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserModel
import com.dicoding.picodiploma.loginwithanimation.data.response.ListStoryItem
import kotlinx.coroutines.launch

class MapsViewModel (private val repository: UserRepository) : ViewModel() {
    private val _storyLocation = MutableLiveData<List<ListStoryItem>>()
    val storyLocation : LiveData<List<ListStoryItem>> = _storyLocation

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _toast = MutableLiveData<String>()
    val toast : LiveData<String> get() = _toast

    fun setStoryLocation(token: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val stories = ApiConfig.getApiService(token).getStoriesWithLocation()
                if (stories.listStory.isNotEmpty()) {
                    _storyLocation.value = stories.listStory
                } else {
                    _toast.value = "Upps.. Belum ada story yang ditambahkan"
                }
            } catch (e: Exception) {
                Log.e(TAG, e.toString())
            }
            _isLoading.value = false
        }
    }
    companion object {
        private const val TAG = "MapsViewModel"
    }

    fun getSession(): LiveData<UserModel> {
        return repository.getSession().asLiveData()
    }
}