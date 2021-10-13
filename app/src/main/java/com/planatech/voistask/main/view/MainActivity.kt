package com.planatech.voistask.main.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.planatech.voistask.databinding.ActivityMainBinding
import com.planatech.voistask.main.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private var binding: ActivityMainBinding? = null
    private var imagesAdapter: ImagesAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        initAdapter()
        setUpViewModel()
    }

    private fun initAdapter() {
        imagesAdapter = ImagesAdapter {
            //item clicked and should show details
        }
    }

    private fun setUpViewModel() {
        lifecycleScope.launch {
            mainViewModel.getImages().collectLatest {
                binding?.adapter = imagesAdapter
                imagesAdapter?.submitData(it)
            }
        }
    }

}