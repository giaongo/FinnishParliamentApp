package fi.giao.finnishparliamentapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import fi.giao.finnishparliamentapp.R
import fi.giao.finnishparliamentapp.database.ParliamentMember
import fi.giao.finnishparliamentapp.databinding.FragmentMemberInfoBinding
private const val IMG_BASE_URL = "https://avoindata.eduskunta.fi/"
class MemberInfoFragment : Fragment() {
    private lateinit var binding: FragmentMemberInfoBinding
    private val safeArgs: MemberInfoFragmentArgs by navArgs()
    private lateinit var currentMember: ParliamentMember

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentMember = safeArgs.requestedMember
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMemberInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            memberNameTextView.text = getString(
                R.string.member_name_text, currentMember.firstname,
                currentMember.lastname
            )
            memberPartyTextView.text = getString(R.string.party_name_text, currentMember.party)
        }
        Glide.with(this).load(IMG_BASE_URL + currentMember.pictureUrl).into(binding.memberImageView)


    }
}