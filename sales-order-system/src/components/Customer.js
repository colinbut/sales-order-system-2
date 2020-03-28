import React, { Fragment, useContext, useEffect } from 'react'
import Button from '@material-ui/core/Button'
import { Link } from 'react-router-dom'
import MaterialTable from 'material-table'
import ShoppingBasketIcon from '@material-ui/icons/ShoppingBasket';
import { BACKEND_SERVICE_URLS } from '../Config.js'
import UserContext from '../state/UserContext'

const columns = [
    {title: '#', field: 'id', type: 'numeric'},
    {title: 'Customer', field: 'customer'},
    {title: 'Address', field: 'address'}
]

const Customer = props => {
    let customer = props.history.location.state.customer
    let userContext = useContext(UserContext)
    
    let dummyData = []
    let ordersData = []

    useEffect(() => {
        ordersData = []
        fetch(BACKEND_SERVICE_URLS['order_service'] + "orders?customerId=" + customer.id, {
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
            let address = customer.address
            for (let i = 0; i < data.length; i++) {
                ordersData.push({
                    id: data[i].id,
                    customer: customer.name + ", " + customer.dob + ", " + customer.email,
                    address: address
                })
            }
            console.log(ordersData)
        })
        .catch(error => {
            ordersData = dummyData
        })
    })

    return (
        <Fragment>
            <div>
                <h1>{customer.name}</h1>
                <p><strong>Date of Birth:</strong> {customer.dob}</p>
                <p><strong>Email:</strong> {customer.email}</p>
                <p><strong>Address:</strong> {customer.address}</p>
            </div>
            <Button variant="contained" color="secondary" component={Link} startIcon={<ShoppingBasketIcon/>} to="/order-form">
                    Create Order
            </Button>
            <p></p>
            <div style={{maxWidth:'100%'}}>
                <MaterialTable title="Orders" columns={columns} data={ordersData} />
            </div>
        </Fragment>
    )
}

export default Customer
