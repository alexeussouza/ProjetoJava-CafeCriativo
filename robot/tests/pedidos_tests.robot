<input type="text" name="login" placeholder="Digite seu login" required />*** Settings ***
Library    BuiltIn
Library    String
Library    RequestsLibrary

Suite Setup    Autenticar Usuario

*** Keywords ***
Autenticar Usuario
    Create Session    cafe    http://localhost:8080
    ${data}=    Create Dictionary    username=admin    password=1234
    ${login_resp}=    POST On Session    cafe    /login    data=${data}    allow_redirects=${False}
    Should Be Equal As Strings    ${login_resp.status_code}    302

    # Aqui faz o log dos headers recebidos
    Log    ${login_resp.headers}

    ${cookie_header}=    Get From Dictionary    ${login_resp.headers}    set-cookie
    ${jsessionid}=    Set Variable    ${cookie_header.split(";")[0].split("=")[1]}
    Add Cookie To Session    cafe    JSESSIONID=${jsessionid}


*** Test Cases ***
Finaliza Pedido Com Ingredientes Válidos
    [Tags]    pedido
    ${resp}=    POST On Session    cafe    /pedidos/finalizar    data=nomeCliente=admin
    Status Should Be    200    ${resp}

Finaliza Pedido Sem NomeCliente
    [Tags]    pedido
    ${resp}=    POST On Session    cafe    /pedidos/finalizar    data=nomeCliente=
    Should Contain    ${resp.text}    erro

Finaliza Pedido Sem Ingredientes Montados
    [Tags]    pedido
    # Supondo que não há ingredientes selecionados em memória
    ${resp}=    POST On Session    cafe    /pedidos/finalizar    data=nomeCliente=admin
    Should Contain    ${resp.text}    erro

Consulta Pedidos Do Usuário
    [Tags]    pedido
    ${resp}=    GET On Session    cafe    /pedidos/meus-pedidos
    Status Should Be    200    ${resp}
    Should Contain    ${resp.text}    Meus Pedidos

Consulta Pedidos Sem Estar Logado
    [Tags]    pedido
    Create Session    anon    http://localhost:8080
    ${resp}=    GET On Session    anon    /pedidos/meus-pedidos
    Status Should Be    302    ${resp}
    Should Contain    ${resp.headers["location"]}    /login

