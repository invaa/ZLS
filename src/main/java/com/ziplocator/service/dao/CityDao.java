package com.ziplocator.service.dao;

import com.ziplocator.service.common.City;

import java.util.Collection;
import java.sql.SQLException;
import java.util.List;


/**
 * DAO interface for City POJO
 */

public interface CityDao {
    public void addCity(City city) throws SQLException;
    public void updateCity(String zipCode, City city) throws SQLException;
    public City getCityByZip(String zipCode) throws SQLException;
    public Collection getAllCities() throws SQLException;
    public void deleteCity(City city) throws SQLException;
    public Collection getCitiesByState(String state) throws SQLException;
    public Collection getCitiesByCityName(String cityName) throws SQLException;

    public boolean checkIfAState(String state) throws SQLException;
    public List<String> getZipsList(String string) throws SQLException;
    public List<String> getCitiesList(String string) throws SQLException;
    public List<String> getStatesCitiesList(String string) throws SQLException;
    public List<String> getCorrelatedAddressStrings(String zipCode, String city, String state) throws SQLException;
}
