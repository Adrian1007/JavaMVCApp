package ro.teamnet.zth.app.controller;

import ro.teamnet.zth.api.annotations.MyController;
import ro.teamnet.zth.api.annotations.MyRequestMethod;

/**
 * Created by Adi on 06.05.2015.
 */

@MyController(urlPath = "/employees")
public class EmployeeController {
    @MyRequestMethod(methodType = "GET", urlPath = "/all")
    public String getAllEmployees() {
        String allEmployees = "allEmployees";
        return allEmployees;
    }
    @MyRequestMethod(methodType = "GET", urlPath = "/one")
    public String getOneEmployees() {
        String oneEmployees = "oneEmployees";
        return oneEmployees;
    }
}
