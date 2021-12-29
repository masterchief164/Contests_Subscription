package com.example.contestssubscription.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.contestssubscription.data.Site
import com.example.contestssubscription.data.SiteDao
import kotlinx.coroutines.launch

class InventoryViewModel(private val siteDao: SiteDao) : ViewModel() {
    private fun insertSite(site:Site){
        viewModelScope.launch {
            siteDao.insert(site)
        }
    }
    private fun getNewSiteEntry(siteName:String, siteEnabled:Boolean):Site{
        return Site(
            siteName = siteName,
            enabled = siteEnabled
        )
    }
    private fun addNewSite(siteName:String, siteEnabled:Boolean)
    {
        val newSite = getNewSiteEntry(siteName,siteEnabled)
        insertSite(newSite)
    }
}

class InventoryViewModelFactory(private val siteDao: SiteDao):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(InventoryViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return InventoryViewModel(siteDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}