package com.kyr.springjpa.service.impl;

import com.kyr.springjpa.config.ViewTypeConfig;
import com.kyr.springjpa.service.OpenLayerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import javax.imageio.ImageIO;
import org.apache.tomcat.util.codec.binary.Base64;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;



@Slf4j
@Service("OpenLayerService")
public class OpenLayerServiceImpl extends ViewTypeConfig implements OpenLayerService {

    public void closeBuffer(FileInputStream fis , BufferedReader rd , String dataType) {
        if (fis != null) {
            try {
                fis.close();
            } catch (IOException e) {
                log.error("fileinput not close " + dataType + "->" + e);
            }
        }
        if (rd != null) {
            try {
                rd.close();
            } catch (IOException e) {
                log.error("BufferedReader not close " + dataType + "->" + e);
            }
        }
    }

    @Override
    public JsonArray getAreaCodeData(String dataType) {
        JsonObject jsonobj = new JsonObject();
        JsonArray jsonarr = new JsonArray();
        FileInputStream fis = null;
        BufferedReader rd = null;
        try {
            File file = new File(typeReturnConfig(dataType));

            fis = new FileInputStream(file);
            rd = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));
            String line = "";
            while ((line = rd.readLine()) != null) {
                String[] data = line.split("\\s,");
                for (int i = 0; i < data.length; i++) {
                    if (i == 0) {
                        jsonobj.addProperty("KONAME", data[i]);
                    } else {
                        jsonobj.addProperty("data", data[i]);
                    }
                }
                jsonarr.add(jsonobj);
                jsonobj = new JsonObject();
            }
        } catch (final Exception e) {
            log.error("not read json file " + dataType + "->" + e);
        } finally {
            closeBuffer(fis , rd , dataType);
        }
        return null;
    }

    @Override
    public JsonArray getAreaJsonData(String dataType) {
        JsonObject jsonobj = new JsonObject();
        JsonArray jsonarr = new JsonArray();
        FileInputStream fis = null;
        BufferedReader rd = null;
        try {
            final File file = new File(typeReturnConfig(dataType));

            fis = new FileInputStream(file);
            rd = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));

            String line = "";
            while ((line = rd.readLine()) != null) {
                String[] data = line.split("\\s,");
                for (int i = 0; i < data.length; i++) {
                    if (i == 0) {
                        jsonobj.addProperty("KONAME", data[i]);
                    } else if(i == 1) {
                        jsonobj.addProperty("Lon", data[i]);
                    } else if(i == 2) {
                        jsonobj.addProperty("Lat", data[i]);
                    } else if(i == 3) {
                        jsonobj.addProperty("Sensor", data[i]);
                    } else if(i == 4) {
                        jsonobj.addProperty("NickName", data[i]);
                    } else if(i == 5) {
                        jsonobj.addProperty("Altitude", data[i]);
                    }
                }
                jsonarr.add(jsonobj);
                jsonobj = new JsonObject();
            }
        } catch (Exception e) {
            log.error("not read json file " + dataType + "->" + e);
        } finally {
            closeBuffer(fis , rd , dataType);
        }
        return jsonarr;
    }

    @Override
    public String getBase64Img(String dataType) throws Exception {
        String sbase64 = "";
        try {
            byte[] imageInByte;
            imageInByte = new byte[] {};
            BufferedImage originalImage = null;
            long imageInfo[] = null;
            imageInfo = new long[3];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            originalImage = ImageIO.read(new File(typeReturnConfig(dataType)));

            imageInfo[0] = originalImage.getWidth();
            imageInfo[1] = originalImage.getHeight();
            ImageIO.write(originalImage, "jpg", baos);
            baos.flush();
            imageInByte = baos.toByteArray();
            baos.close();
            sbase64 = new String(Base64.encodeBase64(imageInByte));


        } catch (final Exception e) {
            log.error("can not read image " + dataType + "->" + e);
        }
        return sbase64;
    }

}
