package com.example.appcontactuser.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.appcontactuser.R
import com.example.appcontactuser.presentation.UserItemActivity.Companion.newIntentAddItem
import com.example.appcontactuser.presentation.UserItemActivity.Companion.newIntentEditItem
import com.example.appcontactuser.presentation.adapters.UserListAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), UserItemFragment.OnEditingFinishedListener {

    private lateinit var userListAdapter: UserListAdapter

    private val viewModel: MainViewModel by viewModels()

    private var shopItemContainer: FragmentContainerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()

        setupRecycler()

        setupClickListener()

        dataInsert()

        addItem()
    }

    private fun initViews() {
        shopItemContainer = findViewById(R.id.user_item_fragment_container)
    }


    private fun addItem() {
        val buttonAddUserItem = findViewById<FloatingActionButton>(R.id.button_add_user_item)
        buttonAddUserItem.setOnClickListener {
            if (isOnePaneMode()) {
                val intent = newIntentAddItem(this)
                startActivity(intent)
            } else {
                launchFragment(UserItemFragment.newInstanceAddItem())
            }
        }
    }

    private fun setupClickListener() {
        userListAdapter.onUserItemClickListener = {

            if (isOnePaneMode()) {
                val intent = newIntentEditItem(this, it.id)
                startActivity(intent)
            } else {
                launchFragment(UserItemFragment.newInstanceEditItem(it.id))
            }
        }
    }

    private fun isOnePaneMode(): Boolean {
        return shopItemContainer == null
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager .popBackStack()
        supportFragmentManager.beginTransaction()
            .replace(R.id.user_item_fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun dataInsert() {
        viewModel.userList.observe(this) {
            userListAdapter.submitList(it)
        }
    }

    private fun setupRecycler() {
        val rvUserList = findViewById<RecyclerView>(R.id.rv_user_list)

        with(rvUserList) {
            userListAdapter = UserListAdapter()
            adapter = userListAdapter

            recycledViewPool.setMaxRecycledViews(
                UserListAdapter.VIEW_TYPE_LAYOUT,
                UserListAdapter.MAX_POOL_SIZE
            )
        }

        setupSwipeListener(rvUserList)
    }

    private fun setupSwipeListener(rvUserList: RecyclerView) {
        val callback = object: ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                val item = userListAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteUserList(item)
            }
        }

        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvUserList)
    }

    override fun onEditingFinished() {
        Toast.makeText(this@MainActivity, "Success", Toast.LENGTH_LONG).show()
        supportFragmentManager.popBackStack()
    }

}