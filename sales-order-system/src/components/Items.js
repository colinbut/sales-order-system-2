import React, { Fragment, useEffect, useContext } from 'react'
import Button from '@material-ui/core/Button'
import { Link } from 'react-router-dom'
import PhotoFilterIcon from '@material-ui/icons/PhotoFilter'
import MaterialTable from 'material-table'
import { BACKEND_SERVICE_URLS } from '../Config.js'
import UserContext from '../state/UserContext'

const columns = [
    {title: 'Name', field: 'name'},
    {title: 'Price', field: 'price'}
]

const dummyData = [
    {name: 'Apple iPhone 11 Pro', price: 1299.99},
    {name: 'Apple iPhone 11', price: 699.99},
]

let itemData = []

const Items = props => {
    const userContext = useContext(UserContext)
    console.log("Redirect:?", props.history.location)
    
    useEffect(() => {
        if (itemData.length === 0 ) {
            fetch(BACKEND_SERVICE_URLS['product_service'] + "item/list", {
                method: 'GET',
                headers: {
                    'Authorization': 'Bearer ' + userContext.auth.jwtToken
                }    
            })
            .then(response => {
                if (response.ok) {
                    return response.json()
                }
                throw new Error('Failed to retrieve items from server')
            })
            .then(data => {
                console.log("Data", data)
                itemData = data
            })
            .catch(error => {
                itemData = dummyData
            })
        } else {
            console.log("Already have items data not going to fetch again...")
        }

    }, [])

    return (
        <Fragment>
            <div>
                <Button variant="contained" color="primary" component={Link} endIcon={<PhotoFilterIcon/>} to="/item-form">Create Item</Button>
            </div>
            <p></p>
            <div style={{maxWidth:'100%'}}>
                <MaterialTable title="Items" columns={columns} data={itemData} />
            </div>
        </Fragment>
    )
}

export default Items