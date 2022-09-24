package fi.giao.finnishparliamentapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fi.giao.finnishparliamentapp.R
import fi.giao.finnishparliamentapp.databinding.FragmentPartyListBinding

class PartyListFragment : Fragment() {
    private lateinit var binding: FragmentPartyListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPartyListBinding.inflate(inflater,container,false)
        return binding.root
    }

}