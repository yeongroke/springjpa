package com.kyr.springjpa.service;

import com.google.gson.JsonArray;

public interface OpenLayerService {
    JsonArray getAreaCodeData(String dataType) throws Exception;

    JsonArray getAreaJsonData(String dataType) throws Exception;

    String getBase64Img(String dataType) throws Exception;
}
