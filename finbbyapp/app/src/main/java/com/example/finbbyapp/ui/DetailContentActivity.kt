package com.example.finbbyapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.finbbyapp.R

class DetailContentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_content)

        getSupportActionBar()?.title = ""
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
    }
}