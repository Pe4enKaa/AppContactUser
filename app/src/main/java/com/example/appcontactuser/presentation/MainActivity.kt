package com.example.appcontactuser.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.appcontactuser.R
import com.example.appcontactuser.domain.UserItem
import java.lang.RuntimeException

class MainActivity : AppCompatActivity(), UserItemFragment.OnEditingFinishedListener {

    private var screenMode = MODE_UNKNOWN

    private var userItemId = UserItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        parseIntent()

        if (savedInstanceState == null) {
            launchRightMode()
        }
    }

    override fun onEditingFinished() {
        finish()
    }

    private fun launchRightMode() {
        val fragment = when (screenMode) {
            MODE_EDIT -> UserItemFragment.newInstanceEditItem(userItemId)
            MODE_ADD -> UserItemFragment.newInstanceAddItem()
            else -> throw RuntimeException("Unknown mode $screenMode")
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.user_item_container, fragment)
            .commit()
    }

    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_SCREEN_MOD)) {
            throw RuntimeException("Param screen mode is absent")
        }

        val mode = intent.getStringExtra(EXTRA_SCREEN_MOD)
        if (mode != MODE_ADD && mode != MODE_EDIT) {
            throw RuntimeException("Unknown mode $mode")
        }

        screenMode = mode

        if (screenMode == MODE_EDIT) {
            if (!intent.hasExtra(EXTRA_USER_ITEM_ID)) {
                throw RuntimeException("Param user item id is absent")
            }
            userItemId = intent.getIntExtra(EXTRA_USER_ITEM_ID, UserItem.UNDEFINED_ID)
        }
    }

    companion object {
        private const val EXTRA_SCREEN_MOD = "extra_mode"
        private const val EXTRA_USER_ITEM_ID = "extra_user_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""

        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MOD, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, shopItemId: Int): Intent {
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MOD, MODE_EDIT)
            intent.putExtra(EXTRA_USER_ITEM_ID, shopItemId)
            return intent
        }
    }
}