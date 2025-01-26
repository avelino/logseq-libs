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

// Mock Editor API
const Editor = {
    // Block operations
    removeBlock: () => Promise.resolve(),
    insertBlock: () => Promise.resolve(),
    updateBlock: () => Promise.resolve(),
    getBlock: () => Promise.resolve({ uuid: 'block-123', content: 'test content' }),

    // Page operations
    getPage: () => Promise.resolve({ name: 'test-page', properties: {} }),
    createPage: () => Promise.resolve({ name: 'new-page' }),
    deletePage: () => Promise.resolve(),
    renamePage: () => Promise.resolve(),
    getAllPages: () => Promise.resolve([{ name: 'page1' }, { name: 'page2' }]),
    getPagesTreeData: () => Promise.resolve([{ name: 'root', children: [] }]),

    // Editing state
    checkEditing: () => Promise.resolve(true),
    exitEditingMode: () => Promise.resolve(),
    restoreEditingCursor: () => Promise.resolve(),
    getEditingCursorPosition: () => Promise.resolve({ line: 1, ch: 10 }),
    getEditingContentSlateValue: () => Promise.resolve({ type: 'paragraph' }),

    // Current state
    getCurrentPage: () => Promise.resolve({ name: 'current-page' }),
    getCurrentBlock: () => Promise.resolve({ uuid: 'current-block' }),
    getSelectedBlocks: () => Promise.resolve([{ uuid: 'block1' }, { uuid: 'block2' }]),

    // Navigation
    openInRightSidebar: () => Promise.resolve(),
    scrollToBlockInPage: () => Promise.resolve(),

    // Block editing
    editBlock: () => Promise.resolve(),
    selectBlock: () => Promise.resolve(),

    // Events
    onInputSelectionEnd: (callback) => callback
};

// Mock UI API
const UI = {
    showMsg: (content, status, opts) => Promise.resolve(),
    closeMsg: (key) => Promise.resolve(),
    queryElementRect: (selector) => Promise.resolve({ top: 0, left: 0, width: 100, height: 100 }),
    checkSlotValid: (slot) => Promise.resolve(true)
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
    Editor,
    UI,
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