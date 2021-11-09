package com.kyr.springjpa.util;

import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.*;
import java.nio.charset.Charset;
import java.util.Iterator;


@Slf4j
public class JsonUtil {

    public JsonUtil(){

    }

    private static void closeInputStream(FileInputStream fileInputStream, BufferedReader bufferedReader){
        if (fileInputStream != null) {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                log.error("fileInputStream not close : {}" , e);
            }
        }
        if (bufferedReader != null) {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                log.error("bufferedReader not close : {}" , e);
            }
        }
    }

    public static JsonObject readFileToJsonObjectData(String filePath, String date){
        JsonObject jsonObject = new JsonObject();
        FileInputStream fileInputStream = null;
        BufferedReader bufferedReader = null;
        try {
            File file = new File(filePath);
            if(file.exists()){
                fileInputStream = new FileInputStream(file);
                bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream , Charset.forName("UTF-8")));

                String readJsonData = "";
                String inputJsonData = "";

                while ((readJsonData = bufferedReader.readLine()) != null) {
                    inputJsonData += readJsonData;
                }

                jsonObject = (JsonObject) JsonParser.parseString(inputJsonData);
            }
        } catch (Exception e) {
            log.error("can not read this file | filePath : {} | date : {} | error : {}" , filePath , date , e);
        } finally {
            closeInputStream(fileInputStream,bufferedReader);
        }
        return jsonObject;
    }

    private static String searchContainManyObjectKey(JsonObject jsonObject , String[] keySet){
        int jsonObjectSize = 0;
        String returnContainManyObjectKey = "";
        for(String key : keySet){
            JsonArray jsonArray = (JsonArray) jsonObject.get(key);
            if(jsonArray.size() > jsonObjectSize) {
                jsonObjectSize = jsonArray.size();
                returnContainManyObjectKey = key;
            }
        }
        return returnContainManyObjectKey;
    }

    public static JsonArray getJsonArrayFromJsonObject(JsonObject jsonObject , String key) {
        JsonArray jsonArray = (JsonArray) jsonObject.get(key);

        return jsonArray;
    }

    public static String[] getJsonObjectKeySetStringArray(JsonObject jsonObject) {
        Iterator jsonObjectKey = jsonObject.keySet().iterator();
        StringBuilder stringBuilder = new StringBuilder();
        while(jsonObjectKey.hasNext()){
            stringBuilder.append(jsonObjectKey.next().toString() + ",");
        }
        if(stringBuilder.toString() != "") {
            stringBuilder.substring(0,stringBuilder.length()-1);
        }
        return stringBuilder.toString().split(",");
    }

}
