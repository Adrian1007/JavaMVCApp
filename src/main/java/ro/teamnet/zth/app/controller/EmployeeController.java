package ro.teamnet.zth.app.controller;

import ro.teamnet.zth.api.annotations.MyController;
import ro.teamnet.zth.api.annotations.MyParamRequest;
import ro.teamnet.zth.api.annotations.MyRequestMethod;
import ro.teamnet.zth.app.dao.EmployeeDao;
import ro.teamnet.zth.app.domain.Employee;
import ro.teamnet.zth.app.service.EmployeeServiceImpl;

import java.util.List;

/**
 * Created by Adi on 06.05.2015.
 */

@MyController(urlPath = "/employees")
public class EmployeeController {
    @MyRequestMethod(methodType = "GET", urlPath = "/all")
    public List getAllEmployees() {
        EmployeeServiceImpl employeeDao = new EmployeeServiceImpl();
        List allEmployees =  employeeDao.findAllEmployees();
        return allEmployees;

        //String allEmployees = "allEmployees";
        //return allEmployees;
    }
    @MyRequestMethod(methodType = "GET", urlPath = "/one")
    public Object getOneEmployees(@MyParamRequest(paranName = "idEmployee") String idEmployee) {
        //String oneEmployees = "oneEmployees";
        EmployeeServiceImpl employeeDao = new EmployeeServiceImpl();
        Employee employee = employeeDao.findOneEmployee(Integer.parseInt(idEmployee));
        //employee.setId(10);
       // employee.setFirstName("Adi");
        //return oneEmployees;
        return employee;
    }
}
