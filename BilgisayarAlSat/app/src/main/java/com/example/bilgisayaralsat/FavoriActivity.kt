package com.example.bilgisayaralsat

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
import kotlinx.android.synthetic.main.activity_ilanlar.*

class FavoriActivity : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth
    private lateinit var database : FirebaseFirestore
    private lateinit var recyclerViewAdapter2: FavoriRecyclerAdapter
    private var postListesi=ArrayList<Post>()
    private var email = ""
    private var deger = ""
    var geri1: Button? = null
    val favoriList = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favori)
        auth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()
        email = auth.currentUser?.email.toString()
        Log.d("favoriemail", email)


        database.collection("Favori").whereEqualTo("kullanici", email).addSnapshotListener{ snapshot, exception ->
            if (exception!= null){
                Toast.makeText(this,exception.localizedMessage, Toast.LENGTH_LONG).show()
            }else{
                if (snapshot!=null){
                    if (!snapshot.isEmpty){

                        val documents=snapshot.documents

                        //favoriList.clear()

                            for (document in documents){
                                val deger = document.get("postID").toString()
                                Log.d("favoripostid", deger)
                                favoriList.add(deger)
                            }
                            Log.d("naber", "asdasd$favoriList")
                        }
                    else
                    {

                    } } } }

        Log.d("favoripostid", favoriList.toString())
        tanimla()
        verileriAl()

        var layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        recyclerViewAdapter2 = FavoriRecyclerAdapter(postListesi)
        recyclerView.adapter = recyclerViewAdapter2}

    private fun verileriAl() {

        database.collection("Post").document("id")
                .get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        Log.d("TAG", "DocumentSnapshot data: ${document.data}")
                        postListesi.clear()

                        var id = document.id
                        val kullaniciEmail = document.get("kullaniciemail") as String
                        val gorselUrl = document.get("gorselUrl").toString()
                        val ilanbaslik = document.get("ilanbaslik") as String
                        val ilanaciklamasi = document.get("ilanaciklamasi") as String
                        val telefon = document.get("telefon").toString()
                        val fiyati = document.get("fiyati") as String
                        val marka = document.get("marka") as String
                        val ram = document.get("ram") as String
                        val islemci = document.get("islemci") as String
                        val ekranboyutu = document.get("ekranboyutu") as String
                        val modeli = document.get("modeli") as String
                        val pcyili = document.get("pcyili") as String
                        val durumu = document.get("durumu") as String
                        val cozunurlugu = document.get("cozunurlugu") as String
                        val tarih = document.get("tarih").toString()

                        val indirilenPost = Post(
                            id,
                            cozunurlugu,
                            durumu,
                            ekranboyutu,
                            fiyati,
                            gorselUrl,
                            ilanaciklamasi,
                            ilanbaslik,
                            islemci,
                            kullaniciEmail,
                            marka,
                            modeli,
                            pcyili,
                            ram,
                            telefon
                        )
                        postListesi.add(indirilenPost)
                    } else {
                        Log.d("TAG", "No such document")
                    }
                    recyclerViewAdapter2.notifyDataSetChanged()
                }
                .addOnFailureListener { exception ->
                    Log.d("TAG", "get failed with ", exception)
                }
    }
    fun tanimla() {

        geri1 = findViewById<View>(R.id.geri1)as Button
        geri1!!.setOnClickListener{
            val intent = Intent(this@FavoriActivity, AnaEkranActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.anim_in, R.anim.anim_out)
        }}
}



// [END get_document]

        /* database.collection("Post").document().get()
             .addOnSuccessListener { document ->
                 if (document != null) {
                     postListesi.clear()

                     var id = document.id
                     val kullaniciEmail = document.get("kullaniciemail") as String
                     val gorselUrl = document.get("gorselUrl").toString()
                     val ilanbaslik = document.get("ilanbaslik") as String
                     val ilanaciklamasi = document.get("ilanaciklamasi") as String
                     val telefon = document.get("telefon").toString()
                     val fiyati = document.get("fiyati") as String
                     val marka = document.get("marka") as String
                     val ram = document.get("ram") as String
                     val islemci = document.get("islemci") as String
                     val ekranboyutu = document.get("ekranboyutu") as String
                     val modeli = document.get("modeli") as String
                     val pcyili = document.get("pcyili") as String
                     val durumu = document.get("durumu") as String
                     val cozunurlugu = document.get("cozunurlugu") as String
                     val tarih = document.get("tarih").toString()

                     val indirilenPost = Post(
                         id,
                         cozunurlugu,
                         durumu,
                         ekranboyutu,
                         fiyati,
                         gorselUrl,
                         ilanaciklamasi,
                         ilanbaslik,
                         islemci,
                         kullaniciEmail,
                         marka,
                         modeli,
                         pcyili,
                         ram,
                         telefon
                     )
                     postListesi.add(indirilenPost)


                 }
                 else {
                     Log.d("TAG", "No such document")
                 }
                 recyclerViewAdapter2.notifyDataSetChanged()
             }
             .addOnFailureListener { exception ->
                 Log.d("TAG", "get failed with ", exception)
             }*/








