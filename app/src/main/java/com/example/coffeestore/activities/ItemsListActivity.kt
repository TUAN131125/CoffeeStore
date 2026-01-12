package com.example.coffeestore.activities

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.coffeestore.adapters.ItemListCategoryAdapter
import com.example.coffeestore.databinding.ActivityItemsListBinding
import com.example.coffeestore.viewmodel.MainViewModel

class ItemsListActivity : AppCompatActivity() {
    // Khởi tạo binding và viewModel
    private lateinit var binding: ActivityItemsListBinding
    private val viewModel = MainViewModel()

    private var id: String = ""
    private var title: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Khởi tạo binding
        binding = ActivityItemsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getBundles()
        initList()
    }

    private fun initList() {
        binding.apply { // <-- Quan trọng: Phải có dòng này để gọi được listView, progressBar...
            progressBar.visibility = View.VISIBLE

            // Sửa lỗi: observe viết thường, không viết hoa Observe
            viewModel.loadItems(id).observe(this@ItemsListActivity) { items ->
                listView.layoutManager = GridLayoutManager(this@ItemsListActivity, 2)
                listView.adapter = ItemListCategoryAdapter(items)

                progressBar.visibility = View.GONE
            }

            backBtn.setOnClickListener { finish() }
        }
    }

    private fun getBundles() {
        id = intent.getStringExtra("id") ?: ""
        title = intent.getStringExtra("title") ?: ""

        binding.categoryTxt.text = title
    }
}