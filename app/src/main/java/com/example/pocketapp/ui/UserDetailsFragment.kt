package com.example.pocketapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apiservicesmodule.models.UserDetailsApiResponse
import com.example.pocketapp.R
import com.example.pocketapp.adapter.UserDetailsAdapter
import com.example.pocketapp.databinding.FragmentUserDetailsBinding
import com.example.pocketapp.models.DataState
import com.example.pocketapp.viewmodels.SharedViewModel

private const val CLICKED_POSITION = "clickedPosition"
private const val USER_ID = "user_id"

class UserDetailsFragment : Fragment() {
    val sharedViewModel: SharedViewModel by activityViewModels()
    lateinit var navController: NavController
    private var itemClickedPosition: Int? = null
    private var userId: Int? = null
    lateinit var fragmentUserDetailsBinding: FragmentUserDetailsBinding
    var userDetailsAdapter: UserDetailsAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            itemClickedPosition = it.getInt(CLICKED_POSITION)
            userId = it.getInt(USER_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentUserDetailsBinding = FragmentUserDetailsBinding.inflate(inflater, container, false)
        return fragmentUserDetailsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        navController.previousBackStackEntry?.savedStateHandle?.set("retry", null)
        userId?.let {
            sharedViewModel.getUserDetails(SharedViewModel.MainStateEvent.GetUserDetailsEvent, it)
        }
        fragmentUserDetailsBinding.ivBack.setOnClickListener {
            navController.navigateUp()
        }
        observeUsersData()
    }

    fun observeUsersData() {
        sharedViewModel.mutableLivedataObserUserdetails.observe(
            viewLifecycleOwner,
            Observer { dataState ->
                when (dataState) {
                    is DataState.Success<UserDetailsApiResponse> -> {
                        fragmentUserDetailsBinding.progressbar.visibility = View.GONE
                        setAdapter(dataState.data)
                    }
                    is DataState.Error -> {
                        fragmentUserDetailsBinding.progressbar.visibility = View.GONE
                        navController.navigate(R.id.action_userDetailsFragment_to_errorFragment)
                    }
                    is DataState.Loading -> {
                        if (!fragmentUserDetailsBinding.progressbar.isVisible)
                            fragmentUserDetailsBinding.progressbar.visibility = View.VISIBLE
                    }
                }
            })
    }

    fun setAdapter(userEmailAndName: UserDetailsApiResponse) {
        val linearLayoutManager = LinearLayoutManager(requireActivity())
        fragmentUserDetailsBinding.recyclerview.setLayoutManager(linearLayoutManager)
        userDetailsAdapter = UserDetailsAdapter(
            requireActivity(),
            userEmailAndName,
            sharedViewModel.modelClassGroupedByUserIdList.get(itemClickedPosition!!).homepageApiResposeList
        )
        fragmentUserDetailsBinding.recyclerview.adapter = userDetailsAdapter
    }
}