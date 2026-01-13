import React from 'react'
import { Switch, Route, Redirect } from 'react-router'
import Customers from './components/Customers'
import CustomerForm from './components/forms/CustomerForm'
import Orders from './components/Orders'
import Items from './components/Items'
import ItemForm from './components/forms/ItemForm'
import About from './components/About'
import Customer from './components/Customer'
import OrderForm from './components/forms/OrderForm'

import { makeStyles } from '@material-ui/core/styles'

const useStyles = makeStyles(theme => ({
    content: {
        flexGrow: 1,
        padding: theme.spacing(3)
    },
    toolbar: {
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'flex-end',
        padding: theme.spacing(0, 1),
        ...theme.mixins.toolbar
    }
}))

const AppContent = () => {
    const classes = useStyles()
    return (
        <main className={classes.content}>
            <div className={classes.toolbar}/>
            <Switch>
                <Route path="/customers/:customerId" component={Customer} />
                <Route path="/customers" component={Customers} />
                <Route path="/customer-form" component={CustomerForm} />
                <Route path="/orders" component={Orders} />
                <Route path="/order-form" component={OrderForm} />
                <Route path="/items" component={Items} />
                <Route path="/item-form" component={ItemForm} />
                <Route path="/about" component={About} />
                <Route exact path="/">
                    <Redirect to="/customers"/>
                </Route>
            </Switch>
        </main>
    )
}

export default AppContent