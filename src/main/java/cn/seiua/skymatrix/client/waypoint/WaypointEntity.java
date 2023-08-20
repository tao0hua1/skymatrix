package cn.seiua.skymatrix.client.waypoint;

import net.minecraft.util.math.BlockPos;

import java.io.Serializable;
import java.util.Map;

public class WaypointEntity implements Serializable {

    public int x;
    public int y;
    public int z;

    public String world;

    public String name;

    public Map<String, String> data;

    public WaypointEntity(int x, int y, int z, String name) {

        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
    }

    public WaypointEntity() {


    }

    public BlockPos toBlockPos() {
        return new BlockPos(x, y, z);
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }
}
