import React from 'react'
import { Switch, Route, Redirect } from 'react-router'
import Customers from './components/Customers'
import CustomerForm from './components/forms/CustomerForm'
import Orders from './components/Orders'
import Items from './components/Items'
import About from './components/About'

import { makeStyles } from '@material-ui/core/styles'

const useStyles = makeStyles(theme => ({
    content: {
        flexGrow: 1,
        padding: theme.spacing(3)
    }
}))

const AppContent = () => {
    const classes = useStyles()
    return (
        <main className={classes.content}>
            <Switch>
                <Route path="/customers" component={Customers} />
                <Route path="/customer-form" component={CustomerForm} />
                <Route path="/orders" component={Orders} />
                <Route path="/items" component={Items} />
                <Route path="/about" component={About} />
                <Route exact path="/">
                    <Redirect to="/customers"/>
                </Route>
            </Switch>
        </main>
    )
}

export default AppContent