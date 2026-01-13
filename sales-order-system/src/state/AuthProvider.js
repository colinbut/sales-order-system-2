import React, { useState, useEffect } from 'react'
import UserContext from './UserContext'
import MuiAlert from '@material-ui/lab/Alert'

const AuthProvider = props => {
    const initialState = {
        isAuthenticated: false
    }

    const [auth, setAuth] = useState(initialState)

    useEffect(() => {
        fetch(process.env.REACT_APP_USER_SERVICE + "/authenticate", {
            method: 'POST',
            body: JSON.stringify({
                username: "colinbut",
                password: "password1"
            }),
            headers: {
                'Content-Type': 'application/json'
            }
        })
        .then(response => {
            if (response.ok) {
                return response.json()
            }
            throw new Error('Failed to authenticate from the server')
        })
        .then(data => {
            setAuth({
                jwtToken: data.jwtToken,
                isAuthenticated: true
            })
        })
        .catch(error => {
            console.log(error)
            setAuth({
                isAuthenticated: false,
                errorMessage: "error: failed to authenticate with backend security server"
            })
        })
    }, [])

    return (!auth.isAuthenticated) ? <UnAuthenticated authInfo={auth}/> : <Authenticated {...props} authInfo={auth} setAuthFunction={setAuth} />
    
}

export default AuthProvider

const UnAuthenticated = (props) => {
    return (
        <MuiAlert elevation={6} variant="filled" severity="error">
            {props.authInfo.errorMessage}
        </MuiAlert>
    )
}

const Authenticated = props => {
    const userContext = {
        auth: props.authInfo,
        setAuth: () => props.setAuthFunction
    }
    return (
        <UserContext.Provider value={userContext}>
            {props.children}
        </UserContext.Provider>
    )
}
