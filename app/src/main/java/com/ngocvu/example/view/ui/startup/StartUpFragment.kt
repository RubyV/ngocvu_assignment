package com.ngocvu.example.view.ui.startup

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ngocvu.example.R
import kotlinx.android.synthetic.main.fragment_start_up.*

class StartUpFragment : Fragment() {

    private lateinit var viewModel: StartUpViewModel
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_start_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(StartUpViewModel::class.java)
        navController = Navigation.findNavController(view)
        initView()

    }
    fun initView() {
        iv_start_up_background.setImageResource(R.drawable.bg_login);
        btn_start_up_login.setOnClickListener {
            navController.navigate(R.id.action_startUpFragment_to_logInFragment)
        }
    }

}