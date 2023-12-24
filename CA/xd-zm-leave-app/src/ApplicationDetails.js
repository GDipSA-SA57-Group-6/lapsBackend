import Application from "./Application"
//import { React, useState } from "react";
import { useEffect, useState } from "react";
import axios from "axios";
export default function ApplicationDetails() {

    const windowUrl = window.location.search;
    const params = new URLSearchParams(windowUrl);
    let application_id = params.get('id');
    //console.log('application_id: ', application_id);

    let currentloginuserID = 1;


    const [commentValue, setCommentValue] = useState("");
    const [showApproveRejectButton, setShowApproveRejectButton] = useState(false);

    //use the application_id in url,
    //use it as a paramter to call api to get application detail by application_id  
    //http://localhost:8080/api/application/get/{application_id} 
    //let pendingApplication = pendingApplicationList.find(x =>
    //    x.application_id == application_id);
    const [pendingApplication, updatePendingApplication] = useState()

    //console.log('http://localhost:8080/api/application/get/' + application_id);

    useEffect(() => {
        let config = {
            method: 'get',
            maxBodyLength: Infinity,
            url: 'http://localhost:8080/api/application/get/' + application_id,
            headers: {}
        };

        axios.request(config)
            .then((response) => {
                console.log(JSON.stringify(response.data));
                console.log(response.data);
                updatePendingApplication(response.data);
            })
            .catch((error) => {
                console.log(error);
            });
    }, []);


    //call api to get all approved/rejected applications by current login user's id
    //http://localhost:8080/api/application/get-subordinates-alive-application
    //in http request header, add user_id = {current login user's id}
    //

    const [pendingApplicationList, updatePendingApplicationList] = useState([])

    useEffect(() => {
        let config = {
            method: 'get',
            maxBodyLength: Infinity,
            url: 'http://localhost:8080/api/application/get-subordinates-alive-application',
            headers: {
                'user_id': currentloginuserID
            }
        };

        axios.request(config)
            .then((response) => {
                updatePendingApplicationList(response.data);
                console.log(JSON.stringify(response.data));

            })
            .catch((error) => {
                console.log(error);
            });
    }, []);


    useEffect(() =>{
        console.log('pa: ', pendingApplication);

    if (pendingApplication && pendingApplication.applicationStatus && 
        pendingApplication.applicationStatus.toUpperCase() == 'APPLIED')
        {
        //showApproveRejectButton = true;
        setShowApproveRejectButton(true);
        setCommentValue(pendingApplication.reviewedComment);
        }
    }, [pendingApplication]);    

    let subordinatesApplicationsInSamePeriodHtml = [];
    if (pendingApplicationList) {
        let subordinatesApplicationsInSamePeriod = pendingApplicationList.filter(x =>
            (x.applicationStatus.toUpperCase() == "APPROVED" ||
                x.applicationStatus.toUpperCase() == "REJECTED") &&
            x.application_id != pendingApplication.application_id &&
            new Date(x.fromDate) <=
            new Date().setDate(new Date(pendingApplication.fromDate).getDate() + pendingApplication.dayOff ?? 0)
            &&
            new Date().setDate(new Date(x.fromDate).getDate() + x.dayOff ?? 0) >=
            new Date(pendingApplication.fromDate)
        );

        if (subordinatesApplicationsInSamePeriod) {
            subordinatesApplicationsInSamePeriodHtml = subordinatesApplicationsInSamePeriod.map(application =>
                <Application pendingApplication={application} />
            );
        }
    }

    //console.log(commentValue);

    //useEffect(() => {
    //    
    //  }, [commentValue]);


    function handleApproveClick(id, e) {
        //console.log('approve click:', id);

        //call api here to approve the application 
        //and then reload the page
        //http://localhost:8080/api/manager/update-application-status
        //set http request header manager_id = xxx, application_id = {id}, status= APPROVED(4)/REJECTED(5),
        //and comment  

            let config = {
                method: 'get',
                maxBodyLength: Infinity,
                url: 'http://localhost:8080/api/manager/update-application-status',
                headers: { 
                  'manager_id': currentloginuserID, 
                  'application_id': application_id, 
                  'status': 'APPROVED', 
                  'reviewedComment': commentValue
                }
              };
              
              axios.request(config)
              .then((response) => {
                console.log('approved', JSON.stringify(response.data));
                updatePendingApplication(response.data);
                setShowApproveRejectButton(false);
              })
              .catch((error) => {
                console.log(error);
              });
    }
    function handleRejectClick(id, e) {
        //console.log('reject click:', id);

        if (!commentValue) {
            alert('Comment is mandatory for rejecting.');
            return;
        }
        //call api here to reject the application 
        //and then reload the page
        //http://localhost:8080/api/manager/update-application-status
        //set http request header manager_id = xxx, application_id = {id}, status= APPROVED(4)/REJECTED(5),
        //and comment  

            let config = {
                method: 'get',
                maxBodyLength: Infinity,
                url: 'http://localhost:8080/api/manager/update-application-status',
                headers: { 
                  'manager_id': currentloginuserID, 
                  'application_id': application_id, 
                  'status': 'REJECTED', 
                  'reviewedComment': commentValue
                }
              };
              
              axios.request(config)
              .then((response) => {
                console.log('rejected', JSON.stringify(response.data));
                updatePendingApplication(response.data);
                setShowApproveRejectButton(false);
              })
              .catch((error) => {
                console.log(error);
              });
        
    }

    return (
        <div>
            <h4>Details</h4>
            <div>Employee: {pendingApplication?.employee_info?.name}</div>
            <div>Leave Type: {pendingApplication?.employeeLeaveType}</div>
            <div>From Date: {pendingApplication?.fromDate}</div>
            <div>Day Off: {pendingApplication?.dayOff}</div>
            <div>Estimated To Date: {pendingApplication?.estimatedToDate}</div>
            <div>Applying Reason: {pendingApplication?.applyingReason}</div>
            <div>Application Status: {pendingApplication?.applicationStatus}</div>
            {showApproveRejectButton ?
                <div>
                    <button onClick={(e) => handleApproveClick(
                        pendingApplication?.application_id, e)}>Approve</button>
                    &nbsp;&nbsp;
                    <button onClick={(e) => handleRejectClick(
                        pendingApplication?.application_id, e)}>Reject</button>
                    <br></br>
                    <span>Comment: </span><br></br>
                    <textarea value={commentValue} placeholder='Mandatory for Rejecting'
                        onChange={(e) => setCommentValue(e.target.value)} style={{ width: '40%' }}
                    />

                </div>
                :
                <div>Reviewed Comment: {pendingApplication?.reviewedComment}</div>

            }
            <br />
            <h4>Subordinates leave applications during same period</h4>
            <table>
                <thead>
                    <tr>
                        <th>Employee</th>
                        <th>Leave Type</th>
                        <th>From Date</th>
                        <th>Day Off</th>
                        <th>Application Status</th>
                        <th>&nbsp;</th>
                    </tr>
                </thead>
                <tbody>
                    {subordinatesApplicationsInSamePeriodHtml}
                </tbody>
            </table>

        </div>
    )
}