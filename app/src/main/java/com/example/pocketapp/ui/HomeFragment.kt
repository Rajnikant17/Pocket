package com.example.pocketapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apiservicesmodule.models.UsersPostList
import com.example.apiservicesmodule.models.ModelClassGroupedByUserId
import com.example.pocketapp.R
import com.example.pocketapp.adapter.HomePageAdapter
import com.example.pocketapp.databinding.FragmentHomeBinding
import com.example.pocketapp.models.DataState
import com.example.pocketapp.viewmodels.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

private const val CLICKED_POSITION = "clickedPosition"
private const val USER_ID = "user_id"

@AndroidEntryPoint
class HomeFragment : Fragment(), HomePageAdapter.ItemClickInterface {
    val sharedViewModel: SharedViewModel by activityViewModels()
    lateinit var navController: NavController
    var homeItemAdapter: HomePageAdapter? = null
    lateinit var fragmentHomeBinding: FragmentHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedViewModel.callHomePageApi(SharedViewModel.MainStateEvent.GetHomePageEvent)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return fragmentHomeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        recalltheApiOnFailure()
        observeHomePageData()
    }

    fun observeHomePageData() {
        sharedViewModel.mutableLiveDataUsersPostList.observe(
            viewLifecycleOwner,
            Observer { dataState ->
                when (dataState) {
                    is DataState.Success<List<UsersPostList>> -> {
                        fragmentHomeBinding.progressbar.visibility = View.GONE
                        setAdapter(sharedViewModel.getListGroupedByUserId(dataState.data))
                    }
                    is DataState.Error -> {
                        fragmentHomeBinding.progressbar.visibility = View.GONE
                        navController.navigate(R.id.action_homeFragment_to_errorFragment)
                    }
                    is DataState.Loading -> {
                        if (!fragmentHomeBinding.progressbar.isVisible)
                            fragmentHomeBinding.progressbar.visibility = View.VISIBLE
                    }
                }
            })
    }

    fun setAdapter(modelClassGroupedByUserIdList: List<ModelClassGroupedByUserId>) {
        val linearLayoutManager = LinearLayoutManager(requireActivity())
        fragmentHomeBinding.recyclerview.setLayoutManager(linearLayoutManager)
        homeItemAdapter = HomePageAdapter(
            requireActivity(),
            modelClassGroupedByUserIdList,
            this
        )
        fragmentHomeBinding.recyclerview.adapter = homeItemAdapter
    }

    override fun itemClickInterfaceMethod(position: Int) {
        val bundle = Bundle()
        bundle.putInt(
            USER_ID,
            sharedViewModel.modelClassGroupedByUserIdList.get(position).user_id.toInt()
        )
        bundle.putInt(CLICKED_POSITION, position)
        navController.navigate(R.id.action_homeFragment_to_userDetailsFragment, bundle)
    }

    fun recalltheApiOnFailure() {
        val liveData: MutableLiveData<String> =
            navController.currentBackStackEntry?.getSavedStateHandle()
                ?.getLiveData<String>("retry") as MutableLiveData<String>
        liveData.observe(viewLifecycleOwner, object : Observer<String?> {
            override fun onChanged(s: String?) {
                // Do something with the result.
                if (s != null) {
                    sharedViewModel.callHomePageApi(SharedViewModel.MainStateEvent.GetHomePageEvent)
                }
            }
        })
    }
}