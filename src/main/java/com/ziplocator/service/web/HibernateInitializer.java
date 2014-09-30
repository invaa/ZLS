package com.ziplocator.service.web;

import com.ziplocator.service.util.HibernateUtil;
import org.hibernate.Session;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.servlet.ServletContext;

/**
 * Created by a.zamkovyi on 21.09.2014.
 */
public class HibernateInitializer extends InternalResourceViewResolver {

    @Override
    public void initApplicationContext() {
        super.initApplicationContext();
        System.out.println("INITIALIZING");
        HibernateUtil.getSessionFactory();
    }

}
