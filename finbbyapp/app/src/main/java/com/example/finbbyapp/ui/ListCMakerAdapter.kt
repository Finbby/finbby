package com.example.finbbyapp.ui

import com.example.finbbyapp.R
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class ListCMakerAdapter(val listContent: ArrayList<DetailContent>) : RecyclerView.Adapter<ListCMakerAdapter.ListViewHolder>() {
    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title_content)
        val deskripsi: TextView = itemView.findViewById(R.id.deskripsi_content)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_content_maker, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (title, deskripsi, photo) = listContent[position]
        holder.title.text = title
        holder.deskripsi.text = deskripsi

        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailContentActivity::class.java)
//            intentDetail.putExtra(DetailActivity.KEY_HERO, listHero[holder.adapterPosition])
            holder.itemView.context.startActivity(intentDetail)
        }

        holder.title.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("myapp://chat"))
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = listContent.size

}