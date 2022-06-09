package com.example.weatherapp.ui.currentlocation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CurrentLocationViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is current location Fragment"
    }
    val text: LiveData<String> = _text
}