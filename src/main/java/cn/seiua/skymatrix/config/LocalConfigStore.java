package cn.seiua.skymatrix.config;

import cn.seiua.skymatrix.client.Client;
import cn.seiua.skymatrix.client.component.Component;
import cn.seiua.skymatrix.client.component.Init;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@Component
public class LocalConfigStore implements Store {
    private static final String BASE = "CF_{UUID}.json";
    private File config = new File(Client.root, "configs/");

    @Override
    public void write(byte[] data, String uuid) {
        try {
            FileUtils.writeByteArrayToFile(new File(config, BASE.replace("{UUID}", uuid)), data, false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Init(level = -10)
    public void init() {
        if (!config.exists()) {
            config.mkdirs();
        }

    }


    @Override
    public byte[] load(String uuid) {
        try {
            return FileUtils.readFileToByteArray(new File(config, BASE.replace("{UUID}", uuid)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String[] loadUUIDs() {

        ArrayList<String> uuids = new ArrayList<>();
        for (File file : config.listFiles()) {
            if (file.isFile()) {
                if (file.getName().startsWith("CF_")) {
                    uuids.add(file.getName().replace(".json", "").replace("CF_", ""));
                }
            }
        }


        return uuids.toArray(new String[]{});
    }
}
