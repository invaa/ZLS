package com.ziplocator.service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import com.ziplocator.service.common.City;
import com.ziplocator.service.dao.DaoFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;
import java.util.List;

import java.util.Locale;

@Controller
public class MapController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView getPages() throws SQLException {

		ModelAndView model = new ModelAndView("map");
        //model.addObject("allCities", DaoFactory.getInstance().getCityDao().getAllCities());
		return model;

	}

	@RequestMapping(value = "/getCityByZip", method = RequestMethod.GET)
	public @ResponseBody
	String getCityInJsonFormat(@RequestParam String zip) throws SQLException {

		ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Hibernate4Module());
	    City city = DaoFactory.getInstance().getCityDao().getCityByZip(zip);

		String result = "";

		try {
			result = mapper.writeValueAsString(city);
		} catch (Exception e) {

			e.printStackTrace();
		}

		return result;
	}

    @RequestMapping(value = "/getCityByZipStateCityName", method = RequestMethod.GET)
    public @ResponseBody
    String getCityByZipStateCityNameInJsonFormat(@RequestParam String zipCode, @RequestParam String cityName, @RequestParam String state) throws SQLException {

        //TODO: implement
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Hibernate4Module());
        City city = DaoFactory.getInstance().getCityDao().getCityByZip(zipCode);

        String result = "";

        try {
            result = mapper.writeValueAsString(city);
        } catch (Exception e) {

            e.printStackTrace();
        }

        return result;
    }

    @RequestMapping(value = "/getCityZips", method = RequestMethod.GET)
    public @ResponseBody
    String getCityByZipInJsonFormat() throws SQLException {

        String result = "['00602', '44602', '00603', '00604']";

        return result;

    }

    /**
     * Checks whether given two symbols have corresponding state
     *
     * @return
     * @throws SQLException
     */
    @RequestMapping(value = "/checkIfAState", method = RequestMethod.GET)
    public @ResponseBody
    String checkIfAStateJsonFormat(@RequestParam String state) throws SQLException {

        if (DaoFactory.getInstance().getCityDao().checkIfAState(state)) {
            return "true";
        } else {
            return "false";
        }
    }

    /**
     * Gets the list of strings for autocomplete
     *
     * @return
     * @throws SQLException
     */
    @RequestMapping(value = "/getCorrelatedAddressStrings", method = RequestMethod.GET)
    public @ResponseBody
    String getCorrelatedAddressStrings(@RequestParam String zipCode, @RequestParam String city, @RequestParam String state) throws SQLException {
        ModelAndView model = new ModelAndView("map");


        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Hibernate4Module());

        List<String> list = DaoFactory.getInstance().getCityDao().getCorrelatedAddressStrings(zipCode, city, state);

        String result = "";

        try {
            result = mapper.writeValueAsString(list);
        } catch (Exception e) {

            e.printStackTrace();
        }

        return result;
    }

    /**
     * Gets full region name by state code (or country code)
     *
     * @return
     * @throws SQLException
     */
    @RequestMapping(value = "/getStateName", method = RequestMethod.GET)
    public @ResponseBody
    String getStateName(@RequestParam String stateCode) {

        String[] locales = Locale.getISOCountries();

        for (String countryCode : locales) {

            Locale obj = new Locale("", countryCode);

            if (obj.getCountry().equals(stateCode)) {
                return obj.getDisplayCountry(Locale.ENGLISH);
            }
        }

        return "";
    }


}
