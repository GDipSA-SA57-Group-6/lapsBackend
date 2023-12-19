package com.batch57.gdipsa.group6.lapsbackend.serviceLayer.holiday;


import com.batch57.gdipsa.group6.lapsbackend.interfaceLayer.holiday.publicHolidayInterface;
import com.batch57.gdipsa.group6.lapsbackend.model.holiday.PublicHoliday;
import com.batch57.gdipsa.group6.lapsbackend.repository.holiday.HolidayRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class holidayInterfaceImpl implements publicHolidayInterface {

    @Autowired
    private HolidayRepository holidayRepository;

    // Other methods like getAllHolidays, addHoliday, etc.
    @Override
    public List<PublicHoliday> getAllPublicHolidays() {
        return holidayRepository.findAll();
    }

    @Override
    public PublicHoliday getPublicHolidayById(int id) {
        return holidayRepository.findById(id).orElse(null);
    }

    @Override
    public PublicHoliday addPublicHoliday(PublicHoliday holiday) {
        return holidayRepository.save(holiday);
    }

    @Override
    public void deletePublicHoliday(int id) {
        holidayRepository.deleteById(id);
    }

    @Override
    public boolean isPublicHoliday(LocalDate date) {
        return holidayRepository.existsByDate(date);
    }

    @Override
    public boolean isWeekend(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }
    
    @Override
	public int countHolidaysAndWeekends(LocalDate start, LocalDate end) {
        int count = 0;
        LocalDate date = start;
        while (!date.isAfter(end)) {
            if (isWeekend(date) || isPublicHoliday(date)) {
                count++;
            }
            date = date.plusDays(1);
        }
        return count;
    }

}
