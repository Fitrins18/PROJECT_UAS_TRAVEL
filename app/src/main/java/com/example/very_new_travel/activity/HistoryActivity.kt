package com.example.very_new_travel.activity

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.very_new_travel.R
import com.example.very_new_travel.adapter.HistoryAdapter
import com.example.very_new_travel.database.DatabaseHelper
import com.example.very_new_travel.model.HistoryModel
import com.example.very_new_travel.session.SessionManager
import java.util.*
import kotlinx.android.synthetic.main.activity_main.*

class HistoryActivity : AppCompatActivity() {
    protected var cursor: Cursor? = null
    var dbHelper: DatabaseHelper? = null
    var db: SQLiteDatabase? = null
    var session: SessionManager? = null
    var id_book = ""
    var asal: String? = null
    var tujuan: String? = null
    var tanggal: String? = null
    var dewasa: String? = null
    var anak: String? = null
    var riwayat: String? = null
    var total: String? = null
    var email: String? = null
    var tvNotFound: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        dbHelper = DatabaseHelper(this)
        db = dbHelper!!.readableDatabase
        tvNotFound = findViewById(R.id.noHistory)
        session = SessionManager(applicationContext)
        val user = session!!.userDetails
        email = user[SessionManager.KEY_EMAIL]
        //refreshList()
        setupToolbar()
    }

    private fun setupToolbar() {
        val toolbar =
            findViewById<Toolbar>(R.id.tbHistory)
        toolbar.title = "Riwayat Booking"
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

   /*fun refreshList() {
        val hasil = ArrayList<HistoryModel>()
        cursor = db!!.rawQuery(
            "SELECT * FROM TB_BOOK, TB_HARGA WHERE TB_BOOK.id_book = TB_HARGA.id_book AND username='$email'",
            null
        )
        cursor.moveToFirst()
        for (i in 0 until cursor.getCount()) {
            cursor.moveToPosition(i)
            id_book = cursor.getString(0)
            asal = cursor.getString(1)
            tujuan = cursor.getString(2)
            tanggal = cursor.getString(3)
            dewasa = cursor.getString(4)
            anak = cursor.getString(5)
            total = cursor.getString(10)
            riwayat =
                "Berhasil melakukan booking untuk melakukan perjalanan dari " + asal + " menuju " + tujuan + " pada tanggal " + tanggal + ". " +
                        "Jumlah pembelian tiket dewasa sejumlah " + dewasa + " dan tiket anak-anak sejumlah " + anak + "."
            hasil.add(HistoryModel(id_book, tanggal, riwayat, total, R.drawable.profile))
        }
        val listBook =
            findViewById<ListView>(R.id.list_booking)
        val arrayAdapter = HistoryAdapter(this, hasil)
        listBook.adapter = arrayAdapter

        //delete data
        listBook.onItemClickListener = OnItemClickListener { adapterView, view, i, l ->
            val selection = hasil[i].idBook
            val dialogitem =
                arrayOf<CharSequence>("Hapus Data")
            val builder =
                AlertDialog.Builder(this@HistoryActivity)
            builder.setTitle("Pilihan")
            builder.setItems(dialogitem) { dialog, item ->
                val db = dbHelper!!.writableDatabase
                try {
                    db.execSQL("DELETE FROM TB_BOOK where id_book = $selection")
                    id_book = ""
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                refreshList()
            }
            builder.create().show()
        }
        if (id_book == "") {
            tvNotFound!!.visibility = View.VISIBLE
            listBook.visibility = View.GONE
        } else {
            tvNotFound!!.visibility = View.GONE
            listBook.visibility = View.VISIBLE
        }
    }*/
}