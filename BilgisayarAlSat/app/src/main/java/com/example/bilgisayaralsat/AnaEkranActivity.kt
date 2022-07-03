package com.example.bilgisayaralsat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_anaekran.*

class AnaEkranActivity : AppCompatActivity() {
    var ilanVerButon: Button? = null

    var ilanlarimMenuButon: Button? = null
    var ılanlarButon: Button? = null
    var kisimesajlari:Button?=null
    var favoriButton:Button?=null
    private lateinit var auth: FirebaseAuth
    private lateinit var database :FirebaseFirestore
    private var email =""

  /*  private lateinit var recyclerViewAdapter: IlanRecyclerAdapter
    var postListesi=ArrayList<Post>()*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anaekran)
        auth= FirebaseAuth.getInstance()
        database=FirebaseFirestore.getInstance()
        tanimla()
        email = auth.currentUser?.email.toString()
        Log.d("email",email)

    }
    fun tanimla() {

        favoriButton = findViewById<View>(R.id.ilanFavori)as Button
        favoriButton!!.setOnClickListener{
            val intent = Intent(this@AnaEkranActivity, FavoriActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.anim_in, R.anim.anim_out)
        }
        ilanVerButon = findViewById<View>(R.id.ilanVerButon) as Button
        ilanVerButon!!.setOnClickListener {
            val intent = Intent(this@AnaEkranActivity, IlanEkleActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.anim_in, R.anim.anim_out)
        }
        ilanlarimMenuButon = findViewById<View>(R.id.ilanlarimMenuButon) as Button
        ilanlarimMenuButon!!.setOnClickListener {
          val intent = Intent(this@AnaEkranActivity, IlanlarimActivity::class.java)
            intent.putExtra("email", email);
            startActivity(intent)
            overridePendingTransition(R.anim.anim_in, R.anim.anim_out)
        }
        ılanlarButon = findViewById<View>(R.id.ılanlarButon) as Button
        ılanlarButon!!.setOnClickListener {
            val intent = Intent(this@AnaEkranActivity, IlanlarActivity::class.java)
            intent.putExtra("email", email);
            startActivity(intent)
         overridePendingTransition(R.anim.anim_in, R.anim.anim_out)
        }
        kisimesajlari = findViewById<View>(R.id.kisimesajlari) as Button
        kisimesajlari!!.setOnClickListener {
            val intent = Intent(this@AnaEkranActivity, MesajlarimActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.anim_in, R.anim.anim_out)
        }
    }




    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater =menuInflater
        menuInflater.inflate(R.menu.secenekler_menusu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       if (item.itemId==R.id.ilan_ekle){
           //ilan ekle activitye gidecek
           val intent=Intent(this,IlanEkleActivity::class.java)
           startActivity(intent)
       }else if (item.itemId==R.id.cikis_yap){
           auth.signOut()
           val intent =Intent( this,KullaniciActivity::class.java)
           startActivity(intent)
           finish()
       }
        return super.onOptionsItemSelected(item)
    }
}