package com.kyr.springjpa.util;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Properties;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class Constants {

    public static String SERVER_TYPE = "";

    static {
        InputStream fis = null;
        try {
            Properties props = new Properties();
            fis = ConfigPath.class.getResourceAsStream("server.properties");
            props.load(new BufferedInputStream(fis));
            SERVER_TYPE = props.getProperty("this.server").trim();
            fis = ConfigPath.class.getResourceAsStream(SERVER_TYPE + ".properties");
            props.load(new java.io.BufferedInputStream(fis));

        } catch (Exception e) {
            log.error("error->" + e);
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (Exception e) {
                    log.error("error->" + e);
                }

            }
        }
    }

}
