package fi.giao.finnishparliamentapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

}