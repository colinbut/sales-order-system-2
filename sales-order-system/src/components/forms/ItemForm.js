import React, { Fragment, useState, useContext } from 'react'
import '../../index.css';
import Form from 'react-bootstrap/Form'
import Button from '@material-ui/core/Button'
import Col from 'react-bootstrap/Col'
import { Formik } from 'formik'
import * as Yup from 'yup' 
import { Link, useHistory } from 'react-router-dom'
import UserContext from '../../state/UserContext'

const schema = Yup.object({
    name: Yup.string().required(),
    price: Yup.string().required()
})

const ItemForm = () => {
    const [validated, setValidated] = useState(false)
    const userContext = useContext(UserContext)
    const history = useHistory()
    
    const submitForm = fields => {
        //console.log("Submitting form with fields: " + JSON.stringify(fields, null, 4))
        fetch(process.env.REACT_APP_PRODUCT_SERVICE + '/item', {
            method: 'POST',
            headers: {
                'Authorization': 'Bearer ' + userContext.auth.jwtToken,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(fields, null, 4)
        })
        .then(response => {
            if (response.status === 201) {
                history.push('items')
            } else {
                alert("Error posting new items")
            }
        })
        .catch(error => {
            alert("Error posting new items")
        })

    }

        return (
            <Fragment>
                <div>
                    <h2>Create Item</h2>
                    <Formik validationSchema={schema} onSubmit={submitForm} initialValues={{}}> 
                    {({
                        handleSubmit,
                        handleChange,
                        handleBlur,
                        handleReset,
                        values,
                        touched,
                        isValid,
                        errors
                    }) => (
                    <Form noValidate validated={validated} onSubmit={handleSubmit}>
                        <Form.Row>
                            <Form.Group as={Col} md="2">
                                <Form.Label>Name*</Form.Label>
                            </Form.Group>
                            <Form.Group as={Col} md="10" controlId="name">
                                <Form.Control required 
                                    name="name" 
                                    value={values.name}
                                    onBlur={handleBlur} 
                                    onChange={handleChange} 
                                    isValid={touched.name && !errors.name} 
                                    isInvalid={!!errors.name}
                                    type="text" />
                            </Form.Group>
                        </Form.Row>
                        <Form.Row>
                            <Form.Group as={Col} md="2">
                                <Form.Label>Price (£)*</Form.Label>
                            </Form.Group>
                            <Form.Group as={Col} md="10" controlId="price">
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
                        <Button variant="contained" color="default" onClick={handleReset}>Reset</Button>
                        <Button variant="contained" color="secondary" type="submit">Create</Button>
                    </Form>
                    )}
                    </Formik>
                </div>
            </Fragment>
        )
}

export default ItemForm