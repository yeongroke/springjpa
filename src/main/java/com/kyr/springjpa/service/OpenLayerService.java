package com.kyr.springjpa.service;

import org.json.simple.JSONArray;

public interface OpenLayerService {
    JSONArray getAreaCodeData(String dataType) throws Exception;

    JSONArray getAreaJsonData(String dataType) throws Exception;

    String getBase64Img(String dataType) throws Exception;
}
