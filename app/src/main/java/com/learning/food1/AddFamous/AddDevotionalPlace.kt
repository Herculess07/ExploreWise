package com.learning.food1.AddFamous

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.learning.food1.Classes.ClassDevotional
import com.learning.food1.databinding.ActivityAddDevotionalPlaceBinding
import java.util.UUID


class AddDevotionalPlace : AppCompatActivity() {

    private lateinit var m: ActivityAddDevotionalPlaceBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var imageUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        m = ActivityAddDevotionalPlaceBinding.inflate(layoutInflater)
        setContentView(m.root)

        firebaseDatabase = FirebaseDatabase.getInstance()

        databaseReference = firebaseDatabase.getReference("Users").child("DevotionalPlaces")

    }

    override fun onStart() {
        super.onStart()

        m.btnUploadImageDevotional.setOnClickListener {
            val pickImg = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            displayIMG.launch(pickImg)

        }
        m.btnSubmitDevotional.setOnClickListener {
            saveDevotionalPlacesData()
            uploadImage()
        }
        m.btnCancelDevotional.setOnClickListener {
            m.etPlaceNameDevotional.setText("")
            m.etAboutDevotional.setText("")
            m.tvImgNamePreviewDevotional.setText("")
            m.etAreaDevotional.setText("")
            m.etAdditionalDevotional.setText("")
            m.spSelectCityNameDevotional.setText("")
            m.etStateDevotional.setText("")
            m.etPostalCodeDevotional.setText("")
            m.etContactNumberDevotional.setText("")
            m.etEmailDevotional.setText("")
            m.etWebsiteDevotional.setText("")
        }
        m.tvRedirectLatLngDevotional.setOnClickListener{redirectCoordinatePicker()}

    }

    private fun saveDevotionalPlacesData() {
        val devotional_name: String = m.etPlaceNameDevotional.text.toString()
        val devotional_about: String = m.etAboutDevotional.text.toString()
//      val devotional_image: ImageView = m.tvImgNamePreviewDevotional
        val devotional_image_name: String = m.tvImgNamePreviewDevotional.text.toString()
        val devotional_area: String = m.etAreaDevotional.text.toString()
        val devotional_additional_address_info: String =
            m.etAdditionalDevotional.text.toString()
        val devotional_city: String = m.spSelectCityNameDevotional.text.toString()
        val devotional_state: String = m.etStateDevotional.text.toString()
        val devotional_postal_code: String = m.etPostalCodeDevotional.text.toString()
        val devotional_contact_number: String =
            m.etContactNumberDevotional.text.toString()
        val devotional_email_address: String = m.etEmailDevotional.text.toString()
        val devotional_website_url: String = m.etWebsiteDevotional.text.toString()


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


    private val displayIMG = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            val data = it.data
            imageUri = data?.data!!
            m.imageViewAddDevotional.setImageURI(imageUri)
        }
    }


    // on below line creating a function to upload our image.
    fun uploadImage() {
        if (imageUri != null) {
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Uploading...")
            progressDialog.setMessage("Uploading your image..")
            progressDialog.show()

            val ref: StorageReference =
                FirebaseStorage.getInstance().reference.child(UUID.randomUUID().toString())
            ref.putFile(imageUri!!).addOnSuccessListener {

                progressDialog.dismiss()
                Toast.makeText(applicationContext, "Image Uploaded..", Toast.LENGTH_SHORT).show()
                finish()
            }.addOnFailureListener {
                progressDialog.dismiss()
                Toast.makeText(applicationContext, "Fail to Upload Image..", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun redirectCoordinatePicker(){
        val openURL = Intent(android.content.Intent.ACTION_VIEW)
        openURL.data = Uri.parse("https://www.latlong.net/")
        startActivity(openURL)
    }

}
