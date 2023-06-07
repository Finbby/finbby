package com.example.finbbyapp.ui

import android.content.ClipData
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.finbbyapp.R
import okhttp3.internal.notifyAll

class ListQSurveyAdapter(var listQSurvey: ArrayList<DetailQSurvey>) : RecyclerView.Adapter<ListQSurveyAdapter.ListViewHolder>() {
    val listTopics = ArrayList<Int>()
    val selectedTags: MutableSet<String> = mutableSetOf()

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val title: TextView = itemView.findViewById(R.id.item_qsurvey)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.list_item_qsurvey, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {

        var (title) = listQSurvey[position]
        holder.title.text = title
        if(title == "") {
            title = position.toString()
        }


        holder.title.setOnClickListener {
//            if (listTopics.contains(position)) {
//                listTopics.remove(position)
//                holder.title.setBackgroundResource(R.drawable.list_btn_qsurvey)
//            } else {
//                if (listTopics.size < 3) {
//                    listTopics.add(position)
//                    holder.title.setBackgroundResource(R.drawable.list_btn_qsurvey_checked)
//                } else {
//                    val firstSelectedPosition = listTopics[0]
//                    listTopics.removeAt(0)
//                    listTopics.add(position)
//                    notifyItemChanged(firstSelectedPosition)
//                    holder.title.setBackgroundResource(R.drawable.list_btn_qsurvey_checked)
//                }
//            }
            //Toast.makeText(holder.itemView.context, selectedTags.size.toString(), Toast.LENGTH_SHORT).show()
            if (selectedTags.contains(title)) {
                // Tag telah dipilih sebelumnya, hapus dari daftar yang dipilih
                selectedTags.remove(title)
                holder.title.setBackgroundResource(R.drawable.list_btn_qsurvey)
            } else if(selectedTags.contains(title) && selectedTags.size == 3) {
                Toast.makeText(holder.itemView.context, "Klik lagi", Toast.LENGTH_SHORT).show()
            } else {
                // Tag belum dipilih, periksa jumlah tag yang dipilih saat ini
                if (selectedTags.size < 3) {
                    // Jika masih kurang dari 3, tambahkan tag ke daftar yang dipilih
                    selectedTags.add(title)
                    holder.title.setBackgroundResource(R.drawable.list_btn_qsurvey_checked)
                } else {
                    Toast.makeText(holder.itemView.context, "Maksimal pilih 3 topik", Toast.LENGTH_SHORT).show()
//                    // Jika sudah 3, hapus tag pertama dari daftar yang dipilih
//                    val firstSelectedTag = selectedTags.first()
//                    selectedTags.remove(firstSelectedTag)
//                    // Kemudian tambahkan tag baru ke daftar yang dipilih
//                    selectedTags.add(title)
//                    // Perbarui latar belakang tag pertama yang dihapus
//                    val firstSelectedTagIndex = listQSurvey.indexOfFirst { it.title == firstSelectedTag }
//                    notifyItemChanged(firstSelectedTagIndex)
//
//
//                    // Atur latar belakang tag yang baru ditambahkan
//                    holder.title.setBackgroundResource(R.drawable.list_btn_qsurvey_checked)

                }
            }
            //Toast.makeText(holder.itemView.context, selectedTags.size.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    fun updateList(newList: ArrayList<DetailQSurvey>) {
        selectedTags.clear()
        listQSurvey = newList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = listQSurvey.size

}