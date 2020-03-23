import React, { Fragment } from 'react'
import Button from '@material-ui/core/Button'
import { Link } from 'react-router-dom'
import { makeStyles } from '@material-ui/core/styles'
import PhotoFilterIcon from '@material-ui/icons/PhotoFilter'
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
    {title: 'Price', field: 'price'}
]

const dummyData = [
    {id: 1, name: 'Apple iPhone 11 Pro', price: 1299.99},
    {id: 2, name: 'Apple iPhone 11', price: 699.99},
]

const Items = () => {
    const classes = useStyles()
    return (
        <Fragment>
            <div className={classes.toolbar}/>
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