package fi.giao.finnishparliamentapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import fi.giao.finnishparliamentapp.R
import fi.giao.finnishparliamentapp.databinding.FragmentIntroAppBinding

/**
 * Date: 5/10/2022
 * Name: Giao Ngo
 * Student id: 2112622
 * This fragment is welcoming intro part that contains bottom navigation bar where user can chooses
 * to view either favorite list, party list and statistic
 */
class IntroAppFragment : Fragment() {
    private lateinit var binding: FragmentIntroAppBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIntroAppBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.view_parties -> {
                    findNavController().navigate(R.id.action_introAppFragment_to_partyListFragment)
                    true
                }
                R.id.view_favorite -> {
                    findNavController().navigate(R.id.action_introAppFragment_to_favoriteListFragment)
                    true
                }
                R.id.view_statistic -> {
                    findNavController().navigate(R.id.action_introAppFragment_to_statisticFragment)
                    true
                }
                else -> false
            }
        }
    }
}