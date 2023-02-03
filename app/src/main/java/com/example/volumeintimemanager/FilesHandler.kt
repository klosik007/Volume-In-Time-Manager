package com.example.volumeintimemanager

import android.content.Context
import android.util.Log
import java.io.*

open class FilesHandler(@JvmField var _context: Context) {
    fun readFile(fileName: String?): String? {
        return try {
            val fs = _context.openFileInput(fileName)
            val isr = InputStreamReader(fs)
            val bufferedReader = BufferedReader(isr)
            val sb = StringBuilder()
            var line: String?
            while (bufferedReader.readLine().also { line = it } != null) {
                sb.append(line)
            }
            sb.toString()
        } catch (fileNotFound: FileNotFoundException) {
            null
        } catch (ioException: IOException) {
            null
        }
    }

    fun createFile(fileName: String?, jsonString: String?): Boolean {
        return try {
            val fos = _context.openFileOutput(fileName, Context.MODE_PRIVATE)
            if (jsonString != null) {
                fos.write(jsonString.toByteArray())
            }
            fos.close()
            true
        } catch (fileNotFound: FileNotFoundException) {
            false
        } catch (ioException: IOException) {
            false
        }
    }

    fun isFileAvailable(fileName: String): Boolean {
        val path = _context.filesDir.absolutePath + "/" + fileName
        Log.d("FilesHandler", path)
        val file = File(path)
        return file.exists()
    }
}