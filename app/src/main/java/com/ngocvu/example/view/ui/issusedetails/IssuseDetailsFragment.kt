package com.ngocvu.example.view.ui.issusedetails

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import com.example.IssuesListQuery
import com.ngocvu.example.R

class IssuseDetailsFragment : Fragment() {


    private lateinit var viewModel: IssuseDetailsViewModel
    private var res = ArrayList<IssuesListQuery.Comments>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_issuse_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(IssuseDetailsViewModel::class.java)
        setFragmentResultListener("requestKey") { requestKey, bundle ->
            // We use a String here, but any type that can be put in a Bundle is supported
            res = bundle.get("bundleKey") as ArrayList<IssuesListQuery.Comments>
            res.forEach {
                Log.d("Git2", it.toString())
            }



            // Do something with the result

        }
    }

}