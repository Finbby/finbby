package com.example.finbbyapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.finbbyapp.R
import com.example.finbbyapp.databinding.FragmentAddContent1Binding
import com.example.finbbyapp.databinding.FragmentAddContent2Binding

class AddContent2Fragment : Fragment() {
    private var _binding: FragmentAddContent2Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddContent2Binding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }


}