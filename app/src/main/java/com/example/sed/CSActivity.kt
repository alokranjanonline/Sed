package com.example.sed

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject



class CSActivity : AppCompatActivity() {
    private var list:ArrayList<ItemsViewModel> = ArrayList()
    private val adapter = CustomAdapter(list,this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_csactivity)
        mAdView = findViewById(R.id.adView)
        val textShow_error_msg = findViewById<TextView>(R.id.textErrorDisplay)
        val actionbar=supportActionBar
        actionbar!!.title="Choose Scheme"
        //actionbar.setDisplayShowHomeEnabled(true)
        //actionbar.setDisplayHomeAsUpEnabled(true)

        fetch_data()
        //Coding for RecycleVIew
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = adapter

        swipeToRefresh(recyclerview)
        //Show mobile ad
        show_banner_ads(mAdView,this)
    }
    fun swipeToRefresh(recyclerview:RecyclerView){
        val swipeRefreshLayout: SwipeRefreshLayout = findViewById(R.id.swipe)
        swipeRefreshLayout.setOnRefreshListener {
            val textShow_error_msg = findViewById<TextView>(R.id.textErrorDisplay)
            textShow_error_msg.text = number++.toString()
            recyclerview.setAdapter(adapter)
            swipeRefreshLayout.isRefreshing = false
        }
    }
    fun fetch_data(){
        // Fetch the data from server //

        val queue = Volley.newRequestQueue(this)
        val url = "http://springtown.in/test/fetch_scheme.php"
        val textShow_error_msg = findViewById<TextView>(R.id.textErrorDisplay)
        val stringRequest = StringRequest( Request.Method.GET, url,
            Response.Listener<String> { response ->
                //textShow_error_msg.text = "Response is: ${response}"
                val jsonObject=JSONObject(response)
                if(jsonObject.get("response").equals("sucess")){
                    val jsonArray=jsonObject.getJSONArray("data")
                    for(i in 0.. jsonArray.length()-1){
                        val jo=jsonArray.getJSONObject(i)
                        val scheme_id=jo.get("scheme_id").toString()
                        val scheme_name=jo.get("scheme_name").toString()
                        val scheme_image=jo.get("scheme_image")
                        val image_url="http://springtown.in/test/images/"+scheme_image
                        val scheme_url=jo.get("scheme_url").toString()
                        val user=ItemsViewModel(scheme_name,scheme_id,image_url,scheme_url,"A")
                        list.add(user)
                    }
                    adapter.notifyDataSetChanged()
                }else{
                    Toast.makeText(this, "There is some problem.", Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener { textShow_error_msg.text = "There is some problem. Please try again." })
        queue.add(stringRequest)
    }


}