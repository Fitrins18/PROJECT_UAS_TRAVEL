package com.example.very_new_travel.activity

import android.app.Activity
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.very_new_travel.R
import com.example.very_new_travel.activity.RegisterActivity
import com.example.very_new_travel.database.DatabaseHelper
import kotlinx.android.synthetic.main.activity_main.*

class RegisterActivity : AppCompatActivity() {
    var txtName: EditText? = null
    var txtUsername: EditText? = null
    var txtPassword: EditText? = null
    var btnDaftar: Button? = null
    var btnKeLogin: Button? = null
    var dbHelper: DatabaseHelper? = null
    var db: SQLiteDatabase? = null
    var name: String? = null
    var username: String? = null
    var password: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(
                this,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                false
            )
            window.statusBarColor = Color.TRANSPARENT
        }
        dbHelper = DatabaseHelper(this)
        db = dbHelper!!.writableDatabase
        txtName = findViewById(R.id.reg_nama) as EditText
        txtUsername = findViewById(R.id.reg_email) as EditText
        txtPassword = findViewById(R.id.reg_password) as EditText
        btnDaftar = findViewById(R.id.daftar)
        btnKeLogin = findViewById(R.id.ke_login)
        btnDaftar?.setOnClickListener(View.OnClickListener {
            name = txtName?.getText().toString()
            username = txtUsername?.getText().toString()
            password = txtPassword?.getText().toString()
            try {
                if (username!!.trim { it <= ' ' }.length > 0 && password!!.trim { it <= ' ' }.length > 0 && name!!.trim { it <= ' ' }.length > 0) {
                    dbHelper!!.open()
                    //dbHelper!!.Register(username, password, name)
                    Toast.makeText(this@RegisterActivity, "Daftar berhasil", Toast.LENGTH_LONG)
                        .show()
                    finish()
                } else {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Daftar gagal, lengkapi form!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@RegisterActivity, e.message, Toast.LENGTH_LONG).show()
            }
        })
        btnKeLogin?.setOnClickListener(View.OnClickListener { finish() })
    }

    companion object {
        fun setWindowFlag(activity: Activity, bits: Int, on: Boolean) {
            val win = activity.window
            val winParams = win.attributes
            if (on) {
                winParams.flags = winParams.flags or bits
            } else {
                winParams.flags = winParams.flags and bits.inv()
            }
            win.attributes = winParams
        }
    }
}