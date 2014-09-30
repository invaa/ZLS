import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import com.ziplocator.service.common.City;
import com.ziplocator.service.dao.CityDao;
import com.ziplocator.service.dao.DaoFactory;
import com.ziplocator.service.util.StateCodeManager;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;

/**
 * Test scenario for DAO classes
 */
public class Main {
    public static void main(String[] args) throws SQLException, IOException {


//        ObjectMapper mapper = new ObjectMapper();
//        mapper.registerModule(new Hibernate4Module());
//        CityDao cityDao = DaoFactory.getInstance().getCityDao();
//
//        City city = cityDao.getCityByZip("00602");//cityBo.getCity(searchQueryString);
//
//        String result = "";
//
//        try {
//            result = mapper.writeValueAsString(city);
//        } catch (Exception e) {
//
//            e.printStackTrace();
//        }
//
//        System.out.println(city);
//        System.out.println(result);

        System.out.println(StateCodeManager.getStateName("PR"));


//        Collection cities = DaoFactory.getInstance().getCityDao().getAllCities();
//        Iterator iterator = cities.iterator();
//        System.out.println("========All cities=========");
//        while (iterator.hasNext()) {
//            City city = (City) iterator.next();
//            System.out.println("City : " + city.getCityName());
//        }
    }
}
