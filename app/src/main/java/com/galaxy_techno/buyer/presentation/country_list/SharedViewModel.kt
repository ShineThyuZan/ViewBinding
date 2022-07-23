package com.galaxy_techno.buyer.presentation.country_list

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel: ViewModel() {
    val bundleStateSpinFragmentToCountryListFragment = MutableLiveData<Bundle>()

}