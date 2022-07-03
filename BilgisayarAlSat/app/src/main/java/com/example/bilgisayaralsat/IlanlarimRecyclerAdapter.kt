package com.example.bilgisayaralsat

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.recycler_row.view.*
import java.util.ArrayList

class IlanlarimRecyclerAdapter (private val postList: ArrayList<Post>):RecyclerView.Adapter<IlanlarimRecyclerAdapter.PostHolder>() {
    class PostHolder(viewGroup: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.activity_ilanlarim_recycler, viewGroup, false)) {

        private val txtkullanici by lazy { itemView.findViewById<TextView>(R.id.recycler_row_kullanici_emaili) }
        private val image by lazy { itemView.findViewById<ImageView>(R.id.recycler_row_imageview) }
        private val txtbaslik by lazy { itemView.findViewById<TextView>(R.id.recycler_row_ilanbaslik) }




        fun bindTo(post: Post, onItemClick: (view: View, newsDTO: Post) -> Unit) {

            //databseden gelene veriyi kendi uygulamammızin içerisine burda yediriyoruz
            txtkullanici.text = post.kullaniciEmail
            txtbaslik.text = post.ilanbaslik



            //fotografı glide kütüphanesi yardimiyla cekip kendi imageimizin icerisine ata
            Glide.with(itemView.context).load(post.gorselUrl).into(image)

            itemView.setOnClickListener {

                val intent = Intent(it.context, IlanlarimDetay::class.java)

                intent.putExtra("id",post.id);
                intent.putExtra("ilanaciklamasi", post.ilanaciklamasi)
                //Detail sayfasına gonderdigimiz verileri put extra kullanarak göndericez.
                intent.putExtra("ilanbaslik", post.ilanbaslik)
                intent.putExtra("telefon",post.telefon)
                intent.putExtra("gorselUrl", post.gorselUrl)
                intent.putExtra("fiyati", post.fiyati)
                intent.putExtra("marka",post.marka)
                intent.putExtra("ram",post.ram)
                intent.putExtra("islemci",post.islemci)
                intent.putExtra("ekranboyutu",post.ekranboyutu)
                intent.putExtra("cozunurlugu",post.cozunurlugu)
                intent.putExtra("modeli",post.modeli)
                intent.putExtra("pcyili",post.pcyili)
                intent.putExtra("durumu",post.durumu)

                it.context.startActivity(intent)
                onItemClick(it, Post())
            }
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        val inflater= LayoutInflater.from(parent.context)
        val view=inflater.inflate(R.layout.recycler_row,parent,false)

        return PostHolder(parent)

    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        holder.itemView.recycler_row_kullanici_emaili.text=postList[position].kullaniciEmail
        holder.itemView.recycler_row_ilanbaslik.text=postList[position].ilanbaslik


        //Picasso.get().load(postList[position].gorselUrl).into(holder.itemView.recycler_row_imageview)
        if (postList[position].gorselUrl.isEmpty()) {
            holder.itemView.recycler_row_imageview.setImageResource(com.google.firebase.inappmessaging.display.R.drawable.image_placeholder);
        } else{
            Picasso.get().load(postList[position].gorselUrl).into(holder.itemView.recycler_row_imageview)

            Log.d("naber", postList[position].gorselUrl.toString())


        }
        holder.bindTo(postList[position])
        { view, newsDTO -> onItemClick(view, newsDTO) }
    }

    private fun onItemClick(view: View, newsDTO: Post) {

    }

    override fun getItemCount(): Int {
        return postList.size
    }


}