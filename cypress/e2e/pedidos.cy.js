describe('Página Meus Pedidos', () => {
  beforeEach(() => {
    cy.visit('/login');
    cy.get('input[name="username"]').type('admin');
    cy.get('input[name="password"]').type('1234');
    cy.get('button[type="submit"]').click();
    cy.visit('/pedidos/meus-pedidos');
  });

  it('exibe tabela de pedidos', () => {
    cy.get('h1').should('contain', '📋 Meus Pedidos');
    cy.get('table').should('exist');
    cy.get('thead tr th').should('contain', 'Sabor');
  });

  it('exibe pelo menos uma linha de pedido', () => {
    cy.get('tbody tr').its('length').should('be.gte', 1);
  });

  it('mostra preço formatado corretamente', () => {
    cy.get('td').contains(/^R\$ \d/).should('exist');
  });

  it('exibe status com classe correspondente', () => {
    cy.get('span[class^="status-"]').should('exist');
  });

  it('botão voltar retorna à home', () => {
    cy.get('a').contains('Voltar').click();
    cy.url().should('include', '/home');
  });
});
