package com.batch57.gdipsa.group6.lapsbackend.model.application;

import com.batch57.gdipsa.group6.lapsbackend.model.enumLayer.APPLICATION_STATUS;
import com.batch57.gdipsa.group6.lapsbackend.model.enumLayer.COMPENSATION_START_POINT;
import com.batch57.gdipsa.group6.lapsbackend.model.holiday.EmployeeSchedule;
import com.batch57.gdipsa.group6.lapsbackend.model.user.employee.model.Employee;
import com.batch57.gdipsa.group6.lapsbackend.model.enumLayer.EMPLOYEE_LEAVE_TYPE;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

/**
 * Application 只专注于最简单的原子形式
 * 考虑用其他的wrapped class 包裹原子形式，而不是设立很多的application class
 *
 * “from”和“to”是保留关键字，所以不能直接作为列名使用。当 Hibernate 尝试创建一个包含这些关键字作为列名的表时，就会导致 SQL 语法错误
 *
 *  * 如果遇到日期时间格式的问题或者需要自定义日期时间的格式，
 *  * 可以在Application类中对LocalDateTime字段使用@JsonFormat注解来指定格式
 *  * @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
 *  * LocalDateTime fromDate;
 *
 */
@Entity
public class Application {
    @JsonProperty("application_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer application_id;

    // 这里一定不会空，因为如果查不到用户，直接返回失败
    @JsonProperty("employee_info")
    @ManyToOne
    private Employee employee; // 由谁发起的

    @NotNull
    LocalDate fromDate;// 开始日期，保证传入的都是合法的

    /**
     * 这里这个结束日期，只是给领导一个参考，并不少员工拿到的真正时间，拿到的真正时间还包括结束时间是上午还是下午，我这样设计是为之后优化做准备，比如领导可以查看今天有没有员工在岗
     */
    LocalDate estimatedToDate; // 计算得出的结束日期

    @NotNull
    Integer dayOff;

    @NotNull
    EMPLOYEE_LEAVE_TYPE employeeLeaveType; // 什么类新的假期

    // 这里一定不会空，因为初始状态为applied
    APPLICATION_STATUS applicationStatus;

    // 以下字段是跟compensation leave 相关的
    COMPENSATION_START_POINT compensationStartPoint;

    // 以下字段是这个申请通过了以后具体的放假安排
    @OneToOne
    EmployeeSchedule schedule;

    // 申请原因
    private String applyingReason;

    // 审批comment
    private String reviewedComment;

    // 其他额外字段
    private String workDissemination;
    private String contactDetails;

    public Application() {
    }

    public Application(Employee employee, LocalDate fromDate, Integer dayOff, EMPLOYEE_LEAVE_TYPE employeeLeaveType) {
        this.employee = employee;
        this.fromDate = fromDate;
        this.dayOff = dayOff;
        this.employeeLeaveType = employeeLeaveType;

        // 默认开始为applied
        this.applicationStatus = APPLICATION_STATUS.APPLIED;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public EMPLOYEE_LEAVE_TYPE getEmployeeLeaveType() {
        return employeeLeaveType;
    }

    public void setEmployeeLeaveType(EMPLOYEE_LEAVE_TYPE employeeLeaveType) {
        this.employeeLeaveType = employeeLeaveType;
    }

    public APPLICATION_STATUS getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(APPLICATION_STATUS applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public COMPENSATION_START_POINT getCompensationStartPoint() {
        return compensationStartPoint;
    }

    public void setCompensationStartPoint(COMPENSATION_START_POINT compensationStartPoint) {
        this.compensationStartPoint = compensationStartPoint;
    }

    public Integer getDayOff() {
        return dayOff;
    }

    public void setDayOff(Integer dayOff) {
        this.dayOff = dayOff;
    }

    public EmployeeSchedule getSchedule() {
        return schedule;
    }

    public void setSchedule(EmployeeSchedule schedule) {
        this.schedule = schedule;
    }

    public Integer getApplication_id() {
        return application_id;
    }

    public void setApplication_id(Integer application_id) {
        this.application_id = application_id;
    }

    public LocalDate getEstimatedToDate() {
        return estimatedToDate;
    }

    public void setEstimatedToDate(LocalDate estimatedToDate) {
        this.estimatedToDate = estimatedToDate;
    }

    public String getApplyingReason() {
        return applyingReason;
    }

    public void setApplyingReason(String applyingReason) {
        this.applyingReason = applyingReason;
    }

    public String getReviewedComment() {
        return reviewedComment;
    }

    public void setReviewedComment(String reviewedComment) {
        this.reviewedComment = reviewedComment;
    }
}
