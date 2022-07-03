package com.example.bilgisayaralsat

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import kotlin.math.log

class IlanlarimDetay: AppCompatActivity() {
    var gorselurl=""

var update:Button? =null
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseFirestore
    var id: String = ""
    var sil:Button? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ilanlarim_detay)

        database = FirebaseFirestore.getInstance()
        update = findViewById<View>(R.id.update) as Button
        update?.setOnClickListener{
            val  intent=Intent(this@IlanlarimDetay,UpdateActivity::class.java)
            intent.putExtra("id",id);


            startActivity(intent)
            overridePendingTransition(R.anim.anim_in, R.anim.anim_out)
        }

        sil = findViewById<View>(R.id.sil) as Button
        sil?.setOnClickListener{
            database.collection("Post").document(id)
                .delete()
                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!") }
                .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
            val intent=Intent(this@IlanlarimDetay,AnaEkranActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.anim_in, R.anim.anim_out)
            }


        gorselurl=intent.extras?.get("gorselUrl").toString()
        id = intent.extras?.get("id").toString()
        Log.d("id ", id)
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
