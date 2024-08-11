package com.developer.rozan.testmobile.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.developer.rozan.testmobile.R
import com.developer.rozan.testmobile.adapter.ListUserAdapter
import com.developer.rozan.testmobile.adapter.LoadingStateAdapter
import com.developer.rozan.testmobile.data.response.DataItem
import com.developer.rozan.testmobile.databinding.ActivityThirdBinding
import com.developer.rozan.testmobile.view_model.ThirdViewModel
import com.developer.rozan.testmobile.view_model.ViewModelFactory
import kotlinx.coroutines.launch

class ThirdActivity : AppCompatActivity(), ListUserAdapter.OnUserClickListener {

    private lateinit var binding: ActivityThirdBinding
    private lateinit var thirdViewModel: ThirdViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        thirdViewModel = obtainViewModel(this)

        binding.ivBack.setOnClickListener {
            finish()
        }

        initFirst()
    }

    private fun initFirst() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                getDataProcess()
                refresh()
            }
        }
    }

    private fun getDataProcess() {
        showLoading(true)

        binding.rvUser.layoutManager = LinearLayoutManager(this)
        binding.rvUser.setHasFixedSize(true)

        val adapter = ListUserAdapter()
        adapter.listener = this
        binding.rvUser.adapter = adapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                adapter.retry()
            })

        thirdViewModel.user.observe(this) {
            showLoading(false)
            if (it != null) {
                adapter.submitData(lifecycle, it)
            } else {
                Toast.makeText(this, "Data User not found", Toast.LENGTH_SHORT).show()
                showEmptyLayout("Data User not found")
            }
        }
    }

    private fun refresh() {
        binding.swipeRefresh.setProgressBackgroundColorSchemeColor(
            ContextCompat.getColor(
                this,
                R.color.colorWhite
            )
        )
        binding.swipeRefresh.setColorSchemeColors(
            ContextCompat.getColor(
                this,
                R.color.colorBlack
            )
        )
        binding.swipeRefresh.setOnRefreshListener {
            initFirst()
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun showEmptyLayout(msg: String) {
        binding.rvUser.visibility = View.GONE
        binding.emptyLayout.clEmpty.visibility = View.VISIBLE
        binding.emptyLayout.tvEmptyDescription.text = msg
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressbarUser.visibility = View.VISIBLE
            binding.rvUser.visibility = View.GONE
        } else {
            binding.progressbarUser.visibility = View.GONE
            binding.emptyLayout.clEmpty.visibility = View.GONE
            binding.rvUser.visibility = View.VISIBLE
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): ThirdViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[ThirdViewModel::class.java]
    }

    override fun onItemClicked(dataItem: DataItem) {
        val resultIntent = Intent()
        resultIntent.putExtra("selected_user", dataItem)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }
}