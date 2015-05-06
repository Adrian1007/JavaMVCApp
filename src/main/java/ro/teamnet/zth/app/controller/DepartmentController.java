package ro.teamnet.zth.app.controller;

import ro.teamnet.zth.api.annotations.MyController;
import ro.teamnet.zth.api.annotations.MyRequestMethod;

/**
 * Created by Adi on 06.05.2015.
 */
@MyController(urlPath = "/departments")
public class DepartmentController {
    @MyRequestMethod(methodType = "GET", urlPath = "/all")
    public String getAllDepartments() {
        String allDepartments = "allDepartments";
        return allDepartments;
    }

    @MyRequestMethod(methodType = "GET", urlPath = "/one")
    public String getOneDepartments() {
        String oneDepartments = "oneDepartments";
        return oneDepartments;
    }
}
