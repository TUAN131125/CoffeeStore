package com.example.coffeestore.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffeestore.Helper.ChangeNumberItemsListener
import com.example.coffeestore.Helper.ManagmentCart
import com.example.coffeestore.adapters.CartAdapter
import com.example.coffeestore.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity() {
    // 1. Khai báo binding
    private lateinit var binding: ActivityCartBinding
    private lateinit var managmentCart: ManagmentCart
    private var tax: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 2. SỬA LỖI CRASH: Phải inflate binding trước khi dùng
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 3. Khởi tạo ManagmentCart
        // 'this' ở đây chính là Context (CartActivity là con của Context)
        managmentCart = ManagmentCart(this)

        // 4. Gọi các hàm logic
        calculateCart()
        setVariable()
        initCartList()
    }

    private fun initCartList() {
        binding.apply {
            listView.layoutManager = LinearLayoutManager(
                this@CartActivity, LinearLayoutManager.VERTICAL, false
            )

            // Truyền this@CartActivity vào adapter
            listView.adapter = CartAdapter(
                managmentCart.getListCart(),
                this@CartActivity,
                object : ChangeNumberItemsListener {
                    override fun onChanged() {
                        calculateCart()
                    }
                }
            )
        }
    }

    private fun setVariable() {
        binding.backBtn.setOnClickListener { finish() }
    }

    private fun calculateCart() {
        val percentTax = 0.02
        val delivery = 10.0
        val totalFee = managmentCart.getTotalFee()

        tax = Math.round((totalFee * percentTax) * 100) / 100.0
        val total = Math.round((totalFee + tax + delivery) * 100) / 100.0
        val itemTotal = Math.round(totalFee * 100) / 100.0

        binding.apply {
            totalFeeTxt.text = "$$itemTotal"
            totalTaxTxt.text = "$$tax"
            deliveryTxt.text = "$$delivery"
            totalTxt.text = "$$total"
        }
    }
}