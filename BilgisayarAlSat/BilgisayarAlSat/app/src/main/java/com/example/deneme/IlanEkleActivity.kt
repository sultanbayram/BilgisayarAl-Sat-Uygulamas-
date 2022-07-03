package com.example.deneme

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

import java.util.*

@Suppress("DEPRECATION")
class IlanEkleActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var edtilanBaslik: EditText

    private lateinit var edtilanAciklama: EditText
    private lateinit var edtilanFiyat: EditText
    private lateinit var edtmarkaBilgi: EditText
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
    private var secilenGorsel:Uri?=null
    private var secilenBitmap:Bitmap?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ilan_ekle)

        storage= FirebaseStorage.getInstance()
        auth= FirebaseAuth.getInstance()
        database= FirebaseFirestore.getInstance()

        imageView = findViewById(R.id.imageView)

        edtilanBaslik = findViewById(R.id.ilanBaslikEditText)
        edtilanAciklama = findViewById(R.id.ilanAciklamaEditText)
        edtilanFiyat = findViewById(R.id.ilanFiyatEditText)
        edtmarkaBilgi = findViewById(R.id.markaBilgiEditText)
        edtramBilgi = findViewById(R.id.ramBilgiEditText)
        edttelefonBilgi = findViewById(R.id.telefonBilgiEditText)
        edtislemciBilgi = findViewById(R.id.islemciBilgiEditText)
        edteboyutuBilgi = findViewById(R.id.eboyutuBilgiEditText)
        edtcozunurlukBilgi = findViewById(R.id.cozunurlukBilgiEditText)
        edtmodelBilgi = findViewById(R.id.modelBilgiEditText)
        edtyilBilgi = findViewById(R.id.yilBilgiEditText)
        edtdurumBilgi = findViewById(R.id.durumBilgiEditText)

        getAllUsers()
    }

    fun paylas(view: View){
        val uuid=UUID.randomUUID()
        val gorselIsmi="${uuid}.jpg"
        val reference=storage.reference
        val gorselReference =reference.child("images").child(gorselIsmi)
        if (secilenGorsel!=null){
            gorselReference.putFile(secilenGorsel!!).addOnSuccessListener {
                val yuklenenGorselReference=FirebaseStorage.getInstance().reference.child("images").child(gorselIsmi)
                yuklenenGorselReference.downloadUrl.addOnSuccessListener { uri ->
                    val downloadUrl=uri.toString()
                    val guncelKullaniciEmaili=auth.currentUser!!.email.toString()
                    val tarih=Timestamp.now().toDate()
                    val ilanBaslik=edtilanBaslik.text.toString()
                    val telefonBilgi=edttelefonBilgi.text.toString()
                    val ilanAciklama=edtilanAciklama.text.toString()
                    val ilanFiyat=edtilanFiyat.text.toString()
                    val markaBilgi= edtmarkaBilgi.text.toString()
                    val ramBilgi= edtramBilgi.text.toString()
                    val islemciBilgi= edtislemciBilgi.text.toString()
                    val ekranBoBilgi=edteboyutuBilgi.text.toString()
                    val modelBilgi= edtmodelBilgi.text.toString()
                    val yilBilgi= edtyilBilgi.text.toString()
                    val durumBilgi= edtdurumBilgi.text.toString()
                    val cozunurlukBilgi= edtcozunurlukBilgi.text.toString()
                    val postHashMap= hashMapOf<String,Any>()

                    postHashMap.put("gorselUrl", downloadUrl)
                    postHashMap.put("kullaniciemail",guncelKullaniciEmaili)
                    postHashMap.put("ilanbaslik",ilanBaslik)
                    postHashMap.put("ilanaciklamasi",ilanAciklama)
                    postHashMap.put("telefon",telefonBilgi)
                    postHashMap.put("fiyati",ilanFiyat)
                    postHashMap.put("marka",markaBilgi)
                    postHashMap.put("ram",ramBilgi)
                    postHashMap.put("islemci",islemciBilgi)
                    postHashMap.put("ekranboyutu",ekranBoBilgi)
                    postHashMap.put("modeli",modelBilgi)
                    postHashMap.put("pcyili",yilBilgi)
                    postHashMap.put("durumu",durumBilgi)
                    postHashMap.put("cozunurlugu",cozunurlukBilgi)
                    postHashMap.put("tarih",tarih)

                    database.collection("Post").add(postHashMap).addOnCompleteListener {task->
                        if (task.isSuccessful){
                            finish()
                        }
                    }.addOnFailureListener { exception ->
                        Toast.makeText(applicationContext,exception.localizedMessage,Toast.LENGTH_LONG).show()
                    }
                }.addOnFailureListener { exception ->
                    Toast.makeText(applicationContext,exception.localizedMessage,Toast.LENGTH_LONG).show()
                }}}
    }
    private fun getAllUsers() {
        // [START get_all_users]
        database.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
        // [END get_all_users]
    }

    fun gorselSec(view: View){
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            //izni almamışız
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),1)
        }else{
            val galeriIntent= Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galeriIntent,2)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode==1){
            if (grantResults.size >0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                val galeriIntent= Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(galeriIntent,2)
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode==2&& resultCode== RESULT_OK&& data!= null){
            secilenGorsel=data.data
            if (secilenGorsel!= null){
                if (Build.VERSION.SDK_INT>=28){
                    val source=ImageDecoder.createSource(this.contentResolver,secilenGorsel!!)
                    secilenBitmap=ImageDecoder.decodeBitmap(source)
                    imageView.setImageBitmap(secilenBitmap)
                }else{
                    secilenBitmap=MediaStore.Images.Media.getBitmap(this.contentResolver,secilenGorsel)
                    imageView.setImageBitmap(secilenBitmap)
                }}
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


}