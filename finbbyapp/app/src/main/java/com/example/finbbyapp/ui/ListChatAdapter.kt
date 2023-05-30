package com.example.finbbyapp.ui

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finbbyapp.R

class ListChatAdapter(val listChat: ArrayList<DataMessage>) : RecyclerView.Adapter<ListChatAdapter.ListViewHolder>() {
    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val photo: ImageView = itemView.findViewById(R.id.img_profil)
        val name: TextView = itemView.findViewById(R.id.name)
        val message: TextView = itemView.findViewById(R.id.message)
        val date: TextView = itemView.findViewById(R.id.date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_message, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (name, message, photo, date) = listChat[position]
        holder.photo.setImageResource(photo)
        holder.name.text = name
        holder.message.text = message
        holder.date.text = date
        val layoutParams = holder.itemView.layoutParams as RecyclerView.LayoutParams

        if (position % 2 == 0) {
            // Item dengan posisi genap (mulai dari 0) ditempatkan di sebelah kiri.
            layoutParams.setMargins(8, 25, 96, 0)
        } else {
            // Item dengan posisi ganjil ditempatkan di sebelah kanan.
            layoutParams.setMargins(350, 25, 8, 0)
            //layoutParams.width = holder.itemView.context.resources.displayMetrics.widthPixels - 90
        }

        holder.itemView.layoutParams = layoutParams

//        holder.itemView.setOnClickListener {
//            val intentDetail = Intent(holder.itemView.context, DetailContentActivity::class.java)
////            intentDetail.putExtra(DetailActivity.KEY_HERO, listHero[holder.adapterPosition])
//            holder.itemView.context.startActivity(intentDetail)
//        }

//        holder.title.setOnClickListener {
//            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("myapp://chat"))
//            holder.itemView.context.startActivity(intent)
//        }
    }

    override fun getItemCount(): Int = listChat.size

}