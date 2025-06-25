describe('Página de Login', () => {

    it('exibe a tela de login corretamente', () => {
        cy.visit('/login');
        cy.get('h2').should('contain', 'Café Criativo – Login');
        cy.get('input[name="username"]').should('exist');
        cy.get('input[name="password"]').should('exist');
    });

    it('campo de senha é do tipo password', () => {
        cy.visit('/login');
        cy.get('input[name="password"]').should('have.attr', 'type', 'password');
    });

     it('impede acesso a /home sem estar logado', () => {
        cy.visit('/home');
        cy.url().should('include', '/login');
    });

    it('exibe erro com senha incorreta', () => {
        cy.visit('/login');
        cy.get('input[name="username"]').type('admin');
        cy.get('input[name="password"]').type('senhaErrada');
        cy.get('button[type="submit"]').click();
        cy.contains('Login ou senha inválidos').should('be.visible');
    });

    it('exibe erro para login inexistente', () => {
        cy.visit('/login');
        cy.get('input[name="username"]').type('naoexiste');
        cy.get('input[name="password"]').type('qualquer');
        cy.get('button[type="submit"]').click();
        cy.contains('Login ou senha inválidos').should('be.visible');
    });

    it('faz logout e redireciona para a página de login', () => {
        // Etapa 1: autentica primeiro
        cy.visit('/login');
        cy.get('input[name="username"]').type('admin');
        cy.get('input[name="password"]').type('1234');
        cy.get('button[type="submit"]').click();
        cy.url().should('include', '/home');

        // Etapa 2: executa o logout
        cy.get('a').contains('Sair').click();

        // Etapa 3: verifica se retornou para /login
        cy.url().should('include', '/login');
        cy.contains('Café Criativo – Login').should('be.visible');
    });

   


    it('login válido redireciona para /home', () => {
        cy.visit('/login');
        cy.get('input[name="username"]').type('admin');
        cy.get('input[name="password"]').type('1234');
        cy.get('button[type="submit"]').click();
        cy.url().should('include', '/home');
    });
});
