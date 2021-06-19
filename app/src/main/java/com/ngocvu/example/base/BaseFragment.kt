package com.ngocvu.example.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.ngocvu.example.view.dialog.ProgressDialog
import com.ngocvu.example.view.ui.popular_movie.MainActivity
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.toolbar_with_back_button.*


abstract class BaseFragment : Fragment() {

    private val loadingDialog = ProgressDialog()
    var gson = Gson()
    protected var subscription = CompositeDisposable()

    lateinit var context: AppCompatActivity


    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (subscription.isDisposed) {
            subscription = CompositeDisposable()
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (iv_toolbar_back_arrow != null) {
            iv_toolbar_back_arrow.setOnClickListener {
                onBackPressed()
            }
        }

    }

    override fun onStop() {
        super.onStop()
        subscription.dispose()
    }

    protected open fun onBackPressed() {
        findNavController().navigateUp()
    }

    protected open fun disableDeviceBackButton() {
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    // do nothing

                }
            })
    }

    open fun deviceBackButtonCustomAction(customAction: OnBackPressedCallback) {
        requireActivity().onBackPressedDispatcher.addCallback(this, customAction)
    }

    fun showProgressDialog() {
        loadingDialog.show(context)
    }

    fun dismissProgressDialog() {
        loadingDialog.dismiss()
    }


}