import React, {FormEvent, useEffect, useRef, useState} from "react";
import {Client, Order, Product} from "../Types";
import {Button, FloatingLabel, Form, Row} from "react-bootstrap";
import {Multiselect} from "multiselect-react-dropdown";
import {edit} from "../../functions/OrderFunctions";
import Decimal from "decimal.js";
import {getAll} from "../../functions/ProductFunctios";

interface Props {
    closeModalHandler: () => void;
    order: Order,
    client: Client
}

const OrderEditor: React.FC<Props> = ({closeModalHandler, order, client}) => {
    const [validatedSubmit, setValidatedSubmit] = useState(false);
    const [products, setProducts] = useState<Product[]>([]);
    const [amount, setAmount] = useState<Decimal>(new Decimal(0.00));
    const multiselectRef = useRef<Multiselect>(null);

    useEffect(() => {
        getAll().then(products => {
            setProducts(products.data);
        });
    }, [client, order]);

    const handleSubmit = (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        const form = event.currentTarget;
        const selectedProducts = multiselectRef.current?.getSelectedItems();

        if (!form.checkValidity() || selectedProducts.length === 0) {
            event.stopPropagation();
            setValidatedSubmit(true);
            return;
        }

        const formData = new FormData(form);

        // Acessando os valores
        const description = formData.get('description') as string | undefined;

        if (description && selectedProducts.length > 0) {
            const products = selectedProducts.map((product: Product) => {
                return {id: product.id}
            });
            edit(order.id, description, client, products).then(closeModalHandler);
        }
    };

    const handleAmount = (prices: number[]) => {
        const total = prices.reduce((acc, curr) => acc.plus(curr), new Decimal(0));
        setAmount(total);
    }

    return (
        <>
            <Form style={{minWidth: '400px'}} noValidate validated={validatedSubmit}
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
                                defaultValue={order.description}
                            />
                        </FloatingLabel>
                    </Form.Group>
                </Row>
                <Row className="mb-3">
                    <Form.Group controlId="validationProducts">
                        <Multiselect
                            ref={multiselectRef}
                            style={{color: 'black'}}
                            emptyRecordMsg="Nenhum produto"
                            displayValue="description"
                            options={products}
                            placeholder="Produtos"
                            onSelect={selected => handleAmount(selected.map((product: Product) => {
                                return product.price
                            }))}
                            onRemove={selected => handleAmount(selected.map((product: Product) => {
                                return product.price
                            }))}
                            selectedValues={order.products}
                        />
                    </Form.Group>
                </Row>
                <Row className="mb-3">
                    <div>
                        Total: R$ <input title="Total" style={{
                        backgroundColor: 'transparent',
                        color: 'black',
                        border: 'none',
                        outline: 'none'
                    }} type="number" readOnly value={amount.toFixed(2)}/>
                    </div>
                </Row>
                <Button variant="secondary" onClick={closeModalHandler}>
                    Cancelar
                </Button>
                <Button variant="primary" type="submit" style={{margin: '2%'}}>
                    Salvar alterações
                </Button>
            </Form>
        </>)
}

export default OrderEditor