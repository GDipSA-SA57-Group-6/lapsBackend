package com.batch57.gdipsa.group6.lapsbackend;

import com.batch57.gdipsa.group6.lapsbackend.model.application.Application;
import com.batch57.gdipsa.group6.lapsbackend.model.enumLayer.HOLIDAY_POINT_COMPONENT;
import com.batch57.gdipsa.group6.lapsbackend.model.holiday.EmployeeSchedule;
import com.batch57.gdipsa.group6.lapsbackend.model.holiday.HolidayPoint;
import com.batch57.gdipsa.group6.lapsbackend.model.holiday.PublicHoliday;
import com.batch57.gdipsa.group6.lapsbackend.model.user.employee.model.Employee;
import com.batch57.gdipsa.group6.lapsbackend.repository.user.userRepository;
import com.batch57.gdipsa.group6.lapsbackend.serviceLayer.application.ApplicationInterfaceImplementation;
import com.batch57.gdipsa.group6.lapsbackend.serviceLayer.holiday.employeeScheduleInterfaceImplementation;
import com.batch57.gdipsa.group6.lapsbackend.serviceLayer.holiday.holidayInterfaceImpl;
import com.batch57.gdipsa.group6.lapsbackend.serviceLayer.user.employeeInterfaceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

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
    /*
    @Bean
    CommandLineRunner loadData(userRepository userRepo) {
        return args -> {
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
       

        };
    }
*/
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

