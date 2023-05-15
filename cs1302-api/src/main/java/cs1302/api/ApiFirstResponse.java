package cs1302.api;

import java.util.List;
import java.util.Map;

 /**
  * This class represents a response from the First Response API, which
  * contains a map of country data.
  */

public class ApiFirstResponse {

    private Map<String, Country> data;

/**
 * Returns a Map of countries where the keys are the country codes
 * and the values are the Country objects.
 * @return a Map of countries.
 */
    public Map<String, Country> getData() {
        return data;
    } //getData

    /**
     * Sets the Map of countries where the keys are the country codes
     * and the values are the Country objects.
     * @param data the Map of countries.
     */

    public void setData(Map<String, Country> data) {
        this.data = data;
    } //setData

} //ApiFirstResponse
