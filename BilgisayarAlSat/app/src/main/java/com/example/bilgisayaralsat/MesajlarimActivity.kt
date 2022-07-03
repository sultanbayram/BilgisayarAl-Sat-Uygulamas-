package com.example.bilgisayaralsat

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MesajlarimActivity: AppCompatActivity() {
    var anaMenu: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mesajlar)

        anaMenu = findViewById<View>(R.id.anaMenu) as Button
        anaMenu!!.setOnClickListener {
            val intent = Intent(this@MesajlarimActivity, AnaEkranActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.anim_in, R.anim.anim_out)
        }

    }
}