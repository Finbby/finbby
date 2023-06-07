package com.example.finbbyapp.ui

import android.content.ClipData
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finbbyapp.R
import com.example.finbbyapp.databinding.ActivitySurveyBinding

class SurveyActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySurveyBinding
    private lateinit var rvQSurvey: RecyclerView
    private lateinit var listContentAdapter: ListQSurveyAdapter
    val listQSurvey = arrayListOf(
        DetailQSurvey("Sport"),
        DetailQSurvey("Book"),
        DetailQSurvey("Movie"),
        DetailQSurvey("Kotak"),
        DetailQSurvey("Lingkar"),
        DetailQSurvey("Tiga"),
        DetailQSurvey("satu"),
        DetailQSurvey("empat"),
        DetailQSurvey("lima"),
        DetailQSurvey("enam"),
        DetailQSurvey("tujuh"),)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySurveyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        rvQSurvey = binding.rvQsurvey
        listContentAdapter = ListQSurveyAdapter(listQSurvey)
        rvQSurvey.layoutManager = GridLayoutManager(this, 3)

        binding.save.setOnClickListener {
            if(listContentAdapter.selectedTags.size < 1) {
                Toast.makeText(this,"Pilih dulu", Toast.LENGTH_SHORT).show()
            }
            else if(listContentAdapter.selectedTags.size > 0 && listContentAdapter.selectedTags.size <= 3) {
                Toast.makeText(this,"OK", Toast.LENGTH_SHORT).show()
                Toast.makeText(this,listContentAdapter.selectedTags.size.toString(), Toast.LENGTH_SHORT).show()
            }
        }

        val editText = binding.edtSearch
        editText.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                val filteredList = ArrayList<DetailQSurvey>()
                for (item in listQSurvey) {
                    if (item.title.contains(editText.text.toString().trim(), ignoreCase = true)) {
                        filteredList.add(item)
                    }
                }

                listContentAdapter.updateList(filteredList)
                listContentAdapter.notifyDataSetChanged()
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(editText.windowToken, 0)
                true
            } else {
                false
            }
        }
        rvQSurvey.adapter = listContentAdapter

    }

    private fun updateList() {

    }
}