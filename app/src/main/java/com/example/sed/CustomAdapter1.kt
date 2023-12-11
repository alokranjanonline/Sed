package com.example.sed

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.example.sed.MainActivity.Companion.adCounter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class CustomAdapter1(private val mList: List<ItemsViewModel>, var context:Context) :
    RecyclerView.Adapter<CustomAdapter1.ViewHolder>() {

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_design, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]

        // sets the image to the imageview from our itemHolder class
        //holder.imageView.setImageResource(ItemsViewModel.image)
        Picasso.with(context).load(ItemsViewModel.image).into(holder.imageView)

        // sets the text to the textview from our itemHolder class
        holder.textView1.text = ItemsViewModel.schemeId
        holder.textView.text = ItemsViewModel.text
        holder.textView2.text = ItemsViewModel.schemeUrl
        holder.itemView.setOnClickListener(){
            Toast.makeText(context, ItemsViewModel.schemeId, Toast.LENGTH_SHORT).show()
            if(ItemsViewModel.schemeUrl=="null"){
                if(adCounter ==5){
                    loadInterestitialAd(context, ItemsViewModel.schemeId.toInt(),ItemsViewModel.text,0)
                    showInterestitialAd(context, StateScheme(),ItemsViewModel.schemeId.toInt(),ItemsViewModel.text,0)
                    adCounter =0
                }else{
                    val intent = Intent(context, SchemeDetails::class.java)
                    intent.putExtra("schemeId", ItemsViewModel.schemeId)
                    intent.putExtra("schemeName", ItemsViewModel.text)
                    context.startActivity(intent)
                }
                adCounter++
            }else{
                if(adCounter ==5){
                    loadInterestitialAd(context, ItemsViewModel.schemeId.toInt(),ItemsViewModel.text,0)
                    showInterestitialAd(context, StateScheme(),ItemsViewModel.schemeId.toInt(),ItemsViewModel.text,0)
                    adCounter =0
                }else{
                    val intent = Intent(context, SchemeDetailsWebview::class.java)
                    intent.putExtra("schemeId", ItemsViewModel.schemeId)
                    intent.putExtra("schemeName", ItemsViewModel.text)
                    intent.putExtra("schemeUrl", ItemsViewModel.schemeUrl)
                    context.startActivity(intent)
                }
                adCounter++
            }
        }

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageview)
        val textView: TextView = itemView.findViewById(R.id.textView)
        val textView1: TextView = itemView.findViewById(R.id.textView1)
        val textView2: TextView = itemView.findViewById(R.id.textView2)
        val textView3: TextView = itemView.findViewById(R.id.textView3)
    }
}

