package com.example.finbbyapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finbbyapp.R
import com.example.finbbyapp.databinding.ActivityChatBinding

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private lateinit var rvChat: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvChat = binding.rvChat
        showRecyclerList()
    }

    private fun showRecyclerList() {
        val listChat = arrayListOf(DataMessage("Andi", "Selamat siang, selmat malam", R.drawable.img_login, "15 April 2023"),
            DataMessage("Andi", "Selamat siang, selmat malam", R.drawable.img_login, "15 April 2023"),
                    DataMessage("Andi", "Selamat skj hnjnkj kjnbiang, senn hhhh hhh  hhh hlmat malam", R.drawable.img_login, "15 April 2023"),
            DataMessage("Andi", "Selamat siang, snkjnj kjnkjnihelmat malam", R.drawable.img_login, "15 April 2023"),
            DataMessage("Andi", "Selamat sianij  nhkjnk g, selmat malam", R.drawable.img_login, "15 April 2023"),
            DataMessage("Andi", "Selamat siang, selmat malam", R.drawable.img_login, "15 April 2023"))

        rvChat.layoutManager = LinearLayoutManager(this)
        val listChatAdapter = ListChatAdapter(listChat)
        rvChat.adapter = listChatAdapter
    }
}