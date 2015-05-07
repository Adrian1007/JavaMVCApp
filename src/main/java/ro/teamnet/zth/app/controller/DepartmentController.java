package ro.teamnet.zth.app.controller;

import ro.teamnet.zth.api.annotations.MyController;
import ro.teamnet.zth.api.annotations.MyRequestMethod;
import ro.teamnet.zth.app.dao.DepartmentDao;
import ro.teamnet.zth.app.domain.Department;

import java.util.List;

/**
 * Created by Adi on 06.05.2015.
 */
@MyController(urlPath = "/departments")
public class DepartmentController {
    @MyRequestMethod(methodType = "GET", urlPath = "/all")
    public List<Department> getAllDepartments() {
        DepartmentDao departmentDao = new DepartmentDao();
        List<Department> allDepartments = departmentDao.getAllDepartments();
        return allDepartments;
        //String allDepartments = "allDepartments";
        //return allDepartments;
    }

    @MyRequestMethod(methodType = "GET", urlPath = "/one")
    public String getOneDepartments() {
        String oneDepartments = "oneDepartments";
        return oneDepartments;
    }
}
