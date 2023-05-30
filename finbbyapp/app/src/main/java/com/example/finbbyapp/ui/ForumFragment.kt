package com.example.finbbyapp.ui

import android.os.Bundle
import android.view.*
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
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

        val activity = requireActivity() as AppCompatActivity
        activity.supportActionBar?.title = "My Forum"

        showRecyclerList()

        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear() // Menghapus semua item menu yang ada

        when (val activity = requireActivity()) {
            is AppCompatActivity -> {
                activity.menuInflater.inflate(R.menu.forum_menu, menu) // Inflate menu untuk Fragment 1
            }
        }

        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun showRecyclerList() {
        val listForum = arrayListOf(DetailForum("Tutor 1", R.drawable.img_login, "jj jhws jhswjshsjshjs wshjs hjhsjd shdjs hsjsh shdjdj hdjsah hxjhs "),
            DetailForum("Tutor 1", R.drawable.img_login, "jj jhws jhswjshsjshjs wshjs hjhsjd shdjs hsjsh shdjdj hdjsah hxjhs "),
            DetailForum("Tutor 1", R.drawable.img_login, "jj jhws jhswjshsjshjs wshjs hjhsjd shdjs hsjsh shdjdj hdjsah hxjhs "),
            DetailForum("Tutor 1", R.drawable.img_login, "jj jhws jhswjshsjshjs wshjs hjhsjd shdjs hsjsh shdjdj hdjsah hxjhs "),
            DetailForum("Tutor 1", R.drawable.img_login, "jj jhws jhswjshsjshjs wshjs hjhsjd shdjs hsjsh shdjdj hdjsah hxjhs "),
            DetailForum("Tutor 1", R.drawable.img_login, "jj jhws jhswjshsjshjs wshjs hjhsjd shdjs hsjsh shdjdj hdjsah hxjhs "))

        rvForum.layoutManager = LinearLayoutManager(requireContext())
        val listForumAdapter = ListForumAdapter(listForum)
        rvForum.adapter = listForumAdapter
    }
}