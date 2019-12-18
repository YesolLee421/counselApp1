package com.example.counselapp

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.lang.Exception

interface Utils {

    fun createFile(realPath: String?): File? {
        return File(realPath)
    }

    fun getPathFromUri(context: Context, uri: Uri?): String? {
        var path: String = ""
        context.contentResolver.query(
            uri, arrayOf(MediaStore.Images.Media.DATA), null, null, null
        )?.apply {
            val columnIndex = getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            moveToFirst()
            path = getString(columnIndex)
            close()
        }
        return path
    }

    fun uploadFiles(file: File): MultipartBody.Part {
        val requestBody: RequestBody = RequestBody.create(MediaType.parse("image/*"), file)
        val body: MultipartBody.Part = MultipartBody.Part.create(requestBody)
        return body
    }
}