// Setup browser globals
if (typeof self === 'undefined') {
    global.self = global;
    global.window = global;
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
    global.navigator = { userAgent: 'node.js' };
}

// Mock LSPluginCore
const LSPluginCore = {
    baseInfo: () => ({ id: 'test-plugin' })
};

// Mock logseq object
const logseq = {
    ready: () => { },
    provideModel: () => { },
    provideStyle: () => { },
    provideUI: () => { },
    updateSettings: () => { },
    onSettingsChanged: () => { },
    beforeunload: () => { },
    useSettingsSchema: () => { },
    App: {
        registerCommandPalette: () => { },
        getUserConfigs: () => Promise.resolve({ preferredLanguage: 'en' })
    }
};

// Export mock module
module.exports = {
    LSPluginCore,
    logseq
};