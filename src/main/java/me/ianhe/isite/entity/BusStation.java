package me.ianhe.isite.entity;

/**
 * BusStation
 *
 * @author linhe2
 * @since 2018/7/9 12:37
 */
public class BusStation {
    private Integer id;

    private String stationName;

    private String busId;

    private Integer busIndex;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public Integer getBusIndex() {
        return busIndex;
    }

    public void setBusIndex(Integer busIndex) {
        this.busIndex = busIndex;
    }
}