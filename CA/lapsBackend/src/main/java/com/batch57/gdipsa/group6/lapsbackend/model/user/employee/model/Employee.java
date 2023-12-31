package com.batch57.gdipsa.group6.lapsbackend.model.user.employee.model;

import com.batch57.gdipsa.group6.lapsbackend.model.application.Application;
import com.batch57.gdipsa.group6.lapsbackend.model.department.Department;
import com.batch57.gdipsa.group6.lapsbackend.model.enumLayer.EMPLOYEE_TYPE;
import com.batch57.gdipsa.group6.lapsbackend.model.enumLayer.USER_TYPE;
import com.batch57.gdipsa.group6.lapsbackend.model.holiday.EmployeeSchedule;
import com.batch57.gdipsa.group6.lapsbackend.model.user.userinfo.User;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Set;

@Entity
public class Employee extends User {
    // 默认为true
    private boolean entitlementToAnnualLeave;

    @JsonProperty("belongToDepartment") // 对于这种reference外table的property，默认是不会打印的
    @ManyToOne()
    private Department belongToDepartment;

    @JsonIgnore
    @OneToMany(mappedBy = "ledByManager", cascade = CascadeType.ALL)
    private Set<Department> leadDepartments;

    @JsonIgnore
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private Set<Application> applications;

    @NotNull
    EMPLOYEE_TYPE employeeType;

    @JsonProperty("medical_leave_day")
    Integer calenderYearMedicalLeave; // 记录medical leave时常
    @JsonProperty("over_working_hour")
    Integer overworkingHour;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    List<EmployeeSchedule> schedules;

    @JsonProperty("email")
    @Email
    private String email;

    public void setBelongToDepartment(Department belongToDepartment) {
        this.belongToDepartment = belongToDepartment;
    }

    public Employee() {
        this.entitlementToAnnualLeave = true;
        calenderYearMedicalLeave = 0;
        overworkingHour = 0;
    }

    public Employee(String name, String password, USER_TYPE userType) {
        super(name, password, userType);
        this.entitlementToAnnualLeave = true;
        calenderYearMedicalLeave = 0;
        overworkingHour = 0;
    }

    public boolean isEntitlementToAnnualLeave() {
        return entitlementToAnnualLeave;
    }

    public void setEntitlementToAnnualLeave(boolean entitlementToAnnualLeave) {
        this.entitlementToAnnualLeave = entitlementToAnnualLeave;
    }

    public EMPLOYEE_TYPE getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(EMPLOYEE_TYPE employeeType) {
        this.employeeType = employeeType;
    }


    public Department getBelongToDepartment() {
        return belongToDepartment;
    }

    public Set<Department> getLeadDepartments() {
        return leadDepartments;
    }

    public Set<Application> getApplications() {
        return applications;
    }

    public Integer getCalenderYearMedicalLeave() {
        return calenderYearMedicalLeave;
    }

    public Integer getOverworkingHour() {
        return overworkingHour;
    }

    public void setOverworkingHour(Integer overworkingHour) {
        this.overworkingHour = overworkingHour;
    }

    public void setCalenderYearMedicalLeave(Integer calenderYearMedicalLeave) {
        this.calenderYearMedicalLeave = calenderYearMedicalLeave;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
