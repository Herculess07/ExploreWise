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
import com.learning.food1.Model.add.ClassPlace
import com.learning.food1.databinding.ActivityAddFamousPlaceBinding


class AddFamousPlace : AppCompatActivity() {

    private lateinit var m: ActivityAddFamousPlaceBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var imageUri: Uri
    private var famPlaceID: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        m = ActivityAddFamousPlaceBinding.inflate(layoutInflater)
        setContentView(m.root)

        firebaseDatabase = FirebaseDatabase.getInstance()

        databaseReference = firebaseDatabase.getReference("Users").child("FamousPlaces")
        init()
    }

    private fun init() {
        m.btnUploadImagePlace.setOnClickListener {
            val pickImg = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            displayIMG.launch(pickImg)

        }
        m.btnSubmitPlace.setOnClickListener {
            savePlacePlacesData()
            uploadImage()
        }
        m.btnCancelPlace.setOnClickListener {
            m.etPlaceNamePlace.setText("")
            m.etAboutPlace.setText("")
            m.tvImgNamePreviewPlace.text = ""
            m.etAreaPlace.setText("")
            m.etAdditionalPlace.setText("")
            m.spSelectCityNamePlace.setText("")
            m.etStatePlace.setText("")
            m.etPostalCodePlace.setText("")
            m.etContactNumberPlace.setText("")
            m.etEmailPlace.setText("")
            m.etWebsitePlace.setText("")
        }
        m.tvRedirectLatLngPlace.setOnClickListener { redirectCoordinatePicker() }

    }

    private fun savePlacePlacesData() {
        val place_name: String = m.etPlaceNamePlace.text.toString()
        val place_about: String = m.etAboutPlace.text.toString()
//      val place_image: ImageView = m.tvImgNamePreviewPlace
        val place_image_name: String = m.tvImgNamePreviewPlace.text.toString()
        val place_area: String = m.etAreaPlace.text.toString()
        val place_additional_address_info: String =
            m.etAdditionalPlace.text.toString()
        val place_city: String = m.spSelectCityNamePlace.text.toString()
        val place_state: String = m.etStatePlace.text.toString()
        val place_postal_code: String = m.etPostalCodePlace.text.toString()
        val place_contact_number: String =
            m.etContactNumberPlace.text.toString()
        val place_email_address: String = m.etEmailPlace.text.toString()
        val place_website_url: String = m.etWebsitePlace.text.toString()


        if (TextUtils.isEmpty(place_name) && TextUtils.isEmpty(place_about) && TextUtils.isEmpty(
                place_area
            ) && TextUtils.isEmpty(place_postal_code)
        ) {
            Toast.makeText(this@AddFamousPlace, "Some Data Are Missing", Toast.LENGTH_SHORT)
                .show()
        } else {
            famPlaceID = databaseReference.push().key!!

            val famPlaceInfo = ClassPlace(
                famPlaceID,
                place_name,
                place_about,
                place_image_name,
                place_area,
                place_additional_address_info,
                place_city,
                place_state,
                place_postal_code,
                place_contact_number,
                place_email_address,
                place_website_url
            )
            // place_image,

            databaseReference.child(famPlaceID).setValue(famPlaceInfo).addOnCompleteListener() {
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
            m.imageViewAddPlace.setImageURI(imageUri)
        }
    }


    // on below line creating a function to upload our image.
    private fun uploadImage() {
        m.pbPlace.visibility = View.VISIBLE
        val sRef = FirebaseStorage.getInstance().reference.child("Users/FamousPlaces").child(famPlaceID)

        sRef
            .putFile(imageUri)
            .addOnSuccessListener {
                m.pbPlace.visibility = View.GONE
                Snackbar.make(m.root, "Image Saved", Snackbar.LENGTH_LONG).show()
                finish()
            }.addOnFailureListener {
                m.pbPlace.visibility = View.GONE
                Toast.makeText(this, "Error ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun redirectCoordinatePicker() {
        val openURL = Intent(android.content.Intent.ACTION_VIEW)
        openURL.data = Uri.parse("https://www.latlong.net/")
        startActivity(openURL)
    }

}