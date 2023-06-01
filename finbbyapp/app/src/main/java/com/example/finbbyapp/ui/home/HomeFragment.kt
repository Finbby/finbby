package com.example.finbbyapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finbbyapp.R
import com.example.finbbyapp.databinding.FragmentHomeBinding
import com.example.finbbyapp.ui.DetailContent
import com.example.finbbyapp.ui.ListContentAdapter

class HomeFragment : Fragment() {
    private lateinit var rvContent: RecyclerView
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        rvContent = binding.rvContent
        showRecyclerList()
        return root
    }

    private fun showRecyclerList() {
        val listContent = arrayListOf(DetailContent("Tutor 1", "jj jhws jhswjshsjshjs wshjs hjhsjd shdjs hsjsh shdjdj hdjsah hxjhs ", R.drawable.img_login),
            DetailContent("Tutor 1", "jj jhws jhswjshsjshjs wshjs hjhsjd shdjs hsjsh shdjdj hdjsah hxjhs ", R.drawable.img_login),
            DetailContent("Tutor 1", "jj jhws jhswjshsjshjs wshjs hjhsjd shdjs hsjsh shdjdj hdjsah hxjhs ", R.drawable.img_login),
            DetailContent("Tutor 1", "jj jhws jhswjshsjshjs wshjs hjhsjd shdjs hsjsh shdjdj hdjsah hxjhs ", R.drawable.img_login),
            DetailContent("Tutor 1", "jj jhws jhswjshsjshjs wshjs hjhsjd shdjs hsjsh shdjdj hdjsah hxjhs ", R.drawable.img_login),
            DetailContent("Tutor 1", "jj jhws jhswjshsjshjs wshjs hjhsjd shdjs hsjsh shdjdj hdjsah hxjhs ", R.drawable.img_login))

        rvContent.layoutManager = LinearLayoutManager(requireContext())
        val listContentAdapter = ListContentAdapter(listContent)
        rvContent.adapter = listContentAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}