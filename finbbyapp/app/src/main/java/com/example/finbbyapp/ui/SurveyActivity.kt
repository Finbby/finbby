package com.example.finbbyapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finbbyapp.R
import com.example.finbbyapp.databinding.ActivitySurveyBinding

class SurveyActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySurveyBinding
    private lateinit var rvQSurvey: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySurveyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvQSurvey = binding.rvQsurvey
        showRecyclerList()
    }

    private fun showRecyclerList() {
        val listQSurvey = arrayListOf(DetailQSurvey("Tutor 1"),
            DetailQSurvey("Tutor 1"),
            DetailQSurvey("Tutor 1"),
            DetailQSurvey("Tutor 1"),
            DetailQSurvey("Tutor 1"),
            DetailQSurvey("Tutor 1"))

        rvQSurvey.layoutManager = GridLayoutManager(this, 3)
        val listContentAdapter = ListQSurveyAdapter(listQSurvey)
        rvQSurvey.adapter = listContentAdapter
    }
}