package com.example.appcontactuser.presentation

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.appcontactuser.R
import com.example.appcontactuser.databinding.UserItemFragmentBinding
import com.example.appcontactuser.domain.UserItem

class UserItemFragment: Fragment() {

    private lateinit var viewModel: UserItemViewModel

    private lateinit var binding: UserItemFragmentBinding

    private lateinit var onEditingFinishedListener: OnEditingFinishedListener

    private var screenMode = MODE_UNKNOWN
    private var userItemId = UserItem.UNDEFINED_ID

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is OnEditingFinishedListener) {
            onEditingFinishedListener = context
        } else {
            throw RuntimeException("Activity must implement OnEditingFinishedListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = UserItemFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addTextChangeListeners()

        launchRightMode()

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.errorInputPhoneNumber.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_phone_number)
            } else {
                null
            }
            binding.tilPhoneNumber.error = message
        }

        viewModel.errorInputName.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_name)
            } else {
                null
            }
            binding.tilName.error = message
        }

        viewModel.errorInputSurname.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_surname)
            } else {
                null
            }
            binding.tilSurname.error = message
        }

        viewModel.errorInputPatronymic.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_patronymic)
            } else {
                null
            }
            binding.tilPatronymic.error = message
        }

        viewModel.errorInputDataOfBirth.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_data_of_birth)
            } else {
                null
            }
            binding.tilDataOfBirth.error = message
        }

        viewModel.errorInputEmail.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_email)
            } else {
                null
            }
            binding.tilEmail.error = message
        }

        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            onEditingFinishedListener.onEditingFinished()
        }
    }

    private fun launchRightMode() {
        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }

    private fun addTextChangeListeners() {

        binding.etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        binding.etSurname.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputSurname()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        binding.etPatronymic.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputPatronymic()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        binding.etDataOfBirth.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputDataOfBirth()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        binding.etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputEmail()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        binding.etPhoneNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputPhoneNumber()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
    }

    private fun launchAddMode() {
        binding.saveButton.setOnClickListener {
            viewModel.addUserItem(
                binding.etSurname.text.toString(),
                binding.etName.text.toString(),
                binding.etPatronymic.text?.toString(),
                binding.etDataOfBirth.text?.toString(),
                binding.etEmail.text?.toString(),
                binding.etPhoneNumber.text?.toString(),
            )
        }
    }

    private fun launchEditMode() {
        viewModel.getShopItem(userItemId)

        viewModel.userItem.observe(viewLifecycleOwner) {
            binding.etName.setText(it.name)
            binding.etPhoneNumber.setText(it.phoneNumber.toString())
        }

        binding.saveButton.setOnClickListener {
            viewModel.editUserItem(binding.etSurname.text.toString(),
                binding.etName.text.toString(),
                binding.etPatronymic.text?.toString(),
                binding.etDataOfBirth.text?.toString(),
                binding.etEmail.text?.toString(),
                binding.etPhoneNumber.text?.toString(),)
        }
    }

    private fun parseParams() {
        val args = requireArguments()
        if (!args.containsKey(SCREEN_MOD)) {
            throw RuntimeException("Param screen mode is absent")
        }

        val mode = args.getString(SCREEN_MOD)
        if (mode != MODE_ADD && mode != MODE_EDIT) {
            throw RuntimeException("Unknown mode $mode")
        }

        screenMode = mode

        if (screenMode == MODE_EDIT) {
            if (!args.containsKey(USER_ITEM_ID)) {
                throw RuntimeException("Param user item id is absent")
            }
            userItemId =
                args.getInt(USER_ITEM_ID, UserItem.UNDEFINED_ID)
        }
    }

    interface OnEditingFinishedListener {
        fun onEditingFinished()
    }

    companion object {
        private const val SCREEN_MOD = "extra_mode"
        private const val USER_ITEM_ID = "extra_user_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""

        fun newInstanceAddItem(): UserItemFragment {
            return UserItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MOD, MODE_ADD)
                }
            }
        }

        fun newInstanceEditItem(shopItemId: Int): UserItemFragment {
            return UserItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MOD, MODE_EDIT)
                    putInt(USER_ITEM_ID, shopItemId)
                }
            }
        }
    }
}