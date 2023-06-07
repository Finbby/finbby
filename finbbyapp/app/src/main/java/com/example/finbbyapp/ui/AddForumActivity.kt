package com.example.finbbyapp.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finbbyapp.R
import com.example.finbbyapp.databinding.ActivityAddForumBinding
import com.example.finbbyapp.helper.uriToFile
import java.io.File

class AddForumActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddForumBinding
    private lateinit var rvTopic: RecyclerView
    private var getFile: File? = null
    private lateinit var listContentAdapter: ListQSurveyAdapter
    val listQSurvey = arrayListOf(
        DetailQSurvey("Sport"),
        DetailQSurvey("Book"),
        DetailQSurvey("Movie"),
        DetailQSurvey(""),
        DetailQSurvey(""),
        DetailQSurvey(""),
        DetailQSurvey(""),
        DetailQSurvey(""),
        DetailQSurvey(""),
        DetailQSurvey(""),
        DetailQSurvey(""),)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddForumBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgContent.setOnClickListener { startGallery() }


        supportActionBar?.title = "New Forum"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        rvTopic = binding.rvTopics
        listContentAdapter = ListQSurveyAdapter(listQSurvey)
        rvTopic.layoutManager = GridLayoutManager(this, 3)

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
        rvTopic.adapter = listContentAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.add_forum_menu, menu)

        return true
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

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri

            selectedImg.let { uri ->
                val myFile = uriToFile(uri, this@AddForumActivity)
                getFile = myFile
                binding.imgContent.setImageURI(uri)
            }
        }
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}