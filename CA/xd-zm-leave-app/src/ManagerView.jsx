import React, { useState, useEffect } from 'react';
import axios from 'axios';
 
const ManagerView = () => {
    const [subordinates, setSubordinates] = useState([]);
    const [selectedEmployee, setSelectedEmployee] = useState(null);
    const [applications, setApplications] = useState([]);
 
    let currentloginuserID = 1;
 
    useEffect(() => {
        axios.get('http://localhost:8080/api/employee/get-subordinates-by-id/' + currentloginuserID)
            .then(response => {
                setSubordinates(response.data);
                console.log(JSON.stringify(response.data));
            })
            .catch(error => console.error('Error fetching subordinates', error));
    }, []);


    
 
 
    const viewApplicationHistory = (employeeId) => {
        setSelectedEmployee(employeeId);
        axios.get('http://localhost:8080/api/application/get-application-by-employee-id/' + employeeId)
            .then(response => {
                setApplications(response.data);
                console.log(JSON.stringify(response.data));
            })
            .catch(error => console.error('Error fetching applications', error));
    };
 
    return (
        <div>
            <h2>Subordinates</h2>
            <ul>
                {subordinates.map(employee => (
                    <li key={employee.user_id}>
                        {employee.name}
                        <button onClick={() => viewApplicationHistory(employee.user_id)}>
                            View Application History
                        </button>
                    </li>
                ))}
            </ul>
 
            {selectedEmployee && (
                <div>
                    <h3>Application History for Employee ID: {selectedEmployee}</h3>
                    <ul>
                        {applications.map(application => (
                            <li key={application.id}>
                                Type: {application.employeeLeaveType} - Status: {application.applicationStatus} - from : {application.fromDate} - duration : {application.dayOff} - estimated to : {application.estimatedToDate} - compensation start: {application.compensationStartPoint}
                            </li>
                        ))}
                    </ul>
                </div>
            )}
        </div>
    );
};
 
export default ManagerView;