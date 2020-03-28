import React, { Fragment, useEffect, useContext } from 'react'
import MaterialTable from 'material-table'
import UserContext from '../state/UserContext'
import { BACKEND_SERVICE_URLS } from '../Config.js'

const columns = [
    {title: '#', field: 'id', type: 'numeric'},
    {title: 'Customer', field: 'customer'},
    {title: 'Address', field: 'address'}
]

const dummyData =[]
let ordersData = []

const Orders = () => {
    const userContext = useContext(UserContext)
    
    useEffect(() => {
        ordersData = []
        fetch(BACKEND_SERVICE_URLS['order_service'] + "orders/list", {
            method: 'GET',
            headers: {
                'Authorization': 'Bearer ' + userContext.auth.jwtToken
            }    
        })
        .then(response => {
            if (response.ok) {
                return response.json()
            }
            throw new Error('Failed to fetch orders data from server')
        })
        .then(data => {
            console.log("Data:", data)
            for (let i = 0; i < data.length; i++){
                //console.log("Making request to fetch customer data for: " + data[i].customerId)
                fetch(BACKEND_SERVICE_URLS['customer_service'] + "customer/" + data[i].customerId, {
                    method: 'GET',
                    headers: {
                        'Authorization': 'Bearer ' + userContext.auth.jwtToken
                    }
                })
                .then(response => {
                    if (response.ok) {
                        return response.json()
                    }
                    throw new Error('Failed to fetch customer data from server')
                })
                .then(customerData => {
                    //console.log("Customer data:", customerData)
                    let address = customerData.address
                    ordersData.push({
                        id: data[i].id,
                        customer: customerData.firstName + " " + customerData.lastName + ", " + customerData.dateOfBirth + ", " + customerData.email,
                        address: address.houseFlatNo + " " + address.addressLine1 + ", " + address.postCode + ", " + address.city
                    })
                })
                .catch(error => {
                    console.error('Error...')
                })
            }
        })
        .catch(error => {
            ordersData = dummyData
        })
    }, [])

    return (
        <Fragment>
            <div style={{maxWidth:'100%'}}>
                <MaterialTable title="Orders" columns={columns} data={ordersData} />
            </div>
        </Fragment>
    )
}

export default Orders