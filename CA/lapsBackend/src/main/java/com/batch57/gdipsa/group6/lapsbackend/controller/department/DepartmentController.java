package com.batch57.gdipsa.group6.lapsbackend.controller.department;

import com.batch57.gdipsa.group6.lapsbackend.interfaceLayer.department.departmentInterface;
import com.batch57.gdipsa.group6.lapsbackend.model.department.Department;
import com.batch57.gdipsa.group6.lapsbackend.model.user.employee.model.Employee;
import com.batch57.gdipsa.group6.lapsbackend.model.user.userinfo.User;
import com.batch57.gdipsa.group6.lapsbackend.repository.department.departmentRepository;
import com.batch57.gdipsa.group6.lapsbackend.serviceLayer.department.DepartmentInterfaceImplementation;
import com.batch57.gdipsa.group6.lapsbackend.serviceLayer.user.employeeInterfaceImpl;
import org.apache.juli.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@CrossOrigin
@RestController
@RequestMapping("/api/department")
public class DepartmentController {
    @Autowired
    private departmentRepository repo;

    @Autowired
    private departmentInterface departmentService;

    @Autowired
    public void setDepartmentService(DepartmentInterfaceImplementation impl) {
        this.departmentService = impl;
    }

    @Autowired
    private employeeInterfaceImpl employeeService;

    @GetMapping("/get/{id}")
    public ResponseEntity<Department> GetDepartmentById(@PathVariable("id") Integer id) {
        Department department = departmentService.GetDepartmentById(id);
        if(department == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(department, HttpStatus.OK);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<Department>> GetAllDepartment() {
        List<Department> departments = departmentService.GetAll();
        if(departments == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(departments, HttpStatus.OK);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Department> CreateDepartment(@RequestBody Department inDepartment) {
        Department department = new Department(inDepartment.getName());
        Department saved = repo.save(department);
        if(saved == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(saved, HttpStatus.OK);
        }
    }

    // localhost:8080/api/department/set-manager-by-id/6/16
    @GetMapping("/set-manager-by-id/{department_id}/{user_id}")
    public ResponseEntity<Department> SetManagerById(@PathVariable("department_id") int department_id, @PathVariable("user_id") int user_id) {
        Department department = departmentService.GetDepartmentById(department_id);
        Employee employee = employeeService.GetEmployeeById(user_id);
        department.setLedByManager(employee);

        Department updated = repo.save(department);
        if(updated == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }
    }

    @GetMapping("/get-department-manger-by-id/{department_id}")
    public ResponseEntity<?> GetDepartmentMangerByDepartmentId(@PathVariable("department_id") int department_id) {
        Employee manager = repo.GetDepartmentMangerByDepartmentId(department_id);

        if(manager == null) {
            return new ResponseEntity<>(manager, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(manager, HttpStatus.OK);
        }
    }


    @GetMapping("/add-sub-department/{super_department_id}/{sub_department_id}")
    public ResponseEntity<?> AddSubDepartment(@PathVariable("super_department_id") int super_department_id, @PathVariable("sub_department_id") int sub_department_id) {
        Department updated = departmentService.AddSubDepartment(super_department_id, sub_department_id);

        if(updated == null) {
            return new ResponseEntity<>("Adding sub department failed for some reasons, please do it again.", HttpStatus.EXPECTATION_FAILED);
        }else {
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }
    }


    @GetMapping("get-interior-employee-by-department-id/{department_id}")
    public ResponseEntity<?> GetInteriorEmployeeByDepartmentId(@PathVariable("department_id") int department_id) {
        return new ResponseEntity<>(departmentService.GetInteriorEmployeeByDepartmentId(department_id), HttpStatus.OK);
    }


    @GetMapping("get-interior-department-by-department-id/{department_id}")
    public ResponseEntity<?> GetInteriorDepartmentByDepartmentId(@PathVariable("department_id") int department_id) {
        return new ResponseEntity<>(departmentService.GetInteriorDepartmentByDepartmentId(department_id), HttpStatus.OK);
    }

}
