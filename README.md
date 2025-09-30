# API da NFL com Quarkus

## Visão Geral do Projeto

A API da NFL é um serviço RESTful projetado para gerenciar informações sobre times, jogadores e posições da National Football League. Construída com o framework Quarkus, a API oferece operações CRUD (Criar, Ler, Atualizar, Excluir) para cada uma dessas entidades, além de endpoints de consulta personalizados.

A aplicação utiliza o Hibernate ORM com Panache para a camada de persistência e um banco de dados H2 em memória, que é pré-populado com dados iniciais para facilitar os testes. A documentação da API é gerada automaticamente com OpenAPI e pode ser acessada através da interface do Swagger UI.

## Tecnologias Utilizadas

* **Framework:** Quarkus
* **Linguagem:** Java
* **Acesso a Dados:** Hibernate ORM com Panache
* **Banco de Dados:** H2 (em memória)
* **Documentação da API:** OpenAPI / Swagger UI

## Como Executar o Projeto

**Pré-requisitos:**
* Java (JDK)
* Apache Maven

**Passos para Execução:**

1.  Clone o repositório do projeto.
2.  Abra um terminal na pasta raiz do projeto.
3.  Execute o seguinte comando para iniciar a aplicação em modo de desenvolvimento:

    ```bash
    ./mvnw quarkus:dev
    ```
4.  A aplicação estará disponível nos seguintes endereços:
    * **API Base URL:** `http://localhost:8080`
    * **Swagger UI:** `http://localhost:8080/documentacao/`

## Banco de Dados e Dados Iniciais

O projeto está configurado para usar um banco de dados H2 em memória com a opção `drop-and-create`, o que significa que o banco de dados é recriado a cada reinicialização da aplicação.

O arquivo `import-nfl.sql` é executado na inicialização para popular o banco de dados com dados de exemplo, incluindo:
* Estádios
* Posições
* Times
* Jogadores
* Relacionamentos entre Jogador e Posição

## Documentação da API

### Recurso: Times (`/api/times`)

| Método | Endpoint                    | Descrição                                         |
| :----- | :-------------------------- | :------------------------------------------------ |
| `POST` | `/`                         | Cria um novo time.                      |
| `GET`  | `/`                         | Lista todos os times.                   |
| `GET`  | `/{id}`                     | Busca um time pelo seu ID.              |
| `PUT`  | `/{id}`                     | Atualiza as informações de um time existente. |
| `DELETE`| `/{id}`                     | Deleta um time.                         |
| `GET`  | `/buscar`                   | Busca times por conferência (`AFC` ou `NFC`). |

### Recurso: Jogadores (`/api/jogadores`)

| Método | Endpoint                    | Descrição                                         |
| :----- | :-------------------------- | :------------------------------------------------ |
| `POST` | `/`                         | Cria um novo jogador.                   |
| `GET`  | `/`                         | Lista todos os jogadores.               |
| `GET`  | `/{id}`                     | Busca um jogador pelo seu ID.           |
| `PUT`  | `/{id}`                     | Atualiza o nome e o número de um jogador. |
| `DELETE`| `/{id}`                     | Deleta um jogador.                      |
| `GET`  | `/portime/{timeId}`         | Lista todos os jogadores de um time específico. |

### Recurso: Posições (`/api/posicoes`)

| Método | Endpoint                    | Descrição                                         |
| :----- | :-------------------------- | :------------------------------------------------ |
| `POST` | `/`                         | Cria uma nova posição.                  |
| `GET`  | `/`                         | Lista todas as posições.                |
| `GET`  | `/{id}`                     | Busca uma posição pelo seu ID.          |
| `PUT`  | `/{id}`                     | Atualiza uma posição existente.         |
| `DELETE`| `/{id}`                     | Deleta uma posição.                     |
| `GET`  | `/buscar`                   | Busca uma posição pela sua sigla (ex: `QB`). |

---

## Instrução de Uso do Thunder Client

O Thunder Client é uma extensão do VS Code para testar APIs.

### 1. Instalação

1.  No VS Code, vá para a aba **Extensions**.
2.  Procure por **Thunder Client** e clique em **Install**.

### 2. Exemplos de Requisições

#### a) Listar todos os Times (GET)
* **Método:** `GET`
* **URL:** `http://localhost:8080/api/times`

#### b) Buscar Jogador por ID (GET com Variável de Caminho)
* **Método:** `GET`
* **URL:** `http://localhost:8080/api/jogadores/1`

#### c) Buscar Posição por Sigla (GET com Parâmetro de Consulta)
* **Método:** `GET`
* **URL:** `http://localhost:8080/api/posicoes/buscar?sigla=QB`

#### d) Criar um Novo Jogador (POST com Corpo)
* **Método:** `POST`
* **URL:** `http://localhost:8080/api/jogadores`
* **Aba Body -> JSON:**
    ```json
    {
      "nome": "CeeDee Lamb",
      "numeroCamisa": 88,
      "timeId": 2,
      "posicoesIds": [3]
    }
    ```

#### e) Atualizar um Time (PUT)
* **Método:** `PUT`
* **URL:** `http://localhost:8080/api/times/1`
* **Aba Body -> JSON:**
    ```json
    {
        "nome": "Green Bay Packers FC",
        "cidade": "Green Bay",
        "conferencia": "NFC",
        "estadio": {
            "nome": "Lambeau Field",
            "capacidade": 81441
        }
    }
    ```

#### f) Deletar um Jogador (DELETE)
* **Método:** `DELETE`
* **URL:** `http://localhost:8080/api/jogadores/12`
