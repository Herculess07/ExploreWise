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
import com.learning.food1.Model.add.ClassFood
import com.learning.food1.databinding.ActivityAddFamousFoodBinding


class AddFamousFood : AppCompatActivity() {

    private lateinit var m: ActivityAddFamousFoodBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var imageUri: Uri
    private var famFoodId: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        m = ActivityAddFamousFoodBinding.inflate(layoutInflater)
        setContentView(m.root)

        firebaseDatabase = FirebaseDatabase.getInstance()

        databaseReference = firebaseDatabase.getReference("Users").child("FamousFood")
        init()
    }

    private fun init() {
        m.btnUploadImageFood.setOnClickListener {
            val pickImg = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            displayIMG.launch(pickImg)

        }
        m.btnSubmitFood.setOnClickListener {
            saveFoodPlacesData()
            uploadImage()
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
        }
        m.tvRedirectLatLngFood.setOnClickListener { redirectCoordinatePicker() }

    }

    private fun saveFoodPlacesData() {
        val food_name: String = m.etPlaceNameFood.text.toString()
        val food_about: String = m.etAboutFood.text.toString()
//      val food_image: ImageView = m.tvImgNamePreviewFood
        val food_image_name: String = m.tvImgNamePreviewFood.text.toString()
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


        if (TextUtils.isEmpty(food_name) && TextUtils.isEmpty(food_about) && TextUtils.isEmpty(
                food_area
            ) && TextUtils.isEmpty(food_postal_code)
        ) {
            Toast.makeText(this@AddFamousFood, "Some Data Are Missing", Toast.LENGTH_SHORT)
                .show()
        } else {
            famFoodId = databaseReference.push().key!!

            val famFoodInfo = ClassFood(
                famFoodId,
                food_name = food_name,
                food_about = food_about,
                /*food_image_name,*/
                food_area = food_area,
                food_additional_address_info = food_additional_address_info,
                food_city,
                food_state,
                food_postal_code,
                food_contact_number,
                food_email_address,
                food_website_url
            )
            // food_image,

            databaseReference.child(famFoodId).setValue(famFoodInfo).addOnCompleteListener() {
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
            m.imageViewAddFood.setImageURI(imageUri)
        }
    }


    // on below line creating a function to upload our image.
    private fun uploadImage() {
        m.pbFood.visibility = View.VISIBLE
        val sRef = FirebaseStorage.getInstance().reference.child("Users/FamousFood").child(famFoodId)

        sRef
            .putFile(imageUri)
            .addOnSuccessListener {
                m.pbFood.visibility = View.GONE
                Snackbar.make(m.root, "Image Saved", Snackbar.LENGTH_LONG).show()
                finish()
            }.addOnFailureListener {
                m.pbFood.visibility = View.GONE
                Toast.makeText(this, "Error ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun redirectCoordinatePicker() {
        val openURL = Intent(android.content.Intent.ACTION_VIEW)
        openURL.data = Uri.parse("https://www.latlong.net/")
        startActivity(openURL)
    }

}