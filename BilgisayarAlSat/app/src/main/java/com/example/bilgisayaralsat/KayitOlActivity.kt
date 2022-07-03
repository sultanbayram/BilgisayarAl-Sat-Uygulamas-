package com.example.bilgisayaralsat

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class KayitOlActivity: AppCompatActivity()  {
    private lateinit var auth: FirebaseAuth
    private lateinit var edtEmail: EditText
    private lateinit var edtPass: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kayit_ol)

        auth = FirebaseAuth.getInstance()
        val guncelKullanici =auth.currentUser

        if (guncelKullanici != null) {
            val intent = Intent(  this, AnaEkranActivity::class.java)
            startActivity(intent)
            finish()}
        edtEmail = findViewById(R.id.emailText)
        edtPass = findViewById(R.id.passwordText)
    }
    fun kayitOl(view: View){
        val email = edtEmail.text.toString()
        val sifre = edtPass.text.toString()

        auth.createUserWithEmailAndPassword(email, sifre).addOnCompleteListener { task ->
            if(task.isSuccessful){
                val intent = Intent(this,AnaEkranActivity::class.java)
                startActivity(intent)
                finish()
            }
        }.addOnFailureListener{ exception ->
            Toast.makeText(applicationContext,exception.localizedMessage, Toast.LENGTH_LONG).show()
        }
    }
}