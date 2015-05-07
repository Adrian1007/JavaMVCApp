package ro.teamnet.zth.app.service;

import ro.teamnet.zth.app.domain.Employee;

import java.util.List;

/**
 * Created by Adi on 07.05.2015.
 */
public interface EmployeeService {
     List findAllEmployees();
     Employee findOneEmployee();
}
