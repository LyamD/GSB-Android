package fr.durandlyam.gsb.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import fr.durandlyam.gsb.Credentials
import fr.durandlyam.gsb.R
import fr.durandlyam.gsb.databinding.FragmentLoginBinding
import kotlin.math.log

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var navController: NavController
    private val loginViewModel: LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_login, container, false
        )

        //On récup les ressources pour le viewModel
        //val application = requireNotNull(this.activity).application
        //val viewModelFactory = LoginViewModelFactory(application)
        //Création du viewModel
        //val loginViewModel = ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)
        //On bind le viewModel avec notre Binding (UI) et le lifeCycle de l'appli
        binding.lifecycleOwner = this
        binding.loginViewModel = loginViewModel


        //Bouton connexion
        binding.buttonLoginConnect.setOnClickListener {
            //Si les champs sont vide on demande à l'user de les remplir
            if (binding.editTextLoginEmail.text.toString().isEmpty() || binding.editTextLoginPassword.text.toString().isEmpty()) {
                Toast.makeText(context, "Veuillez remplir les champs", Toast.LENGTH_LONG).show()
            } else {
                //Sinon on créer un object Credentials et on l'envoie pour se connecter
                val creds = Credentials(binding.editTextLoginEmail.text.toString(), binding.editTextLoginPassword.text.toString())
                loginViewModel.login(creds)
            }
        }

        loginViewModel.loginPhrase.observe(viewLifecycleOwner, Observer {
            val toast = Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        })

        //Observer pour naviguer
        loginViewModel.navigateToMainPage.observe(viewLifecycleOwner, Observer {
            if (it) {
                navController.navigate(R.id.action_loginFragment_to_mainPageFragment)
                loginViewModel.resetNavigate()
            }

        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = view.findNavController()
    }

}
