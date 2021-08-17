package com.harshit.shoplist.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.harshit.shoplist.R
import com.harshit.shoplist.utils.Products

class ProductDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_products)


        //retrieving the product details
        var intent = intent
        val image = intent.getStringExtra("image")
        val title = intent.getStringExtra("title")
        val description = intent.getStringExtra("description")
        val price = intent.getStringExtra("price")
        val category = intent.getStringExtra("category")

        val imgDetail = findViewById<ImageView>(R.id.imgDetail)
        val txtTitleDetail = findViewById<TextView>(R.id.txtTitleDetail)
        val txtCategoryDetail = findViewById<TextView>(R.id.txtCategoryDetail)
        val txtPriceDetail = findViewById<TextView>(R.id.txtPriceDetail)
        val txtDescriptionDetail = findViewById<TextView>(R.id.txtDescriptionDetail)


        Glide.with(this).load(image).into(imgDetail)
        txtTitleDetail.text = title
        txtPriceDetail.text = price
        txtCategoryDetail.text = category
        txtDescriptionDetail.text = description



    }
}