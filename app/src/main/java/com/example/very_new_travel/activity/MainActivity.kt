package com.example.very_new_travel.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.very_new_travel.R
import com.example.very_new_travel.adapter.AlertDialogManager
import com.example.very_new_travel.session.SessionManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var alert = AlertDialogManager()
    var session: SessionManager? = null
    var btnLogout: Button? = null
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        session = SessionManager(applicationContext)
        session!!.checkLogin()
        btnLogout = findViewById(R.id.out)
        btnLogout?.setOnClickListener(View.OnClickListener {
            val dialog =
                AlertDialog.Builder(this@MainActivity)
                    .setTitle("Anda yakin ingin keluar ?")
                    .setPositiveButton("Ya") { dialog, which ->
                        finish()
                        session!!.logoutUser()
                    }
                    .setNegativeButton("Tidak", null)
                    .create()
            dialog.show()
        })
    }

    fun profileMenu(v: View?) {
        val i = Intent(this, ProfileActivity::class.java)
        startActivity(i)
    }

    fun historyMenu(v: View?) {
        val i = Intent(this, HistoryActivity::class.java)
        startActivity(i)
    }

    fun bookKereta(v: View?) {
        val i = Intent(this, BookKeretaActivity::class.java)
        startActivity(i)
    }

    fun bookHotel(v: View?) {
        Toast.makeText(
            applicationContext,
            "Mohon maaf, sistem sedang dalam pengembangan.",
            Toast.LENGTH_LONG
        ).show()
    }
}