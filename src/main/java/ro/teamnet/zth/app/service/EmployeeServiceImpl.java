package ro.teamnet.zth.app.service;

import ro.teamnet.zth.app.dao.EmployeeDao;
import ro.teamnet.zth.app.domain.Employee;

import java.util.List;

/**
 * Created by Adi on 07.05.2015.
 */
public class EmployeeServiceImpl {
    public List<Employee> findAllEmployees() {
        EmployeeDao employeeDao = new EmployeeDao();
        List<Employee> allEmployees = employeeDao.getAllEmployees();
        return allEmployees;
    }

    public Employee findOneEmployee(Integer id) {
        EmployeeDao employeeDao = new EmployeeDao();
        Employee employee = employeeDao.getEmployeeById(id);
        return employee;
    }
}
