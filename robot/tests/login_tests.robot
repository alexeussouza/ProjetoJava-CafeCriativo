*** Settings ***
Library    String
Library    RequestsLibrary
Suite Setup    Create Session    cafe    http://localhost:8080

*** Test Cases ***
Login Com Sucesso
    [Tags]    login
    ${resp}=    POST On Session    cafe    /login    data=username=admin&password=admin123
    Status Should Be    200    ${resp}

Login Com Senha Incorreta
    [Tags]    login
    ${resp}=    POST On Session    cafe    /login    data=username=admin&password=errada
    Should Contain    ${resp.text}    <form action="/login"

Login Com Usuário Vazio
    [Tags]    login
    ${resp}=    POST On Session    cafe    /login    data=username=&password=123
    Should Contain    ${resp.text}    <form action="/login"

Login Com Senha Vazia
    [Tags]    login
    ${resp}=    POST On Session    cafe    /login    data=username=admin&password=
    Should Contain    ${resp.text}    <form action="/login"

Login Com Usuário Inexistente
    [Tags]    login
    ${resp}=    POST On Session    cafe    /login    data=username=desconhecido&password=123
    Should Contain    ${resp.text}    <form action="/login"



