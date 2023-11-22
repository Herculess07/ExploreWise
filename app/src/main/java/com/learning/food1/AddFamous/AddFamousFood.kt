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
import com.learning.food1.Model.add.ClassFood
import com.learning.food1.R
import com.learning.food1.databinding.ActivityAddFamousFoodBinding


class AddFamousFood : Validation() {

    private lateinit var m: ActivityAddFamousFoodBinding
    private lateinit var dbRef: DatabaseReference
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var imageUri: Uri
    private var famFoodId: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        m = ActivityAddFamousFoodBinding.inflate(layoutInflater)
        setContentView(m.root)

        firebaseDatabase = FirebaseDatabase.getInstance()

        dbRef = firebaseDatabase.getReference("Users/FamousFood")
        init()

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }

    private fun init() {
        initText()

        m.btnUploadImageFood.setOnClickListener {
            val pickImg = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            displayIMG.launch(pickImg)

        }
        m.btnSubmitFood.setOnClickListener {
            saveFoodPlacesData()
        }
        m.btnCancelFood.setOnClickListener {
            m.etPlaceNameFood.setText("")
            m.etAboutFood.setText("")
            m.tvImgNamePreviewFood.text = ""
            m.etAreaFood.setText("")
            m.etAdditionalFood.setText("")
            m.spSelectCityNameFood.setText("")
            m.etStateFood.setText("")
            m.etPostalCodeFood.setText("")
            m.etContactNumberFood.setText("")
            m.etEmailFood.setText("")
            m.etWebsiteFood.setText("")
            finish()
        }
        m.tvRedirectLatLngFood.setOnClickListener { redirectCoordinatePicker() }

    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            finish()
        }
    }

    private fun initText() {
        m.abAddFood.txtTitle.text = getString(R.string.add_famous_food)
        m.abAddFood.txtTitle.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
        m.abAddFood.imgBookmark.visibility = View.GONE
        m.abAddFood.imgBack.setOnClickListener {
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
                validateEdittext(m.etPlaceNameFood, name) &&
                        validateEdittext(m.etAboutFood, about) &&
                        validateEdittext(m.etAreaFood, area) &&
                        validateEdittext(m.spSelectCityNameFood, city) &&
                        validateEdittext(m.etStateFood, state) &&
                        validatePostalCode(m.etPostalCodeFood, postalCode)
                )
    }


    private fun saveFoodPlacesData() {
        val food_name: String = m.etPlaceNameFood.text.toString().trim()
        val food_about: String = m.etAboutFood.text.toString()
        // val food_image: ImageView = m.tvImgNamePreviewFood
        // val food_image_name: String = m.tvImgNamePreviewFood.text.toString()
        val food_area: String = m.etAreaFood.text.toString()
        val food_additional_address_info: String =
            m.etAdditionalFood.text.toString()
        val food_city: String = m.spSelectCityNameFood.text.toString()
        val food_state: String = m.etStateFood.text.toString()
        val food_postal_code: String = m.etPostalCodeFood.text.toString()
        val food_contact_number: String =
            m.etContactNumberFood.text.toString()
        val food_email_address: String = m.etEmailFood.text.toString()
        val food_website_url: String = m.etWebsiteFood.text.toString()


        if (validateFields(
                food_name,
                food_about,
                food_area,
                food_city,
                food_state,
                food_postal_code
            )
        ) {
            famFoodId = dbRef.push().key!!
            val uid = FirebaseAuth.getInstance().uid
            val famFoodInfo = ClassFood(
                famFoodId,
                food_name,
                food_about,
                /*food_image_name,*/
                food_area,
                food_additional_address_info,
                food_city,
                food_state,
                food_postal_code,
                food_contact_number,
                food_email_address,
                food_website_url,
                uid
            )
            // food_image,

            dbRef.child(famFoodId).setValue(famFoodInfo).addOnCompleteListener() {
                uploadImage {
                    Toast.makeText(this, "Thanks!", Toast.LENGTH_SHORT).show()
                }
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
            m.imgCard.visibility = View.VISIBLE
            m.imageViewAddFood.visibility = View.VISIBLE
            m.imageViewAddFood.setImageURI(imageUri)

            val imgName = imageUri.path
            m.tvImgNamePreviewFood.text = imgName
        }
    }


    // on below line creating a function to upload our image.
    private fun uploadImage(callback: () -> Unit) {
        if (m.imageViewAddFood.drawable == null) {
            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show()
            return
        }

        m.pbFood.visibility = View.VISIBLE
        val sRef =
            FirebaseStorage.getInstance().reference.child("Users/FamousFood").child(famFoodId)

        try {
            sRef
                .putFile(imageUri)
                .addOnSuccessListener {
                    m.pbFood.visibility = View.GONE
                    finish()
                }.addOnFailureListener {
                    m.pbFood.visibility = View.GONE
                    Toast.makeText(this, "Error ${it.message}", Toast.LENGTH_SHORT).show()
                }
        } catch (e: Error) {
            e.printStackTrace()
        }
    }

    private fun redirectCoordinatePicker() {
        val openURL = Intent(android.content.Intent.ACTION_VIEW)
        openURL.data = Uri.parse("https://www.latlong.net/")
        startActivity(openURL)
    }

}