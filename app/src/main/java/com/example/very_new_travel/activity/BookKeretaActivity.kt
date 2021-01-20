package com.example.very_new_travel.activity

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.text.InputType
import android.view.MenuItem
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.very_new_travel.R
import com.example.very_new_travel.database.DatabaseHelper
import com.example.very_new_travel.session.SessionManager
import kotlinx.android.synthetic.main.activity_main.*

class BookKeretaActivity : AppCompatActivity() {
    var cursor: Cursor? = null
    var dbHelper: DatabaseHelper? = null
    var db: SQLiteDatabase? = null
    var spinAsal: Spinner? = null
    var spinTujuan: Spinner? = null
    var spinDewasa: Spinner? = null
    var spinAnak: Spinner? = null
    var session: SessionManager? = null
    var email: String? = null
    var id_book = 0
    var sAsal: String? = null
    var sTujuan: String? = null
    var sTanggal: String? = null
    var sDewasa: String? = null
    var sAnak: String? = null
    var jmlDewasa = 0
    var jmlAnak = 0
    var hargaDewasa = 0
    var hargaAnak = 0
    var hargaTotalDewasa = 0
    var hargaTotalAnak = 0
    var hargaTotal = 0
    private var etTanggal: EditText? = null
    private var dpTanggal: DatePickerDialog? = null
   // var newCalendar = Calendar.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_kereta)
        dbHelper = DatabaseHelper(this@BookKeretaActivity)
        db = dbHelper!!.readableDatabase
        val asal =
            arrayOf("Jakarta", "Bandung", "Purwokerto", "Yogyakarta", "Surabaya")
        val tujuan =
            arrayOf("Jakarta", "Bandung", "Purwokerto", "Yogyakarta", "Surabaya")
        val dewasa =
            arrayOf("0", "1", "2", "3", "4", "5")
        val anak = arrayOf("0", "1", "2", "3", "4", "5")
        spinAsal = findViewById(R.id.asal)
        spinTujuan = findViewById(R.id.tujuan)
        spinDewasa = findViewById(R.id.dewasa)
        spinAnak = findViewById(R.id.anak)
        val adapterAsal =
            ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, asal)
        adapterAsal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinAsal?.setAdapter(adapterAsal)
        val adapterTujuan =
            ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, tujuan)
        adapterTujuan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinTujuan?.setAdapter(adapterTujuan)
        val adapterDewasa =
            ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, dewasa)
        adapterDewasa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinDewasa?.setAdapter(adapterDewasa)
        val adapterAnak =
            ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, anak)
        adapterAnak.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinAnak?.setAdapter(adapterAnak)
        spinAsal?.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                sAsal = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })
        spinTujuan?.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                sTujuan = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })
        spinDewasa?.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                sDewasa = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })
        spinAnak?.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                sAnak = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })
        val btnBook = findViewById<Button>(R.id.book)
        etTanggal = findViewById(R.id.tanggal_berangkat)
        etTanggal?.setInputType(InputType.TYPE_NULL)
        etTanggal?.requestFocus()
        session = SessionManager(applicationContext)
        val user = session!!.userDetails
        email = user[SessionManager.KEY_EMAIL]
       // setDateTimeField()
        btnBook.setOnClickListener {
            perhitunganHarga()
            if (sAsal != null && sTujuan != null && sTanggal != null && sDewasa != null) {
                if (sAsal.equals("jakarta", ignoreCase = true) && sTujuan.equals(
                        "jakarta",
                        ignoreCase = true
                    )
                    || sAsal.equals(
                        "bandung",
                        ignoreCase = true
                    ) && sTujuan.equals("bandung", ignoreCase = true)
                    || sAsal.equals("purwokerto", ignoreCase = true) && sTujuan.equals(
                        "purwokerto",
                        ignoreCase = true
                    )
                    || sAsal.equals("yogyakarta", ignoreCase = true) && sTujuan.equals(
                        "yogyakarta",
                        ignoreCase = true
                    )
                    || sAsal.equals(
                        "surabaya",
                        ignoreCase = true
                    ) && sTujuan.equals("surabaya", ignoreCase = true)
                ) {
                    Toast.makeText(
                        this@BookKeretaActivity,
                        "Asal dan Tujuan tidak boleh sama !",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    val dialog =
                        AlertDialog.Builder(this@BookKeretaActivity)
                            .setTitle("Ingin booking kereta sekarang?")
                            .setPositiveButton("Ya") { dialog, which ->
                                try {
                                    //db.execSQL(
                                        "INSERT INTO TB_BOOK (asal, tujuan, tanggal, dewasa, anak) VALUES ('" +
                                                sAsal + "','" +
                                                sTujuan + "','" +
                                                sTanggal + "','" +
                                                sDewasa + "','" +
                                                sAnak + "');"
                                    //)
                                    //cursor = db.rawQuery(
                                      //  "SELECT id_book FROM TB_BOOK ORDER BY id_book DESC",
                                       // null
                                    //)
                                    //this.cursor.moveToLast()
                                   // if (cursor.getCount() > 0) {
                                       // cursor.moveToPosition(0)
                                       // id_book = cursor.getInt(0)
                                 //   }
                                    //db.execSQL(
                                        "INSERT INTO TB_HARGA (username, id_book, harga_dewasa, harga_anak, harga_total) VALUES ('" +
                                                email + "','" +
                                                id_book + "','" +
                                                hargaTotalDewasa + "','" +
                                                hargaTotalAnak + "','" +
                                                hargaTotal + "');"
                                    //)
                                    Toast.makeText(
                                        this@BookKeretaActivity,
                                        "Booking berhasil",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    finish()
                                } catch (e: Exception) {
                                    Toast.makeText(
                                        this@BookKeretaActivity,
                                        e.message,
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                            .setNegativeButton("Tidak", null)
                            .create()
                    dialog.show()
                }
            } else {
                Toast.makeText(
                    this@BookKeretaActivity,
                    "Mohon lengkapi data pemesanan!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        setupToolbar()
    }

    private fun setupToolbar() {
        val toolbar =
            findViewById<Toolbar>(R.id.tbKrl)
        toolbar.title = "Form Booking"
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

    fun perhitunganHarga() {
        if (sAsal.equals("jakarta", ignoreCase = true) && sTujuan.equals(
                "bandung",
                ignoreCase = true
            )
        ) {
            hargaDewasa = 100000
            hargaAnak = 70000
        } else if (sAsal.equals("jakarta", ignoreCase = true) && sTujuan.equals(
                "surabaya",
                ignoreCase = true
            )
        ) {
            hargaDewasa = 200000
            hargaAnak = 150000
        } else if (sAsal.equals("jakarta", ignoreCase = true) && sTujuan.equals(
                "purwokerto",
                ignoreCase = true
            )
        ) {
            hargaDewasa = 150000
            hargaAnak = 120000
        } else if (sAsal.equals("jakarta", ignoreCase = true) && sTujuan.equals(
                "yogyakarta",
                ignoreCase = true
            )
        ) {
            hargaDewasa = 180000
            hargaAnak = 140000
        } else if (sAsal.equals("bandung", ignoreCase = true) && sTujuan.equals(
                "jakarta",
                ignoreCase = true
            )
        ) {
            hargaDewasa = 100000
            hargaAnak = 70000
        } else if (sAsal.equals("bandung", ignoreCase = true) && sTujuan.equals(
                "surabaya",
                ignoreCase = true
            )
        ) {
            hargaDewasa = 120000
            hargaAnak = 100000
        } else if (sAsal.equals("bandung", ignoreCase = true) && sTujuan.equals(
                "purwokerto",
                ignoreCase = true
            )
        ) {
            hargaDewasa = 120000
            hargaAnak = 90000
        } else if (sAsal.equals("bandung", ignoreCase = true) && sTujuan.equals(
                "yogyakarta",
                ignoreCase = true
            )
        ) {
            hargaDewasa = 190000
            hargaAnak = 160000
        } else if (sAsal.equals("surabaya", ignoreCase = true) && sTujuan.equals(
                "jakarta",
                ignoreCase = true
            )
        ) {
            hargaDewasa = 200000
            hargaAnak = 150000
        } else if (sAsal.equals("surabaya", ignoreCase = true) && sTujuan.equals(
                "bandung",
                ignoreCase = true
            )
        ) {
            hargaDewasa = 120000
            hargaAnak = 100000
        } else if (sAsal.equals("surabaya", ignoreCase = true) && sTujuan.equals(
                "purwokerto",
                ignoreCase = true
            )
        ) {
            hargaDewasa = 170000
            hargaAnak = 130000
        } else if (sAsal.equals("surabaya", ignoreCase = true) && sTujuan.equals(
                "yogyakarta",
                ignoreCase = true
            )
        ) {
            hargaDewasa = 180000
            hargaAnak = 150000
        } else if (sAsal.equals("purwokerto", ignoreCase = true) && sTujuan.equals(
                "jakarta",
                ignoreCase = true
            )
        ) {
            hargaDewasa = 150000
            hargaAnak = 120000
        } else if (sAsal.equals("purwokerto", ignoreCase = true) && sTujuan.equals(
                "bandung",
                ignoreCase = true
            )
        ) {
            hargaDewasa = 120000
            hargaAnak = 90000
        } else if (sAsal.equals("purwokerto", ignoreCase = true) && sTujuan.equals(
                "yogyakarta",
                ignoreCase = true
            )
        ) {
            hargaDewasa = 80000
            hargaAnak = 40000
        } else if (sAsal.equals("purwokerto", ignoreCase = true) && sTujuan.equals(
                "surabaya",
                ignoreCase = true
            )
        ) {
            hargaDewasa = 170000
            hargaAnak = 130000
        } else if (sAsal.equals("yogyakarta", ignoreCase = true) && sTujuan.equals(
                "jakarta",
                ignoreCase = true
            )
        ) {
            hargaDewasa = 180000
            hargaAnak = 140000
        } else if (sAsal.equals("yogyakarta", ignoreCase = true) && sTujuan.equals(
                "bandung",
                ignoreCase = true
            )
        ) {
            hargaDewasa = 190000
            hargaAnak = 160000
        } else if (sAsal.equals("yogyakarta", ignoreCase = true) && sTujuan.equals(
                "purwokerto",
                ignoreCase = true
            )
        ) {
            hargaDewasa = 80000
            hargaAnak = 40000
        } else if (sAsal.equals("yogyakarta", ignoreCase = true) && sTujuan.equals(
                "surabaya",
                ignoreCase = true
            )
        ) {
            hargaDewasa = 180000
            hargaAnak = 150000
        }
        jmlDewasa = sDewasa!!.toInt()
        jmlAnak = sAnak!!.toInt()
        hargaTotalDewasa = jmlDewasa * hargaDewasa
        hargaTotalAnak = jmlAnak * hargaAnak
        hargaTotal = hargaTotalDewasa + hargaTotalAnak
    }

   /* private fun setDateTimeField() {
        etTanggal!!.setOnClickListener { dpTanggal!!.show() }
        dpTanggal = DatePickerDialog(
            this,
            OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
              //  val newDate = Calendar.getInstance()
               // newDate[year, monthOfYear] = dayOfMonth
                val bulan = arrayOf(
                    "Januari", "Februari", "Maret", "April", "Mei",
                    "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"
                )
                sTanggal = dayOfMonth.toString() + " " + bulan[monthOfYear] + " " + year
                etTanggal!!.setText(sTanggal)
            },
            //newCalendar[Calendar.YEAR],
           // newCalendar[Calendar.MONTH],
           // newCalendar[Calendar.DAY_OF_MONTH]
        )
    }*/
}