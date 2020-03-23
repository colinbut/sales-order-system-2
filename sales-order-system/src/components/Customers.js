import React, { Fragment } from 'react'
import Button from '@material-ui/core/Button'
import { makeStyles } from '@material-ui/core/styles'
import { Link } from 'react-router-dom'
import GroupAddIcon from '@material-ui/icons/GroupAdd';

const useStyles = makeStyles(theme => ({
    toolbar: {
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'flex-end',
        padding: theme.spacing(0, 1),
        ...theme.mixins.toolbar
    }
}))

const Customers = () => {
    const classes = useStyles()
    return (
        <Fragment>
            <div className={classes.toolbar}/>
            <div>
                <Button 
                    variant="contained" 
                    color="primary" 
                    component={Link} 
                    startIcon={<GroupAddIcon/>}
                    to="/customer-form">
                    Create Customer
                </Button>
            </div>
        </Fragment>
    )
}

export default Customers