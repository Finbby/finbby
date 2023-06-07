package com.example.finbbyapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finbbyapp.R
import com.example.finbbyapp.databinding.ActivityJoinForumBinding

class JoinForumActivity : AppCompatActivity() {
    private lateinit var rvForum: RecyclerView
    private lateinit var binding: ActivityJoinForumBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJoinForumBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvForum = binding.rvForum
        showRecyclerList()
    }

    private fun showRecyclerList() {
        val listForum = arrayListOf(DetailForum("Tutor 1", R.drawable.img_login, "jj jhws jhswjshsjshjs wshjs hjhsjd shdjs hsjsh shdjdj hdjsah hxjhs "),
            DetailForum("Tutor 1", R.drawable.img_login, "jj jhws jhswjshsjshjs wshjs hjhsjd shdjs hsjsh shdjdj hdjsah hxjhs jbhjbbbhb hj bh bhb jb bbhbhjbhjb  hj bhb hjbh hbhjbj"),
            DetailForum("Tutor 1", R.drawable.img_login, "jj jhws jhswjshsjshjs wshjs hjhsjd shdjs hsjsh shdjdj hdjsah hxjhs jbhjbbbhb hj bh bhb jb bbhbhjbhjb  hj bhb hjbh hbhjbj"),
            DetailForum("Tutor 1", R.drawable.img_login, "jj jhws jhswjshsjshjs wshjs hjhsjd shdjs hsjsh shdjdj hdjsah hxjhs jbhjbbbhb hj bh bhb jb bbhbhjbhjb  hj bhb hjbh hbhjbj"),
            DetailForum("Tutor 1", R.drawable.img_login, "jj jhws jhswjshsjshjs wshjs hjhsjd shdjs hsjsh shdjdj hdjsah hxjhs jbhjbbbhb hj bh bhb jb bbhbhjbhjb  hj bhb hjbh hbhjbj"),
            DetailForum("Tutor 1", R.drawable.img_login, "jj jhws jhswjshsjshjs wshjs hjhsjd shdjs hsjsh shdjdj hdjsah hxjhs jbhjbbbhb hj bh bhb jb bbhbhjbhjb  hj bhb hjbh hbhjbj"),
            DetailForum("Tutor 1", R.drawable.img_login, "jj jhws jhswjshsjshjs wshjs hjhsjd shdjs hsjsh shdjdj hdjsah hxjhs jbhjbbbhb hj bh bhb jb bbhbhjbhjb  hj bhb hjbh hbhjbj"),
            DetailForum("Tutor 1", R.drawable.img_login, "jj jhws jhswjshsjshjs wshjs hjhsjd shdjs hsjsh shdjdj hdjsah hxjhs jbhjbbbhb hj bh bhb jb bbhbhjbhjb  hj bhb hjbh hbhjbj"))

        rvForum.layoutManager = LinearLayoutManager(this)
        val listForumAdapter = ListJoinForumAdapter(listForum)
        rvForum.adapter = listForumAdapter
    }
}