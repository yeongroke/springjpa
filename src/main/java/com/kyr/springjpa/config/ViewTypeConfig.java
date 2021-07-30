package com.kyr.springjpa.config;


import com.kyr.springjpa.util.Constants;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

@Slf4j
public class ViewTypeConfig {
    protected String typeReturnConfig(String type){
        File file = new File("src/main/resource");
        String returnConfigPath = file.getAbsolutePath();
        switch(type){
            case "test1" :
                returnConfigPath += Constants.file2;
                break;
        }
        log.info("viewtypeconfig-> " + returnConfigPath);
        return returnConfigPath;
    }
}
