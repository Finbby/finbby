package com.example.finbbyapp.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finbbyapp.R

class ListQSurveyAdapter(val listQSurvey: ArrayList<DetailQSurvey>) : RecyclerView.Adapter<ListQSurveyAdapter.ListViewHolder>() {
    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val imgPhoto: ImageView = itemView.findViewById(R.id.img_content)
            val title: TextView = itemView.findViewById(R.id.item_qsurvey)
//        val deskripsi: TextView = itemView.findViewById(R.id.deskripsi_content)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.list_item_qsurvey, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (title) = listQSurvey[position]
        holder.title.text = title

        holder.title.setOnClickListener {
            holder.title.setBackgroundColor(R.drawable.list_btn_qsurvey_checked)
            holder.title.text = "Tes"
//            val intentDetail = Intent(holder.itemView.context, DetailContentActivity::class.java)
////            intentDetail.putExtra(DetailActivity.KEY_HERO, listHero[holder.adapterPosition])
//            holder.itemView.context.startActivity(intentDetail)
        }
    }

    override fun getItemCount(): Int = listQSurvey.size

}