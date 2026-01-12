package com.example.coffeestore.adapters

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeestore.R
import com.example.coffeestore.activities.ItemsListActivity
import com.example.coffeestore.databinding.ViewholderCategoryBinding
import com.example.coffeestore.domain.CategoryModel

class CategoryAdapter(
    private val items: MutableList<CategoryModel>
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private var selectedPosition = -1
    private var lastSelectedPosition = -1

    inner class ViewHolder(
        val binding: ViewholderCategoryBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewholderCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        val context = holder.itemView.context // Lấy context trực tiếp từ view

        holder.binding.titleCat.text = item.title

        // Xử lý background dựa trên vị trí được chọn
        if (selectedPosition == position) {
            holder.binding.titleCat.setBackgroundResource(R.drawable.brown_full_corner_bg)
        } else {
            holder.binding.titleCat.setBackgroundResource(R.drawable.brown_2_full_corner)
        }

        holder.binding.root.setOnClickListener {
            lastSelectedPosition = selectedPosition
            selectedPosition = position

            // Sửa lỗi crash: Chỉ notify khi vị trí hợp lệ
            if (lastSelectedPosition != -1) {
                notifyItemChanged(lastSelectedPosition)
            }
            notifyItemChanged(selectedPosition)

            // Trì hoãn chuyển trang để người dùng kịp thấy hiệu ứng đổi màu (500ms hơi lâu, 150-200ms là vừa)
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(context, ItemsListActivity::class.java).apply {
                    putExtra("title", item.title)
                    putExtra("id", item.id.toString())
                }
                context.startActivity(intent)
            }, 200)
        }
    }

    override fun getItemCount(): Int = items.size
}