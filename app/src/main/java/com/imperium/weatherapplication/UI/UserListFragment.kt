package com.imperium.weatherapplication.UI

import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.imperium.weatherapplication.MainActivity
import com.imperium.weatherapplication.R
import com.imperium.weatherapplication.UI.Adapters.UserListRecyclerAdapter
import com.imperium.weatherapplication.Utils.SwipeToDeleteCallback
import com.imperium.weatherapplication.WeatherAppViewModel
import com.imperium.weatherapplication.databinding.FragmentUserListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi


@DelicateCoroutinesApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class UserListFragment : Fragment() {
    private lateinit var bindingUserListFragment: FragmentUserListBinding
    var adapter:UserListRecyclerAdapter= UserListRecyclerAdapter()
    private val vm: WeatherAppViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu,menu)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (activity as MainActivity?)!!.supportActionBar!!.show()
        (activity as MainActivity?)!!.supportActionBar!!.title = getString(R.string.user_list)
        (activity as MainActivity?)!!.supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        bindingUserListFragment=FragmentUserListBinding.inflate(inflater,container,false)
        setUpRecyclerView()
        observeUserData()
        return bindingUserListFragment.root
    }

    private fun observeUserData() {
        vm.userList.observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
        })
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.add_user->{
                Navigation.findNavController(bindingUserListFragment.root).navigate(R.id.action_userListFragment_to_userFormFragment);
                true
            }
            else->super.onOptionsItemSelected(item)
        }
    }

    fun setUpRecyclerView(){
        bindingUserListFragment.userRecycler.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        bindingUserListFragment.userRecycler.layoutManager = LinearLayoutManager(activity)
        bindingUserListFragment.userRecycler.adapter = adapter
        val swipeHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = bindingUserListFragment.userRecycler.adapter as UserListRecyclerAdapter
                adapter.removeAt(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(bindingUserListFragment.userRecycler)
    }

}