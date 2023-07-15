package cn.seiua.skymatrix.client.waypoint;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

public class WaypointGroupEntity implements Serializable {

    private ArrayList<WaypointEntity> waypoints;

    public String name;
    public String world;
    public Map<String, String> data;

    public WaypointGroupEntity(ArrayList<WaypointEntity> waypoints, String name, String world) {
        this.waypoints = waypoints;
        this.name = name;
        this.world = world;
    }

    public WaypointGroupEntity() {

    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    public ArrayList<WaypointEntity> getWaypoints() {
        return waypoints;
    }

    public void setWaypoints(ArrayList<WaypointEntity> waypoints) {
        this.waypoints = waypoints;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
