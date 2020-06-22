package fr.durandlyam.gsb.visites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import fr.durandlyam.gsb.Credentials
import fr.durandlyam.gsb.Practiciens
import fr.durandlyam.gsb.database.Visite
import fr.durandlyam.gsb.database.VisiteDao
import fr.durandlyam.gsb.network.VisitesApi
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VisitesViewModel(
    val dataSource: VisiteDao ,
    val api_token: String,
    application: Application): AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var lastVisite = MutableLiveData<Visite?>()

    private val _visites = MutableLiveData<List<Visite>>()
    val visites : LiveData<List<Visite>>
        get() = _visites

    private val _reponse = MutableLiveData<String>();
    val reponse : LiveData<String>
        get() = _reponse

    private val _practiciens = MutableLiveData<List<Practiciens>>()
    val practiciens : LiveData<List<Practiciens>>
         get() = _practiciens

    init {
        //initializeLastVisite()
        getVisitesparAPI(api_token)
    }

    fun sendVisite(date: String, motif: String, bilan: String, practiciensID : Int) {
        val visite = Visite(date = "2020-10-11", motif = "le motif", bilan = "ahuuiii", utilisateursNom = "1", visiteurNom = "1")
    }

    fun SendVisiteAPI(visite: Visite) {
        VisitesApi.retrofitService.createVisite(visite).enqueue(
            object : Callback<Visite> {
                override fun onFailure(call: Call<Visite>, t: Throwable) {
                    _reponse.value = "Dommage : " + t.message
                }

                override fun onResponse(call: Call<Visite>, response: Response<Visite>) {
                    _reponse.value = response.message()
                }

            }
        )
        getVisitesparAPI(api_token)
    }

    private fun getVisitesparAPI(token : String) {
        VisitesApi.retrofitService.getVisites(token).enqueue(
            object : Callback<List<Visite>> {
                override fun onFailure(call: Call<List<Visite>>, t: Throwable) {
                    _reponse.value = "Fail : " + t.message
                }

                override fun onResponse(call: Call<List<Visite>>, response: Response<List<Visite>>) {
                   _visites.value = response.body()
                }

            }
        )
    }

    private fun GetPracticiensAPI(token: String) {
        VisitesApi.retrofitService.getPracticiens(token).enqueue(
            object : Callback<List<Practiciens>> {
                override fun onFailure(call: Call<List<Practiciens>>, t: Throwable) {
                    _reponse.value = "Fail : " + t.message
                }

                override fun onResponse(call: Call<List<Practiciens>>, response: Response<List<Practiciens>>) {
                    _practiciens.value = response.body()
                }

            }
        )
    }

    //Requete BDD locale

    fun newVisite() {
        uiScope.launch {
            val newVisite = Visite(date = "2020-10-11", motif = "le motif", bilan = "ahuuiii", utilisateursNom = "1", visiteurNom = "1")
            insert(newVisite)
            lastVisite.value = getLastVisiteFromDB()
        }
    }

    private fun initializeLastVisite() {
        uiScope.launch {
            lastVisite.value = getLastVisiteFromDB()
        }
    }

    private suspend fun getLastVisiteFromDB() : Visite? {
        return withContext(Dispatchers.IO) {
            val visite = dataSource.getLastVisite()
            visite
        }
    }

    private suspend fun insert(visite: Visite) {
        withContext(Dispatchers.IO) {
            dataSource.insert(visite)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}