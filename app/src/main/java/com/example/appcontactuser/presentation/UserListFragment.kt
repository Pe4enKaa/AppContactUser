package com.example.appcontactuser.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.appcontactuser.R
import com.example.appcontactuser.databinding.UserListFragmentBinding
import com.example.appcontactuser.presentation.MainActivity.Companion.newIntentAddItem
import com.example.appcontactuser.presentation.MainActivity.Companion.newIntentEditItem
import com.example.appcontactuser.presentation.adapters.UserListAdapter

class UserListFragment: Fragment() {

    private lateinit var binding: UserListFragmentBinding

    private lateinit var userListAdapter: UserListAdapter

    private val viewModel: UserListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = UserListFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycler()

        setupClickListener()

        dataInsert()

        addItem()
    }

    private fun addItem() {
        binding.buttonAddUserItem.setOnClickListener {
            if (isOnePaneMode()) {
                val intent = newIntentAddItem(requireContext())
                startActivity(intent)
            } else {
                launchFragment(UserItemFragment.newInstanceAddItem())
            }
        }
    }

    private fun setupClickListener() {
        userListAdapter.onUserItemClickListener = {

            if (isOnePaneMode()) {
                val intent = newIntentEditItem(requireContext(), it.id)
                startActivity(intent)
            } else {
                launchFragment(UserItemFragment.newInstanceEditItem(it.id))
            }
        }
    }

    private fun isOnePaneMode(): Boolean {
        return binding.userItemFragmentContainer == null
    }

    private fun launchFragment(fragment: Fragment) {
        fragmentManager ?.popBackStack()
        fragmentManager?.beginTransaction()
            ?.replace(R.id.user_item_fragment_container, fragment)
            ?.addToBackStack(null)
            ?.commit()
    }

    private fun dataInsert() {
        viewModel.userList.observe(viewLifecycleOwner) {
            userListAdapter.submitList(it)
        }
    }

    private fun setupRecycler() {
        val rvUserList = binding.rvUserList

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
}