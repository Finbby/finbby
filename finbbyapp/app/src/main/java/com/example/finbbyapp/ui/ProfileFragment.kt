package com.example.finbbyapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.finbbyapp.R
import com.example.finbbyapp.databinding.FragmentAddForumBinding
import com.example.finbbyapp.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.cMaker.setOnClickListener {
            val intent = Intent(requireActivity(), ContentMakerActivity::class.java)
            startActivity(intent)
        }

        binding.logout.setOnClickListener {
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            startActivity(intent)
        }

        val activity = requireActivity() as AppCompatActivity
        activity.supportActionBar?.hide()

        return root
    }
}