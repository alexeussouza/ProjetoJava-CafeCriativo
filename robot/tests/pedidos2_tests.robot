*** Settings ***
Library    RequestsLibrary
Library    BuiltIn
Library    String
Suite Setup    Autenticar Usuario

*** Keywords ***
Autenticar Usuario
    # 1. Cria sessão temporária só pra login
    Create Session    tmp    http://localhost:8080
    ${data}=    Create Dictionary    username=admin    password=admin123
    ${login_resp}=    POST On Session    tmp    /login    data=${data}    allow_redirects=${False}
    Should Be Equal As Strings    ${login_resp.status_code}    302
    ${headers}=    Set Variable    ${login_resp.headers}
    ${cookie}=    Get From Dictionary    ${headers}    set-cookie
    ${jsessionid}=    Set Variable    ${cookie.split(";")[0].split("=")[1]}

    # 2. Cria a sessão oficial já com o cookie
    Create Session    cafe    http://localhost:8080    headers=Cookie=JSESSIONID=${jsessionid}


*** Test Cases ***
Finaliza Pedido Com Ingredientes Válidos
    [Tags]    pedido
    ${resp}=    POST On Session    cafe    /pedidos/finalizar    data=nomeCliente=Alex&ingredientes=cafe,chantilly
    Status Should Be    200    ${resp}

Finaliza Pedido Sem NomeCliente
    [Tags]    pedido
    ${resp}=    POST On Session    cafe    /pedidos/finalizar    data=nomeCliente=&ingredientes=cafe
    Should Contain    ${resp.text}    name="username"

Finaliza Pedido Sem Ingredientes Montados
    [Tags]    pedido
    ${resp}=    POST On Session    cafe    /pedidos/finalizar    data=nomeCliente=Alex
    Should Contain    ${resp.text}    name="username"

Consulta Pedidos Do Usuário
    [Tags]    pedido
    ${resp}=    GET On Session    cafe    /pedidos/meus-pedidos
    Status Should Be    200    ${resp}
    Should Contain    ${resp.text}    Meus Pedidos

Consulta Pedidos Sem Estar Logado
    [Tags]    pedido
    Create Session    anon    http://localhost:8080
    ${resp}=    GET On Session    anon    /pedidos/meus-pedidos    allow_redirects=${False}
    Status Should Be    302    ${resp}
    Should Contain    ${resp.headers["location"]}    /login
