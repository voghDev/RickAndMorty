package com.juangomez.rickandmorty.views.base

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.afollestad.materialdialogs.MaterialDialog
import com.juangomez.common.Failure
import com.juangomez.rickandmorty.R

abstract class BaseActivity : AppCompatActivity() {

    abstract val viewModel: BaseViewModel
    abstract val binding: ViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupObservers()
        prepare(intent)
        viewModel.initialState()
    }

    open fun prepare(intent: Intent?) {}

    abstract fun setupObservers()

    abstract fun manageState(state: BaseViewModel.State)

    fun showDefaultError(failure: Failure, retryFunction: (() -> Unit)? = null) {
        when (failure) {
            Failure.NetworkConnection -> basicDialog(
                message = getString(R.string.dialog_network_error_description),
                tryAgainFunction = retryFunction
            )
            else -> basicDialog(message = getString(R.string.dialog_generic_error_description))
        }
    }

    private fun basicDialog(
        title: String = getString(R.string.dialog_generic_error_title),
        message: String,
        tryAgainFunction: (() -> Unit)? = null
    ) {
        MaterialDialog(this).show {
            title(text = title)
            message(text = message)
            positiveButton(text = getString(R.string.dialog_generic_error_try_again_button)) { tryAgainFunction?.invoke() }
        }
    }
}