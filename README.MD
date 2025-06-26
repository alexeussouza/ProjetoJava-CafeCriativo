# ☕ Café Criativo

ma aplicação **Java/Spring Boot** para montar cafés personalizados, identificar receitas clássicas e gerenciar ingredientes com uma interface segura e estilizada. Servida com um toque de **Flyway**, **Docker** e segurança embutida com **Spring Security**.

---

## 🚀 Tecnologias Utilizadas

- **Java 21 (Temurin)**
- **Spring Boot 3.x**
- **Spring Security**
- **Spring Data JPA (Hibernate)**
- **Flyway (migrations)**
- **PostgreSQL**
- **Docker & Docker Compose**
- **Thymeleaf** (views)
- **Lombok**
- **Cypress** (testes E2E frontend)
- **Robot Framework** (testes integração backend)

---

## 📦 Como rodar a aplicação

### 1. Pré-requisitos

- Docker e Docker Compose instalados
- (Opcional) Java 21 e Maven, caso queira rodar fora do container

### 2. Comandos

Dentro da pasta raiz do projeto:

```bash
docker-compose down -v        # remove containers e volume do banco
docker-compose up --build     # sobe banco e app, construindo o .jar com Maven
```
> Acesse a aplicação em: http://localhost:8080/login

# 🗄️ Banco de Dados

| Sistema       | Banco         | Usuário   | Senha     | Porta |
|:--------------|:--------------|:----------|:----------|:------|
| PostgreSQL 15 | db_cafeteria  | postgres  | postgres  | 5432  |

# 📜 Migrations 

A pasta resources/db/migration/ contém arquivos Flyway, como:

```
V1__create_tables.sql

```
O Spring aplica as migrations automaticamente ao subir a aplicação com Flyway habilitado.

---

## 📬 Endpoints da Aplicação

| Método | Caminho                   | Acesso        | Descrição                              |
|:---------|:--------------------------|:----------------|:-----------------------------------------|
| GET     | `/login`                  | Público        | Página de login                         |
| POST    | `/login`                  | Público        | Autenticação via formulário             |
| GET     | `/home`                   | Autenticado    | Página inicial do sistema               |
| GET     | `/usuarios/cadastrar`     | Público        | Exibe tela de cadastro de usuário       |
| POST    | `/usuarios/cadastrar`     | Público        | Processa novo cadastro                  |
| POST    | `/seed`                   | Público (dev)  | Cria usuário admin se não existir       |
| GET     | `/pedidos/montar`         | Autenticado    | Tela para montar um café personalizado  |
| POST    | `/pedidos/base`           | Autenticado    | Seleciona ingredientes base             |
| POST    | `/pedidos/adicionais`     | Autenticado    | Seleciona ingredientes adicionais       |
| POST    | `/pedidos/finalizar`      | Autenticado    | Confirma pedido de café                 |
| GET     | `/pedidos/meus-pedidos`   | Autenticado    | Lista pedidos do usuário logado         |

---

## ✅ Testes Automatizados

### 🧪 Frontend – Cypress

| Arquivo            | Caso de Teste                                                        |
|:-------------------|:---------------------------------------------------------------------|
| **login.cy.js**    | Login com dados válidos                                              |
|                    | Login com senha inválida                                             |
|                    | Login com campos vazios                                              |
|                    | Redirecionamento pós-login                                           |
|                    | Exibição de erro visual na falha de autenticação                     |
|                    |                                                                      | 
| **cadastro.cy.js** | Cadastro com login já existente                                      |
|                    | Cadastro com campos vazios                                           |
|                    | Cadastro válido e redirecionamento para login                        |
|                    | Exibição do formulário e botão de envio                              |
|                    | Link para "Entrar" funcionando                                       |
|                    |                                                                      | 
| **pedidos.cy.js**  | Exibição correta dos pedidos após login                              |
|                    | Verificação de tabela de pedidos                                     |
|                    | Validação de preço formatado                                         |
|                    | Checagem de status estilizado (ex: status-pronto)                    |
|                    | Navegação de volta para `/home`                                      |


➡️ Formas de executar o cypress:

**Executar a interface gráfica (modo interativo):**

```bash
npx cypress open
```

**Executar os testes direto no terminal (modo headless):**

```bash
npx cypress run
```

---

### 🤖 Backend – Robot Framework

| Arquivo                     | Casos de Teste                                             |
|:----------------------------|:-----------------------------------------------------------|
| `login_tests.robot`         | Login com sucesso, inválido, inexistente, falhas           |
| `cadastro_tests.robot`      | Cadastro válido, inválido, login curto, mensagens de erro  |
| `pedidos_tests.robot`       | Finalização de pedidos, consultas autenticadas e bloqueios |

➡️ Executar com:

```bash
robot robot-tests/tests
```

---


# 🛡️ Segurança

> BCryptPasswordEncoder usado para criptografar senhas

> Rotas protegidas com SecurityFilterChain

> Autenticação via formulário (formLogin)

> Usuários possuem papel implícito: ADMIN ou USER

# 🎨 Interface

As páginas Thymeleaf ficam em:

```
src/main/resources/templates/

```

> login.html: tela de login personalizada com o tema “Café Criativo – Login”

## 🧾 Cadastro e Acesso ao Sistema
Para utilizar o sistema da Cafeteria, o usuário precisa realizar um cadastro com os seguintes dados obrigatórios:

- Nome completo

- Login (nome de usuário único)

- Senha

Após o cadastro, o acesso deve ser feito usando:

- Login: mesmo valor preenchido no campo “Usuário”

- Senha: exatamente como foi cadastrada (atenção a maiúsculas e minúsculas)

> ⚠️ Os pedidos realizados são vinculados ao **login do usuário autenticado**. Portanto, é necessário estar logado para visualizar corretamente a página “Meus Pedidos”.

## ℹ️ Autenticação e visualização de pedidos
O sistema associa cada pedido ao usuário logado no momento da finalização. Isso significa que:

Os pedidos são filtrados por **login (usuário autenticado)**, e não pelo nome digitado no momento do pedido.

A página “Meus Pedidos” exibe **apenas** os pedidos realizados pelo usuário atualmente autenticado.

Isso garante segurança e privacidade entre usuários distintos.

> 💡 Exemplo: Se você estiver logado como alex, apenas os pedidos confirmados nesse login serão listados. Pedidos feitos anteriormente com o nome “jose”, por outro login, não aparecem.


> Estilo CSS em: static/css/cafeteria.css

# ☕ Contribuindo

> Faça um fork

> Crie uma branch com sua feature

> Envie um PR com um bom café e descrição clara ☕😄

# 📄 Licença

> Projeto livre para fins educacionais.


https://github.com/alexeussouza/ProjetoJava-CafeCriativo/blob/main/1%20-%20login.JPG
