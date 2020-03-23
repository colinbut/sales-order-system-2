import React, { Fragment, useState, useMemo, forwardRef } from 'react'
import clsx from 'clsx'
import { makeStyles } from '@material-ui/core/styles'
import CssBaseline from '@material-ui/core/CssBaseline'
import AppBar from '@material-ui/core/AppBar'
import Toolbar from '@material-ui/core/Toolbar'
import Drawer from '@material-ui/core/Drawer'
import IconButton from '@material-ui/core/IconButton'
import MenuIcon from '@material-ui/icons/Menu'
import Typography from '@material-ui/core/Typography'
import Divider from '@material-ui/core/Divider'
import ChevronLeftIcon from '@material-ui/icons/ChevronLeft'
import InboxIcon from '@material-ui/icons/MoveToInbox'
import MailIcon from '@material-ui/icons/Mail'
import List from '@material-ui/core/List'
import ListItem from '@material-ui/core/ListItem'
import ListItemIcon from '@material-ui/core/ListItemIcon'
import ListItemText from '@material-ui/core/ListItemText'
import { Link } from 'react-router-dom'
import AddShoppingCartIcon from '@material-ui/icons/AddShoppingCart'
import GroupIcon from '@material-ui/icons/Group'
import InfoIcon from '@material-ui/icons/Info'
import PhotoLibraryIcon from '@material-ui/icons/PhotoLibrary'
import ExitToAppIcon from '@material-ui/icons/ExitToApp'
import Tooltip from '@material-ui/core/Tooltip'

const drawerWidth = 240;

const useStyles = makeStyles(theme => ({
    root: {display: 'flex'},
    appBar: {
        zIndex: theme.zIndex.drawer + 1,
        transition: theme.transitions.create(['width', 'margin'], {
            easing: theme.transitions.easing.sharp,
            duration: theme.transitions.duration.leavingScreen
        })
    },
    appBarShift: {
        marginLeft: drawerWidth,
        width: `calc(100% - ${drawerWidth}px)`,
        transition: theme.transitions.create(['width', 'margin'], {
            easing: theme.transitions.easing.sharp,
            duration: theme.transitions.duration.enteringScreen
        })
    },
    menuButton: {
        marginRight: 36
    },
    hide: {
        display: 'none'
    },
    drawer: {
        width: drawerWidth,
        flexShrink: 0,
        whiteSpace: 'nowrap'
    },
    drawerOpen: {
        width: drawerWidth,
        transition: theme.transitions.create('width', {
            easing: theme.transitions.easing.sharp,
            duration: theme.transitions.duration.enteringScreen
        })
    },
    drawerClose: {
        transition: theme.transitions.create('width', {
            easing: theme.transitions.easing.sharp,
            duration: theme.transitions.duration.leavingScreen
        }),
        overflowX: 'hidden',
        width: theme.spacing(7) + 1,
        [theme.breakpoints.up('sm')]: {
            width: theme.spacing(9) + 1
        }
    },
    toolbar: {
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'flex-end',
        padding: theme.spacing(0, 1),
        ...theme.mixins.toolbar
    }
}))

const ListItemLink = (props) => {
    const { icon, text, to } = props

    const renderLink = useMemo(
        () => forwardRef((itemProps, ref) => <Link to={to} ref={ref} {...itemProps} />), 
        [to]
    )

    return (
        <Tooltip title={text} arrow>
            <ListItem button component={renderLink}>
                <ListItemIcon>{icon}</ListItemIcon>
                <ListItemText primary={text} />
            </ListItem>
        </Tooltip>
    )
}

export default function AppWrapper() {
    const classes = useStyles()
    const [open, setOpen] = useState(false)

    const handleDrawerOpen = () => {
        setOpen(true)
    }

    const handleDrawerClose = () => {
        setOpen(false)
    }

    return (
        <Fragment>
            <CssBaseline />
            <AppBar 
                position="fixed" 
                className={clsx(classes.appBar, {
                    [classes.appBarShift]: open
                })}
            >
                <Toolbar>
                    <IconButton 
                        color="inherit" 
                        aria-label="open drawer" 
                        onClick={handleDrawerOpen} 
                        edge="start"
                        className={clsx(classes.menuButton, {
                            [classes.hide]: open
                        })}
                    >
                        <MenuIcon />
                    </IconButton>
                    <Typography variant="h4" noWrap>
                        Sales Order System 2.0
                    </Typography>
                </Toolbar>
            </AppBar>
            <Drawer 
                variant="permanent" 
                className={clsx(classes.drawer, {
                    [classes.drawerOpen]: open,
                    [classes.drawerClose]: !open
                })}
                classes= {{
                    paper: clsx({
                        [classes.drawerOpen]: open,
                        [classes.drawerClose]: !open
                    })
                }}
            >
                <div className={classes.toolbar}>
                    <IconButton onClick={handleDrawerClose}>
                        <ChevronLeftIcon/>
                    </IconButton>
                </div>
                <Divider />
                <List>
                    <ListItemLink text="Customers" icon={<GroupIcon/>} to="/customers"/>
                    <ListItemLink text="Orders" icon={<AddShoppingCartIcon/>} to="/orders"/>
                    <ListItemLink text="Items" icon={<PhotoLibraryIcon/>} to="/items"/>
                </List>
                <Divider/>
                <List>
                    <ListItemLink text="About" icon={<InfoIcon/>} to="/about"/>
                </List>
                <Divider/>
                <List>
                    <ListItemLink text="Logout" icon={<ExitToAppIcon/>} to="/logout"/>
                </List>
            </Drawer>
        </Fragment>
    )
}