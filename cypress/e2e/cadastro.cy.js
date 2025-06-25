describe('Tela de Cadastro', () => {

    it('exibe o formulário corretamente', () => {
        cy.visit('/usuarios/cadastrar');
        cy.get('input[name="nome"]').should('exist');
        cy.get('input[name="login"]').should('exist');
        cy.get('input[name="senha"]').should('exist');
        cy.get('button[type="submit"]').should('contain', 'Cadastrar');
    });

    it('exibe erro ao cadastrar com login já existente', () => {
        cy.visit('/usuarios/cadastrar');
        cy.get('input[name="nome"]').type('admin');
        cy.get('input[name="login"]').type('admin');
        cy.get('input[name="senha"]').type('1234');
        cy.get('button[type="submit"]').click();
        cy.contains('Usuário já existe').should('be.visible');
    });

    it('cadastro válido redireciona para login', () => {
        const login = 'user' + Date.now();
        cy.visit('/usuarios/cadastrar');
        cy.get('input[name="nome"]').type(login);
        cy.get('input[name="login"]').type(login);
        cy.get('input[name="senha"]').type('123456');
        cy.get('button[type="submit"]').click();
        cy.url().should('include', '/login');
    });

    it('possui link para voltar ao login', () => {
        cy.visit('/usuarios/cadastrar');
        cy.get('a').contains('Já tem conta? Entrar').should('have.attr', 'href').and('include', '/login');
    });
});
