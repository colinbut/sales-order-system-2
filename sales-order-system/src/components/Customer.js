import React, { Fragment, useContext } from 'react'
import Button from '@material-ui/core/Button'
import { Link } from 'react-router-dom'
import MaterialTable from 'material-table'
import ShoppingBasketIcon from '@material-ui/icons/ShoppingBasket';
import { BACKEND_SERVICE_URLS } from '../Config.js'
import UserContext from '../state/UserContext'


const Customer = props => {
    let customer = props.history.location.state.customer
    let userContext = useContext(UserContext)
    console.log("Context", userContext)
    const columns = [
        {title: '#', field: 'id', type: 'numeric'},
        {title: 'Customer', field: 'customer'},
        {title: 'Address', field: 'address'}
    ]

    console.log('making call to: ' + BACKEND_SERVICE_URLS['order_service'])

    const dummyData = []

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
                <MaterialTable title="Orders" columns={columns} data={dummyData} />
            </div>
        </Fragment>
    )
}

export default Customer
