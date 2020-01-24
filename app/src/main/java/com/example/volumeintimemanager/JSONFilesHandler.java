package com.example.volumeintimemanager;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class JSONFilesHandler extends FilesHandler {

    public JSONFilesHandler(Context context){
        super(context);
    }

    public String addRuleToJSON(String fileName, Integer id, String timeFrom, String timeTo, String daysOfWeek, int spinnerChoice, boolean isRuleInUse){
        String file = readFile(fileName);

        try{
            JSONObject previousJSONObj = new JSONObject(file);
            JSONArray array = previousJSONObj.getJSONArray("rules");
            JSONObject jsonObj = new JSONObject();

            jsonObj.put("id", id);
            jsonObj.put("timeFrom", timeFrom);
            jsonObj.put("timeTo", timeTo);
            jsonObj.put("daysOfWeek", daysOfWeek);
            jsonObj.put("spinnerChoice", spinnerChoice);
            jsonObj.put("isRuleInUse", isRuleInUse);

            array.put(jsonObj);

            JSONObject currentJSONObj = new JSONObject();
            currentJSONObj.put("rules", array);
            //Log.d("JSONFileHandler", currentJSONObj.toString());
            return currentJSONObj.toString();
        }catch (JSONException e){
            e.printStackTrace();
            return "";
        }
    }

    public void overwriteJSONFile(String fileName, String json) {
        String path = _context.getFilesDir().getAbsolutePath() + "/" + fileName;
        File oldJSON = new File(path);
        oldJSON.delete();

        File newJSON = new File(path);

        try {
            FileWriter f2 = new FileWriter(newJSON, false);
            f2.write(json);
            f2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int returnLastIdFromJSON(String json){
        try{
            JSONObject previousJSONObj = new JSONObject(json);
            JSONArray obj = previousJSONObj.getJSONArray("rules");
            int objLength = obj.length() - 1;
            if (objLength == -1) objLength = 0;
            JSONObject last_rule = obj.getJSONObject(objLength);
            return last_rule != null ? last_rule.optInt("id") : -1;
        }catch (JSONException e){
            e.printStackTrace();
            return -1;
        }
    }
}
