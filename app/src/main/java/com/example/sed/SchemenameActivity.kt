package com.example.sed

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import org.json.JSONObject

class SchemenameActivity : AppCompatActivity() {
    private lateinit var  mAdView : AdView
    private var list:ArrayList<ItemsViewModel> = ArrayList()
    val adapter = CustomAdapter1(list,this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schemename)
        mAdView = findViewById(R.id.adView)
        val actionbar=supportActionBar
        var receiver_msg: TextView = findViewById(R.id.txtSchemename)
        val intet: Intent
        val str = intent.getStringExtra("schemeId")
        actionbar!!.title=intent.getStringExtra("schemeName")
        receiver_msg.text=str
        fetch_data(1)

        //Coding for RecycleVIew
        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)
        recyclerview.layoutManager = LinearLayoutManager(this)
        // This will pass the ArrayList to our Adapter
        //val adapter = CustomAdapter(list,this)
        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter

        show_banner_ads(mAdView,this)

    }
    fun fetch_data(str:Int){
        // Fetch the data from server //

        val queue = Volley.newRequestQueue(this)
        val url = "http://springtown.in/test/fetch_scheme_name.php?scheme_id="+str
        val textshow_error_msg = findViewById<TextView>(R.id.textView1)
        val stringRequest = StringRequest( Request.Method.GET, url,
            Response.Listener<String> { response ->
                //textshow_error_msg.text = "Response is: ${response}"
                val jsonObject= JSONObject(response)
                if(jsonObject.get("response").equals("sucess")){
                    val jsonArray=jsonObject.getJSONArray("data")
                    for(i in 0.. jsonArray.length()-1){
                        val jo=jsonArray.getJSONObject(i)
                        val scheme_id=jo.get("scheme_id").toString()
                        val scheme_name=jo.get("scheme_name").toString()
                        val scheme_image=jo.get("scheme_image").toString()
                        val url="http://springtown.in/test/images/"+scheme_image
                        val user=ItemsViewModel(scheme_name,scheme_id,url)
                        list.add(user)
                    }
                    adapter.notifyDataSetChanged()
                    //scheme_data=response
                    //val intent = Intent(this, MainActivity::class.java)
                    //startActivity(intent)
                }else{
                    Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
                }
            },
            Response.ErrorListener { textshow_error_msg.text = "Failed" })
        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }

}