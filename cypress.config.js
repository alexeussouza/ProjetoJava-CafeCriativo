const { defineConfig } = require("cypress");

module.exports = defineConfig({
    chromeWebSecurity: false,
  e2e: {
    setupNodeEvents(on, config) {
      // implement node event listeners here
    },
    baseUrl: 'http://localhost:8080',
    testIsolation: false // não limpa o estado da tela após cada it
  },
});
