package com.kyr.springjpa.util;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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

    public static JSONObject readFileToJsonObjectData(String filePath, String date){
        JSONObject jsonObject = new JSONObject();
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

                JSONParser jsonParser = new JSONParser();
                jsonObject = (JSONObject) jsonParser.parse(inputJsonData);
            }
        } catch (Exception e) {
            log.error("can not read this file | filePath : {} | date : {} | error : {}" , filePath , date , e);
        } finally {
            closeInputStream(fileInputStream,bufferedReader);
        }
        return jsonObject;
    }

    private static String searchContainManyObjectKey(JSONObject jsonObject , String[] keySet){
        int jsonObjectSize = 0;
        String returnContainManyObjectKey = "";
        for(String key : keySet){
            JSONArray jsonArray = (JSONArray) jsonObject.get(key);
            if(jsonArray.size() > jsonObjectSize) {
                jsonObjectSize = jsonArray.size();
                returnContainManyObjectKey = key;
            }
        }
        return returnContainManyObjectKey;
    }

    public static JSONArray getJsonArrayFromJsonObject(JSONObject jsonObject , String key) {
        JSONArray jsonArray = (JSONArray) jsonObject.get(key);

        return jsonArray;
    }

    public static String[] getJsonObjectKeySetStringArray(JSONObject jsonObject) {
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
