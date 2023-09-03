package com.learning.food1.AddFamous

import android.os.Bundle
import android.text.TextUtils
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.learning.food1.Classes.ClassDevotional
import com.learning.food1.R
import com.learning.food1.databinding.ActivityAddDevotionalPlaceBinding


class AddDevotionalPlace : AppCompatActivity() {

    private lateinit var bindingAddIA: ActivityAddDevotionalPlaceBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var classDevotional: ClassDevotional

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingAddIA = ActivityAddDevotionalPlaceBinding.inflate(layoutInflater)
        setContentView(bindingAddIA.root)

        firebaseDatabase = FirebaseDatabase.getInstance()

        databaseReference = firebaseDatabase.getReference("Explorewise")


    }

    override fun onStart() {
        super.onStart()
        citySpinner()
        bindingAddIA.btnSubmitDevotional.setOnClickListener {
            saveDevotionalPlacesData()
        }

    }

    private fun saveDevotionalPlacesData() {
        // val devotional_image = bindingAddIA.tvImgNamePreviewDevotional
        // val devotional_image_name:String = bindingAddIA.tvImgNamePreviewDevotional.text.toString()
        val devotional_city: String = bindingAddIA.spSelectCityNameDevotional.toString()
        val devotional_name: String = bindingAddIA.etPlaceNameDevotional.text.toString()
        val devotional_about: String = bindingAddIA.etAboutDevotional.text.toString()
        val devotional_area: String = bindingAddIA.etAreaDevotional.text.toString()
        val devotional_additional_address_info: String =
            bindingAddIA.etAdditionalDevotional.text.toString()
        val devotional_postal_code: String = bindingAddIA.etPostalCodeDevotional.text.toString()
        val devotional_contact_number: String =
            bindingAddIA.etContactNumberDevotional.text.toString()
        val devotional_email_address: String = bindingAddIA.etEmailDevotional.text.toString()
        val devotional_website_url: String = bindingAddIA.etWebsiteDevotional.text.toString()


//        if (devotional_name.isEmpty()) {
//            bindingAddIA.etPlaceNameDevotional.error = "Please Enter Devotional Place Name!!"
//        }
//        if (devotional_area.isEmpty()) {
//            bindingAddIA.etAreaDevotional.error = "Please Enter Devotional Place Nearest Area!!"
//        }
//        if (devotional_about.isEmpty()) {
//            bindingAddIA.etAboutDevotional.error =
//                "You Must have to give some information About This Place!!"
//        }
//        if (devotional_postal_code.isEmpty()) {
//            bindingAddIA.etPostalCodeDevotional.error =
//                "Please Enter Devotional Place Postal Code!!"
//        }


        if (TextUtils.isEmpty(devotional_name) && TextUtils.isEmpty(devotional_about) && TextUtils.isEmpty(devotional_area) && TextUtils.isEmpty(devotional_postal_code)
        ) {
            Toast.makeText(this@AddDevotionalPlace, "Some Data Are Missing", Toast.LENGTH_SHORT)
                .show()
        } else {
            val devPlaceID = databaseReference.push().key!!

            val devPlaceInfo = ClassDevotional(
                devPlaceID,
                devotional_name,
                devotional_about,
                devotional_area,
                devotional_contact_number,
                devotional_additional_address_info,
                devotional_email_address,
                devotional_website_url,
                devotional_city
            )

            databaseReference.child(devPlaceID).setValue(devPlaceInfo).addOnCompleteListener() {
                    Toast.makeText(this, "Data Saved", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener { err ->
                    Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_SHORT).show()
                }
        }

//        when(TextUtils.isEmpty) {
//
//        }

    }


    private fun citySpinner() {
        val options = resources.getStringArray(R.array.cities)
        val spinner = bindingAddIA.spSelectCityNameDevotional

        if (spinner != null) {
            val adapter = ArrayAdapter(this, R.layout.spinner_layout, options)
            spinner.adapter = adapter
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
    }
}






