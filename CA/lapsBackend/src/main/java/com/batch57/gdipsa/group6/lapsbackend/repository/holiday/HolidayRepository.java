package com.batch57.gdipsa.group6.lapsbackend.repository.holiday;

import com.batch57.gdipsa.group6.lapsbackend.model.holiday.PublicHoliday;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HolidayRepository extends JpaRepository<PublicHoliday, Integer> {
    @Query("select h from PublicHoliday h where h.isWeekend = false and h.date >= current_date")
    List<PublicHoliday> findUpcomingWorkingDayHolidays();

	boolean existsByDate(LocalDate date);
}
