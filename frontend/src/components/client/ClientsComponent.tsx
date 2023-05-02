import {Badge, Button, Dropdown, ListGroup, Modal, Spinner, Table} from "react-bootstrap";
import React, {useEffect, useState} from "react";
import Pagination from "react-bootstrap/Pagination";
import {getAll as getAllClients, remove as removeClient} from "../../functions/ClientFunctions";
import {getAll as getAllOrders, remove as removeOrder} from "../../functions/OrderFunctions";
import {BiEdit, FaShoppingCart, FaTrashAlt, GrLinkPrevious} from "react-icons/all";
import {confirmAlert} from "react-confirm-alert";
import {Client, FlexDirection, Order} from "../Types";
import {format, subHours} from 'date-fns';
import ClientEditor from "./ClientEditor";
import OrderRegister from "../order/OrderRegister";
import OrderEditor from "../order/OrderEditor";

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

const ClientsComponent: React.FC<Props> = ({handleAlert}) => {
    const [clients, setClients] = useState<Client[]>([]);
    const [orders, setOrders] = useState<Order[]>([]);
    const [screen, setScreen] = useState<{ screen: string, clientId: number }>();
    const [refresh, setRefresh] = useState(false);
    const [showModal, setShowModal] = useState(false);
    const [modal, setModal] = useState<string>();
    const [clientEditing, setClientEditing] = useState<Client>();
    const [orderEditing, setOrderEditing] = useState<Order>();
    const [loading, setLoading] = useState<boolean>(true);

    useEffect(() => {
        getAllClients().then((clients) => {
            if (clients.status === 200) {
                clients.data.sort((a: Client, b: Client) => b.id - a.id);
                setClients(clients.data);
            } else {
                throw new Error(clients.message)
            }
        }).catch(reason => {
            handleAlert(reason.message)
        }).finally(() => setLoading(false));
        getAllOrders().then(orders => setOrders(orders)).catch(reason => handleAlert(reason.message));
    }, [refresh]);

    type ClickDTO = { screen: string, clientId: number }

    const handlerClick = (click: ClickDTO) => {
        setScreen(click);
    }

    const ClientsList = () => {
        const [currentPage, setCurrentPage] = useState(1);
        const [clientsPerPage] = useState(7);

        // Get current clients
        const indexOfLastClient = currentPage * clientsPerPage;
        const indexOfFirstClient = indexOfLastClient - clientsPerPage;
        const currentClients = clients.slice(indexOfFirstClient, indexOfLastClient);

        // Change page
        const paginate = (pageNumber: number) => setCurrentPage(pageNumber);

        const pageNumbers = [];
        for (let i = 1; i <= Math.ceil(clients.length / clientsPerPage); i++) {
            pageNumbers.push(i);
        }

        return (
            <>
                <h2>Clientes</h2>
                {currentClients.length !== 0 ?
                    <ListGroup as="ol" style={{width: "50%", minHeight: '85%', padding: '1em', minWidth: '400px'}}>
                        {currentClients.map((client: Client) => (
                            <ListGroup.Item
                                style={{cursor: 'pointer'}}
                                key={client.id}
                                as="li"
                                className="d-flex justify-content-between align-items-start"
                                onClick={() => handlerClick({screen: "clientDetails", clientId: client.id})}
                            >
                                <div className="ms-2 me-auto">
                                    <div className="fw-bold">{client.name}</div>
                                    <div>Telefone: {client.telephone}</div>
                                    <div>Email: {client.email}</div>

                                </div>
                                <Badge bg="primary" pill>
                                    CPF: {client.cpf}
                                </Badge>
                            </ListGroup.Item>
                        ))}
                    </ListGroup> : <p style={{marginBottom: '20%'}}>Nenhum cliente cadastrado</p>}
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
            </>)
    }

    const ClientDetails = () => {

        const client = clients.filter(client => client.id === screen?.clientId)[0];
        const ordersClient = orders.filter(order => order.client.id === screen?.clientId);

        const renderModalBody = () => {
            switch (modal) {
                case "Editar cliente":
                    return (clientEditing ?
                        <ClientEditor client={clientEditing} closeModalHandler={closeModalHandler}/> : null)
                case "Editar pedido":
                    return (orderEditing && clientEditing ?
                        <OrderEditor client={clientEditing} order={orderEditing}
                                     closeModalHandler={closeModalHandler}/> : null)
                default:
                    return <OrderRegister closeModalHandler={closeModalHandler} client={client}/>
            }
        }

        const removeHandler = (id: number, clientName: string, clientOrOrder: string) => {
            confirmAlert({
                customUI: ({onClose}) => {
                    return (
                        <div className='react-confirm-alert-body'>
                            <h2>Tem certeza?</h2>
                            <p>Você deseja remover {clientName}?</p>
                            <div className="react-confirm-alert-button-group">
                                <button onClick={onClose}>Cancelar</button>
                                <Button variant="danger"
                                        onClick={() => {
                                            switch (clientOrOrder) {
                                                case "client":
                                                    removeClient(id).then(() => {
                                                        setRefresh(!refresh);
                                                        handlerClick({screen: "clientsList", clientId: client.id});
                                                    }).catch(() => handleAlert("Erro ao tentar remover")).finally(onClose);
                                                    break;
                                                default:
                                                    removeOrder(id).then(() => {
                                                        setRefresh(!refresh);
                                                        handlerClick({screen: "clientsList", clientId: client.id});
                                                    }).catch(() => handleAlert("Erro ao tentar remover")).finally(onClose);
                                            }

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

        const editClientHandler = (client: Client) => {
            setClientEditing(client);
            setModal("Editar cliente");
            setShowModal(true);
        }

        const editOrderHandler = (client: Client, order: Order) => {
            setClientEditing(client);
            setOrderEditing(order);
            setModal("Editar pedido");
            setShowModal(true);
        }

        const addOrderHandler = () => {
            setModal("Novo pedido");
            setShowModal(true);
        }

        const closeModalHandler = () => {
            setRefresh(!refresh);
            setShowModal(false);
        }

        return (
            <div style={{
                width: "100%"
            }}>
                <div style={{
                    width: "100%",
                    textAlign: "start"
                }}>
                    <Button onClick={() => handlerClick({screen: "clientsList", clientId: client.id})} variant="light"
                            title="Voltar"><GrLinkPrevious/> Voltar</Button>
                </div>
                <div style={{
                    display: "flex",
                    flexDirection: "column",
                    alignItems: "center"
                }}>
                    <div style={{
                        minWidth: '400px',
                        width: '50%',
                        display: 'flex',
                        flexDirection: 'column',
                        backgroundColor: '#eeeeee',
                        padding: '2%',
                        borderRadius: '5px',
                        alignItems: 'flex-start',
                        margin: '1%'
                    }}>
                        <Dropdown style={{alignSelf: 'end'}} title="Opções" drop="down-centered">
                            <Dropdown.Toggle variant="link" style={{color: 'black'}}>
                            </Dropdown.Toggle>
                            <Dropdown.Menu variant="dark">
                                <Dropdown.Item
                                    onClick={() => removeHandler(client.id, client.name, "client")}>
                                    <FaTrashAlt/> Remover
                                </Dropdown.Item>
                                <Dropdown.Item
                                    onClick={() => editClientHandler(client)}><BiEdit/> Editar</Dropdown.Item>
                            </Dropdown.Menu>
                        </Dropdown>
                        <h1>{client.name}</h1>
                        <div style={{
                            margin: '5% 0 0 1%',
                        }}>
                            <p>CPF: {client.cpf}</p>
                            <p>Telefone: {client.telephone}</p>
                            <p>Email: {client.email}</p>
                        </div>
                        <div style={{
                            width: '100%',
                            display: "flex",
                            flexDirection: 'row',
                            justifyContent: 'space-between',
                            alignItems: 'center'
                        }}>
                            <h2 style={{margin: '4% 0 4% 1%'}}>Pedidos</h2>
                            <Button style={{}}
                                    onClick={addOrderHandler}
                                    variant="secondary"><FaShoppingCart/> Adicionar pedido</Button>
                        </div>
                        <div style={{
                            padding: '2%',
                            width: '100%',
                            display: 'flex',
                            flexDirection: 'column',
                            alignItems: 'center'
                        }}>
                            {ordersClient.length !== 0 ?
                                <Table striped bordered hover>
                                    <thead>
                                    <tr>
                                        <th>N° pedido</th>
                                        <th>Descrição</th>
                                        <th>Data</th>
                                        <th>Total</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    {ordersClient.map((order: Order) => (
                                        <tr key={order.id}>
                                            <td>{order.id}</td>
                                            <td>{order.description}</td>
                                            <td>{format(subHours(new Date(order.issueDate), -4), 'dd/MM/yyyy')}</td>
                                            <td>R$ {order.amount.toFixed(2)}</td>
                                            <td style={{width: '20px'}}>
                                                <Dropdown style={{alignSelf: 'end'}} title="Opções"
                                                          drop="down-centered">
                                                    <Dropdown.Toggle variant="link" style={{color: 'black'}}>
                                                    </Dropdown.Toggle>
                                                    <Dropdown.Menu variant="dark">
                                                        <Dropdown.Item
                                                            onClick={() => removeHandler(order.id, order.description, "order")}>
                                                            <FaTrashAlt/> Remover
                                                        </Dropdown.Item>
                                                        <Dropdown.Item
                                                            onClick={() => editOrderHandler(client, order)}><BiEdit/> Editar</Dropdown.Item>
                                                    </Dropdown.Menu>
                                                </Dropdown>
                                            </td>
                                        </tr>
                                    ))}
                                    </tbody>
                                </Table> : <p>Nenhum pedido cadastrado</p>}
                        </div>
                    </div>
                </div>
                <Modal show={showModal} onHide={() => setShowModal(false)}>
                    <Modal.Header closeButton>
                        <Modal.Title>{modal}</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        {renderModalBody()}
                    </Modal.Body>
                </Modal>
            </div>
        )
    }

    const renderScreen = () => {
        if (loading)
            return <Spinner/>
        switch (screen?.screen) {
            case "clientDetails":
                return <ClientDetails/>
            default:
                return <ClientsList/>
        }
    }

    return (
        <div style={containerStyle}>
            {renderScreen()}
        </div>
    );
};

export default ClientsComponent;
