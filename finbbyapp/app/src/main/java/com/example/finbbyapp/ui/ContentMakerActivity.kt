package com.example.finbbyapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finbbyapp.R
import com.example.finbbyapp.databinding.ActivityContentMakerBinding

class ContentMakerActivity : AppCompatActivity() {
    private lateinit var rvForum: RecyclerView
    private lateinit var rvCMaker: RecyclerView
    private lateinit var binding: ActivityContentMakerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContentMakerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Content Maker"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        rvForum = binding.rvForum
        rvCMaker = binding.rvContentMaker
        showRecyclerList2()
        showRecyclerList()
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

    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun showRecyclerList() {
        val listContent = arrayListOf(DetailContent("Tutor 1", "jj jhws jhswjshsjshjs wshjs hjhsjd shdjs hsjsh shdjdj hdjsah hxjhs ", R.drawable.img_login),
            DetailContent("Tutor 1", "jj jhws jhswjshsjshjs wshjs hjhsjd shdjs hsjsh shdjdj hdjsah hxjhs ", R.drawable.img_login),
            DetailContent("Tutor 1", "jj jhws jhswjshsjshjs wshjs hjhsjd shdjs hsjsh shdjdj hdjsah hxjhs ", R.drawable.img_login),
            DetailContent("Tutor 1", "jj jhws jhswjshsjshjs wshjs hjhsjd shdjs hsjsh shdjdj hdjsah hxjhs ", R.drawable.img_login),
            DetailContent("Tutor 1", "jj jhws jhswjshsjshjs wshjs hjhsjd shdjs hsjsh shdjdj hdjsah hxjhs ", R.drawable.img_login),
            DetailContent("Tutor 1", "jj jhws jhswjshsjshjs wshjs hjhsjd shdjs hsjsh shdjdj hdjsah hxjhs ", R.drawable.img_login))

        rvCMaker.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val listContentAdapter = ListCMakerAdapter(listContent)
        rvCMaker.adapter = listContentAdapter
    }

    private fun showRecyclerList2() {
        val listForum = arrayListOf(DetailForum("Tutor 1", R.drawable.img_login, "jj jhws jhswjshsjshjs wshjs hjhsjd shdjs hsjsh shdjdj hdjsah hxjhs "),
            DetailForum("Tutor 1", R.drawable.img_login, "jj jhws jhswjshsjshjs wshjs hjhsjd shdjs hsjsh shdjdj hdjsah hxjhs "),
            DetailForum("Tutor 1", R.drawable.img_login, "jj jhws jhswjshsjshjs wshjs hjhsjd shdjs hsjsh shdjdj hdjsah hxjhs "),
            DetailForum("Tutor 1", R.drawable.img_login, "jj jhws jhswjshsjshjs wshjs hjhsjd shdjs hsjsh shdjdj hdjsah hxjhs "),
            DetailForum("Tutor 1", R.drawable.img_login, "jj jhws jhswjshsjshjs wshjs hjhsjd shdjs hsjsh shdjdj hdjsah hxjhs "),
            DetailForum("Tutor 1", R.drawable.img_login, "jj jhws jhswjshsjshjs wshjs hjhsjd shdjs hsjsh shdjdj hdjsah hxjhs "))

        rvForum.layoutManager = LinearLayoutManager(this)
        val listForumAdapter = ListForumAdapter(listForum)
        rvForum.adapter = listForumAdapter
    }
}