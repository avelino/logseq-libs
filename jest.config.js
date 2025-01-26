module.exports = {
  moduleNameMapper: {
    '^@logseq/libs$': '<rootDir>/test/__mocks__/@logseq/libs.js'
  },
  setupFiles: ['<rootDir>/test/__mocks__/@logseq/libs.js'],
  testEnvironment: 'node'
};