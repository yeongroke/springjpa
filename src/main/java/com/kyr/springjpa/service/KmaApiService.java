package com.kyr.springjpa.service;


public interface KmaApiService {

    String restApiGetKmaData(int pageNo , int pageSize , String set_Date , String set_Time , String dataX , String dataY) throws Exception;


}
