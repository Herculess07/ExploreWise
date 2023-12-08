package com.learning.food1.BottomNavFragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.learning.food1.AddFamous.AddDevotionalPlace
import com.learning.food1.AddFamous.AddFamousFood
import com.learning.food1.AddFamous.AddFamousPlace
import com.learning.food1.databinding.FragmentAddBinding


class AddFragment : DialogFragment() {

    private lateinit var m: FragmentAddBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        m = FragmentAddBinding.inflate(layoutInflater)
        init()
        return m.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }


    private fun init(){
        m.llAddFamousFood.setOnClickListener {
            val intent = Intent(this.requireContext(), AddFamousFood::class.java)
            this.startActivity(intent)
        }

        m.llAddDevotionalPlace.setOnClickListener {
            val intent = Intent(this.requireContext(), AddDevotionalPlace::class.java)
            this.startActivity(intent)
        }

        m.llAddPlace.setOnClickListener {
            val intent = Intent(this.requireContext(), AddFamousPlace::class.java)
            this.startActivity(intent)
        }
    }

}