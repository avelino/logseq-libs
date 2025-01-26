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