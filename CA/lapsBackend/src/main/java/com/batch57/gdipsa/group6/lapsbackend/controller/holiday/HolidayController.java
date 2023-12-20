package com.batch57.gdipsa.group6.lapsbackend.controller.holiday;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.batch57.gdipsa.group6.lapsbackend.model.holiday.PublicHoliday;
import com.batch57.gdipsa.group6.lapsbackend.serviceLayer.holiday.publicHolidayInterfaceImplementation;
//... 其他导入 ...

@RestController
@RequestMapping("/api/public-holidays")
public class HolidayController {
	
	 @Autowired
	 private publicHolidayInterfaceImplementation holidayService;
	
	 // GET 所有公共假期
	 @GetMapping
	 public ResponseEntity<List<PublicHoliday>> getAllPublicHolidays() {
	     List<PublicHoliday> holidays = holidayService.getAllPublicHolidays();
	     return ResponseEntity.ok(holidays);
	 }
	
	 // GET 单个公共假期通过 ID
	 @GetMapping("/{id}")
	 public ResponseEntity<PublicHoliday> getPublicHolidayById(@PathVariable int id) {
	     PublicHoliday holiday = holidayService.getPublicHolidayById(id);
	     return ResponseEntity.ok(holiday);
	 }
	
	 // POST 新增公共假期
	 @PostMapping
	 public ResponseEntity<PublicHoliday> createPublicHoliday(@RequestBody PublicHoliday holiday) {
	     PublicHoliday createdHoliday = holidayService.addPublicHoliday(holiday);
	     return ResponseEntity.ok(createdHoliday);
	 }
	
//	 // PUT 更新现有公共假期
//	 @PutMapping("/{id}")
//	 public ResponseEntity<PublicHoliday> updatePublicHoliday(@PathVariable int id, @RequestBody PublicHoliday holidayDetails) {
//	     PublicHoliday existingHoliday = holidayService.getPublicHolidayById(id);
//	     if (existingHoliday != null) {
//	         existingHoliday.setDate(holidayDetails.getDate());
//	         existingHoliday.setName(holidayDetails.getName());
//	         existingHoliday = holidayService.addPublicHoliday(existingHoliday); // 确保更新后的对象被返回
//	     } else {
//	         return ResponseEntity.notFound().build(); // 如果找不到假期，则返回404 Not Found
//	     }
//	     return ResponseEntity.ok(existingHoliday);
//	 }
	
	
	 // DELETE 删除公共假期
	 @DeleteMapping("/{id}")
	 public ResponseEntity<Void> deletePublicHoliday(@PathVariable int id) {
	     holidayService.deletePublicHolidayById(id);
	     return ResponseEntity.ok().build();
	 }


	/**
	 * 新增模块
	 */
	@GetMapping("/initialize/{year}")
	public ResponseEntity<?> InitializePublicHoliday(@PathVariable("year") int year) {
		holidayService.InitPublicHolidayOfYear(year);
		return new ResponseEntity<>("Successfully initialized", HttpStatus.OK);
	}
}
