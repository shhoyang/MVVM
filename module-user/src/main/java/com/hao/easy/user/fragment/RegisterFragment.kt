package com.hao.easy.user.fragment

import android.text.TextUtils
import androidx.navigation.Navigation
import com.hao.easy.user.R
import com.hao.easy.user.databinding.UserFragmentRegisterBinding
import com.hao.easy.user.viewmodel.RegisterViewModel
import com.mvvm.annotation.AndroidEntryPoint
import com.mvvm.extensions.addTextChangedListener
import com.mvvm.extensions.hideSoftInput
import com.mvvm.extensions.showError
import com.mvvm.ui.BaseFragment

/**
 * @author Yang Shihao
 */
@AndroidEntryPoint
class RegisterFragment : BaseFragment<UserFragmentRegisterBinding, RegisterViewModel>() {

    override fun initView() {
        activity?.setTitle(R.string.user_register)
        viewBinding {
            editTextUsername.addTextChangedListener(textInputUsername)
            editTextPassword.addTextChangedListener(textInputPassword)
            editTextConfirmPassword.addTextChangedListener(textInputConfirmPassword)
            buttonRegister.setOnClickListener {
                register()
            }
            textLogin.setOnClickListener {
                Navigation.findNavController(root).popBackStack()
            }
        }
    }

    private fun register() {
        viewBinding {
            val username = editTextUsername.text.toString().trim()
            val password = editTextPassword.text.toString().trim()
            val confirmPassword = editTextConfirmPassword.text.toString().trim()
            if (username.length < 6) {
                textInputUsername.showError(R.string.user_username_rule)
                return@viewBinding
            }
            if (TextUtils.isEmpty(password)) {
                textInputPassword.showError(R.string.user_password_rule)
                return@viewBinding
            }
            if (TextUtils.isEmpty(confirmPassword)) {
                textInputConfirmPassword.showError(R.string.user_confirm_password_rule)
                return@viewBinding
            }
            if (password != confirmPassword) {
                textInputConfirmPassword.showError(R.string.user_password_not_equals)
                return@viewBinding
            }
            viewModel {
                register(username, password)
            }
            hideSoftInput()
        }
    }

    override fun initData() {
        viewModel {
            registerLiveData.observe(this@RegisterFragment) {
                parentFragmentManager.popBackStack()
            }
        }
    }
}
