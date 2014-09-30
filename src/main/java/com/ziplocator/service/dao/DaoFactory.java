package com.ziplocator.service.dao;

/**
 * Created by a.zamkovyi on 20.09.2014.
 */
public class DaoFactory {
    private static CityDao cityDao = null;
    private static DaoFactory instance = null;

    public static synchronized DaoFactory getInstance(){
        if (instance == null){
            instance = new DaoFactory();
        }
        return instance;
    }

    public CityDao getCityDao(){
        if (cityDao == null){
            cityDao = new CityDaoImpl();
        }
        return cityDao;
    }
}
