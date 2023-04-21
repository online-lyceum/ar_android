package com.exception.ufofind.activity

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.exception.ufofind.R
import com.exception.ufofind.databinding.ActivityAllUsersBinding
import com.exception.ufofind.databinding.ItemUserBinding
import com.exception.ufofind.viewmodel.AllUsersViewModel
import com.vuforia.engine.native_sample.gson.UserJson

class AllUsersActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAllUsersBinding
    private lateinit var viewModel: AllUsersViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllUsersBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this, AllUsersViewModel.AllUsersViewModelFactory(application))[AllUsersViewModel::class.java]


        viewModel.liveDataUsers.observe(this) {
            if(it!=null) {
                setContentView(binding.root)
                binding.recyclerUsers.adapter = UserAdapter(this, it)
            }
            else {
                setContentView(R.layout.activity_no_internet)
            }
        }

    }


    class UserAdapter(private val context: Context, private val users: List<UserJson>) : RecyclerView.Adapter<UserAdapter.Holder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
            return Holder(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }

        override fun onBindViewHolder(holder: Holder, position: Int) {
            holder.binding.textName.text = users[position].name
            holder.binding.textJobTitle.text = users[position].jobTitle
            holder.binding.root.setOnClickListener {
                startMapActivity()
            }
        }

        private fun startMapActivity() {

        }

        override fun getItemCount(): Int {
            return users.size
        }

        class Holder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)
    }
}