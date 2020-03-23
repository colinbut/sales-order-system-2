import React, { Fragment } from 'react'
import Typography from '@material-ui/core/Typography'

import { makeStyles } from '@material-ui/core/styles'

const useStyles = makeStyles(theme => ({
    toolbar: {
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'flex-end',
        padding: theme.spacing(0, 1),
        ...theme.mixins.toolbar
    }
}))

const About = () => {
    const classes = useStyles()
    return (
        <Fragment>
            <div className={classes.toolbar}/>
            <div>
                <h1>About</h1>
                <Typography>
                    Sales Order System 2.0 (2020) is a sequel to the Sales Order System that was developed by Colin But in 2015.
                     
                </Typography>
                <p></p>
                <Typography>
                    The original Sales Order System was a demo that showcases a J2EE style monolithic web app built using Java/JavaEE technologies 
                    on the Spring framework. It did server-side rendering using a server side scripting language (JSP) to render frontend views built in plain HTML, CSS, JS.
                    Utilised Dandilion Data Tables. On the backend, it was entirely Java based bootstrapped by Spring Framework 4. 
                    JPA (with Hibernate as the underlying implementation) was used for the ORM to map between objects-relational tables in a 
                    relational database: MySQL, H2, HSQL, Apache Derby. However, since late 2017, this project was no longer actively maintained by the author himself.  
                </Typography>
                <p></p>
                <Typography>
                    Sales Order System 2.0 is a modernised incarnation of that previous project where it aims to showcases a Java web app
                    but with a more cutting edge modernised software architecture & technologies to implement it.
                </Typography>
                <Typography>
                    This new project fully embraces the microservices architecture - an architectural style which is now the norm. Sales Order System 2.0's application is the 
                    same as 1.0 (Sales Order management system) but is built entirely using React - the de-facto UI library.
                    Backend is now instead composed of several finer grained small 'microservices' which themselves acts as web services to expose REST-ful like APIs. 
                </Typography>
            </div>
        </Fragment>
    )
}

export default About