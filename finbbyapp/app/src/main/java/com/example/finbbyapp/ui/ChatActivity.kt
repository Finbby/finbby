package com.example.finbbyapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finbbyapp.R
import com.example.finbbyapp.databinding.ActivityChatBinding
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*

class ChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatBinding
    private lateinit var rvChat: RecyclerView
    private lateinit var db: FirebaseDatabase
    private lateinit var adapter: FirebaseMessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.show()

        supportActionBar?.title = "Forum Name"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        db = Firebase.database

        val messagesRef = db.reference.child(MESSAGES_CHILD)

        binding.sendButton.setOnClickListener {
            val friendlyMessage = DataMessage(
                "user123",
                binding.edtChat.text.toString().trim(),
                "https://wallpapercave.com/wp/wp3024281.jpg",
                Date().time
            )
            messagesRef.push().setValue(friendlyMessage) { error, _ ->
                if (error != null) {
                    Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
                } else {
                }
            }
            binding.edtChat.setText("")
        }

        val manager = LinearLayoutManager(this)
        manager.stackFromEnd = true
        binding.rvChat.layoutManager = manager

        val options = FirebaseRecyclerOptions.Builder<DataMessage>()
            .setQuery(messagesRef, DataMessage::class.java)
            .build()
        adapter = FirebaseMessageAdapter(options)
        binding.rvChat.adapter = adapter

        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                val newItemPosition = positionStart + itemCount - 1
                binding.rvChat.scrollToPosition(newItemPosition)
            }
        })
        //showRecyclerList()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

//    private fun showRecyclerList() {
//        val listChat = arrayListOf(DataMessage("Andi", "Selamat siang, selmat malam", R.drawable.img_login, "15 April 2023"),
//            DataMessage("Andi", "Selamat siang, selmat malam", R.drawable.img_login, "15 April 2023"),
//                    DataMessage("Andi", "Selamat skj hnjnkj kjnbiang, senn hhhh hhh  hhh hlmat malam", R.drawable.img_login, "15 April 2023"),
//            DataMessage("Andi", "Selamat siang, snkjnj hjuh huuhu hguhuhu huhuhyuhuh u kjnkjnihelmat malam", R.drawable.img_login, "15 April 2023"),
//            DataMessage("Andi", "Selamat sianij  nhkjnk g, selmat malam", R.drawable.img_login, "15 April 2023"),
//            DataMessage("Andi", "Selamat siang, selmat malam", R.drawable.img_login, "15 April 2023"))
//
//        rvChat.layoutManager = LinearLayoutManager(this)
//        val listChatAdapter = ListChatAdapter(listChat)
//        rvChat.adapter = listChatAdapter
//    }

    public override fun onResume() {
        super.onResume()
        adapter.startListening()
    }

    public override fun onPause() {
        adapter.stopListening()
        super.onPause()
    }



    override fun onBackPressed() {
        super.onBackPressed()
    }

    companion object {
        const val MESSAGES_CHILD = "messages2"
    }
}