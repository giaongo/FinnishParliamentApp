package fi.giao.finnishparliamentapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import fi.giao.finnishparliamentapp.databinding.FragmentFavoriteListBinding
import fi.giao.finnishparliamentapp.viewmodel.FavoriteListViewModel
import fi.giao.finnishparliamentapp.viewmodel.FavoriteListViewModelFactory

class FavoriteListFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteListBinding
    private val viewModel: FavoriteListViewModel by viewModels {
        FavoriteListViewModelFactory(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.print()

        viewModel.listHetekaId.observe(viewLifecycleOwner) {
            val result = viewModel.getTestMember(it)
            result?.sortedBy { it.hetekaId }?.forEach {
                Log.d("TEST", it.firstname)
            }

        }

    }
}