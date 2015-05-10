package edu.upc.eetac.dsa.oriol.libreria.api;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Oriol on 10/05/2015.
 */
public class LibreriaRootAPI {
    private Map<String, Link> links;

    public LibreriaRootAPI() {
        links = new HashMap<String, Link>();
    }

    public Map<String, Link> getLinks() {
        return links;
    }
}
