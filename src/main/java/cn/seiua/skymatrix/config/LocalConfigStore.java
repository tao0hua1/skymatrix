package cn.seiua.skymatrix.config;

import cn.seiua.skymatrix.client.Client;
import cn.seiua.skymatrix.client.component.Component;
import cn.seiua.skymatrix.client.component.Init;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

@Component
public class LocalConfigStore implements Store {
    private static final String BASE = "CF_{UUID}.json";
    private File config = new File(Client.root, "configs/");
    private File extra = new File(Client.root, "extra/");

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
        if (!extra.exists()) {
            extra.mkdirs();
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

    public String[] extraFiles() {
        ArrayList<String> names = new ArrayList<>();
        for (File file : Objects.requireNonNull(extra.listFiles())) {
            if (file.isFile()) {
                names.add(file.getName());
            }
        }
        return names.toArray(new String[]{});
    }

    public String writeExtraFile(String name) {
        try {
            return FileUtils.readFileToString(new File(extra, name), "utf-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String loadExtraFile(String name) {
        try {
            return FileUtils.readFileToString(new File(extra, name), "utf-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveExtraFile(String name, byte[] data) {
        try {
            FileUtils.writeByteArrayToFile(new File(extra, name + ".json"), data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
