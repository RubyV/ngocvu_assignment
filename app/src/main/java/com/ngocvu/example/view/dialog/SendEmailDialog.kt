package com.ngocvu.example.view.dialog

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.ngocvu.example.R
import com.ngocvu.example.utils.BundleKeys
import com.ngocvu.example.view.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.dialog_new_issue.*
import timber.log.Timber


@AndroidEntryPoint
class SendEmailDialog : DialogFragment() {

    private lateinit var viewModel: SendEmailViewModel

    companion object {
        fun newInstance(): SendEmailDialog {
            val frag = SendEmailDialog()
            val args = Bundle()
//            args.putString(BundleKeys.trxId, trxId)
//            args.putString(BundleKeys.trxType, trxType)
//            frag.arguments = args
            return frag
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.dialog_new_issue, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(SendEmailViewModel::class.java)

        et_set_export_email.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                btn_export.isEnabled = true
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        btn_export.setOnClickListener {
            handleGetCreditCard()
        }

        iv_dialog_testing_back_arrow.setOnClickListener {
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        // Set dialog to be full screen, by default it floating center minor padding
        val window = dialog!!.window
        if (window != null) {
            // kill stupid white background
            window.setBackgroundDrawableResource(android.R.color.transparent)
            val params = window.attributes
            params.width = WindowManager.LayoutParams.MATCH_PARENT
            params.height = WindowManager.LayoutParams.WRAP_CONTENT
            params.gravity = Gravity.CENTER
            window.attributes = params as WindowManager.LayoutParams
            window.setDimAmount(1.0f)
        }
    }

    @SuppressLint("CheckResult")
    private fun handleGetCreditCard() {
        viewModel.createNewIssue( et_set_export_email.text.toString())
        viewModel.newIssue.observe(viewLifecycleOwner) { response ->
            dismiss()
        }


    }
}