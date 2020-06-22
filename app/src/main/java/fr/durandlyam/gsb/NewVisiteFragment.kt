package fr.durandlyam.gsb

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import fr.durandlyam.gsb.database.GSBDatabase
import fr.durandlyam.gsb.database.Visite
import fr.durandlyam.gsb.databinding.FragmentNewVisiteBinding
import fr.durandlyam.gsb.login.LoginViewModel
import fr.durandlyam.gsb.visites.VisitesViewModel
import fr.durandlyam.gsb.visites.VisitesViewModelFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
/**
 * A simple [Fragment] subclass.
 * Use the [NewVisiteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewVisiteFragment : Fragment() {

    private lateinit var  binding: FragmentNewVisiteBinding
    private val loginViewModel: LoginViewModel by activityViewModels()

    private lateinit var arrayAdapter: ArrayAdapter<String>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_visite, container, false)

        //On récup les ressources pour créer notre viewModel
        val application = requireNotNull(this.activity).application
        val dataSource = GSBDatabase.getInstance(application).visiteDao
        val viewModelFactory = VisitesViewModelFactory(dataSource,loginViewModel.user.value!!.api_token , application)
        //création du viewModel
        val visiteViewModel = ViewModelProvider(this, viewModelFactory).get(VisitesViewModel::class.java)
        //On bind le viewModel avec notre Binding (UI) et le lifeCycle de l'appli
        binding.lifecycleOwner = this
        binding.visiteViewModel = visiteViewModel



        /*binding.buttonEnvoyer.setOnClickListener {
            val visite = Visite(
                date = binding.editTextDateMission.text.toString(),
                motif = binding
            )
        }*/

        return binding.root
    }
}
