import React, { Fragment } from 'react'
import Button from '@material-ui/core/Button'
import { Link } from 'react-router-dom'
import PhotoFilterIcon from '@material-ui/icons/PhotoFilter'
import MaterialTable from 'material-table'

const columns = [
    {title: '#', field: 'id', type: 'numeric'},
    {title: 'Name', field: 'name'},
    {title: 'Price', field: 'price'}
]

const dummyData = [
    {id: 1, name: 'Apple iPhone 11 Pro', price: 1299.99},
    {id: 2, name: 'Apple iPhone 11', price: 699.99},
]

const Items = () => {
    return (
        <Fragment>
            <div>
                <Button variant="contained" color="primary" component={Link} endIcon={<PhotoFilterIcon/>} to="/item-form">Create Item</Button>
            </div>
            <p></p>
            <div style={{maxWidth:'100%'}}>
                <MaterialTable title="Items" columns={columns} data={dummyData} />
            </div>
        </Fragment>
    )
}

export default Items