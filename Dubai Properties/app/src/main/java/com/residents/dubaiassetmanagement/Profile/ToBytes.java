package com.residents.dubaiassetmanagement.Profile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ToBytes

{

    public static byte[] getBytes(File f) throws FileNotFoundException,IOException{

        byte[] buffer = new byte[1024];
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        FileInputStream fis = new FileInputStream(f);
        int read;
        while ((read = fis.read(buffer)) != -1){
            os.write(buffer,0,read);
        }
        fis.close();
        os.close();
        return os.toByteArray();


    }
}
