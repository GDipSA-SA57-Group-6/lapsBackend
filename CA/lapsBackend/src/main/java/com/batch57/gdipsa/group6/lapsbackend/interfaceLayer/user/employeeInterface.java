package com.batch57.gdipsa.group6.lapsbackend.interfaceLayer.user;

import com.batch57.gdipsa.group6.lapsbackend.model.application.Application;
import com.batch57.gdipsa.group6.lapsbackend.model.enumLayer.APPLICATION_STATUS;
import com.batch57.gdipsa.group6.lapsbackend.model.holiday.HolidayPoint;
import com.batch57.gdipsa.group6.lapsbackend.model.holiday.PublicHoliday;
import com.batch57.gdipsa.group6.lapsbackend.model.user.employee.model.Employee;
import org.springframework.web.bind.annotation.RequestHeader;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public interface employeeInterface {
    void CreateEmployee(Employee employee);
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
    Application UpdateApplicationStatus(int application_id, APPLICATION_STATUS newStatus);
    Set<LocalDate> GetEmployeeHolidaySet(int user_id);
    Boolean IsDateApplicableToEmployee(int user_id, LocalDate date);
    HolidayPoint GetEndHolidayPointBasedOnApplication(Application application);
    void DeleteEmployeeById(int user_id);

}
