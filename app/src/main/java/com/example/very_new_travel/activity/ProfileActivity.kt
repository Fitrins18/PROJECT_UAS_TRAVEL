package com.example.very_new_travel.activity

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.very_new_travel.R
import com.example.very_new_travel.database.DatabaseHelper
import com.example.very_new_travel.session.SessionManager
import kotlinx.android.synthetic.main.activity_main.*

class ProfileActivity : AppCompatActivity() {
    protected var cursor: Cursor? = null
    var dbHelper: DatabaseHelper? = null
    var db: SQLiteDatabase? = null
    var session: SessionManager? = null
    var name: String? = null
    var email: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        dbHelper = DatabaseHelper(this)
        session = SessionManager(applicationContext)
        val user = session!!.userDetails
        email = user[SessionManager.KEY_EMAIL]
        db = dbHelper!!.readableDatabase
        //cursor = db.rawQuery("SELECT * FROM TB_USER WHERE username = '$email'", null)
       // cursor.moveToFirst()
        //if (cursor.getCount() > 0) {
         //   cursor.moveToPosition(0)
          //  name = cursor.getString(2)
      //  }
        val lblName = findViewById<TextView>(R.id.lblName)
        val lblEmail = findViewById<TextView>(R.id.lblEmail)
        lblName.text = name
        lblEmail.text = email
        setupToolbar()
    }

    private fun setupToolbar() {
        val toolbar =
            findViewById<Toolbar>(R.id.tbProfile)
        toolbar.title = "Identitas Penyewa"
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
}