# Projeto CRUD de clientes

## Tecnologias utilizadas
- Java
- String Boot
- JPA / Hibernate
- Mavem
- H2 database (banco de dados em memória)

## EndPoints disponíveis

- O projeto está configurado para rodar na porta 8080.

### H2

- ```http://localhost:8080/h2-console``` - GET: Acesso ao banco de dados H2

### Cliente

- ```http://localhost:8080/clients?page=0&linesPerPage=6&direction=ASC&orderBy=name``` - GET: Busca paginada.

- ```http://localhost:8080/clients/1``` - GET: Busca o cliente pelo id.

- ```http://localhost:8080/clients``` - POST: Inclui um novo cliente, e preciso informa o JSON abaixo no corpo da requesição.

```
  {
    "name": "Maria Silva",
    "cpf": "12345678901",
    "income": 6500.0,
    "birthDate": "1994-07-20T10:30:00Z",
    "children": 2
}
```

- ```http://localhost:8080/clients/1``` - PUT: Para atualizar um cliente basta informa o id na URL e informa o JSON abaixo no corpo da requesição.

```
  {
    "name": "Maria Silva",
    "cpf": "12345678901",
    "income": 6500.0,
    "birthDate": "1994-07-20T10:30:00Z",
    "children": 2
}
```

- ```http://localhost:8080/clients/1``` - DEL: Para deletar um cliente basta informa o id na URL. 
