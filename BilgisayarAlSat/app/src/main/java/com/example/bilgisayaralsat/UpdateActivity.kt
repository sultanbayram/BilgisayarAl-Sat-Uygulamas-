package com.example.bilgisayaralsat

import android.Manifest
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*

import kotlinx.android.synthetic.main.update_dialog.*
import java.util.*

class UpdateActivity : AppCompatActivity() {
    var imageView: ImageView? =null
    private lateinit var edtilanBaslik: EditText

    private lateinit var edtilanAciklama: EditText
    private lateinit var edtilanFiyat: EditText
    private lateinit  var edtmarkaBilgi: EditText
    private lateinit var edtramBilgi: EditText
    private lateinit var edttelefonBilgi: EditText
    private lateinit var edtislemciBilgi: EditText
    private lateinit var edteboyutuBilgi: EditText
    private lateinit var edtcozunurlukBilgi: EditText
    private lateinit var edtmodelBilgi: EditText
    private lateinit var edtyilBilgi: EditText
    private lateinit var edtdurumBilgi: EditText
    private lateinit var storage: FirebaseStorage
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseFirestore
    private var secilenGorsel: Uri?=null
    private var secilenBitmap: Bitmap?=null
    var id: String = ""
    var gorselurl=""
    var UpdateEt: Button? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.update_dialog)


        storage = FirebaseStorage.getInstance()
        auth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()
        edtilanBaslik = findViewById(R.id.ilanBaslikEditText  )
        edtilanAciklama = findViewById(R.id.ilanAciklamaEditText  )
        edtilanFiyat = findViewById(R.id.ilanFiyatEditText )
        edtmarkaBilgi = findViewById(R.id.markaBilgiEditText  )
        edtramBilgi = findViewById(R.id.ramBilgiEditText  )
        edttelefonBilgi=findViewById(R.id.telefonBilgiEditText)
        edtislemciBilgi = findViewById(R.id.islemciBilgiEditText )
        edteboyutuBilgi = findViewById(R.id.eboyutuBilgiEditText  )
        edtcozunurlukBilgi = findViewById(R.id.cozunurlukBilgiEditText )
        edtmodelBilgi = findViewById(R.id.modelBilgiEditText  )
        edtyilBilgi = findViewById(R.id.yilBilgiEditText )
        edtdurumBilgi = findViewById(R.id.durumBilgiEditText  )

        imageView = findViewById(R.id.imageView)

       // gorselurl = intent.extras?.get("gorselUrl").toString()
        id = intent.extras?.get("id").toString()
        Log.d("id ", id)

        UpdateEt = findViewById<View>(R.id.UpdateEt) as Button
        UpdateEt?.setOnClickListener {
            database.collection("Post").document(id)
                .update(
                    mapOf("cozunurlugu" to edtcozunurlukBilgi.text.toString(),
                        "durumu" to edtdurumBilgi.text.toString() ,
                        "ekranboyutu" to edteboyutuBilgi.text.toString(),
                        "edtilanFiyat" to edtilanFiyat.text.toString(),
                       // "gorselurl" to gorselurl,
                    "ilanaciklamasi" to edtilanAciklama.text.toString(),
                        "ilanbaslik" to edtilanBaslik.text.toString(),
                        " islemci" to edtislemciBilgi.text.toString(),
                    "marka" to edtmarkaBilgi.text.toString(),
                        "modeli" to edtmodelBilgi.text.toString(),
                        "pcyili" to edtyilBilgi.text.toString(),
                    "ram" to edtramBilgi.text.toString(),
                    "telefon" to edttelefonBilgi.text.toString())
                    )
                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully updated!") }
                .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }
            Log.d("degisken ", edtilanBaslik.text.toString())


            val intent = Intent(this@UpdateActivity, AnaEkranActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.anim_in, R.anim.anim_out)
        }}
}

