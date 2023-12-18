package com.batch57.gdipsa.group6.lapsbackend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.batch57.gdipsa.group6.lapsbackend.model.department.Department;
import com.batch57.gdipsa.group6.lapsbackend.model.enumLayer.EMPLOYEE_TYPE;
import com.batch57.gdipsa.group6.lapsbackend.model.enumLayer.USER_TYPE;
import com.batch57.gdipsa.group6.lapsbackend.model.user.employee.model.Employee;
import com.batch57.gdipsa.group6.lapsbackend.model.user.userinfo.User;
import com.batch57.gdipsa.group6.lapsbackend.repository.department.departmentRepository;
import com.batch57.gdipsa.group6.lapsbackend.repository.user.employeeRepository;
import com.batch57.gdipsa.group6.lapsbackend.repository.user.userRepository;

@SpringBootApplication
public class LapsBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(LapsBackendApplication.class, args);
    }

    @Bean
    CommandLineRunner loadData(userRepository userRepo,
    							employeeRepository emplRepo,
    							departmentRepository deptRepo
    		
    		
    							) {
        return args -> {
        	User u1_emp = userRepo.save(new User("Emp1", "emp1pw", USER_TYPE.EMPLOYEE));
        	User u2_man = userRepo.save(new User("Man1", "man1pw", USER_TYPE.MANAGER));
        	User u3_adm = userRepo.save(new User("Adm1", "adm1pw", USER_TYPE.ADMIN));
        	/*
        	User u1 = new User("John", "johnpw", USER_TYPE.ADMIN);
        	Employee e1 = new Employee();
        	e1.setEntitlementToAnnualLeave(true);
        	e1.setCalenderYearMedicalLeave(60);
        	e1.setOverworkingHour(40);
        	e1.setEmployeeType(EMPLOYEE_TYPE.PROFESSIONAL);
        	e1.setName(u1.getName());
        	e1.setPassword(u1.getPassword());
        	e1.setUserType(u1.getUserType());
        	emplRepo.save(e1);
        	
			/*
		    Department d1 = new Department();
        	d1.setName("Finance");
        	d1.setLedByManager(e1);
        	deptRepo.save(d1);
        	
        	e1.setBelongToDepartment(d1);
        	emplRepo.save(e1);
        	*/
        	/*
        	User u2 = new User("Jenny", "jennypw", USER_TYPE.EMPLOYEE);
        	Employee e2 = new Employee();
        	e2.setEntitlementToAnnualLeave(true);
        	e2.setCalenderYearMedicalLeave(10);
        	e2.setOverworkingHour(0);
        	e2.setEmployeeType(EMPLOYEE_TYPE.ADMINISTRATIVE);
        	e2.setName(u2.getName());
        	e2.setPassword(u2.getPassword());
        	e2.setUserType(u2.getUserType());
        	emplRepo.save(e2);
        	
        	/*
		    Department d2 = new Department();
        	d2.setName("Finance");
        	d2.setLedByManager(e2);
        	deptRepo.save(d2);
        	
        	e2.setBelongToDepartment(d2);
        	emplRepo.save(e2);
        	*/
        };
    }


}
