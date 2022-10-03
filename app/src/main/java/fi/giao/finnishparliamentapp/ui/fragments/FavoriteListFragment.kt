package fi.giao.finnishparliamentapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import fi.giao.finnishparliamentapp.adapter.FavoriteAdapter
import fi.giao.finnishparliamentapp.databinding.FragmentFavoriteListBinding
import fi.giao.finnishparliamentapp.viewmodel.FavoriteListViewModel
import fi.giao.finnishparliamentapp.viewmodel.FavoriteListViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

        val favoriteAdapter = FavoriteAdapter(
            requireContext(),
            viewMoreListener = {
                val action = FavoriteListFragmentDirections.actionFavoriteListFragmentToMemberInfoFragment(it)
                view.findNavController().navigate(action)
            },
            unMarkListener = {
                viewModel.unMarkFavorite(it.hetekaId)
            }
        )
        binding.favoriteListRecyclerView.apply {
            adapter = favoriteAdapter
            layoutManager = GridLayoutManager(requireContext(),2)
        }
        viewModel.listHetekaId.observe(viewLifecycleOwner) {
            viewModel.getFavoriteMemberList(it)
        }
        viewModel.favoriteList.observe(viewLifecycleOwner) {
            favoriteAdapter.submitList(it)
        }


    }
}