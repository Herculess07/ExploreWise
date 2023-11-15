package com.learning.food1.AddFamous

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.learning.food1.Model.add.ClassDevotional
import com.learning.food1.databinding.ActivityAddDevotionalPlaceBinding


class AddDevotionalPlace : AppCompatActivity() {

    private lateinit var m: ActivityAddDevotionalPlaceBinding
    private lateinit var dbRef: DatabaseReference
    private lateinit var fdb: DatabaseReference

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var imageUri: Uri
    var devPlaceID: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        m = ActivityAddDevotionalPlaceBinding.inflate(layoutInflater)
        setContentView(m.root)

        firebaseDatabase = FirebaseDatabase.getInstance()


        init()
    }

    private fun init() {
        dbRef = firebaseDatabase.getReference("Users/DevotionalPlaces")

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
            m.tvImgNamePreviewDevotional.text = ""
            m.etAreaDevotional.setText("")
            m.etAdditionalDevotional.setText("")
            m.spSelectCityNameDevotional.setText("")
            m.etStateDevotional.setText("")
            m.etPostalCodeDevotional.setText("")
            m.etContactNumberDevotional.setText("")
            m.etEmailDevotional.setText("")
            m.etWebsiteDevotional.setText("")
            finish()
        }
        m.tvRedirectLatLngDevotional.setOnClickListener { redirectCoordinatePicker() }
    }

    private fun saveDevotionalPlacesData() {
        val devotional_name = m.etPlaceNameDevotional.text.toString()
        val devotional_about = m.etAboutDevotional.text.toString()
        /*val devotional_image: ImageView = m.tvImgNamePreviewDevotional*/
        val devotional_image_name = m.tvImgNamePreviewDevotional.text.toString()
        val devotional_area = m.etAreaDevotional.text.toString()
        val devotional_additional_address_info =
            m.etAdditionalDevotional.text.toString()
        val devotional_city = m.spSelectCityNameDevotional.text.toString()
        val devotional_state = m.etStateDevotional.text.toString()
        val devotional_postal_code = m.etPostalCodeDevotional.text.toString()
        val devotional_contact_number =
            m.etContactNumberDevotional.text.toString()
        val devotional_email_address = m.etEmailDevotional.text.toString()
        val devotional_website_url = m.etWebsiteDevotional.text.toString()


        if (TextUtils.isEmpty(devotional_name) && TextUtils.isEmpty(devotional_about) && TextUtils.isEmpty(
                devotional_area
            ) && TextUtils.isEmpty(devotional_postal_code) && TextUtils.isEmpty(devotional_city)
        ) {
            Toast.makeText(this@AddDevotionalPlace, "Some Data Are Missing", Toast.LENGTH_SHORT)
                .show()
        } else {
            devPlaceID = dbRef.push().key!!

            val devPlaceInfo = ClassDevotional(
                devPlaceID,
                devotional_name,
                devotional_about,
                /*devotional_image_name = devotional_image_name,*/
                devotional_area,
                devotional_additional_address_info,
                devotional_city,
                devotional_state,
                devotional_postal_code,
                devotional_contact_number,
                devotional_email_address,
                devotional_website_url
            )
            // devotional_image,

            dbRef.child(devPlaceID).setValue(devPlaceInfo).addOnCompleteListener() {
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
    private fun uploadImage() {
        m.pbDev.visibility = View.VISIBLE
        val sRef = FirebaseStorage.getInstance().reference.child("Users/DevotionalPlaces").child(devPlaceID)

        sRef
            .putFile(imageUri)
            .addOnSuccessListener {
                m.pbDev.visibility = View.GONE
                Snackbar.make(m.root, "Image Saved", Snackbar.LENGTH_LONG).show()
                finish()
            }.addOnFailureListener {
                m.pbDev.visibility = View.GONE
                Toast.makeText(this, "Error ${it.message}", Toast.LENGTH_SHORT).show()
            }

    }

    private fun redirectCoordinatePicker() {
        val openURL = Intent(Intent.ACTION_VIEW)
        openURL.data = Uri.parse("https://www.latlong.net/")
        startActivity(openURL)
    }

}