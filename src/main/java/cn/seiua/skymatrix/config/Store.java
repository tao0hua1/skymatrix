package cn.seiua.skymatrix.config;

import com.sun.jna.Library;

import java.io.IOException;

public interface Store extends Library {

     void write(byte[] data,String uuid);
     byte[] load(String uuid) throws IOException;
     String[] loadUUIDs();

}
