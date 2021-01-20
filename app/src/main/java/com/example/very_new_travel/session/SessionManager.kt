package com.example.very_new_travel.session

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import com.example.very_new_travel.activity.LoginActivity
import kotlinx.android.synthetic.main.activity_main.*

class SessionManager @SuppressLint("CommitPrefEdits") constructor(var context: Context) {
    var pref: SharedPreferences
    var editor: Editor
    var PRIVATE_MODE = 0
    fun createLoginSession(email: String?) {
        editor.putBoolean(
            IS_LOGIN,
            true
        )
        editor.putString(
            KEY_EMAIL,
            email
        )
        editor.commit()
    }

    fun checkLogin() {
        if (!isLoggedIn) {
            val i = Intent(context, LoginActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(i)
        }
    }

    val userDetails: HashMap<String, String?>
        get() {
            val user =
                HashMap<String, String?>()
            user[KEY_NAME] = pref.getString(
                KEY_NAME,
                null
            )
            user[KEY_EMAIL] = pref.getString(
                KEY_EMAIL,
                null
            )
            return user
        }

    fun logoutUser() {
        editor.clear()
        editor.commit()
        val i = Intent(context, LoginActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(i)
    }

    val isLoggedIn: Boolean
        get() = pref.getBoolean(
            IS_LOGIN,
            false
        )

    companion object {
        private const val PREF_NAME = "AzharPref"
        private const val IS_LOGIN = "IsLoggedIn"
        const val KEY_NAME = "name"
        const val KEY_EMAIL = "email"
    }

    init {
        pref = context.getSharedPreferences(
            PREF_NAME,
            PRIVATE_MODE
        )
        editor = pref.edit()
    }
}