package com.ziplocator.service.common;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * City POJO Object
 *
 * @version 1.0
 * @author a.zamkovyi
 */

@Entity
@Table(name="cities")
public class City implements Serializable {

    @Id
    private String zipCode;
    private String cityName;
    private String state;
    private Float latitude;
    private Float longitude;
    private Integer timezone;
    private Integer dst;
    private String cityzip;
    private String statecityzip;

    public String getCityzip() {
        return cityzip;
    }

    public void setCityzip(String cityzip) {
        this.cityzip = cityzip;
    }

    public String getStatecityzip() {
        return statecityzip;
    }

    public void setStatecityzip(String statecityzip) {
        this.statecityzip = statecityzip;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Integer getTimezone() {
        return timezone;
    }

    public void setTimezone(Integer timezone) {
        this.timezone = timezone;
    }

    public Integer getDst() {
        return dst;
    }

    public void setDst(Integer dst) {
        this.dst = dst;
    }

    @Override
    public String toString() {
        return "City [" +
                "zipCode=" + zipCode +
                ", cityName=" + cityName +
                ", state=" + state +
                ", latitude=" + String.format("%f",latitude) +
                ", longitude=" + String.format("%f",longitude) +
                ", timezone=" + timezone +
                ", dst=" + dst +
                ", cityzip=" + cityzip +
                ", statecityzip=" + statecityzip +
                ']';
    }
}

