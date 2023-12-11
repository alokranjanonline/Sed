package com.example.sed

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.ads.AdView
import org.json.JSONObject

class StateScheme : AppCompatActivity() {
    private var number: Int = 0
    private lateinit var  mAdView : AdView
    private var list:ArrayList<ItemsViewModel> = ArrayList()
    val adapter = CustomAdapter2(list,this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_state_scheme)
        mAdView = findViewById(R.id.adView)
        val actionbar=supportActionBar
        val textShow_error_msg: TextView = findViewById(R.id.textErrorDisplay)
        var textShow_Internet_msg: TextView = findViewById(R.id.internetAvailability)
        val str = intent.getStringExtra("schemeId")
        actionbar!!.title=intent.getStringExtra("schemeName")
        textShow_Internet_msg.text= MainActivity.adCounter.toString()
        //textShow_error_msg.text=str
        fetch_data()

        //Coding for RecycleVIew
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = adapter

        //Swipe to refresh
        swipeToRefresh(recyclerview)
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
        val queue = Volley.newRequestQueue(this)
        val url = "http://springtown.in/test/fetch_states.php?scheme_id=2"
        val textShow_error_msg = findViewById<TextView>(R.id.textErrorDisplay)
        val stringRequest = StringRequest( Request.Method.GET, url,
            { response ->
                //textshow_error_msg.text = "Response is: ${response}"
                val jsonObject= JSONObject(response)
                if(jsonObject.get("response").equals("sucess")){
                    val jsonArray=jsonObject.getJSONArray("data")
                    for(i in 0.. jsonArray.length()-1){
                        val jo=jsonArray.getJSONObject(i)
                        val state_id=jo.get("state_id").toString()
                        val schemeTypeId=jo.get("scheme_type_id").toString()
                        val state_name=jo.get("state_name").toString()
                        val state_image=jo.get("state_image").toString()
                        val image_url="http://springtown.in/test/images/"+state_image
                        val state_url=jo.get("state_url").toString()
                        val user=ItemsViewModel(state_name,schemeTypeId,image_url,state_url,state_id)
                        list.add(user)
                    }
                    adapter.notifyDataSetChanged()
                }else{
                    Toast.makeText(this, "There is some problem", Toast.LENGTH_SHORT).show()
                }
            },
            { textShow_error_msg.text = "There is some problem. Please try again." })
        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }
}