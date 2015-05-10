package edu.upc.eetac.dsa.oriol.libreria.api;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Oriol on 10/05/2015.
 */
public class Link {
    private String target;
    private Map<String, String> parameters;

    public Link() {
        parameters = new HashMap<String, String>(); //A partir de este mapa se obtienen los parametros
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }
}
