package ro.teamnet.zth.app.controller;

import ro.teamnet.zth.api.annotations.MyController;
import ro.teamnet.zth.api.annotations.MyRequestMethod;

/**
 * Created by Adi on 06.05.2015.
 */
@MyController(urlPath = "/locations")
public class LocationController {

    @MyRequestMethod(methodType = "GET", urlPath = "/all")
    public String getAllLocations() {
        String allLocations = "getAllLocations";
        return allLocations;
    }

    @MyRequestMethod(methodType = "GET", urlPath = "/one")
    public String getOneLocations() {
        String oneLocations = "getOneLocations";
        return oneLocations;
    }
}
