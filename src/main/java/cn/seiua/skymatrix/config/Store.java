package cn.seiua.skymatrix.config;

import com.sun.jna.Library;

import java.io.IOException;

public interface Store extends Library {
    String WAYPOINT = "WP_";

    void write(byte[] data, String uuid);

    byte[] load(String uuid) throws IOException;

    String[] loadUUIDs();

    String[] extraFiles();

    String loadExtraFile(String name);

    public void saveExtraFile(String name, byte[] data);
}
