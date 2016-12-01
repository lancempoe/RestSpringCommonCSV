package com.lancep.csv.util;

import java.io.InputStreamReader;

public class ResourceLoader {

    public InputStreamReader getResource(String location){
        return new InputStreamReader(this.getClass().getResourceAsStream(location));
    }
}
