package com.ilamcvel.service;

/**
 * Service to display the results to console.
 */
public interface DisplayService {
    /**
     * Method that returns a String based on the key and the params supplied.
     *
     * @param key
     * @param params
     * @return
     */
    String getMessage(String key, Object... params);
}
