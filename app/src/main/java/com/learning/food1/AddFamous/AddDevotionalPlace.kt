package com.learning.food1.AddFamous

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.text.TextUtils
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.learning.food1.Classes.ClassDevotional
import com.learning.food1.R
import com.learning.food1.databinding.ActivityAddDevotionalPlaceBinding
import java.io.File
import java.util.UUID


class AddDevotionalPlace : AppCompatActivity() {

    private lateinit var bindingAddIA: ActivityAddDevotionalPlaceBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var imageUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingAddIA = ActivityAddDevotionalPlaceBinding.inflate(layoutInflater)
        setContentView(bindingAddIA.root)

        firebaseDatabase = FirebaseDatabase.getInstance()

        databaseReference = firebaseDatabase.getReference("Users").child("DevotionalPlaces")

    }

    override fun onStart() {
        super.onStart()

        bindingAddIA.btnUploadImageDevotional.setOnClickListener {
            val pickImg = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            displayIMG.launch(pickImg)

        }
        bindingAddIA.btnSubmitDevotional.setOnClickListener {
            saveDevotionalPlacesData()
            uploadImage()
        }
        bindingAddIA.btnCancelDevotional.setOnClickListener {
            bindingAddIA.etPlaceNameDevotional.setText("")
            bindingAddIA.etAboutDevotional.setText("")
            bindingAddIA.tvImgNamePreviewDevotional.setText("")
            bindingAddIA.etAreaDevotional.setText("")
            bindingAddIA.etAdditionalDevotional.setText("")
            bindingAddIA.spSelectCityNameDevotional.setText("")
            bindingAddIA.etStateDevotional.setText("")
            bindingAddIA.etPostalCodeDevotional.setText("")
            bindingAddIA.etContactNumberDevotional.setText("")
            bindingAddIA.etEmailDevotional.setText("")
            bindingAddIA.etWebsiteDevotional.setText("")
        }

    }

    private fun saveDevotionalPlacesData() {
        val devotional_name: String = bindingAddIA.etPlaceNameDevotional.text.toString()
        val devotional_about: String = bindingAddIA.etAboutDevotional.text.toString()
//      val devotional_image: ImageView = bindingAddIA.tvImgNamePreviewDevotional
        val devotional_image_name: String = bindingAddIA.tvImgNamePreviewDevotional.text.toString()
        val devotional_area: String = bindingAddIA.etAreaDevotional.text.toString()
        val devotional_additional_address_info: String =
            bindingAddIA.etAdditionalDevotional.text.toString()
        val devotional_city: String = bindingAddIA.spSelectCityNameDevotional.text.toString()
        val devotional_state: String = bindingAddIA.etStateDevotional.text.toString()
        val devotional_postal_code: String = bindingAddIA.etPostalCodeDevotional.text.toString()
        val devotional_contact_number: String =
            bindingAddIA.etContactNumberDevotional.text.toString()
        val devotional_email_address: String = bindingAddIA.etEmailDevotional.text.toString()
        val devotional_website_url: String = bindingAddIA.etWebsiteDevotional.text.toString()


        if (TextUtils.isEmpty(devotional_name) && TextUtils.isEmpty(devotional_about) && TextUtils.isEmpty(
                devotional_area
            ) && TextUtils.isEmpty(devotional_postal_code)
        ) {
            Toast.makeText(this@AddDevotionalPlace, "Some Data Are Missing", Toast.LENGTH_SHORT)
                .show()
        } else {
            val devPlaceID = databaseReference.push().key!!

            val devPlaceInfo = ClassDevotional(
                devPlaceID,
                devotional_name,
                devotional_about,

                devotional_image_name,
                devotional_area,
                devotional_additional_address_info,
                devotional_city,
                devotional_state,
                devotional_postal_code,
                devotional_contact_number,
                devotional_email_address,
                devotional_website_url
            )
            //                devotional_image,

            databaseReference.child(devPlaceID).setValue(devPlaceInfo).addOnCompleteListener() {
                Toast.makeText(this, "Data Saved", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_SHORT).show()
            }
        }

    }


    private val displayIMG =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                val data = it.data
                imageUri = data?.data!!
                bindingAddIA.imageViewAddDevotional.setImageURI(imageUri)
            }
        }


    // on below line creating a function to upload our image.
    fun uploadImage() {
        // on below line checking weather our file uri is null or not.
        if (imageUri != null) {
            // on below line displaying a progress dialog when uploading an image.
            val progressDialog = ProgressDialog(this)
            // on below line setting title and message for our progress dialog and displaying our progress dialog.
            progressDialog.setTitle("Uploading...")
            progressDialog.setMessage("Uploading your image..")
            progressDialog.show()

            // on below line creating a storage refrence for firebase storage and creating a child in it with
            // random uuid.
            val ref: StorageReference = FirebaseStorage.getInstance().getReference()
                .child(UUID.randomUUID().toString())
            // on below line adding a file to our storage.
            ref.putFile(imageUri!!).addOnSuccessListener {
                // this method is called when file is uploaded.
                // in this case we are dismissing our progress dialog and displaying a toast message
                progressDialog.dismiss()
                Toast.makeText(applicationContext, "Image Uploaded..", Toast.LENGTH_SHORT).show()
                finish()
            }.addOnFailureListener {
                // this method is called when there is failure in file upload.
                // in this case we are dismissing the dialog and displaying toast message
                progressDialog.dismiss()
                Toast.makeText(applicationContext, "Fail to Upload Image..", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}
