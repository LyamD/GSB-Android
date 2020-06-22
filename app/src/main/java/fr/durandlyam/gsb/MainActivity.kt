package fr.durandlyam.gsb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import fr.durandlyam.gsb.login.LoginViewModel
import fr.durandlyam.gsb.login.LoginViewModelFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val application = requireNotNull(this).application
        //val viewModelFactory = LoginViewModelFactory(application)
        //Création du viewModel
        //val loginViewModel = ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)
    }
}
