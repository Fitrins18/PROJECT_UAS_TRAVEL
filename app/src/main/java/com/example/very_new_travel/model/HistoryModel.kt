package com.example.very_new_travel.model

class HistoryModel(
    val idBook: String,
    val tanggal: String,
    val riwayat: String,
    val total: String,
    val imageResourceId: Int
) {

    fun hasImage(): Boolean {
        return imageResourceId != NO_IMAGE_PROVIDED
    }

    companion object {
        private const val NO_IMAGE_PROVIDED = -1
    }

}