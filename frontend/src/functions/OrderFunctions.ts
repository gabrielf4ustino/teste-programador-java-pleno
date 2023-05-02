import axios from "axios";
import {Client, Product} from "../components/Types";

export const register = (description: string, client: Client, products: Product[]) => {
    const options = {
        method: 'POST',
        url: 'http://localhost:8080/pedido/registro',
        headers: {'Content-Type': 'application/json'},
        data: {
            description: description,
            client: client,
            products: products
        }
    };

    return axios.request(options).then(function (response) {
        return response.data;
    }).catch(function (error) {
        return error;
    });
}

export const edit = (id: number, description: string, client: Client, products: Product[]) => {
    const options = {
        method: 'PUT',
        url: 'http://localhost:8080/pedido/editar',
        headers: {'Content-Type': 'application/json'},
        data: {
            id: id,
            description: description,
            client: client,
            products: products
        }
    };

    return axios.request(options).then(function (response) {
        return response.data;
    }).catch(function (error) {
        return error;
    });
}

export const remove = (id: number) => {
    const options = {method: 'DELETE', url: 'http://localhost:8080/pedido/remover/' + id};

    return axios.request(options).then(function (response) {
        return response;
    }).catch(function (error) {
        return error;
    });
}

export const getAll = () => {
    const options = {method: 'GET', url: 'http://localhost:8080/pedido/todos'};

    return axios.request(options).then(function (response) {
        return response.data;
    }).catch(function (error) {
        return error;
    });
}

export const getById = (id: number) => {
    const options = {method: 'GET', url: 'http://localhost:8080/pedido/' + id};

    return axios.request(options).then(function (response) {
        return response.data;
    }).catch(function (error) {
        return error;
    });
}
