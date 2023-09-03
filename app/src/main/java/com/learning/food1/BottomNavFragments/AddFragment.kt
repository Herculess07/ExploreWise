package com.learning.food1.BottomNavFragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.learning.food1.AddFamous.AddDevotionalPlace
import com.learning.food1.AddFamous.AddFamousFood
import com.learning.food1.AddFamous.AddFamousPlace
import com.learning.food1.databinding.FragmentAddBinding


class AddFragment : Fragment() {

    private lateinit var bindingAddFrag:FragmentAddBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        // Inflate the layout for this fragment
        bindingAddFrag = FragmentAddBinding.inflate(layoutInflater)
        return bindingAddFrag.root

    }

    override fun onStart() {
        super.onStart()
        bindingAddFrag.btnAddFood.setOnClickListener {
            val intent = Intent(this.requireContext(), AddFamousFood::class.java)
            this.startActivity(intent)
        }

        bindingAddFrag.btnAddDevotionalPlace.setOnClickListener {
            val intent = Intent(this.requireContext(), AddDevotionalPlace::class.java)
            this.startActivity(intent)
        }

        bindingAddFrag.btnAddPlace.setOnClickListener {
            val intent = Intent(this.requireContext(), AddFamousPlace::class.java)
            this.startActivity(intent)
        }
    }

}