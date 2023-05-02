import './App.css'
import ClientsRegister from "./components/client/ClientsRegister";
import ProductRegister from "./components/product/ProductRegister";
import {Alert, Container, Nav, Navbar} from "react-bootstrap";
import 'bootstrap/dist/css/bootstrap.min.css';
import {useRef, useState} from "react";
import logo from '../src/assets/logo2.svg';
import ClientsComponent from "./components/client/ClientsComponent";
import ProductsList from "./components/product/ProductsList";

type FlexDirection = 'row' | 'row-reverse' | 'column' | 'column-reverse';

const containerStyle = {
    width: '100%',
    height: '93%',
    display: 'flex',
    flexDirection: 'column' as FlexDirection,
    alignItems: 'center',
    justifyContent: 'space-between',
    fontSize: '16px',
    padding: '10px',
};

function App() {

    const [screen, setScreen] = useState<string>();
    const [alert, setAlert] = useState<string | null>(null);
    const navbarRef = useRef<HTMLDivElement>(null);

    const handleSelect = (selectedKey: string | null) => {
        if (selectedKey != null) {
            setScreen(selectedKey);
            const navLinks = navbarRef.current?.querySelectorAll('a') || [];
            navLinks.forEach(link => {
                if (link.getAttribute('data-rr-ui-event-key') === selectedKey) {
                    link.classList.add('active');
                } else {
                    link.classList.remove('active');
                }
            });
        }
    };

    const alertHandler = (message: string) => {
        setAlert(message);
    }

    const renderScreen = () => {
        switch (screen) {
            case "clientRegister":
                return <ClientsRegister handleSelect={handleSelect} handleAlert={alertHandler}/>
            case "productRegister":
                return <ProductRegister handleSelect={handleSelect}/>
            case "clientList":
                return <ClientsComponent handleAlert={alertHandler}/>
            case "productList":
                return <ProductsList/>
            default:
                return <ClientsComponent handleAlert={alertHandler}/>
        }
    }

    return (
        <>
            {alert != null ?
                <Alert style={{zIndex: '9999', position: 'absolute', top: 0, margin: '10px 35%'}} variant="danger"
                       onClose={() => setAlert(null)} dismissible>
                    <Alert.Heading>Erro!</Alert.Heading>
                    <p>
                        {alert}
                    </p>
                </Alert>
                : null}
            <Navbar ref={navbarRef} style={{height: '7%', margin: '0', minHeight: '50px'}} bg="dark" variant="dark"
                    onSelect={handleSelect}>
                <Container style={{height: '50px', margin: '0'}}>
                    <img src={logo} alt="logo"/>
                    <Nav className="me-auto">
                        <Nav.Link eventKey="clientList" active>Clientes</Nav.Link>
                        <Nav.Link eventKey="productList">Produtos</Nav.Link>
                        <Nav.Link eventKey="clientRegister">Novo cliente</Nav.Link>
                        <Nav.Link eventKey="productRegister">Novo produto</Nav.Link>
                    </Nav>
                </Container>
            </Navbar>
            <div style={containerStyle}>
                {renderScreen()}
            </div>
        </>
    )
}

export default App
