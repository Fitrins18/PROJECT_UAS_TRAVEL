package com.example.very_new_travel.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.very_new_travel.R
import com.example.very_new_travel.model.HistoryModel
import java.util.*
import kotlinx.android.synthetic.main.activity_main.*

class HistoryAdapter(
    context: Activity?,
    notification: ArrayList<HistoryModel?>?
) : ArrayAdapter<HistoryModel?>(context!!, 0, notification!!) {
    @SuppressLint("SetTextI18n")
    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        var listItemView = convertView
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(
                R.layout.list_item_booking, parent, false
            )
        }
        val current = getItem(position)
        val idBook = listItemView!!.findViewById<TextView>(R.id.id_booking)
        idBook.text = "ID : " + current!!.idBook
        val tanggal = listItemView.findViewById<TextView>(R.id.tanggal)
        tanggal.text = current.tanggal
        val riwayat = listItemView.findViewById<TextView>(R.id.riwayat)
        riwayat.text = current.riwayat
        val tvTotal = listItemView.findViewById<TextView>(R.id.tv_total)
        tvTotal.text = "Total :"
        val total = listItemView.findViewById<TextView>(R.id.total)
        total.text = "Rp. " + current.total
        val imageIcon =
            listItemView.findViewById<ImageView>(R.id.image)
        if (current.hasImage()) {
            imageIcon.setImageResource(current.imageResourceId)
            imageIcon.visibility = View.VISIBLE
        } else {
            imageIcon.visibility = View.GONE
        }
        return listItemView
    }
}