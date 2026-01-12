package com.example.coffeestore.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.coffeestore.databinding.ActivityMainBinding
import com.example.coffeestore.viewmodel.MainViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffeestore.adapters.PopularAdapter
import com.example.coffeestore.adapters.CategoryAdapter


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBanner()
        initCategory()
        initPopular()
        initButtomMenu()
    }

    private fun initButtomMenu() {
        binding.cartBtn.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }
    }

    private fun initPopular() {
        binding.apply {
            progressBarPopular.visibility = View.VISIBLE

            viewModel.loadPopular().observeForever {
                popularView.layoutManager = LinearLayoutManager(
                    this@MainActivity,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )

                popularView.adapter = PopularAdapter(items = it)
                progressBarPopular.visibility = View.GONE
            }
        }

        viewModel.loadPopular()
    }


    private fun initCategory() {
        binding.apply {
            progressBarCategory.visibility = View.VISIBLE
            viewModel.loadCategory().observeForever {
                categoryView.adapter = CategoryAdapter(it)
                categoryView.layoutManager =
                    LinearLayoutManager(
                        this@MainActivity,
                        LinearLayoutManager.HORIZONTAL,false
                    )
                progressBarCategory.visibility = View.GONE
            }
            viewModel.loadCategory()
        }
    }

    private fun initBanner() {
        binding.apply {

            progressBarBanner.visibility = View.VISIBLE

            viewModel.loadBanner().observeForever {
                Glide.with(this@MainActivity)
                    .load(it[0].url)
                    .into(banner)

                progressBarBanner.visibility = View.GONE
            }
        }

        viewModel.loadBanner()
    }
}