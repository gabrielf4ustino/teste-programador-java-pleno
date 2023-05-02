import {Button, FloatingLabel, Form, Row} from "react-bootstrap";
import React, {FormEvent, useState} from "react";
import 'bootstrap/dist/css/bootstrap.min.css';
import {register} from "../../functions/ProductFunctios";

type FlexDirection = 'row' | 'row-reverse' | 'column' | 'column-reverse';

const containerStyle = {
    width: '100%',
    height: '93%',
    display: 'flex',
    flexDirection: 'column' as FlexDirection,
    alignItems: 'center',
    justifyContent: 'center',
    fontSize: '16px',
    padding: '10px',
};

interface Props {
    handleSelect: (newScreen: string) => void;
}

const ProductRegister: React.FC<Props> = ({handleSelect}) => {

    const [validatedSubmit, setValidatedSubmit] = useState(false);

    const handleSave = () => {
        handleSelect("productList");
    };

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
            register(description, unit, price).then(handleSave);
        }

    };

    return (
        <>
            <h3>Novo produto</h3>
            <div style={containerStyle}>
                <Form style={{width: '30%', minWidth: '400px'}} noValidate validated={validatedSubmit}
                      onSubmit={handleSubmit}>
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
                                />
                            </FloatingLabel>
                        </Form.Group>
                    </Row>
                    <Button type="submit">Adicionar</Button>
                </Form>
            </div>
        </>
    );
}

export default ProductRegister
