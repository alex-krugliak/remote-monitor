package com.web.service.imp;


import org.apache.log4j.Logger;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created on 26.10.15.
 */

@Service
public class BundlesServiceImpl {

    private static final Logger logger = Logger.getLogger(BundlesServiceImpl.class);

    private Map<String, String> cachedBundles = null;

    private Map<String, Double> cachedCoefficients = null;


    public synchronized Map<String, String> getAllBundles() {

        if (cachedBundles == null) {

            Properties bundles = getProperties("classpath:label_ru.properties");
            if (bundles == null) {
                logger.error("Bundles load error");
                return null;
            }

            Map<String, String> result = new HashMap<>();

            for (String name : bundles.stringPropertyNames()) {
                result.put(name, bundles.getProperty(name));
            }
            logger.debug("Bundles loaded");
            cachedBundles = result;

        }

        return cachedBundles;

    }

    public synchronized Map<String, Double> getAllCoefficient() {

        if (cachedCoefficients == null) {
            Properties coefficients = getProperties("classpath:coefficients.properties");
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
            cachedCoefficients = result;
        }

        return cachedCoefficients;


    }

    private Properties getProperties(String properties) {
        Properties prop = new Properties();
        try {
            InputStream stream = new FileSystemResourceLoader().getResource(properties).getInputStream();

            InputStreamReader inputStream = new InputStreamReader(stream, "UTF-8");

            prop.load(inputStream);
            return prop;

        } catch (Exception ex) {
            return null;
        }
    }
}
