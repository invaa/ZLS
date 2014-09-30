package com.ziplocator.service.dao;

import com.ziplocator.service.common.City;
import com.ziplocator.service.util.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class CityDaoImpl implements CityDao {

    public void addCity(City city) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(city);
            session.getTransaction().commit();
        } catch (Exception e) {
            //TODO: log
            System.err.println(e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {

                session.close();
            }
        }
    }

    public void updateCity(String zipCode, City city) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(city);
            session.getTransaction().commit();
        } catch (Exception e) {
            //TODO: log
            System.err.println(e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public City getCityByZip(String zipCode) throws SQLException {
        Session session = null;
        City city = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            city = (City) session.load(City.class, zipCode);
            Hibernate.initialize(city);
        } catch (Exception e) {
            //TODO: log
            System.err.println(e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return city;
    }

    public Collection getAllCities() throws SQLException {
        Session session = null;
        List cities = new ArrayList<City>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            cities = session.createCriteria(City.class).list();
        } catch (Exception e) {
            //TODO: log
            System.err.println(e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return cities;
    }

    public void deleteCity(City city) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(city);
            session.getTransaction().commit();
        } catch (Exception e) {
            //TODO: log
            System.err.println(e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public Collection getCitiesByState(String state) throws SQLException {
        Session session = null;
        List cities = new ArrayList<City>();
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Query query = session.createQuery(" from cities as c "
                            + " where c.state = :state "
            )
                    .setString("state", state);
            cities = (List<City>) query.list();
            session.getTransaction().commit();

        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return cities;
    }

    public Collection getCitiesByCityName(String cityName) throws SQLException {
        Session session = null;
        List cities = new ArrayList<City>();
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Query query = session.createQuery("from cities as c where c.cityName = :cityName").setString("cityName", cityName);
            cities = (List<City>) query.list();
            session.getTransaction().commit();

        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return cities;
    }

    @Override
    public boolean checkIfAState(String state) throws SQLException {
        Session session = null;
        int result = 0;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            result = session.createQuery("select count(distinct c.zip) from cities as c "
                            + " where c.state = :state "
            )
                    .setString("state", state)
                    .getFirstResult();

            session.getTransaction().commit();

        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return result == 0;
    }

    @Override
    public List<String> getZipsList(String string) throws SQLException {
        return null;
    }

    @Override
    public List<String> getCitiesList(String string) throws SQLException {
        return null;
    }

    @Override
    public List<String> getStatesCitiesList(String string) throws SQLException {
        return null;
    }

    @Override
    public List<String> getCorrelatedAddressStrings(String zipCode, String city, String state) throws SQLException {
        Session session = null;
        List cities = new ArrayList<City>();
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            Query query = session.createQuery("select c.statecityzip from cities as c "
                            + " where c.state = :state and c.city like :city and c.zip like :zipCode"
            )
                    .setString("state", state)
                    .setString("city", city + "%")
                    .setString("zipCode", zipCode + "%");
            cities = (List<City>) query.list();
            session.getTransaction().commit();

        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return cities;
    }

}