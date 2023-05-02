import axios from "axios";

export const register = (name: string, cpf: string, telephone: string, email: string) => {
    const options = {
        method: 'POST',
        url: 'http://localhost:8080/cliente/cadastro',
        headers: {'Content-Type': 'application/json'},
        data: {
            name: name,
            cpf: cpf,
            telephone: telephone,
            email: email
        }
    };

    return axios.request(options).then(function (response) {
        return response;
    }).catch(function (error) {
        return error;
    });
}

export const edit = (id: number, name: string, cpf: string, telephone: string, email: string) => {
    const options = {
        method: 'PUT',
        url: 'http://localhost:8080/cliente/editar',
        headers: {'Content-Type': 'application/json'},
        data: {
            id: id,
            name: name,
            cpf: cpf,
            telephone: telephone,
            email: email
        }
    };

    return axios.request(options).then(function (response) {
        return response;
    }).catch(function (error) {
        return error;
    });
}

export const remove = (id: number) => {
    const options = {method: 'DELETE', url: 'http://localhost:8080/cliente/remover/' + id};

    return axios.request(options).then(function (response) {
        return response;
    }).catch(function (error) {
        return error;
    });
}

export const getAll = () => {
    const options = {method: 'GET', url: 'http://localhost:8080/cliente/todos'};

    return axios.request(options).then(function (response) {
        return response;
    }).catch(function (error) {
        return error;
    });
}

export const getById = (id: number) => {
    const options = {method: 'GET', url: 'http://localhost:8080/cliente/' + id};

    return axios.request(options).then(function (response) {
        return response;
    }).catch(function (error) {
        return error;
    });
}

export const isValidCPF = (cpf: string): boolean => {

    // Verifica se todos os dígitos são iguais
    if (/^(\d)\1+$/.test(cpf)) {
        return false;
    }

    // Calcula o primeiro dígito verificador
    let sum = 0;
    for (let i = 0; i < 9; i++) {
        sum += parseInt(cpf.charAt(i)) * (10 - i);
    }
    let remainder = sum % 11;
    let digit = remainder < 2 ? 0 : 11 - remainder;
    if (digit !== parseInt(cpf.charAt(9))) {
        return false;
    }

    // Calcula o segundo dígito verificador
    sum = 0;
    for (let i = 0; i < 10; i++) {
        sum += parseInt(cpf.charAt(i)) * (11 - i);
    }
    remainder = sum % 11;
    digit = remainder < 2 ? 0 : 11 - remainder;
    if (digit !== parseInt(cpf.charAt(10))) {
        return false;
    }

    return true;
}
