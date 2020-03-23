import React, { Fragment } from 'react'
import Button from '@material-ui/core/Button'
import { makeStyles } from '@material-ui/core/styles'
import { Link } from 'react-router-dom'
import GroupAddIcon from '@material-ui/icons/GroupAdd';
import MaterialTable from 'material-table'

const useStyles = makeStyles(theme => ({
    toolbar: {
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'flex-end',
        padding: theme.spacing(0, 1),
        ...theme.mixins.toolbar
    }
}))

const columns = [
    {title: '#', field: 'id', type: 'numeric'},
    {title: 'Name', field: 'name'},
    {title: 'Email', field: 'email'},
    {title: 'Address', field: 'address'}
]

const dummyData = [
    {id: 1, name: 'Colin But', email: 'colin.but1@gmail.com', address: '78 RockingFeller Street, Paris'},
    {id: 2, name: 'Amy Man', email: 'amy.but1@gmail.com', address: '78 RockingFeller Street, Paris'},
    {id: 3, name: 'Daniel But', email: 'daniel.but1@yahoo.co.uk', address: '78 RockingFeller Street, Paris'},
    {id: 4, name: 'Martin Man', email: 'martin.man@hotmail.com', address: '78 RockingFeller Street, Paris'}
]

const Customers = () => {
    const classes = useStyles()
    return (
        <Fragment>
            <div className={classes.toolbar}/>
            <div>
                <Button variant="contained" color="primary" component={Link} startIcon={<GroupAddIcon/>} to="/customer-form">
                    Create Customer
                </Button>
            </div>
            <p></p>
            <div style={{maxWidth:'100%'}}>
                <MaterialTable
                    title="Customers"
                    columns={columns}
                    data={dummyData}
                />
            </div>
        </Fragment>
    )
}

export default Customers