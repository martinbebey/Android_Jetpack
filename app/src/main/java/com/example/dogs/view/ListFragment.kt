package com.example.dogs.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.MenuProvider
import androidx.lifecycle.Observer
import androidx.lifecycle.VIEW_MODEL_STORE_OWNER_KEY
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.dogs.R
import kotlinx.android.synthetic.main.fragment_list.*
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dogs.viewmodel.ListViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [ListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListFragment : Fragment() {

    private lateinit var viewModel: ListViewModel
    private val dogsListAdapter = DogsListAdapter(arrayListOf())
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewModel.refresh()

        dogsList.apply{
            layoutManager = LinearLayoutManager(context) //could use grid layout manager
            adapter = dogsListAdapter
        }

        refreshLayout.setOnRefreshListener {
            dogsList.visibility = View.GONE
            listError.visibility = View.GONE
            loadingView.visibility = View.VISIBLE
            viewModel.refreshBypassCache()
            refreshLayout.isRefreshing = false
        }

        observeViewModel()
//        buttonDetails.setOnClickListener {

            //to send args to another Fragment put in bundle then add bundle to navigate below
//            var bundle: Bundle? = null
//            bundle?.putInt("dogUuid", 5)

            //to navigate to another fragment using R
//            findNavController().navigate(R.id.actionDetailFragment, bundle)

            //nav to another fragment using action
//            val action = ListFragmentDirections.actionDetailFragment()
//            action.dogUuid = 5
//            Navigation.findNavController(it).navigate(action)
//        }
    }

    fun observeViewModel(){
        viewModel.dogs.observe(viewLifecycleOwner, Observer {
            it?.let{
                dogsList.visibility = View.VISIBLE
                dogsListAdapter.updateDogList(it)
            }

        })

        viewModel.dogsLoadError.observe(viewLifecycleOwner, Observer {
            it?.let{
                listError.visibility = if(it) View.VISIBLE else View.GONE
            }

        })

        viewModel.loading.observe(viewLifecycleOwner, Observer {
            it?.let{
                loadingView.visibility = if (it) View.VISIBLE else View.GONE

                if(it){
                    listError.visibility = View.GONE
                    dogsList.visibility = View.GONE
                }
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.actionSettings -> {
                view?.let {Navigation.findNavController(it).navigate(ListFragmentDirections.actionSettings())}
            }
        }

        return super.onContextItemSelected(item)
    }
    companion object {

    }
}