// Set up globals immediately
global.self = global;
global.window = global;
global.globalThis = global;

// Set up process.env
process.env.NODE_ENV = 'test';

// Set up document
global.document = {
    createElement: () => ({
        style: {},
        classList: {
            add: () => { }
        },
        parentNode: {
            removeChild: () => { }
        },
        contentWindow: {},
        addEventListener: () => { },
        attachShadow: () => ({
            innerHTML: ''
        })
    }),
    querySelector: () => null,
    body: {
        appendChild: () => { },
        removeChild: () => { }
    }
};

// Set up URL
global.URL = class URL {
    constructor(url) {
        this.url = url;
        this.searchParams = new Map();
    }
};

// Set up fetch
global.fetch = () => Promise.resolve({
    ok: true,
    json: () => Promise.resolve({})
});

// Set up other browser APIs
global.postMessage = () => { };
global.addEventListener = () => { };
global.removeEventListener = () => { };

// Set up navigator
global.navigator = {
    platform: 'MacIntel',
    userAgent: 'Node.js'
};

// Set up storage
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

// Set up console
if (!global.console) {
    global.console = {};
}

['log', 'error', 'warn', 'debug'].forEach(method => {
    if (!global.console[method]) {
        global.console[method] = () => { };
    }
});

// Register a handler for .js files to ensure globals are set
require.extensions['.js'] = function (module, filename) {
    // Save the original require
    const originalRequire = module.require;

    // Override require to ensure globals are set
    module.require = function (path) {
        return originalRequire.call(this, path);
    };

    // Load the module
    return require.extensions['.js'].original.apply(this, arguments);
};

// Store the original .js loader
require.extensions['.js'].original = require.extensions['.js'];

// Now we can safely require modules
require('source-map-support').install();

// Mock window object if needed
if (typeof window === 'undefined') {
    global.window = global;
}

// Mock document object
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

// Mock navigator
global.navigator = { userAgent: 'node.js' };

// Import and set up mocks
const mocks = require('../out/cljs-runtime/run.avelino.logseq_libs.mocks');

// Set up global objects
global.LSPlugin = mocks.LSPlugin;
global.logseq = mocks.logseq;