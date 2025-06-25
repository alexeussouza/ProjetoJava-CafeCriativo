*** Settings ***
Library    String
Library    RequestsLibrary
Suite Setup    Create Session    cafe    http://localhost:8080

*** Test Cases ***
Cadastro Valido
    [Tags]    cadastro
    ${rand}=    Generate Random String    6
    ${login}=    Set Variable    user${rand}
    ${resp}=    POST On Session    cafe    /usuarios/cadastrar
    ...    data=login=${login}&senha=123456&nome=RobotTest
    Should Contain    ${resp.text}    login

Cadastro Com Login Existente
    [Tags]    cadastro
    ${resp}=    POST On Session    cafe    /usuarios/cadastrar
    ...    data=login=admin&senha=123456&nome=AdminUser
    Should Contain    ${resp.text}    name="username"

Cadastro Sem Login
    [Tags]    cadastro
    ${resp}=    POST On Session    cafe    /usuarios/cadastrar
    ...    data=login=&senha=senha123&nome=TesteSemLogin
    Should Contain    ${resp.text}    name="username"

Cadastro Sem Senha
    [Tags]    cadastro
    ${resp}=    POST On Session    cafe    /usuarios/cadastrar
    ...    data=login=novoUser&senha=&nome=TesteSemSenha
    Should Contain    ${resp.text}    name="username"

Cadastro Com Login Muito Curto
    [Tags]    cadastro
    ${resp}=    POST On Session    cafe    /usuarios/cadastrar
    ...    data=login=a&senha=123456&nome=CurtoLogin
    Should Contain    ${resp.text}    name="username"


