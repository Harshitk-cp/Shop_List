package com.harshit.shoplist.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.PopupWindow
import android.widget.Toast
import androidx.core.content.res.ComplexColorCompat.inflate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.harshit.shoplist.R
import com.harshit.shoplist.adapter.ProductItemClicked
import com.harshit.shoplist.adapter.ProductListAdapter
import com.harshit.shoplist.utils.MySingleton
import com.harshit.shoplist.utils.Products
import org.json.JSONException
import org.json.JSONObject

class ShopLists : AppCompatActivity(), ProductItemClicked {

    private lateinit var recyclerView: RecyclerView
    private lateinit var mAdapter: ProductListAdapter
    private lateinit var LogOut: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        fetchData()
        mAdapter = ProductListAdapter(this)
        recyclerView.adapter = mAdapter
        LogOut = findViewById(R.id.LogOut)
        LogOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            finishAffinity()
        }
    }

    //function for parsing json array from API
    private fun fetchData() {
        val url = "https://fakestoreapi.com/products/"
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            {response ->
                try {
                    // loop through the array elements
                    val productArray = ArrayList<Products>()
                    for (i in 0 until response.length()) {
                        // get current json object as product instance
                        val productJsonObject: JSONObject = response.getJSONObject(i)
                        val products = Products(
                            productJsonObject.getString("title"),
                            productJsonObject.getDouble("price"),
                            productJsonObject.getString("image"),
                            productJsonObject.getString("description"),
                            productJsonObject.getString("category")
                        )
                        productArray.add(products)
                    }
                    mAdapter.updateProducts(productArray)

                }catch (e: JSONException){
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }
            },

            {error -> // error listener
                println("error in volley!")
                Toast.makeText(this, "volley Error", Toast.LENGTH_SHORT).show()
            }
        )
        MySingleton.getInstance(this).addToRequestQueue(jsonArrayRequest)
    }

    private fun showPopUp(){

    }


    override fun onItemClicked(item: Products) {
        val image  = item.image
        val title  = item.title
        val price  = item.price
        val description  = item.description
        val category  = item.category

        //sending the product details
        val intent = Intent(this, ProductDetails::class.java)
        intent.putExtra("image", image)
        intent.putExtra("title", title)
        intent.putExtra("price", price)
        intent.putExtra("description", description)
        intent.putExtra("category", category)
        startActivity(intent)


    }
}