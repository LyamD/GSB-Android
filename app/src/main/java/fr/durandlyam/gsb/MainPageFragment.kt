package fr.durandlyam.gsb

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.navGraphViewModels
import fr.durandlyam.gsb.databinding.FragmentMainPageBinding
import fr.durandlyam.gsb.login.LoginViewModel
import fr.durandlyam.gsb.login.LoginViewModelFactory


/**
 * A simple [Fragment] subclass.
 * Use the [MainPageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainPageFragment : Fragment() {

    private lateinit var binding: FragmentMainPageBinding
    private val loginViewModel: LoginViewModel by activityViewModels()
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate<FragmentMainPageBinding>(inflater, R.layout.fragment_main_page, container, false)


        //val application = requireNotNull(this.activity).application
        //val viewModelFactory = LoginViewModelFactory(application)
        //Création du viewModel
        //val loginViewModel = //ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)
        //On bind le viewModel avec notre Binding (UI) et le lifeCycle de l'appli
        binding.lifecycleOwner = this
        binding.loginViewModel = loginViewModel

        val text = "Bonjour " + loginViewModel.user.value!!.prenom + " " + loginViewModel.user.value!!.nom
        binding.textViewAcceuilMain.text = text

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = view.findNavController()

        binding.buttonGotoListeVisite.setOnClickListener {
            navController.navigate(R.id.action_mainPageFragment_to_listeVisitesFragment)
        }

        binding.buttonGotoNewVisite.setOnClickListener {
            navController.navigate(R.id.action_mainPageFragment_to_newVisiteFragment)
        }
    }

}
