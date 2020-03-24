import React, { Fragment } from 'react'
import MaterialTable from 'material-table'

const columns = [
    {title: '#', field: 'id', type: 'numeric'},
    {title: 'Customer', field: 'customer'},
    {title: 'Address', field: 'address'}
]

const dummyData =[]

const Orders = () => {
    return (
        <Fragment>
            <div style={{maxWidth:'100%'}}>
                <MaterialTable title="Orders" columns={columns} data={dummyData} />
            </div>
        </Fragment>
    )
}

export default Orders