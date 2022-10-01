package fi.giao.finnishparliamentapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import fi.giao.finnishparliamentapp.R
import fi.giao.finnishparliamentapp.databinding.FragmentIntroAppBinding

class IntroAppFragment : Fragment() {
    private lateinit var binding:FragmentIntroAppBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIntroAppBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.view_parties -> {
                    findNavController().navigate(R.id.action_introAppFragment_to_partyListFragment)
                    true
                }
                R.id.view_favorite -> {
                    Toast.makeText(requireContext(),"Heart clicked",Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
    }
}