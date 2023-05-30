package com.example.finbbyapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finbbyapp.R
import com.example.finbbyapp.databinding.FragmentForumBinding
import com.example.finbbyapp.databinding.FragmentHomeBinding

class ForumFragment : Fragment() {
    private lateinit var rvForum: RecyclerView
    private var _binding: FragmentForumBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentForumBinding.inflate(inflater, container, false)
        val root: View = binding.root
        rvForum = binding.rvForum

        return root
    }

}