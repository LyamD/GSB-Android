package fr.durandlyam.gsb.visites

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import fr.durandlyam.gsb.AdapterVisite
import fr.durandlyam.gsb.R
import fr.durandlyam.gsb.database.GSBDatabase
import fr.durandlyam.gsb.databinding.FragmentListeVisitesBinding
import fr.durandlyam.gsb.login.LoginViewModel


class ListeVisitesFragment : Fragment() {

    private lateinit var binding: FragmentListeVisitesBinding
    private val loginViewModel: LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_liste_visites, container, false)

        //On récup les ressources pour créer notre viewModel
        val application = requireNotNull(this.activity).application
        val dataSource = GSBDatabase.getInstance(application).visiteDao
        val viewModelFactory = VisitesViewModelFactory(dataSource,loginViewModel.user.value!!.api_token , application)
        //création du viewModel
        val visiteViewModel = ViewModelProvider(this, viewModelFactory).get(VisitesViewModel::class.java)
        //On bind le viewModel avec notre Binding (UI) et le lifeCycle de l'appli
        binding.lifecycleOwner = this
        binding.visiteViewModel = visiteViewModel

        // On s'occupe de l'adapter
        val adapterVisite = AdapterVisite()
        binding.recyclerViewListeVisite.adapter = adapterVisite

        visiteViewModel.visites.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapterVisite.submitList(it)
            }
        })

        visiteViewModel.reponse.observe(viewLifecycleOwner, Observer {
            Log.d("api", it);
        })

        return binding.root
    }


}
