package com.batch57.gdipsa.group6.lapsbackend.interfaceLayer.holiday;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import com.batch57.gdipsa.group6.lapsbackend.model.holiday.PublicHoliday;

public interface publicHolidayInterface {
    List<PublicHoliday> getAllPublicHolidays();
    PublicHoliday getPublicHolidayById(int id);
    PublicHoliday addPublicHoliday(PublicHoliday holiday);
    void deletePublicHolidayById(int id);
    boolean isWeekend(LocalDate date);
    int countHolidaysAndWeekends(LocalDate startDate, LocalDate endDate);
    boolean isPublicHoliday(LocalDate date);

    /**
     * 新增方法
     */
    void InitPublicHolidayOfYear(int year);
    void DeleteAll();
    PublicHoliday GetHolidayByDate(LocalDate date);
    PublicHoliday AddingPublicHolidayToDatabase(LocalDate date, String name);
    Set<LocalDate> GetPublicHolidaySet();
}