import React, { Fragment } from 'react'
import Button from '@material-ui/core/Button'
import { makeStyles } from '@material-ui/core/styles'
import { Link } from 'react-router-dom'
import GroupAddIcon from '@material-ui/icons/GroupAdd';
import MaterialTable from 'material-table'
import ShoppingBasketIcon from '@material-ui/icons/ShoppingBasket';

const useStyles = makeStyles(theme => ({
    toolbar: {
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'flex-end',
        padding: theme.spacing(0, 1),
        ...theme.mixins.toolbar
    }
}))

const Customer = props => {
    const classes = useStyles()
    console.log(props.history.location.state)
    let customer = props.history.location.state.customer

    const columns = [
        {title: '#', field: 'id', type: 'numeric'},
        {title: 'Customer', field: 'customer'},
        {title: 'Address', field: 'address'}
    ]

    const dummyData = []

    return (
        <Fragment>
            <div className={classes.toolbar}/>
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
