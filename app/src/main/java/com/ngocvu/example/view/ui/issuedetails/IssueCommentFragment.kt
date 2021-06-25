package com.ngocvu.example.view.ui.issuedetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.fragment.IssuesFragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.ngocvu.example.R
import com.ngocvu.example.databinding.FragmentIssueCommentBinding
import com.ngocvu.example.view.state.ViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_issue_comment.*

@AndroidEntryPoint
class IssueCommentFragment : Fragment() {
    var walletTransactionViewPagerTab = -1
    companion object {
        fun newInstance(
            commentArr: ArrayList<IssuesFragment.Node>,
            issueId: String
        ): IssueCommentFragment {
            val IssueCommentFragment = IssueCommentFragment()
            IssueCommentFragment.commentArr = commentArr
            IssueCommentFragment.issueId = issueId
            return IssueCommentFragment

        }
    }

    private lateinit var viewModel: IssueCommentViewModel
    private lateinit var binding: FragmentIssueCommentBinding
    private var commentArr = ArrayList<IssuesFragment.Node>()
    private lateinit var issueId: String
    private val issueCommentAdapter by lazy { IssueCommentAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentIssueCommentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(IssueCommentViewModel::class.java)
        binding.rvComment.adapter = issueCommentAdapter
        val intent = requireActivity().getIntent()
        val message = intent.getStringExtra("message")
        if(!message.isNullOrEmpty()) {
            viewModel.addNewComment(message, issueId)
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
        issueCommentAdapter.submitList(commentArr)
    }

    private fun postComment()
    {
        btn_post_comment.setOnClickListener {
            val content: String = et_comment_body.getText().toString()
            viewModel.addNewComment(content, issueId)
            viewModel.addComment.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is ViewState.Loading -> {
                        binding.rvComment.visibility = View.GONE
                        binding.issuesCommentFetchProgress.visibility = View.VISIBLE
                    }
                    is ViewState.Success -> {
                        binding.rvComment.visibility = View.VISIBLE
                        binding.issuesCommentFetchProgress.visibility = View.GONE

                        Log.d("Git", response.value?.data?.addComment.toString())




                    }
                    is ViewState.Error -> {
                        issueCommentAdapter.submitList(emptyList())
                        binding.issuesCommentFetchProgress.visibility = View.GONE
                        binding.rvComment.visibility = View.GONE
                        binding.commentEmptyText.visibility = View.VISIBLE
                    }
                }
            }
        }
    }
}