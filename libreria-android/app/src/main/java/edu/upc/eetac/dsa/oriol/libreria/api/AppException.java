package edu.upc.eetac.dsa.oriol.libreria.api;

/**
 * Created by Oriol on 10/05/2015.
 */
public class AppException extends Exception {
    public AppException() {
        super();
    }

    public AppException(String detailMessage) {
        super(detailMessage);
    }
}