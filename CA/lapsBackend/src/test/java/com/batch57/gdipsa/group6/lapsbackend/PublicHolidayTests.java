package com.batch57.gdipsa.group6.lapsbackend;

import com.batch57.gdipsa.group6.lapsbackend.model.holiday.PublicHoliday;
import com.batch57.gdipsa.group6.lapsbackend.serviceLayer.holiday.holidayInterfaceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

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
}
