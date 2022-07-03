package com.example.deneme

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.deneme.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail.view.*

import kotlinx.android.synthetic.main.recycler_row.view.*


class IlanlarDetailActivity : AppCompatActivity() {
var gorselurl=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        gorselurl=intent.extras?.get("gorselUrl").toString()


        txtaciklama.text = intent.extras?.get("ilanaciklamasi").toString()
        txtbaslik.text = intent.extras?.get("ilanbaslik").toString()
        txttelefon.text=intent.extras?.get("telefon").toString()
        txtfiyat.text = intent.extras?.get("fiyati").toString()
        txtmarka.text = intent.extras?.get("marka").toString()
        txtram.text = intent.extras?.get("ram").toString()
        txtislemci.text = intent.extras?.get("islemci").toString()
        txtekranboyutu.text = intent.extras?.get("ekranboyutu").toString()
        txtcozunurluk.text = intent.extras?.get("cozunurlugu").toString()
        txtmodel.text = intent.extras?.get("modeli").toString()
        txtyil.text = intent.extras?.get("pcyili").toString()
        txtdurum.text = intent.extras?.get("durumu").toString()

        Picasso.get().load(gorselurl).into(imageNewDetail)

    }
}
