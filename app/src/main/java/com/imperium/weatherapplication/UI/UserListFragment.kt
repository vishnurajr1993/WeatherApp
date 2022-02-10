package com.imperium.weatherapplication.UI

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.imperium.weatherapplication.R
import com.imperium.weatherapplication.Room.UserEntity
import com.imperium.weatherapplication.UI.Adapters.OnItemTouchListener
import com.imperium.weatherapplication.UI.Adapters.UserListRecyclerAdapter
import com.imperium.weatherapplication.Utils.MarginItemDecoration
import com.imperium.weatherapplication.Utils.SwipeToDeleteCallback
import com.imperium.weatherapplication.UI.ViewModels.WeatherAppViewModel
import com.imperium.weatherapplication.databinding.FragmentUserListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi


@DelicateCoroutinesApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class UserListFragment : BaseFragment<FragmentUserListBinding>(), OnItemTouchListener {

   lateinit var adapter:UserListRecyclerAdapter

    private var users:List<UserEntity>?=null;
    override fun setUpAppbar() {
        super.setUpAppbar()
        setHasOptionsMenu(true)


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu,menu)
    }


    private fun observeUserData() {
        vm.userList.observe(viewLifecycleOwner, Observer {
            users=it
            adapter.setData(it)

        })
    }

    override fun setUpViews() {
        super.setUpViews()
        (activity as MainActivity?)!!.apply {
            supportActionBar!!.show()
            supportActionBar!!.title = getString(R.string.user_list)
            supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        }
        setUpRecyclerView()
    }

    override fun observeData() {
        super.observeData()
        observeUserData()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.add_user->{
                Navigation.findNavController(binding.root).navigate(R.id.action_userListFragment_to_userFormFragment);
                true
            }
            else->super.onOptionsItemSelected(item)
        }
    }

    private fun setUpRecyclerView(){
        adapter= UserListRecyclerAdapter(this)
        binding.userRecycler.layoutManager = LinearLayoutManager(activity)

        binding.userRecycler.adapter = adapter
        val swipeHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = binding.userRecycler.adapter as UserListRecyclerAdapter
                users?.get(viewHolder.adapterPosition)?.let { vm.deleteUser(it.id) }
                adapter.removeAt(viewHolder.adapterPosition)

            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(binding.userRecycler)
    }

    override fun onItemViewTap() {
        findNavController().navigate(R.id.action_userListFragment_to_weatherFragment)
    }

    override fun getViewBinding(): FragmentUserListBinding = FragmentUserListBinding.inflate(layoutInflater)

}