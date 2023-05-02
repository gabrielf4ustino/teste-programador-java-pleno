import axios from "axios";

export const register = (description: string, unit: string, price: number) => {
    const options = {
        method: 'POST',
        url: 'http://localhost:8080/produto/cadastro',
        headers: {'Content-Type': 'application/json'},
        data: {description: description, unit: unit, price: price}
    };

    return axios.request(options).then(function (response) {
        return response;
    }).catch(function (error) {
        return error;
    });
}

export const edit = (id: number, description: string, unit: string, price: number) => {
    const options = {
        method: 'PUT',
        url: 'http://localhost:8080/produto/editar',
        headers: {'Content-Type': 'application/json'},
        data: {id: id, description: description, unit: unit, price: price}
    };

    return axios.request(options).then(function (response) {
        return response;
    }).catch(function (error) {
        return error;
    });
}

export const remove = (id: number) => {
    const options = {method: 'DELETE', url: 'http://localhost:8080/produto/remover/' + id};

    return axios.request(options).then(function (response) {
        return response;
    }).catch(function (error) {
        return error;
    });
}

export const getAll = () => {
    const options = {method: 'GET', url: 'http://localhost:8080/produto/todos'};

    return axios.request(options).then(function (response) {
        return response;
    }).catch(function (error) {
        return error;
    });
}

export const getById = (id: number) => {
    const options = {method: 'GET', url: 'http://localhost:8080/produto/' + id};

    return axios.request(options).then(function (response) {
        return response;
    }).catch(function (error) {
        return error;
    });
}

