import React, { Fragment, useState } from 'react'
import '../../index.css';
import Form from 'react-bootstrap/Form'
import Button from '@material-ui/core/Button'
import Col from 'react-bootstrap/Col'
import { Formik } from 'formik'
import * as Yup from 'yup' 
import { makeStyles } from '@material-ui/core/styles'
import { Link } from 'react-router-dom'

const useStyles = makeStyles(theme => ({
    toolbar: {
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'flex-end',
        padding: theme.spacing(0, 1),
        ...theme.mixins.toolbar
    }
}))

const schema = Yup.object({
    items: Yup.array().of(Yup.string()).required()
})  

const submitForm = () => {
    console.log("Submitting form")
}

const OrderForm = () => {
    const classes = useStyles()
    const [validated, setValidated] = useState(false)
        return (
            <Fragment>
                <div className={classes.toolbar}/>
                <div className="contact-page-container-wrapper">
                    <h2 className="contact-form-heading">
                        Create Order
                    </h2>
                    <Formik validationSchema={schema} onSubmit={submitForm} 
                        initialValues={{}}
                    > 
                    {({handleSubmit}) => (
                    <Form noValidate validated={validated} onSubmit={handleSubmit}>
                        <p><strong>Customer:</strong>customer.name</p>
                        <Form.Row>
                            <Form.Group as={Col} md="12">
                                <Form.Label>Items*</Form.Label>
                                <Form.Check type="checkbox" id="test" label="item 1"/>
                            </Form.Group>
                        </Form.Row>
                        <Button variant="contained" color="default" type="reset">Reset</Button>
                        <Button variant="contained" color="secondary" type="submit">Create</Button>
                    </Form>
                    )}
                    </Formik>
                </div>
            </Fragment>
        )
}

export default OrderForm