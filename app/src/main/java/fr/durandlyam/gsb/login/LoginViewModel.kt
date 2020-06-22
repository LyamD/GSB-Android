package fr.durandlyam.gsb.login

import android.app.Application
import android.os.Debug
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import fr.durandlyam.gsb.Credentials
import fr.durandlyam.gsb.User
import fr.durandlyam.gsb.network.VisitesApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel (application: Application) : AndroidViewModel(application) {

    private val _user = MutableLiveData<User>()
    val user : LiveData<User>
        get() = _user

    private val _loginPhrase = MutableLiveData<String>()
    val loginPhrase : LiveData<String>
        get() = _loginPhrase

    private val _navigateToMainPage = MutableLiveData<Boolean>()
    val navigateToMainPage : LiveData<Boolean>
        get() = _navigateToMainPage

    fun login(credentials: Credentials) {
        //Log.i("login", "login ViewModel appelé")
        VisitesApi.retrofitService.getLoginToken(credentials).enqueue(
            object : Callback<User> {
                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.i("login", "Fail : " + t.message)
                    _loginPhrase.value = "Mauvais identifiants (ou erreur)"
                }

                override fun onResponse(call: Call<User>, response: Response<User>) {
                    _user.value = response.body()
                    //Log.i("login", "succès : " + response.body().toString())
                    loginNavigate()
                }
            }
        )
    }

    fun loginNavigate() {
        //Log.i("login", "LoginNavigateViewModel appelé")
        if (user.value == null) {
            _loginPhrase.value = "Mauvais identifiants"
        } else {
            _loginPhrase.value = "Bienvenue " + user.value!!.nom + " " + user.value!!.prenom
            _navigateToMainPage.value = true
        }
    }

    fun resetNavigate() {
        _navigateToMainPage.value = false;
    }

    override fun onCleared() {
        super.onCleared()
    }
}