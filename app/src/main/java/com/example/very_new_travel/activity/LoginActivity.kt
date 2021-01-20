package com.example.very_new_travel.activity

import android.app.Activity
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.very_new_travel.R
import com.example.very_new_travel.activity.LoginActivity
import com.example.very_new_travel.adapter.AlertDialogManager
import com.example.very_new_travel.database.DatabaseHelper
import com.example.very_new_travel.session.SessionManager
import kotlinx.android.synthetic.main.activity_main.*

class LoginActivity : AppCompatActivity() {
    var txtUsername: EditText? = null
    var txtPassword: EditText? = null
    var btnLogin: Button? = null
    var btnRegister: Button? = null
    var alert = AlertDialogManager()
    var session: SessionManager? = null
    var dbHelper: DatabaseHelper? = null
    var db: SQLiteDatabase? = null
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
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
        session = SessionManager(applicationContext)
        txtUsername = findViewById(R.id.email) as EditText
        txtPassword = findViewById(R.id.password) as EditText
        btnLogin = findViewById(R.id.masuk)
        btnRegister = findViewById(R.id.ke_daftar)
        btnLogin?.setOnClickListener(View.OnClickListener {
            // Get username, password from EditText
            val username = txtUsername?.getText().toString()
            val password = txtPassword?.getText().toString()

            // Check if username, password is filled
            try {
                // Check if username, password is filled
                if (username.trim { it <= ' ' }.length > 0 && password.trim { it <= ' ' }.length > 0) {
                    dbHelper!!.open()
                    if (dbHelper!!.Login(username, password)) {
                        session!!.createLoginSession(username)
                        finish()
                        val i =
                            Intent(applicationContext, MainActivity::class.java)
                        startActivity(i)
                    } else {
                        alert.showAlertDialog(
                            this@LoginActivity,
                            "Login gagal..",
                            "Email atau Password salah!",
                            false
                        )
                    }
                } else {
                    alert.showAlertDialog(
                        this@LoginActivity,
                        "Login gagal..",
                        "Form tidak boleh kosong!",
                        false
                    )
                }
            } catch (e: Exception) {
                Toast.makeText(this@LoginActivity, e.message, Toast.LENGTH_LONG).show()
            }
        })
        btnRegister?.setOnClickListener(View.OnClickListener {
            val i = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(i)
        })
    }

    override fun onBackPressed() {
        val builder =
            AlertDialog.Builder(this@LoginActivity)
        builder.setMessage("Apakah Anda ingin keluar dari aplikasi?")
        builder.setCancelable(true)
        builder.setNegativeButton(
            getString(R.string.batal)
        ) { dialog, which -> dialog.cancel() }
        builder.setPositiveButton(
            getString(R.string.keluar)
        ) { dialog, which -> finishAffinity() }
        val alertDialog = builder.create()
        alertDialog.show()
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