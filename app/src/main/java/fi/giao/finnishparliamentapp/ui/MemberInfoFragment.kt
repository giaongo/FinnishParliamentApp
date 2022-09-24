package fi.giao.finnishparliamentapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fi.giao.finnishparliamentapp.R
import fi.giao.finnishparliamentapp.databinding.FragmentMemberInfoBinding

class MemberInfoFragment : Fragment() {
    private lateinit var binding: FragmentMemberInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMemberInfoBinding.inflate(inflater,container,false)
        return binding.root
    }
}