import React, { Fragment, useEffect, useContext } from 'react'
import Button from '@material-ui/core/Button'
import { Link, useHistory } from 'react-router-dom'
import GroupAddIcon from '@material-ui/icons/GroupAdd';
import MaterialTable from 'material-table'
import UserContext from '../state/UserContext'
import { BACKEND_SERVICE_URLS } from '../Config.js'


const columns = [
    {title: 'Id', field: 'id'},
    {title: 'Name', field: 'name'},
    {title: 'Date Of Birth', field: 'dob'},
    {title: 'Email', field: 'email'},
    {title: 'Address', field: 'address'}
]

const dummyData = [
    {id: 1, name: 'Colin But', dob: '', email: 'colin.but1@gmail.com', address: '78 RockingFeller Street, Paris'},
    {id: 2, name: 'Amy Man', dob: '', email: 'amy.but1@gmail.com', address: '78 RockingFeller Street, Paris'},
    {id: 3, name: 'Daniel But', dob: '', email: 'daniel.but1@yahoo.co.uk', address: '78 RockingFeller Street, Paris'},
    {id: 4, name: 'Martin Man', dob: '', email: 'martin.man@hotmail.com', address: '78 RockingFeller Street, Paris'}
]

let customerData = []

const Customers = () => {
    const history = useHistory()
    const userContext = useContext(UserContext)
    
    const actions = [
        {
            icon: 'launch',
            tooltip: 'Open Customer',
            onClick: (event, rowData) => {
                history.push("/customers/" + rowData.id, {
                    customer: rowData
                })
            }
        }
    ]

    useEffect(() => {
        customerData = []
        fetch(BACKEND_SERVICE_URLS['customer_service'] + "customer/list", {
            method: 'GET',
            headers: {
                'Authorization': 'Bearer ' + userContext.auth.jwtToken
            }    
        })
        .then(response => {
            if (response.ok) {
                return response.json()
            }
            throw new Error("Error getting customers data from server")
        })
        .then(data => {
            //console.log("Data:", data)
            for (let i = 0; i < data.length; i++){
                let address = data[i].address
                customerData.push({
                    id: data[i].id,
                    name: data[i].firstName + " " + data[i].lastName,
                    dob: data[i].dateOfBirth,
                    email: data[i].email,
                    address: address.houseFlatNo + " " + address.addressLine1 + ", " + address.postCode
                })
            }
            //console.log(customerData)
        })
        .catch(error => {
            customerData = dummyData
        })
    }, [])


    return (
        <Fragment>
            <div>
                <Button variant="contained" color="primary" component={Link} startIcon={<GroupAddIcon/>} to="/customer-form">
                    Create Customer
                </Button>
            </div>
            <p></p>
            <div style={{maxWidth:'100%'}}>
                <MaterialTable title="Customers" columns={columns} data={customerData} actions={actions}/>
            </div>
        </Fragment>
    )
}

export default Customers