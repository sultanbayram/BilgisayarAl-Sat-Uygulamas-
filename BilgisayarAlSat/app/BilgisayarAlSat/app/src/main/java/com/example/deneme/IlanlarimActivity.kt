package com.example.deneme

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_ilanlar.*


class IlanlarimActivity : AppCompatActivity() {
    private lateinit var database :FirebaseFirestore
    private var email =""
    var geri2: Button? = null
    private lateinit var recyclerViewAdapter: IlanlarimRecyclerAdapter
    var postListesi=ArrayList<Post>()
    var liste=ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ilanlarim)

        database = FirebaseFirestore.getInstance()
        email = intent.extras?.get("email").toString()
        Log.d("email", email)

        getAllIlanById()
        verileriAl()
        tanimla()
        var layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        recyclerViewAdapter = IlanlarimRecyclerAdapter(postListesi)
        recyclerView.adapter = recyclerViewAdapter




}
    fun verileriAl(){

        database.collection("Post").orderBy("tarih",
            Query.Direction.DESCENDING).whereEqualTo("kullaniciemail", email).addSnapshotListener{ snapshot, exception ->
            if (exception!= null){
                Toast.makeText(this,exception.localizedMessage, Toast.LENGTH_LONG).show()
            }else{
                if (snapshot!=null){
                    if (!snapshot.isEmpty){

                        val documents=snapshot.documents

                        postListesi.clear()

                        for (document in documents){
                            val id = document.id
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
                            liste.add(marka)
                        }
                        Log.d("liste", liste.toString())
                        recyclerViewAdapter.notifyDataSetChanged()
                    }
                }

            }
        }
    }
    private fun getAllIlanById() {
        // [START get_all_users]
        database.collection("Post")
            .whereEqualTo("kullaniciemail", email)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("TAG", "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w("TAG", "Error getting documents.", exception)
            }
        // [END get_all_users]
    }
    fun tanimla() {

        geri2 = findViewById<View>(R.id.geri2)as Button
        geri2!!.setOnClickListener{
            val intent = Intent(this@IlanlarimActivity, AnaEkranActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.anim_in, R.anim.anim_out)
        }}
}
