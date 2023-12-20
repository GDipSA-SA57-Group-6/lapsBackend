package com.batch57.gdipsa.group6.lapsbackend.serviceLayer.holiday;


import com.batch57.gdipsa.group6.lapsbackend.interfaceLayer.holiday.publicHolidayInterface;
import com.batch57.gdipsa.group6.lapsbackend.model.holiday.PublicHoliday;
import com.batch57.gdipsa.group6.lapsbackend.repository.holiday.publicHolidayRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class publicHolidayInterfaceImplementation implements publicHolidayInterface {

    @Autowired
    private publicHolidayRepository publicHolidayRepo;

    // Other methods like getAllHolidays, addHoliday, etc.
    @Override
    public List<PublicHoliday> getAllPublicHolidays() {
        return publicHolidayRepo.findAll();
    }

    @Override
    public PublicHoliday getPublicHolidayById(int id) {
        return publicHolidayRepo.findById(id).orElse(null);
    }

    @Override
    public PublicHoliday addPublicHoliday(PublicHoliday holiday) {
        return publicHolidayRepo.save(holiday);
    }

    @Override
    public void deletePublicHolidayById(int id) {
        publicHolidayRepo.deleteById(id);
    }

    @Override
    public boolean isPublicHoliday(LocalDate date) {
        return publicHolidayRepo.existsByDate(date);
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


    /**
     * 重构后的新增方法
     */


    /**
     * 初始化某年的所有公共假期，保存在数据库里，默认一个calender year为1/1 -> 12/31
     * @param year
     */
    @Override
    public void InitPublicHolidayOfYear(int year) {
        DeleteAll();

        LocalDate yearStart = LocalDate.of(year,1,1);
        LocalDate endStart = LocalDate.of(year,12,31);

        // 把今天设置为开头
        LocalDate curDate = yearStart;
        // 遍历一整个calender year
        while(!curDate.isAfter(endStart)) {
            // 如果是周末
            if(curDate.getDayOfWeek() == DayOfWeek.SATURDAY || curDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
                PublicHoliday p = new PublicHoliday(curDate, "weekend");
                p.setWeekend(true);

                publicHolidayRepo.save(p); // 把这个日期保存到数据库里
            }

            curDate =  curDate.plusDays(1); // +1
        }

        /**
         * 插入一些常规的假期
         */
        AddingPublicHolidayToDatabase(LocalDate.of(year,1,1),"New Year's Day");
        AddingPublicHolidayToDatabase(LocalDate.of(year,4,4),"Qing Ming Festival");
        AddingPublicHolidayToDatabase(LocalDate.of(year,5,1),"Labour Day");
        AddingPublicHolidayToDatabase(LocalDate.of(year,5,8),"Vesak Day");
        AddingPublicHolidayToDatabase(LocalDate.of(year,4,23),"Hari Raya Puasa");
        AddingPublicHolidayToDatabase(LocalDate.of(year,4,24),"Hari Raya Puasa");
        AddingPublicHolidayToDatabase(LocalDate.of(year,8,9),"National Day");
        AddingPublicHolidayToDatabase(LocalDate.of(year,9,30),"Mid-Autumn Festival");
        AddingPublicHolidayToDatabase(LocalDate.of(year,10,23),"Deepvali");
        AddingPublicHolidayToDatabase(LocalDate.of(year,12,25),"Christmas Day");
    }

    /**
     * 删除所有
     */
    @Override
    public void DeleteAll() {
        publicHolidayRepo.deleteAll();
    }

    @Override
    public PublicHoliday GetHolidayByDate(LocalDate date) {
        List<PublicHoliday> publicHolidays = getAllPublicHolidays();

        Optional<PublicHoliday> holiday = publicHolidays
                .stream()
                .filter(h -> h.getDate().equals(date))
                .findFirst();

        return  holiday.orElse(null);
    }

    @Override
    public PublicHoliday AddingPublicHolidayToDatabase(LocalDate date, String name) {
        PublicHoliday holiday;
        holiday = GetHolidayByDate(date);

        if(holiday == null){
            // 没有找到数据库里的
            holiday = new PublicHoliday(date, name);
        }else{
            // 数据库里有记录了
            holiday.setName(holiday.getName() + " + " + name);
        }

        return publicHolidayRepo.save(holiday);
    }

    @Override
    public Set<LocalDate> GetPublicHolidaySet() {
        List<PublicHoliday> publicHolidays = getAllPublicHolidays();
        Set<LocalDate> publicHolidaySet = new HashSet<>();

        publicHolidays
                .forEach(p -> {
                    publicHolidaySet.add(p.getDate());
                });

        return publicHolidaySet;
    }
}
