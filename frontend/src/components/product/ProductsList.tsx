import React, {useEffect, useState} from "react";
import {getAll, remove} from "../../functions/ProductFunctios";
import {Badge, Button, Dropdown, ListGroup, Modal, Spinner} from "react-bootstrap";
import Pagination from "react-bootstrap/Pagination";
import {BiEdit, FaTrashAlt} from "react-icons/all";
import {confirmAlert} from 'react-confirm-alert'; // Import
import 'react-confirm-alert/src/react-confirm-alert.css';
import ProductEditor from "./ProductEditor";
import {FlexDirection, Product} from "../Types";

const containerStyle = {
    width: '100%',
    height: '100%',
    display: 'flex',
    flexDirection: 'column' as FlexDirection,
    alignItems: 'center',
    justifyContent: 'space-between',
    fontSize: '16px',
    padding: '10px',
};

interface Props {
    handleAlert: (message: string) => void;
}

const ProductsList: React.FC<Props> = ({handleAlert}) => {
    const [products, setProducts] = useState<Product[]>([]);
    const [currentPage, setCurrentPage] = useState(1);
    const [clientsPerPage] = useState(7);
    const [refresh, setRefresh] = useState(false);
    const [showModal, setShowModal] = useState(false);
    const [productEditing, setProductEditing] = useState<Product>();
    const [loading, setLoading] = useState<boolean>(true);

    useEffect(() => {
        getAll().then((products) => {
            if (products.status === 200) {
                products.data.sort((a: Product, b: Product) => b.id - a.id);
                setProducts(products.data);
            } else {
                throw new Error(products.message)
            }
        }).catch(reason => handleAlert(reason.message)).finally(() => setLoading(false));
    }, [refresh]);

    // Get current products
    const indexOfLastProduct = currentPage * clientsPerPage;
    const indexOfFirstProduct = indexOfLastProduct - clientsPerPage;
    const currentProducts = products.slice(indexOfFirstProduct, indexOfLastProduct);

    // Change page
    const paginate = (pageNumber: number) => setCurrentPage(pageNumber);

    const pageNumbers = [];
    for (let i = 1; i <= Math.ceil(products.length / clientsPerPage); i++) {
        pageNumbers.push(i);
    }

    const removeHandler = (id: number, productDescription: string) => {
        confirmAlert({
            customUI: ({onClose}) => {
                return (
                    <div className='react-confirm-alert-body'>
                        <h2>Tem certeza?</h2>
                        <p>Você deseja remover {productDescription}?</p>
                        <div className="react-confirm-alert-button-group">
                            <button onClick={onClose}>Cancelar</button>
                            <Button variant="danger"
                                    onClick={() => {
                                        remove(id).then(() => setRefresh(!refresh)).catch(reason => handleAlert(reason.message)).finally(onClose);
                                    }}
                            >
                                Sim, remover!
                            </Button>
                        </div>
                    </div>
                );
            }
        });
    }

    const editHandler = (product: Product) => {
        setProductEditing(product);
        setShowModal(true);
    }

    const closeModalHandler = () => {
        setRefresh(!refresh);
        setShowModal(false);
    }

    if (loading)
        return (<div style={containerStyle}>
            <Spinner/>
        </div>)

    return (
        <>
            <div style={containerStyle}>
                <h2>Produtos</h2>
                {products.length !== 0 ?
                    <ListGroup as="ol" style={{width: "50%", minHeight: '85%', padding: '1em', minWidth: '400px'}}>
                        {currentProducts.map((product) => (
                            <ListGroup.Item
                                key={product.id}
                                as="li"
                                className="d-flex justify-content-between align-items-start"
                            >
                                <div className="ms-2 me-auto">
                                    <div className="fw-bold">{product.description}</div>
                                    <div>Unidade: {product.unit}</div>
                                </div>
                                <Badge bg="primary" pill>
                                    R$ {product.price.toFixed(2)}
                                </Badge>
                                <Dropdown title="Opções">
                                    <Dropdown.Toggle variant="link" style={{color: 'black'}}>
                                    </Dropdown.Toggle>
                                    <Dropdown.Menu variant="dark">
                                        <Dropdown.Item
                                            onClick={() => removeHandler(product.id, product.description)}>
                                            <FaTrashAlt/> Remover
                                        </Dropdown.Item>
                                        <Dropdown.Item
                                            onClick={() => editHandler(product)}><BiEdit/> Editar</Dropdown.Item>
                                    </Dropdown.Menu>
                                </Dropdown>
                            </ListGroup.Item>
                        ))}
                    </ListGroup> : <p style={{marginBottom: '20%'}}>Nenhum produto cadastrado</p>}
                {pageNumbers.length > 1 ? <div style={{margin: '1em auto', height: '5%'}}>
                    <Pagination>
                        {pageNumbers.map((number) => (
                            <Pagination.Item
                                key={number}
                                active={number === currentPage}
                                onClick={() => paginate(number)}
                            >
                                {number}
                            </Pagination.Item>
                        ))}
                    </Pagination>
                </div> : null}

            </div>
            <Modal show={showModal} onHide={() => setShowModal(false)}>
                <Modal.Header closeButton>
                    <Modal.Title>Editar</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    {productEditing ?
                        <ProductEditor product={productEditing} closeModalHandler={closeModalHandler}/> : null}
                </Modal.Body>
            </Modal>
        </>
    );
};

export default ProductsList