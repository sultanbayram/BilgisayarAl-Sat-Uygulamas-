package com.example.bilgisayaralsat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class KullaniciActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var edtEmail: EditText
    private lateinit var edtPass: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        val guncelKullanici =auth.currentUser

        if (guncelKullanici != null) {
            val intent = Intent(  this, AnaEkranActivity::class.java)
            startActivity(intent)
            finish()}
        edtEmail = findViewById(R.id.emailText)
        edtPass = findViewById(R.id.passwordText)
    }

    fun girisYap(view: View){
     auth.signInWithEmailAndPassword(edtEmail.text.toString(),edtPass.text.toString()).addOnCompleteListener { task ->
        if(task.isSuccessful){
            val guncelKullanici = auth.currentUser?.email.toString()
            Toast.makeText(this,"Hoşgeldin: ${guncelKullanici}",Toast.LENGTH_LONG).show()
            val intent = Intent(this,AnaEkranActivity::class.java)
            startActivity(intent)
            finish()   }
     }.addOnFailureListener {  exception ->
         Toast.makeText(this,exception.localizedMessage,Toast.LENGTH_LONG).show()
     }
    }

    fun kayitOl(view: View){
        val intent = Intent(this@KullaniciActivity, KayitOlActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.anim_in, R.anim.anim_out)
    }

}