package com.batch57.gdipsa.group6.lapsbackend.model.holiday;



import java.time.LocalDate;
import java.time.DayOfWeek;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;

@Entity
@Table(name = "PublicHoliday", uniqueConstraints = @UniqueConstraint(columnNames = "date"))
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
        this.isWeekend = false;
    }

    public PublicHoliday(LocalDate date, String name) {
        this.date = date;
        this.name = name;
        this.isWeekend = false;
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

    // toString method for debugging
    @Override
    public String toString() {
        return "Holiday{" +
                "id=" + id +
                ", date=" + date +
                ", name='" + name +
                '}';
    }

    public Boolean getWeekend() {
        return isWeekend;
    }

    public void setWeekend(Boolean weekend) {
        isWeekend = weekend;
    }
}
