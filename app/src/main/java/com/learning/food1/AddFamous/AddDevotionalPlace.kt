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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.learning.food1.Main.Validation
import com.learning.food1.Model.add.ClassDevotional
import com.learning.food1.R
import com.learning.food1.databinding.ActivityAddDevotionalPlaceBinding


class AddDevotionalPlace : Validation() {

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
        dbRef = firebaseDatabase.getReference("Users/DevotionalPlaces")
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
        m.btnUploadImageDevotional.setOnClickListener {
            val pickImg = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            displayIMG.launch(pickImg)
        }
        m.btnSubmitDevotional.setOnClickListener {
            saveDevotionalPlacesData()
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


    private fun initText() {
        m.abAddDev.txtTitle.text = getString(R.string.add_devotional_place)
        m.abAddDev.txtTitle.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
        m.abAddDev.imgBookmark.visibility = View.GONE
        m.abAddDev.imgBack.setOnClickListener {
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
                validateEdittext(m.etPlaceNameDevotional, name) &&
                        validateEdittext(m.etAboutDevotional, about) &&
                        validateEdittext(m.etAreaDevotional, area) &&
                        validateEdittext(m.spSelectCityNameDevotional, city) &&
                        validateEdittext(m.etStateDevotional, state) &&
                        validatePostalCode(m.etPostalCodeDevotional, postalCode)
                )
    }


    private fun saveDevotionalPlacesData() {
        val devotional_name = m.etPlaceNameDevotional.text.toString().trim()
        val devotional_about = m.etAboutDevotional.text.toString()
        /*val devotional_image: ImageView = m.tvImgNamePreviewDevotional*/
        /*val devotional_image_name = m.tvImgNamePreviewDevotional.text.toString()*/
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

        if (validateFields(
                devotional_name,
                devotional_about,
                devotional_area,
                devotional_city,
                devotional_state,
                devotional_postal_code
            )
        ) {
            devPlaceID = dbRef.push().key!!
            val uid = FirebaseAuth.getInstance().uid
            try {

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
                    devotional_website_url,
                    uid
                )
                // devotional_image,

                dbRef.child(devPlaceID).setValue(devPlaceInfo).addOnCompleteListener {
                    uploadImage {
                        Toast.makeText(this, "Thanks!", Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener { err ->
                    Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_SHORT).show()
                }

            } catch (e: Error) {
                e.printStackTrace()
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
            m.imageViewAddDevotional.visibility = View.VISIBLE
            m.imageViewAddDevotional.setImageURI(imageUri)

            m.tvImgNamePreviewDevotional.visibility = View.VISIBLE
            val imgName = imageUri.path
            m.tvImgNamePreviewDevotional.text = imgName

        }
    }

    // on below line creating a function to upload our image.
    private fun uploadImage(callback: () -> Unit) {
        if (m.imageViewAddDevotional.drawable == null) {
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show()
            return
        }
        m.pbDev.visibility = View.VISIBLE
        val sRef = FirebaseStorage.getInstance().reference.child("Users/DevotionalPlaces")
            .child(devPlaceID)

        try {
            sRef
                .putFile(imageUri)
                .addOnSuccessListener {
                    m.pbDev.visibility = View.GONE
                    finish()
                }.addOnFailureListener {
                    m.pbDev.visibility = View.GONE
                    Toast.makeText(this, "Error ${it.message}", Toast.LENGTH_SHORT).show()
                }
        } catch (e: Error) {
            e.printStackTrace()
        }

    }

    private fun redirectCoordinatePicker() {
        val openURL = Intent(Intent.ACTION_VIEW)
        openURL.data = Uri.parse("https://www.latlong.net/")
        startActivity(openURL)
    }

}