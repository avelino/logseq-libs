(ns run.avelino.logseq-libs.mocks)

;; Mock LSPluginCore
(def LSPluginCore
  #js {:baseInfo (fn [] #js {:id "test-plugin"})})

;; Mock logseq global object
(def logseq
  (let [app-obj (js-obj)
        plugin-obj (js-obj)]

    ;; Core methods
    (set! (.-ready plugin-obj)
          (fn [& args] nil))
    (set! (.-provideModel plugin-obj)
          (fn [& args] nil))
    (set! (.-provideStyle plugin-obj)
          (fn [& args] nil))
    (set! (.-provideUI plugin-obj)
          (fn [& args] nil))

    ;; Settings methods
    (set! (.-updateSettings plugin-obj)
          (fn [& args] nil))
    (set! (.-onSettingsChanged plugin-obj)
          (fn [& args] nil))
    (set! (.-beforeunload plugin-obj)
          (fn [& args] nil))
    (set! (.-useSettingsSchema plugin-obj)
          (fn [& args] nil))

    ;; App methods
    (set! (.-registerCommandPalette app-obj)
          (fn [& args] nil))
    (set! (.-getUserConfigs app-obj)
          (fn [] (js/Promise.resolve #js {:preferredLanguage "en"})))

    ;; Set App object
    (set! (.-App plugin-obj) app-obj)

    plugin-obj))

;; Export mock for use in tests
(def LSPlugin #js {:LSPluginCore LSPluginCore
                   :logseq logseq})