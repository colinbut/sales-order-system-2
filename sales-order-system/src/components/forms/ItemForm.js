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
    name: Yup.string().required(),
    price: Yup.string().required()
})  

const submitForm = () => {
    console.log("Submitting form")
}

const ItemForm = () => {
    const classes = useStyles()
    const [validated, setValidated] = useState(false)
        return (
            <Fragment>
                <div className={classes.toolbar}/>
                <div className="contact-page-container-wrapper">
                    <h2 className="contact-form-heading">
                        Create Item
                    </h2>
                    <Formik validationSchema={schema} onSubmit={submitForm} 
                        initialValues={{}}
                    > 
                    {({
                        handleSubmit,
                        handleChange,
                        handleBlur,
                        values,
                        touched,
                        isValid,
                        errors
                    }) => (
                    <Form noValidate validated={validated} onSubmit={handleSubmit}>
                        <Form.Row>
                            <Form.Group as={Col} md="3">
                                <Form.Label>Name*</Form.Label>
                            </Form.Group>
                            <Form.Group as={Col} md="9" controlId="name">
                                <Form.Control required 
                                    name="firstName" 
                                    value={values.name || ''}
                                    onBlur={handleBlur} 
                                    onChange={handleChange} 
                                    isValid={touched.name && !errors.name} 
                                    isInvalid={!!errors.name}
                                    type="text" />
                            </Form.Group>
                        </Form.Row>
                        <Form.Row>
                            <Form.Group as={Col} md="6">
                                <Form.Label>Price*</Form.Label>
                            </Form.Group>
                            <Form.Group as={Col} md="6" controlId="price">
                                <Form.Control required 
                                    name="price" 
                                    value={values.price} 
                                    onBlur={handleBlur}
                                    onChange={handleChange} 
                                    isValid={touched.price && !errors.price} 
                                    isInvalid={!!errors.price}
                                    type="text" />
                            </Form.Group>
                        </Form.Row>
                        <Button variant="contained" color="secondary" type="submit">Create</Button>
                    </Form>
                    )}
                    </Formik>
                </div>
            </Fragment>
        )
}

export default ItemForm