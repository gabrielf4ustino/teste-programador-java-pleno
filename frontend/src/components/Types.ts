import Decimal from "decimal.js";

export type Product = {
    id: number,
    description: string,
    unit: string,
    price: Decimal
}

export type Client = {
    id: number
    name: string,
    cpf: string,
    telephone: string,
    email: string
};

export type Order = {
    id: number,
    issueDate: Date,
    description: string,
    client: Client,
    products: Product[],
    amount: Decimal
}

export type FlexDirection = 'row' | 'row-reverse' | 'column' | 'column-reverse';
