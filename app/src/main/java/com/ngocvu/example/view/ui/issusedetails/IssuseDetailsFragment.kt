package com.ngocvu.example.view.ui.issusedetails

import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent.getIntent
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.setFragmentResultListener
import com.example.IssuesListQuery
import com.google.android.gms.tasks.OnCompleteListener
import com.ngocvu.example.R
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class IssuseDetailsFragment : Fragment() {


    private lateinit var viewModel: IssuseDetailsViewModel
    private var res = ArrayList<IssuesListQuery.Comments>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // [END handle_data_extras]
        return inflater.inflate(R.layout.fragment_issuse_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(IssuseDetailsViewModel::class.java)
        setFragmentResultListener("requestKey") { requestKey, bundle ->
            // We use a String here, but any type that can be put in a Bundle is supported
            var results = bundle.get("bundleKey") as ArrayList<IssuesListQuery.Comments>
            results.forEach {
                res.add(it)
                Log.d("Git2", it.toString())
            }
        }
        val intent = requireActivity().getIntent()
        val message = intent.getStringExtra("message")
        if(!message.isNullOrEmpty()) {
            viewModel.addNewComment(message)
        }
        receiveFcm()
    }


    private fun receiveFcm(){
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("Git", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            // Get new FCM registration token
            val token = task.result

            // Log and toast
            val msg = getString(R.string.msg_token_fmt, token)
            Log.d("Git", msg)
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        })

    }

}