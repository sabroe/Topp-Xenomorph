package com.yelstream.topp.lang;

import lombok.experimental.UtilityClass;

/**
 * Gobbles up individual lines from a stream of raw data.
 *
 * @author Morten Sabroe Mortenen
 * @version 1.0
 * @since 2023-02-07
 */
@UtilityClass
public class Strings {
    public static String capitalizeFirstLetter(String text) {  //TO-DO: Consider moving to string-utility!
        String capitalizedText=text;
        if (text!=null && !text.isEmpty()) {
            capitalizedText=text.substring(0, 1).toUpperCase() + text.substring(1);  //See e.g. https://www.baeldung.com/java-string-uppercase-first-letter
        }
        return capitalizedText;
    }
}
