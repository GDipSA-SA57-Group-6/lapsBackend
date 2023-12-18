package com.batch57.gdipsa.group6.lapsbackend.interfaceLayer.holiday;

import java.time.LocalDate;
import java.util.List;

import com.batch57.gdipsa.group6.lapsbackend.model.holiday.PublicHoliday;

public interface publicHolidayInterface {
    List<PublicHoliday> getAllPublicHolidays();
    PublicHoliday getPublicHolidayById(int id);
    PublicHoliday addPublicHoliday(PublicHoliday holiday);
    void deletePublicHoliday(int id);
    boolean isPublicHoliday(LocalDate date);
    boolean isWeekend(LocalDate date);
    int countHolidaysAndWeekends(LocalDate startDate, LocalDate endDate);
}