package com.example.very_new_travel.database

import android.annotation.SuppressLint
import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import kotlinx.android.synthetic.main.activity_main.*

class DatabaseHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    private var db: SQLiteDatabase? = null
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("PRAGMA foreign_keys=ON")
        db.execSQL(
            "create table " + TABLE_USER + " (" + COL_USERNAME + " TEXT PRIMARY KEY, " + COL_PASSWORD +
                    " TEXT, " + COL_NAME + " TEXT)"
        )
        db.execSQL(
            "create table " + TABLE_BOOK + " (" + COL_ID_BOOK + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_ASAL + " TEXT, " + COL_TUJUAN + " TEXT" + ", " + COL_TANGGAL + " TEXT, " + COL_DEWASA + " TEXT, "
                    + COL_ANAK + " TEXT)"
        )
        db.execSQL(
            "create table " + TABLE_HARGA + " (" + COL_USERNAME + " TEXT, " + COL_ID_BOOK + " INTEGER, " +
                    COL_HARGA_DEWASA + " TEXT, " + COL_HARGA_ANAK + " TEXT, " + COL_HARGA_TOTAL +
                    " TEXT, FOREIGN KEY(" + COL_USERNAME + ") REFERENCES " + TABLE_USER
                    + ", FOREIGN KEY(" + COL_ID_BOOK + ") REFERENCES " + TABLE_BOOK + ")"
        )
        db.execSQL("insert into $TABLE_USER values ('azhar@gmail.com','azhar','Azhar Rivaldi');")
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USER")
        onCreate(db)
    }

    @Throws(SQLException::class)
    fun open() {
        db = this.writableDatabase
    }

    @Throws(SQLException::class)
    fun Register(
        username: String,
        password: String,
        name: String
    ): Boolean {
        @SuppressLint("Recycle") val mCursor = db!!.rawQuery(
            "INSERT INTO $TABLE_USER($COL_USERNAME, $COL_PASSWORD, $COL_NAME) VALUES (?,?,?)",
            arrayOf(username, password, name)
        )
        return if (mCursor != null) {
            mCursor.count > 0
        } else false
    }

    @Throws(SQLException::class)
    fun Login(username: String, password: String): Boolean {
        @SuppressLint("Recycle") val mCursor = db!!.rawQuery(
            "SELECT * FROM $TABLE_USER WHERE $COL_USERNAME=? AND $COL_PASSWORD=?",
            arrayOf(username, password)
        )
        return if (mCursor != null) {
            mCursor.count > 0
        } else false
    }

    companion object {
        const val DATABASE_NAME = "db_travel"
        const val TABLE_USER = "tb_user"
        const val COL_USERNAME = "username"
        const val COL_PASSWORD = "password"
        const val COL_NAME = "name"
        const val TABLE_BOOK = "tb_book"
        const val COL_ID_BOOK = "id_book"
        const val COL_ASAL = "asal"
        const val COL_TUJUAN = "tujuan"
        const val COL_TANGGAL = "tanggal"
        const val COL_DEWASA = "dewasa"
        const val COL_ANAK = "anak"
        const val TABLE_HARGA = "tb_harga"
        const val COL_HARGA_DEWASA = "harga_dewasa"
        const val COL_HARGA_ANAK = "harga_anak"
        const val COL_HARGA_TOTAL = "harga_total"
    }
}