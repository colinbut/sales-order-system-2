import React, { Fragment } from 'react'
import Button from '@material-ui/core/Button'
import { Link, useHistory } from 'react-router-dom'
import GroupAddIcon from '@material-ui/icons/GroupAdd';
import MaterialTable from 'material-table'


const columns = [
    {title: 'Id', field: 'id'},
    {title: 'Name', field: 'name'},
    {title: 'Date Of Birth', field: 'dob'},
    {title: 'Email', field: 'email'},
    {title: 'Address', field: 'address'}
]

const dummyData = [
    {id: 1, name: 'Colin But', dob: '', email: 'colin.but1@gmail.com', address: '78 RockingFeller Street, Paris'},
    {id: 2, name: 'Amy Man', dob: '', email: 'amy.but1@gmail.com', address: '78 RockingFeller Street, Paris'},
    {id: 3, name: 'Daniel But', dob: '', email: 'daniel.but1@yahoo.co.uk', address: '78 RockingFeller Street, Paris'},
    {id: 4, name: 'Martin Man', dob: '', email: 'martin.man@hotmail.com', address: '78 RockingFeller Street, Paris'}
]

const Customers = () => {
    const history = useHistory()
    
    const actions = [
        {
            icon: 'launch',
            tooltip: 'Open Customer',
            onClick: (event, rowData) => {
                history.push("/customers/" + rowData.id, {
                    customer: rowData
                })
            }
        }
    ]

    return (
        <Fragment>
            <div>
                <Button variant="contained" color="primary" component={Link} startIcon={<GroupAddIcon/>} to="/customer-form">
                    Create Customer
                </Button>
            </div>
            <p></p>
            <div style={{maxWidth:'100%'}}>
                <MaterialTable title="Customers" columns={columns} data={dummyData} actions={actions}/>
            </div>
        </Fragment>
    )
}

export default Customers