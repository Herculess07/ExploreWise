package com.learning.food1.AddFamous

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.learning.food1.Main.Validation
import com.learning.food1.Model.add.ClassPlace
import com.learning.food1.R
import com.learning.food1.databinding.ActivityAddFamousPlaceBinding


class AddFamousPlace : Validation() {

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

        databaseReference = firebaseDatabase.getReference("Users/FamousPlaces")
        init()
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            finish()
        }
    }

    private fun init() {
        initText()

        m.btnUploadImagePlace.setOnClickListener {
            val pickImg = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            displayIMG.launch(pickImg)

        }
        m.btnSubmitPlace.setOnClickListener {
            savePlacePlacesData()
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

    private fun initText() {
        m.abAddPlace.txtTitle.text = getString(R.string.add_famous_place)
        m.abAddPlace.txtTitle.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
        m.abAddPlace.imgBookmark.visibility = View.GONE
        m.abAddPlace.imgBack.setOnClickListener {
            finish()
        }

    }


    private fun validateFields(
        name: String,
        about: String,
        area: String,
        city: String,
        state: String,
        postalCode: String,
    ): Boolean {
        return (
                validateEdittext(m.etPlaceNamePlace, name) &&
                        validateEdittext(m.etAboutPlace, about) &&
                        validateEdittext(m.etAreaPlace, area) &&
                        validateEdittext(m.spSelectCityNamePlace, city) &&
                        validateEdittext(m.etStatePlace, state) &&
                        validatePostalCode(m.etPostalCodePlace, postalCode)
                )
    }



    private fun savePlacePlacesData() {
        val place_name: String = m.etPlaceNamePlace.text.toString().trim()
        val place_about: String = m.etAboutPlace.text.toString()
//      val place_image: ImageView = m.tvImgNamePreviewPlace
        // val place_image_name: String = m.tvImgNamePreviewPlace.text.toString()
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


        if (validateFields(
                place_name,
                place_about,
                place_area,
                place_city,
                place_state,
                place_postal_code
            )
        ) {
            famPlaceID = databaseReference.push().key!!
            try {
                val famPlaceInfo = ClassPlace(
                    famPlaceID,
                    place_name,
                    place_about,
                    // place_image_name,
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
                    uploadImage {
                        Toast.makeText(this, "Thanks!", Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener { err ->
                    Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this, "${e.message}", Toast.LENGTH_SHORT).show()
            }
        }

    }


    private val displayIMG = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            val data = it.data
            imageUri = data?.data!!
            m.imgCard.visibility = View.VISIBLE
            m.imageViewAddPlace.visibility = View.VISIBLE
            m.imageViewAddPlace.setImageURI(imageUri)

            m.tvImgNamePreviewPlace.visibility = View.VISIBLE
            val imgName = imageUri.path
            m.tvImgNamePreviewPlace.text = imgName
        }
    }


    // on below line creating a function to upload our image.
    private fun uploadImage(callback: () -> Unit) {
        if (m.imageViewAddPlace.drawable == null) {
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show()
            return
        }

        m.pbPlace.visibility = View.VISIBLE
        val sRef =
            FirebaseStorage.getInstance().reference.child("Users/FamousPlaces").child(famPlaceID)
        try {
            sRef
                .putFile(imageUri)
                .addOnSuccessListener {
                    m.pbPlace.visibility = View.GONE
                    finish()
                }.addOnFailureListener {
                    m.pbPlace.visibility = View.GONE
                    Toast.makeText(this, "Error ${it.message}", Toast.LENGTH_SHORT).show()
                }
        } catch (e: Error) {
            Toast.makeText(this, "${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun redirectCoordinatePicker() {
        val openURL = Intent(android.content.Intent.ACTION_VIEW)
        openURL.data = Uri.parse("https://www.latlong.net/")
        startActivity(openURL)
    }

}