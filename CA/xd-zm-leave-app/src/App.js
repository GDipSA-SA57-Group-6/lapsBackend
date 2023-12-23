import logo from './logo.svg';
import './App.css';
import Application from './Application';
import ListApplication from './ListApplication';
import { BrowserRouter, Link, Route, Routes } from 'react-router-dom';
import ApplicationDetails from './ApplicationDetails';
import ManagerView from './ManagerView';

function App() {
  const applicationList=[{
     application_id: 1, 
     employee_info: {
      user_id: 1,
      name: 'SHUAIHAO',
      userType: 0,
      userTypeText: 'Employee',
      entitlementToAnnualLeave: true,
      employeeType: 18,
      employeeTypeText: 'PROFESSIONAL'
     },
     fromDate: '2023-12-01',
     dayOff: 5,
     employeeLeaveType: 0,
     employeeLeaveTypeText: 'Annual Leave',
     applicationStatus: 0,
     applicationStatusText: 'Approved'
  },
  {
    application_id: 2, 
    employee_info: {
     user_id: 10,
     name: 'LiFang',
     userType: 0,
     userTypeText: 'Employee',
     entitlementToAnnualLeave: true,
     employeeType: 18,
     employeeTypeText: 'PROFESSIONAL'
    },
    fromDate: '2023-12-02',
    dayOff: 20,
    employeeLeaveType: 0,
    employeeLeaveTypeText: 'Compensation Leave',
    applicationStatus: 0,
    applicationStatusText: 'Applied'
 },
 {
   application_id: 3, 
   employee_info: {
    user_id: 10,
    name: 'LiFang',
    userType: 'Employee',
    entitlementToAnnualLeave: true,
    employeeType: 'PROFESSIONAL'
   },
   fromDate: '2024-01-15',
   dayOff: 2,
   employeeLeaveType: 'Compensation Leave',
   applicationStatus: 'Applied'
}
  ]

  function getNavigationHTML(){
    return (
      <nav className="py-2 bg-light border-bottom">
      <div className="container d-flex flex-wrap">
        <ul className="nav me-auto">
          <li className="nav-item">
            <Link className="nav-link" to={"/"}>Home</Link>
          </li>
          <li className="nav-item">
            <Link className="nav-link" to={"/list-application"}>View Applications for Approval</Link>
          </li>
          <li className="nav-item">
            <Link className="nav-link" to={"/view-history"}>Subordinate Leave History</Link>
          </li>
 
        </ul>
      </div>
    </nav>
    )
  }

  return (
    <BrowserRouter>
    {getNavigationHTML()}

    <div>
    <h2>Leave Applications Management System</h2>
    <br></br>
    <Routes>
    
    <Route path="/list-application" element={<ListApplication pendingApplicationList={applicationList}/>}
/>;
    <Route path="/application-details" element={<ApplicationDetails pendingApplicationList={applicationList}/>}/>
    <Route path="/view-history" element={<ManagerView/>}/>

    </Routes>
    
    </div>
    </BrowserRouter>

     
  );
}

export default App;
