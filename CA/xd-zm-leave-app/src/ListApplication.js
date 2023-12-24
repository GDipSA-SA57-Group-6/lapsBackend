import { useEffect, useState } from "react";
import Application from "./Application"
import axios from "axios";
export default function ListApplication() {

    //call api to get all applications by current login user's id
    //


    const [pendingApplicationList, updatePendingApplicationList] = useState([])

    useEffect(() => {
        let currentloginuserID = 1;
        
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

    //useEffect(() => {
    //    console.log("Retrieve the course");
    //    axios
    //        .get("http://localhost:8080/api/application/get-subordinates-alive-application")
    //        .then(response => {
    //            updatePendingApplicationList(response.data);
    //            console.log(response.data);
    //        })
    //        .catch(e => {console.log(e)})
    //},[]);




    const pendingApplicationListGroupedByName = pendingApplicationList.reduce((acc, curr) => {
        let user_id = curr.employee_info.user_id;

        if (!acc[user_id]) {
            acc[user_id] = {
                items: [],
            };
        }
        acc[user_id].items.push(curr);

        return acc;
    }, {});

    //console.log(Object.keys(pendingApplicationListGroupedByName));

    const finalHtml = [];

    Object.keys(pendingApplicationListGroupedByName).map((key, index) => {
        //console.log('Name: ${pendingApplicationListGroupedByName[key].items[0].employee_info.name}');
        //console.log(key, index);
        //console.log(pendingApplicationListGroupedByName[key].items[0].employee_info.name);

        finalHtml.push(
            (
                <div>
                    <h4>Name: {pendingApplicationListGroupedByName[key].items[0].employee_info.name}</h4>
                    <table>
                        <thead>
                            <tr>
                                <th>Employee</th>
                                <th>Leave Type</th>
                                <th>From Date</th>
                                <th>Day Off</th>
                                <th>Estimated To Date</th>
                                <th>Application Status</th>
                                <th>&nbsp;</th>
                            </tr>
                        </thead>
                        <tbody>
                            {pendingApplicationListGroupedByName[key].items.map(application =>
                                <Application pendingApplication={application} />
                            )}

                        </tbody>
                    </table>
                </div>
            )
        )
    });
    //console.log({finalHtml});

    return (
        <div>
            {finalHtml}
        </div>
    );

    //const listPendingApplicationHtml = pendingApplicationList.map(application =>
    //     <div>
    //        <Application pendingApplication={application}/>
    //     </div>
    //    );
    //return (
    //    <div>      
    //       {listPendingApplicationHtml}
    //    </div>
    //
    //)

    // const listPendingApplicationHtml = pendingApplicationList.map(application =>
    //        <Application pendingApplication={application}/>
    //    );
    //return (
    //    <table>
    //     <thead>
    //        <tr>
    //            <th>Employee</th>
    //            <th>Leave Type</th>
    //            <th>From Date</th>
    //            <th>Day Off</th>
    //            <th>Application Status</th>
    //            <th>&nbsp;</th>
    //        </tr>
    //        </thead>
    //        <tbody>
    //        {listPendingApplicationHtml}
    //        </tbody>
    //    </table>
    //
    //)
}