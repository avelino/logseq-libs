require('source-map-support').install();

// Mock browser globals needed by @logseq/libs
global.self = global;
global.window = global;
global.navigator = {
    platform: 'darwin'
};
global.top = global.window;

// Mock other browser APIs that might be needed
global.URL = class URL {
    constructor(url) {
        this.url = url;
        this.origin = '';
    }
};

// Mock fetch API
global.fetch = () => Promise.resolve({
    text: () => Promise.resolve('')
});