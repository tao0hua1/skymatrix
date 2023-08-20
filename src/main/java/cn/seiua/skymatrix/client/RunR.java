package cn.seiua.skymatrix.client;

import cn.seiua.skymatrix.client.waypoint.WaypointEntity;

public interface RunR<V> {
    V run(WaypointEntity entity);
}
