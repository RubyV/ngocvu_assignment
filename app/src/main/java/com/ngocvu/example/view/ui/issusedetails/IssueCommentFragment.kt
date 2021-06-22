package com.ngocvu.example.view.ui.issusedetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.example.IssuesListQuery
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.ngocvu.example.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_issue_comment.*

@AndroidEntryPoint
class IssueCommentFragment : Fragment() {
    var walletTransactionViewPagerTab = -1
    companion object {
        fun newInstance(
            commentArr: ArrayList<IssuesListQuery.Comments>
        ): IssueCommentFragment {
            val IssueCommentFragment = IssueCommentFragment()
            IssueCommentFragment.commentArr = commentArr
            return IssueCommentFragment

        }
    }

    private lateinit var viewModel: IssueCommentViewModel
    private lateinit var navController: NavController
    private var commentArr = ArrayList<IssuesListQuery.Comments>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_issue_comment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(IssueCommentViewModel::class.java)

        val intent = requireActivity().getIntent()
        val message = intent.getStringExtra("message")
        if(!message.isNullOrEmpty()) {
            viewModel.addNewComment(message)
        }
        receiveFcm()
        setUpRecyclerView()
        postComment()
    }
    private fun receiveFcm()
    {

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
        })
    }

    private fun setUpRecyclerView(){
        val adapter = IssueCommentAdapter(requireContext(), commentArr)
        rv_comment.setHasFixedSize(true)
        rv_comment.adapter = adapter

    }

    private fun postComment()
    {
        btn_post_comment.setOnClickListener {
            val content: String = et_comment_body.getText().toString()
            viewModel.addNewComment(content)
        }
    }
}