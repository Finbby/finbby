package com.example.finbbyapp.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finbbyapp.R
import com.example.finbbyapp.databinding.FragmentAddContent1Binding
import com.example.finbbyapp.databinding.FragmentHomeBinding

class AddContent1Fragment : Fragment() {
    private lateinit var rvQSurvey: RecyclerView
    private var _binding: FragmentAddContent1Binding? = null
    private val binding get() = _binding!!
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddContent1Binding.inflate(inflater, container, false)
        val root: View = binding.root
        val activity = requireActivity() as AppCompatActivity
        activity.supportActionBar?.show()
        rvQSurvey = binding.rvTopics
        listContentAdapter = ListQSurveyAdapter(listQSurvey)
        rvQSurvey.layoutManager = GridLayoutManager(requireActivity(), 3)

        setHasOptionsMenu(true)

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
                val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(editText.windowToken, 0)
                true
            } else {
                false
            }
        }
        rvQSurvey.adapter = listContentAdapter
        //showRecyclerList()

        binding.next.setOnClickListener {
            val intent = Intent(requireActivity(), AddContent2Activity::class.java)
            startActivity(intent)
        }

        return root
    }

    private fun showRecyclerList() {
        val listQSurvey = arrayListOf(DetailQSurvey("Tutor 1"),
            DetailQSurvey("Tutor 1"),
            DetailQSurvey("Tutor 1"),
            DetailQSurvey("Tutor 1"),
            DetailQSurvey("Tutor 1"),
            DetailQSurvey("Tutor 1"))

        rvQSurvey.layoutManager = GridLayoutManager(requireContext(), 3)
        val listContentAdapter = ListQSurveyAdapter(listQSurvey)

    }
}