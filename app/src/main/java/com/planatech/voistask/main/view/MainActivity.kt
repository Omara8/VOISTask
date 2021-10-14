package com.planatech.voistask.main.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.planatech.voistask.FullScreenActivity
import com.planatech.voistask.databinding.ActivityMainBinding
import com.planatech.voistask.main.viewmodel.MainViewModel
import com.planatech.voistask.utils.IMAGE_URL
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
            val intent = Intent(this, FullScreenActivity::class.java)
            intent.putExtra(IMAGE_URL, it.download_url)
            startActivity(intent)
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