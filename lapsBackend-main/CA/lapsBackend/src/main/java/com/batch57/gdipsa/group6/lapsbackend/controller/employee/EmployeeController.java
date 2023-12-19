package com.batch57.gdipsa.group6.lapsbackend.controller.employee;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.batch57.gdipsa.group6.lapsbackend.model.department.Department;
import com.batch57.gdipsa.group6.lapsbackend.model.user.employee.model.Employee;
import com.batch57.gdipsa.group6.lapsbackend.model.user.userinfo.User;
import com.batch57.gdipsa.group6.lapsbackend.serviceLayer.department.DepartmentInterfaceImplementation;
import com.batch57.gdipsa.group6.lapsbackend.serviceLayer.user.employeeInterfaceImpl;
import com.batch57.gdipsa.group6.lapsbackend.serviceLayer.user.userInterfaceImpl;

@CrossOrigin
@RestController
@RequestMapping("/api/employee")
public class EmployeeController {
    @Autowired
    private employeeInterfaceImpl employeeService;
    
    //LF add
    @Autowired
    private userInterfaceImpl userService;

    @Autowired
    private DepartmentInterfaceImplementation departmentService;

    /**
     * 返回employee列表
     * @return
     */
    //LF done
    @GetMapping("/list")
    public ResponseEntity<List<Employee>> GetAllEmployee() {
        List<Employee> employeeList = employeeService.GetAll();
        if(employeeList == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(employeeList, HttpStatus.OK);
        }
    }
    
    //LF add & done
    @GetMapping("/get/{id}")
    public ResponseEntity<User> GetEmployeeById(@PathVariable("id") Integer id) {
        Employee u = employeeService.GetEmployeeById(id);
        if(u == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(u, HttpStatus.OK);
        }
    }

    //LF add & done. User also deleted
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> DeleteById(@PathVariable("id") Integer id) {
	    try {
	    	employeeService.DeleteEmployeeById(id);
	      return new ResponseEntity<HttpStatus>(HttpStatus.OK);
	    } catch (Exception e) {
	      return new ResponseEntity<HttpStatus>(HttpStatus.EXPECTATION_FAILED);
	    }
	  }
    
    
    
