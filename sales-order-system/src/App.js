import React, {useContext} from 'react'
import 'bootstrap/dist/css/bootstrap.min.css'
import AppWrapper from './components/AppWrapper'
import AppContent from './AppContent'
import { BrowserRouter as Router} from 'react-router-dom'
import AuthProvider from './state/AuthProvider'
import UserContext from './state/UserContext'

import { makeStyles } from '@material-ui/core/styles'

const useStyles = makeStyles(() => ({
    root: {
        display: 'flex'
    }
}))

export default function App() {
    const classes = useStyles()
    return (
        <AuthProvider>
            <Router>
                <div className={classes.root}>
                    <AppWrapper />
                    <AppContent />
                </div>
            </Router>
        </AuthProvider>
    )
} 