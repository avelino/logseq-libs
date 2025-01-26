// Set up globals immediately
global.self = global;
global.window = global;
global.globalThis = global;

// Ensure these are set before requiring any modules
const originalLoader = require.extensions['.js'];
require.extensions['.js'] = function (module, filename) {
    // Make sure globals are available
    module._compile('global.self = global;', filename);
    return originalLoader(module, filename);
};

// Set up remaining globals
global.document = {
    createElement: () => ({
        style: {},
        classList: { add: () => { } },
        parentNode: { removeChild: () => { } },
        contentWindow: {},
        addEventListener: () => { },
        attachShadow: () => ({ innerHTML: '' })
    }),
    querySelector: () => null,
    body: {
        appendChild: () => { },
        removeChild: () => { }
    }
};

global.URL = class URL {
    constructor(url) {
        this.url = url;
        this.searchParams = new Map();
    }
};

global.fetch = () => Promise.resolve({
    ok: true,
    json: () => Promise.resolve({})
});

global.postMessage = () => { };
global.addEventListener = () => { };
global.removeEventListener = () => { };

global.navigator = {
    platform: 'MacIntel',
    userAgent: 'Node.js'
};

global.localStorage = {
    getItem: () => null,
    setItem: () => { },
    removeItem: () => { }
};

global.sessionStorage = {
    getItem: () => null,
    setItem: () => { },
    removeItem: () => { }
};

// Now load and run the tests
require('../out/node-tests.js');