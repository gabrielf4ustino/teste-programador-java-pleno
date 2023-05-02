import {Button, FloatingLabel, Form, Row} from "react-bootstrap";
import React, {FormEvent, useState} from "react";
import {edit} from "../../functions/ProductFunctios";
import {Product} from "../Types";


interface Props {
    closeModalHandler: () => void;
    product: Product
}


const ProductEditor: React.FC<Props> = ({closeModalHandler, product}) => {
    const [validatedSubmit, setValidatedSubmit] = useState(false);

    const handleSubmit = (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        const form = event.currentTarget;

        if (!form.checkValidity()) {
            event.stopPropagation();
            setValidatedSubmit(true);
            return;
        }

        const formData = new FormData(form);

        // Acessando os valores
        const description = formData.get('description') as string | undefined;
        const unit = formData.get('unit') as string | undefined;
        const price = Number(formData.get('price'));

        if (description && unit && !isNaN(price)) {
            edit(product.id, description, unit, price).then(closeModalHandler);
        }

    };

    return (
        <Form style={{minWidth: '400px'}} noValidate validated={validatedSubmit} onSubmit={handleSubmit}>
            <Row className="mb-3">
                <Form.Group controlId="validationDescription">
                    <FloatingLabel
                        controlId="floatingDescription"
                        label="Descrição"
                        className="mb-3"
                    >
                        <Form.Control
                            name="description"
                            required
                            type="text"
                            defaultValue={product.description}
                        />
                    </FloatingLabel>
                </Form.Group>
            </Row>
            <Row className="mb-3">
                <Form.Group controlId="validationUnit">
                    <FloatingLabel
                        controlId="floatingUnit"
                        label="Unidade"
                        className="mb-3"
                    >
                        <Form.Control
                            name="unit"
                            required
                            type="text"
                            defaultValue={product.unit}
                        />
                    </FloatingLabel>
                </Form.Group>
            </Row>
            <Row className="mb-3">
                <Form.Group controlId="validationPrice">
                    <FloatingLabel
                        controlId="floatingPrice"
                        label="Valor"
                        className="mb-3"
                    >
                        <Form.Control
                            name="price"
                            required
                            type="number"
                            step=".01"
                            defaultValue={product.price.toFixed(2)}
                        />
                    </FloatingLabel>
                </Form.Group>
            </Row>
                <Button variant="secondary" onClick={closeModalHandler}>
                    Cancelar
                </Button>
                <Button variant="primary" type="submit" style={{margin: '2%'}}>
                    Salvar alterações
                </Button>
        </Form>
    )
};


export default ProductEditor