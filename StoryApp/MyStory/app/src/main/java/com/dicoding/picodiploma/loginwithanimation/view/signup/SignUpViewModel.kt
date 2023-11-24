package com.dicoding.picodiploma.loginwithanimation.view.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.loginwithanimation.data.UserRepository
import com.dicoding.picodiploma.loginwithanimation.data.api.ApiConfig
import kotlinx.coroutines.launch

class SignUpViewModel (private val repository: UserRepository) : ViewModel(){

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    fun signUp(name : String, email : String, password : String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val apiService = ApiConfig.getApiService("")
                val successResponse = apiService.register(name, email, password)
                Log.d(TAG, "Berhasil membuat akun")
            } catch (e: Exception) {
                Log.e(TAG, e.toString())
            }
            _isLoading.value = false

        }

    }

    companion object {
        private const val TAG = "SignUpViewModel"
    }
}