    //LF done. LF change to simpler version. Department id is not tag to Department here.
    @PostMapping("/update/{id}")
    public ResponseEntity<User> modifyEmployeeById(@PathVariable("id") int id, @RequestBody Employee inEmployee) {
        
        Employee employee = employeeService.GetEmployeeById(id);
        
        if (employee != null) {
            if (inEmployee.getName() != null)employee.setName(inEmployee.getName());
            if (inEmployee.getUserType() != null)employee.setUserType(inEmployee.getUserType());
            if (inEmployee.getPassword() != null)employee.setPassword(inEmployee.getPassword());           
            if (inEmployee.getBelongToDepartment() != null) {
                // Assuming Department has the necessary setters for its properties
                Department department = inEmployee.getBelongToDepartment();               
                if (department.getName() != null) {
                    inEmployee.getBelongToDepartment().setName(department.getName());
                }
                if (department.getIncludedBy() != null) {
                    inEmployee.getBelongToDepartment().setIncludedBy(department.getIncludedBy());
                }
                // Set the updated Department to the Employee
                employee.setBelongToDepartment(inEmployee.getBelongToDepartment());
            }           
            if (inEmployee.getCalenderYearMedicalLeave() != null)employee.setCalenderYearMedicalLeave(inEmployee.getCalenderYearMedicalLeave());
            if (inEmployee.getEmployeeType() != null)employee.setEmployeeType(inEmployee.getEmployeeType());
            if (inEmployee.isEntitlementToAnnualLeave() != true)employee.setEntitlementToAnnualLeave(inEmployee.isEntitlementToAnnualLeave());
            if (inEmployee.getOverworkingHour() != null)employee.setOverworkingHour(inEmployee.getOverworkingHour());
            
            // Save the updated user by using CreateUser, same result
            Employee updatedEmployee = employeeService.CreateEmployee(employee); 

            return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
        } else {
            // User not found
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    
    

    /**
     * 创建新employee的同时，添加数据到user数据库中
     */
    /*
    //EmployeeController
    @PostMapping("/create")
    public ResponseEntity<Employee> Create(@RequestBody Employee inEmployee) {
        Employee newEmployee = new Employee(inEmployee.getName(), inEmployee.getPassword(), inEmployee.getUserType());
        newEmployee.setEmployeeType(inEmployee.getEmployeeType());

        employeeService.CreateEmployee(newEmployee);
        return new ResponseEntity<>(newEmployee, HttpStatus.CREATED);
    }
    */
  
    
    //EmployeeController
    @PostMapping("/create/{user_id}")
    public ResponseEntity<Employee> create(@PathVariable("user_id") int user_id, 
    		@RequestBody Employee inEmployee) {
        try {
            // Assuming user_id is unique across both User and Employee
            User currentUser = userService.GetUserById(user_id);

            if (currentUser == null) {
                // Handle the case where the user with the specified user_id is not found
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            
            // Use the user data directly to create the Employee
            Employee newEmployee = new Employee();
    		newEmployee.setUser_id(currentUser.getUser_id());
    		newEmployee.setBelongToDepartment(inEmployee.getBelongToDepartment());
    		newEmployee.setCalenderYearMedicalLeave(inEmployee.getCalenderYearMedicalLeave());
    		newEmployee.setEmployeeType(inEmployee.getEmployeeType());
    		newEmployee.setEntitlementToAnnualLeave(inEmployee.isEntitlementToAnnualLeave());
            newEmployee.setOverworkingHour(inEmployee.getOverworkingHour());

            employeeService.CreateEmployee(newEmployee);

            return new ResponseEntity<>(newEmployee, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }
    
    
    

    /**
     * 修改employee annual leave entitlement, 默认为true
     * @param user_id
     * @param flag
     * @return
     */
    @GetMapping("set-entitlement/{user_id}/{flag}")
    public ResponseEntity<Employee> SetEntitlement(@PathVariable("user_id") int user_id, @PathVariable("flag") String flag) {
        if(flag == "true") {
            employeeService.UpdateEntitlement(user_id, true);
        }else{
            employeeService.UpdateEntitlement(user_id, false);
        }

        Employee employee = employeeService.GetEmployeeById(user_id);

        if(employee == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(employee, HttpStatus.OK);
        }
    }

    /**
     * 为部门添加employee
     * @param department_id
     * @param user_id
     * @return
     */
    @GetMapping("add-employee/{department_id}/{user_id}")
    public ResponseEntity<Employee> SetEmployeeForDepartment(@PathVariable("department_id") int department_id, @PathVariable("user_id") int user_id ) {
        Department department = departmentService.GetDepartmentById(department_id);
        Employee employee = employeeService.GetEmployeeById(user_id);

        employee.setBelongToDepartment(department);

        Employee updated = employeeService.GetEmployeeById(user_id);

        if(updated == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<>(updated, HttpStatus.OK);
        }

    }

    /**
     * 让其他类来调用的静态方法，返回一个user_id的department id
     * @param user_id
     * @return
     */
    public Integer GetEmployeeDepartmentId(int user_id) {
        return employeeService.GetEmployeeById(user_id).getBelongToDepartment().getId();
    }

    @GetMapping("/get-superior/{user_id}")
    public ResponseEntity<?> GetSuperior(@PathVariable("user_id") int user_id) {
        int department_id = GetEmployeeDepartmentId(user_id);
        int department_manager_id = departmentService.GetDepartmentById(department_id).getLedByManager().getUser_id();

        Department cur = departmentService.GetDepartmentById(department_id);

        if(department_manager_id == user_id) {
            // 当前查询的用户所在的部门的领导是这个用户 返回它的上一级领导
            // 当前部门是被哪个更大的部门给include的
            Department includedByDepartment = cur.getIncludedBy();
            if(includedByDepartment == null){
                // 老板没有上级 返回失败
                return new ResponseEntity<>("Boss doesn't have a superior", HttpStatus.NOT_FOUND);
            }
            // 返回更高部门的领导者
            return new ResponseEntity<>(includedByDepartment.getLedByManager(), HttpStatus.OK);
        }else {
            // 不是所在部门的领导，直接返回所在部门的领导
            return new ResponseEntity<>(cur.getLedByManager(), HttpStatus.OK);
        }
    }

    @GetMapping("/get-employees-by-department-id/{department_id}")
    public ResponseEntity<?> GetEmployeesByDepartmentId(@PathVariable("department_id") int department_id) {
        Department department = departmentService.GetDepartmentById(department_id);
        if(department == null) {
            // department not found
            return new ResponseEntity<>("The department you are looking for is not found", HttpStatus.NOT_FOUND);
        }

        List<Employee> employees = departmentService.GetEmployeesByDepartmentId(department_id);
        if(employees.isEmpty()) {
            return new ResponseEntity<>("The department has no employees", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/get-employees-sub-managers-by-department-id/{department_id}")
    public ResponseEntity<?> GetEmployeesAndSubManagerByDepartmentId(@PathVariable("department_id") int department_id) {
        Department department = departmentService.GetDepartmentById(department_id);
        if(department == null) {
            // department not found
            return new ResponseEntity<>("The department you are looking for is not found", HttpStatus.NOT_FOUND);
        }

        List<Employee> employees = departmentService.GetEmployeesAndSubManagerByDepartmentId(department_id);
        if(employees.isEmpty()) {
            return new ResponseEntity<>("The department has no employees", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(employees, HttpStatus.OK);
    }


//    @GetMapping("/get-subordinates/{user_id}")
//    public ResponseEntity<?> GetSubordinates(@PathVariable("user_id")int user_id) {
//        Employee employee = employeeService.GetEmployeeById(user_id);
//        Department department = departmentService.GetDepartmentById(employee.getBelongToDepartment().getId());
//
//        if(department.getLedByManager().getUser_id() != user_id) {
//            // 不是manager
//            return new ResponseEntity<>("Can't find a subordinate.", HttpStatus.NOT_FOUND);
//        }else {
//            // 是manager
//            List<Employee> subordinates = new ArrayList<>();
//
//        }
//    }
}
