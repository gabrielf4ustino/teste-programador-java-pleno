# EasyOrder API

## Versão Atual

- **v1.0.0**

## Sobre

Este é o backend da aplicação de cadastro e gerenciamento de clientes, produtos e pedidos. A aplicação foi desenvolvida em ambiente Windows e projetada para ser executada em Docker. 

## Pré-requisitos

- Java Local
    - Maven 3.9
    - JDK 20
- Docker
    - Docker Compose

## Instalação

- Desenvolvimento
    - Java Local
        - Clone o repositório: `git clone https://github.com/gabrielf4ustino/teste-programador-java-pleno.git`
        - Navegue até a pasta: `cd teste-programador-java-pleno`
        - Execute o comando: `mvn clean install`
        - Execute o comando: `java -jar target/EasyOrder-0.0.1-SNAPSHOT.jar`
    - Docker Local
        - Navegue até a pasta: `cd project`
        - Execute o comando: `docker-compose up`

      *Nota: executando pelo método docker executará todos os componentes da aplicação (API, bando de dados e UI)*

## Variáveis de ambiente

    As seguintes variáveis de ambiente são utilizadas pela aplicação:
    spring.datasource.url=jdbc:postgresql://db:5432/db
    spring.datasource.username=admin
    spring.datasource.password=admin
    spring.datasource.driver-class-name=org.postgresql.Driver
    spring.jpa.show-sql=false
    spring.jpa.hibernate.ddl-auto=update
  
## Banco de Dados

O banco de dados é composto por quatro tabelas: clientes, produtos, pedidos e pedido_produto. As tabelas têm os seguintes campos:

- **clientes**

| Campo    | Tipo   |
|----------|--------|
| id       | long   |
| nome     | string |
| cpf      | string |
| telefone | string |
| email    | string |

- **produtos**

| Campo     | Tipo       |
|-----------|------------|
| id        | long       |
| descricao | string     |
| unidade   | string     |
| valor     | BigDecimal |

- **pedidos**

| Campo          | Tipo         |
|----------------|--------------|
| id             | long         |
| data_de_emissao| Date         |
| descricao      | string       |
| cliente        | BigDecimal   |
| produto        | foreign key  |
| valor_total    | BigDecimal   |

- **pedido_produto**

| Campo      | Tipo   |
|------------|--------|
| pedido_id  | long   |
| produto_id | long   |

## Tecnologias

As seguintes tecnologias foram utilizadas para o desenvolvimento da aplicação:

- Java
- Spring Boot
- Docker
- PostgreSQL
- Hibernate
- JPA
- Swagger

## Documentação

    A documentação da API está disponível em *uri*/swagger-ui/index.html.
