package com.example.volumeintimemanager

import android.content.Context
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.io.FileWriter
import java.io.IOException

class JSONFilesHandler(context: Context?) : FilesHandler(context!!) {
    fun addRuleToJSON(
        fileName: String?,
        id: Int?,
        timeFrom: String?,
        timeTo: String?,
        daysOfWeek: String?,
        spinnerChoice: Int,
        isRuleInUse: Boolean
    ): String {
        val file = readFile(fileName)
        return try {
            val previousJSONObj = JSONObject(file)
            val array = previousJSONObj.getJSONArray("rules")
            val jsonObj = JSONObject()
            jsonObj.put("id", id)
            jsonObj.put("timeFrom", timeFrom)
            jsonObj.put("timeTo", timeTo)
            jsonObj.put("daysOfWeek", daysOfWeek)
            jsonObj.put("spinnerChoice", spinnerChoice)
            jsonObj.put("isRuleInUse", isRuleInUse)
            array.put(jsonObj)
            val currentJSONObj = JSONObject()
            currentJSONObj.put("rules", array)
            //Log.d("JSONFileHandler", currentJSONObj.toString());
            currentJSONObj.toString()
        } catch (e: JSONException) {
            e.printStackTrace()
            ""
        }
    }

    fun overwriteJSONFile(fileName: String, json: String?) {
        val path = _context.filesDir.absolutePath + "/" + fileName
        val oldJSON = File(path)
        oldJSON.delete()
        val newJSON = File(path)
        try {
            val f2 = FileWriter(newJSON, false)
            f2.write(json)
            f2.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun returnLastIdFromJSON(json: String?): Int {
        return try {
            val previousJSONObj = JSONObject(json)
            val obj = previousJSONObj.getJSONArray("rules")
            var objLength = obj.length() - 1
            if (objLength == -1) objLength = 0
            val last_rule = obj.getJSONObject(objLength)
            last_rule?.optInt("id") ?: -1
        } catch (e: JSONException) {
            e.printStackTrace()
            -1
        }
    }
}