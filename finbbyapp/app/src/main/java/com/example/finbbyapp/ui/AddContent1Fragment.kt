package com.example.finbbyapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finbbyapp.R
import com.example.finbbyapp.databinding.FragmentAddContent1Binding
import com.example.finbbyapp.databinding.FragmentHomeBinding

class AddContent1Fragment : Fragment() {
    private lateinit var rvQSurvey: RecyclerView
    private var _binding: FragmentAddContent1Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddContent1Binding.inflate(inflater, container, false)
        val root: View = binding.root
        rvQSurvey = binding.rvTopics
        setHasOptionsMenu(true)

        showRecyclerList()

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
        rvQSurvey.adapter = listContentAdapter
    }

    fun replaceFragment() {
        val categoryFragment = AddContent2Fragment()
        val fragmentManager = parentFragmentManager
        fragmentManager.beginTransaction().apply {
            replace(R.id.tes1, categoryFragment, AddContent2Fragment::class.java.simpleName)
            addToBackStack(null)
            commit()
        }
    }
}