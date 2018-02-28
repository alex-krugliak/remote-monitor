package com.web.service.imp;


import org.apache.log4j.Logger;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created on 26.10.15.
 */

public class BundlesServiceImpl {

    private static final Logger logger = Logger.getLogger(BundlesServiceImpl.class);

    public static Map<String, String> getAllBundles(String properties) {

        Properties bundles = getProperties(properties);
        if (bundles == null) {
            logger.error("Bundles load error");
            return null;
        }

        Map<String, String> result = new HashMap<>();

        for (String name : bundles.stringPropertyNames()) {
            result.put(name, bundles.getProperty(name));
        }
        logger.debug("Bundles loaded");
        return result;


    }

    public static Map<String, Double> getAllCoefficient(String properties) {

        Properties coefficients = getProperties(properties);
        if (coefficients == null) {
            logger.error("Coefficients load error");
            return null;
        }

        Map<String, Double> result = new HashMap<>();

        for (String name : coefficients.stringPropertyNames()) {
            String value = coefficients.getProperty(name);
            result.put(name, Double.parseDouble(value));
        }
        logger.debug("Coefficients loaded" + result.values());
        return result;


    }

    private static Properties getProperties(String properties) {
        Properties prop = new Properties();
        try {
            InputStream stream = BundlesServiceImpl.class.getClassLoader().getResourceAsStream(properties);

            InputStreamReader inputStream = new InputStreamReader(stream, "UTF-8");

            prop.load(inputStream);
            return prop;

        } catch (Exception ex) {
            return null;
        }
    }
}
