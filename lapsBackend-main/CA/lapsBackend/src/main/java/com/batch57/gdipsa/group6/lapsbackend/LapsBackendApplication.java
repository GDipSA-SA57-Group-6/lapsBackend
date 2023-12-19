package com.batch57.gdipsa.group6.lapsbackend;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.batch57.gdipsa.group6.lapsbackend.serviceLayer.application.ApplicationInterfaceImplementation;
import com.batch57.gdipsa.group6.lapsbackend.serviceLayer.holiday.employeeScheduleInterfaceImplementation;
import com.batch57.gdipsa.group6.lapsbackend.serviceLayer.user.employeeInterfaceImpl;

@SpringBootApplication
public class LapsBackendApplication {
    @Autowired
    employeeScheduleInterfaceImplementation employeeScheduleService;
    @Autowired
    employeeInterfaceImpl employeeService;
    @Autowired
    ApplicationInterfaceImplementation applicationService;

    public static void main(String[] args) {
        SpringApplication.run(LapsBackendApplication.class, args);
    }
    
    
    @Bean
    CommandLineRunner loadData(userRepository userRepo,
    							employeeRepository emplRepo,
    							departmentRepository deptRepo
    		
    		
    							) {
        return args -> {
        	User u_emp1 = userRepo.save(new User("Emp1", "emp1pw", USER_TYPE.EMPLOYEE));
        	User u_man1 = userRepo.save(new User("Man1", "man1pw", USER_TYPE.MANAGER));
        	User u_adm1 = userRepo.save(new User("Adm1", "adm1pw", USER_TYPE.ADMIN));
        	User u_adm2 = userRepo.save(new User("Adm2", "adm1pw", USER_TYPE.ADMIN));
        	
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
        	
			
		    Department d1 = new Department();
        	d1.setName("Finance");
        	d1.setLedByManager(e1);
        	deptRepo.save(d1);
        	
        	e1.setBelongToDepartment(d1);
        	emplRepo.save(e1);
        	
        	
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
        	
		    Department d2 = new Department();
        	d2.setName("Finance");
        	d2.setLedByManager(e2);
        	deptRepo.save(d2);
        	
        	e2.setBelongToDepartment(d2);
        	emplRepo.save(e2);
    	
        	       	
        	
        	/*
            HolidayPoint p1 = new HolidayPoint(LocalDate.of(2018,6,12), HOLIDAY_POINT_COMPONENT.MORNING);
            HolidayPoint p2 = new HolidayPoint(LocalDate.of(2018, 6, 18), HOLIDAY_POINT_COMPONENT.MORNING);
            Employee employee = employeeService.GetEmployeeById(1);
            EmployeeSchedule schedule = new EmployeeSchedule(employee, p1, p2);
            employeeScheduleService.CreateSchedule(schedule);
            Application application = applicationService.GetApplicationById(1);
            schedule = employeeScheduleService.GetScheduleById(3);
            application.setSchedule(schedule);
            applicationService.UpdateApplication(application);

            // 尝试删除一个记录
            schedule = application.getSchedule();
            application.setSchedule(null); // 先解开application对它的外键依赖
            applicationService.UpdateApplication(application); // 更新application在数据库里的内容

            employeeScheduleService.DeleteSchedule(schedule);
      		*/
        };
    }


}       


/*
@SpringBootTest
public class PublicHolidayTests {

    @Autowired
    private holidayInterfaceImpl holidayService;

    @Test
    public void testCreateAndReadPublicHoliday() {
        // 创建一个新的 PublicHoliday 对象
        PublicHoliday newHoliday = new PublicHoliday(LocalDate.of(2023, 1, 1), "New Year's Day", true);
        PublicHoliday savedHoliday = holidayService.addPublicHoliday(newHoliday);
        assertNotNull(savedHoliday);

        // 读取 PublicHoliday 对象
        PublicHoliday foundHoliday = holidayService.getPublicHolidayById(savedHoliday.getId());
        assertNotNull(foundHoliday);
        assertEquals("New Year's Day", foundHoliday.getName());
    }

    @Test
    public void testUpdatePublicHoliday() {
        // 更新 PublicHoliday 对象
        PublicHoliday holiday = holidayService.getPublicHolidayById(1); // 假设这个 ID 存在
        holiday.setName("Updated Name");
        PublicHoliday updatedHoliday = holidayService.addPublicHoliday(holiday);

        assertEquals("Updated Name", updatedHoliday.getName());
    }

    @Test
    public void testDeletePublicHoliday() {
        // 删除 PublicHoliday 对象
        int id = 2; // 假设这个 ID 存在
        holidayService.deletePublicHoliday(id);
        assertNull(holidayService.getPublicHolidayById(id));
    }
}*/

