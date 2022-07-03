package com.example.deneme

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_ilanlar.*

class IlanlarActivity: AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database :FirebaseFirestore
    private lateinit var recyclerViewAdapter: IlanRecyclerAdapter
    var postListesi=ArrayList<Post>()
    private var email =""
    var geri3: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ilanlar)
        auth= FirebaseAuth.getInstance()
        database= FirebaseFirestore.getInstance()
        email = intent.extras?.get("email").toString()
        Log.d("email", email)

        tanimla()
        verileriAl()
        var layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        recyclerViewAdapter = IlanRecyclerAdapter(postListesi)
        recyclerView.adapter = recyclerViewAdapter}


    fun verileriAl(){

        database.collection("Post").orderBy("tarih",
            Query.Direction.DESCENDING).addSnapshotListener{ snapshot, exception ->
            if (exception!= null){
                Toast.makeText(this,exception.localizedMessage, Toast.LENGTH_LONG).show()
            }else{
                if (snapshot!=null){
                    if (!snapshot.isEmpty){

                        val documents=snapshot.documents

                        postListesi.clear()

                        for (document in documents){
                            var id = document.id
                            val kullaniciEmail=document.get("kullaniciemail")as String
                            val gorselUrl=document.get("gorselUrl").toString()
                            val ilanbaslik=document.get("ilanbaslik")as String
                            val ilanaciklamasi=document.get("ilanaciklamasi")as String
                            val telefon=document.get("telefon").toString()
                            val fiyati=document.get("fiyati")as String
                            val marka =document.get("marka")as String
                            val ram =document.get("ram")as String
                            val islemci =document.get("islemci")as String
                            val ekranboyutu=document.get("ekranboyutu")as String
                            val modeli=document.get("modeli")as String
                            val pcyili=document.get("pcyili")as String
                            val durumu=document.get("durumu")as String
                            val cozunurlugu=document.get("cozunurlugu")as String
                            val tarih = document.get("tarih").toString()
                            val indirilenPost=Post(id, cozunurlugu,durumu, ekranboyutu, fiyati, gorselUrl, ilanaciklamasi, ilanbaslik, islemci, kullaniciEmail, marka, modeli, pcyili, ram,telefon)
                            postListesi.add(indirilenPost)

                        }
                        recyclerViewAdapter.notifyDataSetChanged()
                    }
                }

            }
        }
    }
    fun tanimla() {

        geri3 = findViewById<View>(R.id.geri3)as Button
        geri3!!.setOnClickListener{
            val intent = Intent(this@IlanlarActivity, AnaEkranActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.anim_in, R.anim.anim_out)
        }}
}