package com.batch57.gdipsa.group6.lapsbackend.model.holiday;



import java.time.LocalDate;
import java.time.DayOfWeek;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;

@Entity
@Table(name = "PublicHoliday")
@Inheritance(strategy = InheritanceType.JOINED) // 使用 JOINED 继承策略
public class PublicHoliday {
	@JsonProperty("holiday_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private Boolean isWeekend;

    // Constructors
    public PublicHoliday() {
        // This is needed for Hibernate
    }

    public PublicHoliday(LocalDate date, String name, Boolean isWeekend) {
        this.date = date;
        this.name = name;
        this.isWeekend = isWeekend;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsWeekend() {
    	DayOfWeek day = date.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }

    // toString method for debugging
    @Override
    public String toString() {
        return "Holiday{" +
                "id=" + id +
                ", date=" + date +
                ", name='" + name + '\'' +
                ", isWeekend=" + isWeekend +
                '}';
    }
}
