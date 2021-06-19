package com.ngocvu.example.view.ui.issusedetails

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ngocvu.example.R

class IssuseDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = IssuseDetailsFragment()
    }

    private lateinit var viewModel: IssuseDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_issuse_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(IssuseDetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}