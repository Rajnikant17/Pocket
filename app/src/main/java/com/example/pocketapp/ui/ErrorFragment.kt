package com.example.pocketapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.pocketapp.databinding.FragmentErrorBinding

// TODO: Rename parameter arguments, choose names that match
class ErrorFragment : Fragment() {
    lateinit var navController: NavController
    lateinit var fragmentErrorBinding: FragmentErrorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentErrorBinding = FragmentErrorBinding.inflate(inflater, container, false)
        return fragmentErrorBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        fragmentErrorBinding.btError.setOnClickListener {
            navController.previousBackStackEntry?.savedStateHandle?.set("retry", "retry")
            navController.navigateUp()
        }
    }
}