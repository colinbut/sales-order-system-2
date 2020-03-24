import React, { Fragment, useState } from 'react'
import '../../index.css';
import Form from 'react-bootstrap/Form'
import Button from '@material-ui/core/Button'
import Col from 'react-bootstrap/Col'
import { Formik } from 'formik'
import * as Yup from 'yup' 
import { Link } from 'react-router-dom'

const schema = Yup.object({
    firstName: Yup.string().required(),
    lastName: Yup.string().required(),
    dateOfBirth: Yup.date().required(),
    email: Yup.string().email().required(),
    houseFlatNo: Yup.number().required(),
    addressLine1: Yup.string().required(),
    addressLine2: Yup.string().required(),
    postcode: Yup.string().required(),
    city: Yup.string().required(),
    county: Yup.string().required(),
    cardNo: Yup.string(),
    cardExpDate: Yup.date(),
    customerReference: Yup.string()
})  

const submitForm = () => {
    console.log("Submitting form")
}

const CustomerForm = () => {
    const [validated, setValidated] = useState(false)
        return (
            <Fragment>
            <div className="contact-page-container-wrapper">
                <h2 className="contact-form-heading">
                    Contact Details
                </h2>
                <Formik validationSchema={schema} onSubmit={submitForm} 
                    initialValues={{
                        firstName: 'First Name',
                        lastName: 'Last Name',
                        email: 'example@email.com'
                    }}
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
                            <Form.Label>First Name*</Form.Label>
                        </Form.Group>
                        <Form.Group as={Col} md="9" controlId="firstName">
                            <Form.Control required 
                                name="firstName" 
                                value={values.firstName || ''}
                                onBlur={handleBlur} 
                                onChange={handleChange} 
                                isValid={touched.firstName && !errors.firstName} 
                                isInvalid={!!errors.firstName}
                                type="text" />
                        </Form.Group>
                    </Form.Row>
                    <Form.Row>
                        <Form.Group as={Col} md="3">
                            <Form.Label>Last Name*</Form.Label>
                        </Form.Group>
                        <Form.Group as={Col} md="9" controlId="lastName">
                            <Form.Control required 
                                name="lastName" 
                                value={values.lastName} 
                                onBlur={handleBlur}
                                onChange={handleChange} 
                                isValid={touched.lastName && !errors.lastName} 
                                isInvalid={!!errors.lastName}
                                type="text" />
                        </Form.Group>
                    </Form.Row>
                    <Form.Row>
                        <Form.Group as={Col} md="3" >
                            <Form.Label>Date Of Birth*</Form.Label>
                        </Form.Group>
                        <Form.Group as={Col} md="9" controlId="formDateOfBirth">
                            <Form.Control 
                                required 
                                name="dateOfBirth" 
                                value={values.dateOfBirth}
                                onBlur={handleBlur} 
                                onChange={handleChange} 
                                isValid={touched.dateOfBirth && !errors.dateOfBirth} 
                                isInvalid={!!errors.dateOfBirth}
                                type="date"  />
                        </Form.Group>
                    </Form.Row>
                    <Form.Row>
                        <Form.Group as={Col} md="3" >
                            <Form.Label>Email*</Form.Label>
                        </Form.Group>
                        <Form.Group as={Col} md="9" controlId="formGridEmail">
                            <Form.Control 
                                required 
                                name="email" 
                                value={values.email}
                                onBlur={handleBlur} 
                                onChange={handleChange} 
                                isValid={touched.email && !errors.email} 
                                isInvalid={!!errors.email}
                                type="email"  />
                        </Form.Group>
                    </Form.Row>
                    <Form.Row>
                        <Form.Group as={Col} md="3">
                            <Form.Label>Address Line 1*</Form.Label>
                        </Form.Group>
                        <Form.Group as={Col} md="9" controlId="addressLine1">
                            <Form.Control required 
                                name="addressLine1" 
                                value={values.addressLine1} 
                                onBlur={handleBlur}
                                onChange={handleChange} 
                                isValid={touched.addressLine1 && !errors.addressLine1} 
                                isInvalid={!!errors.addressLine1}
                                type="text" />
                        </Form.Group>
                    </Form.Row>
                    <Form.Row>
                        <Form.Group as={Col} md="3">
                            <Form.Label>Address Line 2*</Form.Label>
                        </Form.Group>
                        <Form.Group as={Col} md="9" controlId="addressLine2">
                            <Form.Control required 
                                name="addressLine2" 
                                value={values.addressLine2} 
                                onBlur={handleBlur}
                                onChange={handleChange} 
                                isValid={touched.addressLine2 && !errors.addressLine2} 
                                isInvalid={!!errors.addressLine2}
                                type="text" />
                        </Form.Group>
                    </Form.Row>
                    <Form.Row>
                        <Form.Group as={Col} md="3">
                            <Form.Label>Postcode*</Form.Label>
                        </Form.Group>
                        <Form.Group as={Col} md="9" controlId="postcode">
                            <Form.Control required 
                                name="postcode" 
                                value={values.postcode} 
                                onBlur={handleBlur}
                                onChange={handleChange} 
                                isValid={touched.postcode && !errors.postcode} 
                                isInvalid={!!errors.postcode}
                                type="text" />
                        </Form.Group>
                    </Form.Row>
                    <Form.Row>
                        <Form.Group as={Col} md="3">
                            <Form.Label>City*</Form.Label>
                        </Form.Group>
                        <Form.Group as={Col} md="9" controlId="city">
                            <Form.Control required 
                                name="city" 
                                value={values.city} 
                                onBlur={handleBlur}
                                onChange={handleChange} 
                                isValid={touched.city && !errors.city} 
                                isInvalid={!!errors.city}
                                type="text" />
                        </Form.Group>
                    </Form.Row>
                    <Form.Row>
                        <Form.Group as={Col} md="3">
                            <Form.Label>County*</Form.Label>
                        </Form.Group>
                        <Form.Group as={Col} md="9" controlId="county">
                            <Form.Control required 
                                name="county" 
                                value={values.county} 
                                onBlur={handleBlur}
                                onChange={handleChange} 
                                isValid={touched.county && !errors.county} 
                                isInvalid={!!errors.county}
                                type="text" />
                        </Form.Group>
                    </Form.Row>
                    <h2 className="contact-form-heading">
                        Payment Details
                    </h2>
                    <Form.Row>
                        <Form.Group as={Col} md="3">
                            <Form.Label>Card No.</Form.Label>
                        </Form.Group>
                        <Form.Group as={Col} md="9" controlId="cardNo">
                            <Form.Control 
                                name="cardNo" 
                                value={values.cardNo} 
                                onBlur={handleBlur}
                                onChange={handleChange} 
                                isValid={touched.cardNo && !errors.cardNo} 
                                isInvalid={!!errors.cardNo}
                                type="text" />
                        </Form.Group>
                    </Form.Row>
                    <Form.Row>
                        <Form.Group as={Col} md="3">
                            <Form.Label>Card Exp Date</Form.Label>
                        </Form.Group>
                        <Form.Group as={Col} md="9" controlId="cardExpDate">
                            <Form.Control 
                                name="cardExpDate" 
                                value={values.cardExpDate} 
                                onBlur={handleBlur}
                                onChange={handleChange} 
                                isValid={touched.cardExpDate && !errors.cardExpDate} 
                                isInvalid={!!errors.cardExpDate}
                                type="date" />
                        </Form.Group>
                    </Form.Row>
                    <Form.Row>
                        <Form.Group as={Col} md="3">
                            <Form.Label>Customer Reference</Form.Label>
                        </Form.Group>
                        <Form.Group as={Col} md="9" controlId="customerReference">
                            <Form.Control 
                                name="customerReference" 
                                value={values.customerReference} 
                                onBlur={handleBlur}
                                onChange={handleChange} 
                                isValid={touched.customerReference && !errors.customerReference} 
                                isInvalid={!!errors.customerReference}
                                type="text" />
                        </Form.Group>
                    </Form.Row>
                    <Button variant="contained" color="secondary" type="submit">Submit</Button>
                </Form>
                )}
                </Formik>
            </div>
            </Fragment>
        )
}

export default CustomerForm