import React, {FormEvent, useState} from 'react';
import InputMask from 'react-input-mask';
import {Button, FloatingLabel, Form, Row} from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import {isValidCPF, register} from "../../functions/ClientFunctions";
import {FlexDirection} from "../Types";

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
    handleAlert: (message: string) => void;
}


const ClientsRegister: React.FC<Props> = ({handleSelect, handleAlert}) => {
    const [cpfValid, setCpfValid] = useState<boolean | null>(null);
    const [validatedSubmit, setValidatedSubmit] = useState(false);

    const handleSave = () => {
        handleSelect("clientList");
    };

    const handleSubmit = (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        const form = event.currentTarget;

        if (!form.checkValidity() || !cpfValid) {
            event.stopPropagation();
            setValidatedSubmit(true);
            return;
        }

        const formData = new FormData(form);

        // Acessando os valores
        const name = formData.get('name') as string | undefined;
        const cpf = formData.get('cpf') as string | undefined;
        const telephone = formData.get('telephone') as string | undefined;
        const email = formData.get('email') as string | undefined;

        if (name && cpf && telephone && email) {
            register(name, cpf, telephone, email).then(handleSave).catch(reason => handleAlert(reason))
        }
    };

    const handleCpfChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const value = event.target.value;
        const cpf = value.replace(/[^\d]/g, ""); // Remove caracteres não numéricos
        if (cpf.length === 11)
            setCpfValid(isValidCPF(cpf));
    };

    return (
        <>
            <h3>Novo cliente</h3>
            <div style={containerStyle}>
                <Form style={{width: '30%', minWidth: '400px'}} noValidate validated={validatedSubmit}
                      onSubmit={handleSubmit}>
                    <Row className="mb-3">
                        <Form.Group controlId="validationName">
                            <FloatingLabel
                                controlId="floatingName"
                                label="Nome"
                                className="mb-3"
                            >
                                <Form.Control
                                    name="name"
                                    required
                                    type="text"
                                />
                            </FloatingLabel>
                        </Form.Group>
                    </Row>
                    <Row className="mb-3">
                        <div className="d-flex">
                            <Form.Group className="flex-grow-1 me-3" controlId="cpf">
                                <FloatingLabel
                                    controlId="floatingCpf"
                                    label="CPF"
                                    className="mb-3"
                                >
                                    <Form.Control
                                        name="cpf"
                                        required
                                        as={InputMask}
                                        mask="999.999.999-99"
                                        pattern="[0-9]{3}\.[0-9]{3}\.[0-9]{3}-[0-9]{2}"
                                        onChange={handleCpfChange}
                                        isValid={cpfValid != null ? cpfValid : false}
                                        isInvalid={cpfValid != null ? !cpfValid : false}
                                    />
                                    <Form.Control.Feedback type="invalid">Por favor, digite um CPF
                                        válido.</Form.Control.Feedback>
                                </FloatingLabel>
                            </Form.Group>
                            <Form.Group className="flex-grow-1 ms-3" controlId="telephone">
                                <FloatingLabel
                                    controlId="floatingTelephone"
                                    label="Telefone"
                                    className="mb-3"
                                >
                                    <Form.Control
                                        name="telephone"
                                        required
                                        as={InputMask}
                                        mask="(99) 99999-9999"
                                        pattern="\([0-9]{2}\) [0-9]{5}-[0-9]{4}"
                                    />
                                    <Form.Control.Feedback type="invalid">Por favor, digite um número de telefone
                                        válido.</Form.Control.Feedback>
                                </FloatingLabel>
                            </Form.Group>
                        </div>
                    </Row>
                    <Row className="mb-3">
                        <Form.Group controlId="email">
                            <FloatingLabel
                                controlId="floatingEmail"
                                label="Email"
                                className="mb-3"
                            >
                                <Form.Control
                                    name="email"
                                    type="email"
                                    required
                                    pattern="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$"
                                />
                                <Form.Control.Feedback type="invalid">Por favor, digite um email
                                    válido.</Form.Control.Feedback>
                            </FloatingLabel>
                        </Form.Group>
                    </Row>
                    <Button type="submit">Adicionar</Button>
                </Form>
            </div>
        </>
    );
};

export default ClientsRegister;
