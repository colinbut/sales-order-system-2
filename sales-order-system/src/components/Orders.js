import React, { Fragment, useEffect, useContext, useState } from 'react'
import MaterialTable from 'material-table'
import UserContext from '../state/UserContext'

const columns = [
    {title: '#', field: 'id', type: 'numeric'},
    {title: 'Customer', field: 'customer'},
    {title: 'Address', field: 'address'}
]

const dummyData =[]

const Orders = () => {
    const userContext = useContext(UserContext)
    const [ordersData, setOrdersData] = useState([])
    
    useEffect(() => {
        if (!userContext.auth.jwtToken) {
            setOrdersData(dummyData)
            return
        }

        fetch(process.env.REACT_APP_ORDER_SERVICE + "/orders/list", {
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
            console.log("Orders Data:", data)
            const customerFetches = data.map(order => 
                fetch(process.env.REACT_APP_CUSTOMER_SERVICE + "/customer/" + order.customerId, {
                    method: 'GET',
                    headers: {
                        'Authorization': 'Bearer ' + userContext.auth.jwtToken
                    }
                }).then(response => {
                    if (response.ok) {
                        return response.json()
                    }
                    throw new Error('Failed to fetch customer data for order ' + order.id)
                }).then(customerData => ({ order, customerData }))
            )

            Promise.all(customerFetches)
                .then(results => {
                    const newOrders = results.map(({ order, customerData }) => {
                        let address = customerData.address
                        return {
                            id: order.id,
                            customer: customerData.firstName + " " + customerData.lastName + ", " + customerData.dateOfBirth + ", " + customerData.email,
                            address: address.houseFlatNo + " " + address.addressLine1 + ", " + address.postCode + ", " + address.city
                        }
                    })
                    setOrdersData(newOrders)
                })
                .catch(error => {
                    console.error('Error fetching customer data:', error)
                    setOrdersData(dummyData)
                })
        })
        .catch(error => {
            console.error('Error fetching orders data:', error)
            setOrdersData(dummyData)
        })
    }, [userContext.auth.jwtToken])

    return (
        <Fragment>
            <div style={{maxWidth:'100%'}}>
                <MaterialTable title="Orders" columns={columns} data={ordersData} />
            </div>
        </Fragment>
    )
}

export default Orders