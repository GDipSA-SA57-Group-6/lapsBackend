package com.batch57.gdipsa.group6.lapsbackend.interfaceLayer.user;

import com.batch57.gdipsa.group6.lapsbackend.model.user.employee.model.Employee;

import java.util.List;

public interface employeeInterface {
    Employee CreateEmployee(Employee employee);
    Employee GetEmployeeById(int id);
    Employee UpdateEntitlement(int user_id, boolean entitled);
    List<Employee> GetAll();
    Integer GetEmployeeMedicalLeave(int user_id);
    Integer GetOverworkingHourById(int user_id);
    Employee IncrementOverworkingHour(int user_id, int increment_hour);
    Integer isManager(int user_id);
    Employee GetSuperior(int user_id);
    Integer GetEmployeeDepartmentId(int user_id);
    Employee UpdateEmployee(Employee employee);
	void DeleteEmployeeById(int id);
}
