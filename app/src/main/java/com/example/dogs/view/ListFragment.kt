package com.example.dogs.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.dogs.R
import kotlinx.android.synthetic.main.fragment_list.*
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [ListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonDetails.setOnClickListener {

            //to send args to another Fragment put in bundle then add bundle to navigate below
//            var bundle: Bundle? = null
//            bundle?.putInt("dogUuid", 5)

            //to navigate to another fragment using R
//            findNavController().navigate(R.id.actionDetailFragment, bundle)

            //nav to another fragment using action
            val action = ListFragmentDirections.actionDetailFragment()
            action.dogUuid = 5
            Navigation.findNavController(it).navigate(action)
        }
    }

    companion object {

    }
}