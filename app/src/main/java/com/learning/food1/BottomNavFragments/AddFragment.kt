package com.learning.food1.BottomNavFragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.learning.food1.AddFamous.AddDevotionalPlace
import com.learning.food1.AddFamous.AddFamousFood
import com.learning.food1.AddFamous.AddFamousPlace
import com.learning.food1.databinding.FragmentAddBinding


class AddFragment : BottomSheetDialogFragment() {

    private lateinit var bindingAddFrag: FragmentAddBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        bindingAddFrag = FragmentAddBinding.inflate(layoutInflater)


        init()
        return bindingAddFrag.root

    }


    private fun init(){
        bindingAddFrag.llAddFamousFood.setOnClickListener {
            val intent = Intent(this.requireContext(), AddFamousFood::class.java)
            this.startActivity(intent)
        }

        bindingAddFrag.llAddDevotionalPlace.setOnClickListener {
            val intent = Intent(this.requireContext(), AddDevotionalPlace::class.java)
            this.startActivity(intent)
        }

        bindingAddFrag.llAddPlace.setOnClickListener {
            val intent = Intent(this.requireContext(), AddFamousPlace::class.java)
            this.startActivity(intent)
        }
    }

}