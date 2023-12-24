
export default function Application({pendingApplication}){

    //return(
//
    //    <div>
    //    <div>Employee: {pendingApplication.employee_info.name}</div>
    //    <div>Leave Type:{pendingApplication.employeeLeaveType}</div>
    //    <div>From Date: {pendingApplication.fromDate}</div>
    //    <div>Day Off: {pendingApplication.dayOff}</div>
    //    <div>Application Status: {pendingApplication.applicationStatus}</div>
    //    <a href={"/application-details?id=" + pendingApplication.application_id} 
    //        target="_blank">ApplicationDetails</a>
    //    <div>&nbsp;</div>
    //  </div>
//
    //) 

    return(

        <tr key={pendingApplication.application_id}>
            <td>{pendingApplication.employee_info.name}</td>
            <td>{pendingApplication.employeeLeaveType}</td>
            <td>{pendingApplication.fromDate}</td>
            <td>{pendingApplication.dayOff}</td>
            <td>{pendingApplication.estimatedToDate}</td>
            <td>{pendingApplication.applicationStatus}</td>
            <td>
            <a href={"/application-details?id=" + pendingApplication.application_id}
                target="_blank">Application Details</a>
            </td>
        </tr>

    ) 
  
  
}