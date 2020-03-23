import React from 'react'
import AppWrapper from './components/AppWrapper'
import AppContent from './AppContent'
import { BrowserRouter as Router} from 'react-router-dom'

import { makeStyles } from '@material-ui/core/styles'

const useStyles = makeStyles(() => ({
    root: {
        display: 'flex'
    }
}))

export default function App() {
    const classes = useStyles()
    return (
        <Router>
            <div className={classes.root}>
                <AppWrapper />
                <AppContent />
            </div>
        </Router>
    )
} 