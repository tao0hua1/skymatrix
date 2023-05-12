package cn.seiua.skymatrix.config;

import cn.seiua.skymatrix.client.Client;
import cn.seiua.skymatrix.client.component.Component;
import cn.seiua.skymatrix.client.component.Init;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

@Component
public class LocalConfigStore implements Store {
    private File config= new File(Client.root,"config.json");


    @Override
    public void write(byte[] data, String uuid) {
        try {
            FileUtils.writeByteArrayToFile(config,data,false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Init(level = -10)
    public void init() {  try {
        if(!config.exists()){
                Client.root.mkdirs();
                config.createNewFile();

            FileUtils.writeStringToFile(config,"{}");
        }
        } catch (IOException e) {
        throw new RuntimeException(e);
    }
    }

    @Override
    public byte[] load(String uuid)  {
        try {
            return FileUtils.readFileToByteArray(config);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String[] loadUUIDs() {
        return new String[0];
    }
}
