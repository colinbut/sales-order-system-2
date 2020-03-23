import React, { Fragment } from 'react'
import Button from '@material-ui/core/Button'
import { Link } from 'react-router-dom'
import { makeStyles } from '@material-ui/core/styles'
import PhotoFilterIcon from '@material-ui/icons/PhotoFilter'

const useStyles = makeStyles(theme => ({
    toolbar: {
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'flex-end',
        padding: theme.spacing(0, 1),
        ...theme.mixins.toolbar
    }
}))

const Items = () => {
    const classes = useStyles()
    return (
        <Fragment>
            <div className={classes.toolbar}/>
            <div>
                <Button variant="contained" color="primary" component={Link} endIcon={<PhotoFilterIcon/>} to="/item-form">Create Item</Button>
            </div>
        </Fragment>
    )
}

export default Items