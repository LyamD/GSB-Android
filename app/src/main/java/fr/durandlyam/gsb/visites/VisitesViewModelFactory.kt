package fr.durandlyam.gsb.visites

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import fr.durandlyam.gsb.database.VisiteDao
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class VisitesViewModelFactory(
    private val dataSource: VisiteDao,
    private val api_token: String,
    private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(VisitesViewModel::class.java)) {
            return VisitesViewModel(dataSource, api_token, application) as T
        }
        throw IllegalArgumentException("Unknow viewModel Class")
    }
}