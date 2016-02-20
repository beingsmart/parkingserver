package com.park.api.query;

/**
 * Created by sarath on 07/02/16.
 */
public class Find {
    private final String query;
    private final Object value;

    public Find(String query, Object value){
        this.query=query;
        this.value=value;
    }
}
