(ns run.avelino.logseq-libs.test-utils
  (:require [cljs.test :refer [is]]))

;; Mock LSPluginCore
(def LSPluginCore
  #js {:baseInfo (fn [] #js {:id "test-plugin"})})

;; Mock Editor API
(def Editor
  #js {:removeBlock (fn [block-id] (js/Promise.resolve nil))
       :insertBlock (fn [block-id content opts] (js/Promise.resolve nil))
       :updateBlock (fn [block-id content] (js/Promise.resolve nil))
       :getBlock (fn [block-id] (js/Promise.resolve #js {:uuid block-id :content "test content"}))
       :getPage (fn [page-name] (js/Promise.resolve #js {:name page-name :properties #js {}}))
       :createPage (fn [name props] (js/Promise.resolve #js {:name name :properties props}))
       :deletePage (fn [name] (js/Promise.resolve nil))
       :renamePage (fn [old-name new-name] (js/Promise.resolve nil))
       :getAllPages (fn [] (js/Promise.resolve #js [#js {:name "page1"} #js {:name "page2"}]))
       :getPagesTreeData (fn [] (js/Promise.resolve #js [{:name "root" :children #js []}]))
       :checkEditing (fn [] (js/Promise.resolve true))
       :exitEditingMode (fn [] (js/Promise.resolve nil))
       :restoreEditingCursor (fn [] (js/Promise.resolve nil))
       :getEditingCursorPosition (fn [] (js/Promise.resolve #js {:line 1 :ch 10}))
       :getEditingContentSlateValue (fn [] (js/Promise.resolve #js {:type "paragraph"}))
       :getCurrentPage (fn [] (js/Promise.resolve #js {:name "current-page"}))
       :getCurrentBlock (fn [] (js/Promise.resolve #js {:uuid "current-block"}))
       :getSelectedBlocks (fn [] (js/Promise.resolve #js [#js {:uuid "block1"} #js {:uuid "block2"}]))
       :openInRightSidebar (fn [id] (js/Promise.resolve nil))
       :scrollToBlockInPage (fn [page-name block-id opts] (js/Promise.resolve nil))
       :editBlock (fn [block-id opts] (js/Promise.resolve nil))
       :selectBlock (fn [block-id] (js/Promise.resolve nil))
       :onInputSelectionEnd (fn [callback] callback)})

;; Mock @logseq/libs module
(def logseq-libs-mock
  #js {:LSPluginCore LSPluginCore
       :Editor Editor})

;; Mock require for @logseq/libs
(when (exists? js/jest)
  (.mock js/jest "@logseq/libs" (fn [] logseq-libs-mock)))

(defn js-equal? [a b]
  (let [a-clj (js->clj a :keywordize-keys true)
        b-clj (js->clj b :keywordize-keys true)]
    (= (dissoc a-clj :callback) (dissoc b-clj :callback))))

(defn was-called? [test-plugin]
  (not (nil? @(:last-call test-plugin))))

(defn create-test-plugin []
  (let [calls (atom [])
        app-obj (js-obj)
        plugin-obj (js-obj)
        editor-obj (js-obj)
        ui-obj (js-obj)
        last-call (atom nil)]

    ;; Core methods
    (set! (.-ready plugin-obj)
          (fn [& args]
            (reset! last-call args)
            (swap! calls conj [:ready (vec args)])))

    (set! (.-provideModel plugin-obj)
          (fn [model]
            (reset! last-call [model])
            (swap! calls conj [:provide-model [(clj->js model)]])))

    (set! (.-provideStyle plugin-obj)
          (fn [style]
            (reset! last-call [style])
            (swap! calls conj [:provide-style [style]])))

    (set! (.-provideUI plugin-obj)
          (fn [ui]
            (reset! last-call [ui])
            (swap! calls conj [:provide-ui [(clj->js ui)]])))

    ;; UI methods
    (set! (.-showMsg ui-obj)
          (fn [content status opts]
            (reset! last-call [content status opts])
            (swap! calls conj [:show-msg [content status opts]])))

    (set! (.-closeMsg ui-obj)
          (fn [key]
            (reset! last-call [key])
            (swap! calls conj [:close-msg [key]])))

    (set! (.-queryElementRect ui-obj)
          (fn [selector]
            (reset! last-call [selector])
            (swap! calls conj [:query-element-rect [selector]])))

    (set! (.-checkSlotValid ui-obj)
          (fn [slot]
            (reset! last-call [slot])
            (swap! calls conj [:check-slot-valid [slot]])))

    ;; Settings methods
    (set! (.-updateSettings plugin-obj)
          (fn [settings]
            (reset! last-call [settings])
            (swap! calls conj [:update-settings [(clj->js settings)]])))

    (set! (.-onSettingsChanged plugin-obj)
          (fn [callback]
            (reset! last-call [callback])
            (swap! calls conj [:on-settings-changed [callback]])))

    (set! (.-beforeUnload plugin-obj)
          (fn [callback]
            (reset! last-call [callback])
            (swap! calls conj [:before-unload [callback]])))

    (set! (.-useSettingsSchema plugin-obj)
          (fn [schema]
            (reset! last-call [schema])
            (swap! calls conj [:use-settings-schema [(clj->js schema)]])))

    ;; Editor methods
    (set! (.-removeBlock editor-obj)
          (fn [block-id]
            (reset! last-call [block-id])
            (swap! calls conj [:remove-block [block-id]])))

    (set! (.-insertBlock editor-obj)
          (fn [block-id content opts]
            (reset! last-call [block-id content opts])
            (swap! calls conj [:insert-block [block-id content opts]])))

    (set! (.-updateBlock editor-obj)
          (fn [block-id content]
            (reset! last-call [block-id content])
            (swap! calls conj [:update-block [block-id content]])))

    (set! (.-deletePage editor-obj)
          (fn [page-name]
            (reset! last-call [page-name])
            (swap! calls conj [:delete-page [page-name]])))

    (set! (.-renamePage editor-obj)
          (fn [old-name new-name]
            (reset! last-call [old-name new-name])
            (swap! calls conj [:rename-page [old-name new-name]])))

    (set! (.-checkEditing editor-obj)
          (fn []
            (reset! last-call [])
            (swap! calls conj [:check-editing []])))

    (set! (.-exitEditingMode editor-obj)
          (fn []
            (reset! last-call [])
            (swap! calls conj [:exit-editing-mode []])))

    (set! (.-restoreEditingCursor editor-obj)
          (fn []
            (reset! last-call [])
            (swap! calls conj [:restore-editing-cursor []])))

    (set! (.-openInRightSidebar editor-obj)
          (fn [id]
            (reset! last-call [id])
            (swap! calls conj [:open-in-right-sidebar [id]])))

    (set! (.-scrollToBlockInPage editor-obj)
          (fn [page-name block-id opts]
            (reset! last-call [page-name block-id opts])
            (swap! calls conj [:scroll-to-block-in-page [page-name block-id opts]])))

    (set! (.-editBlock editor-obj)
          (fn [block-id opts]
            (reset! last-call [block-id opts])
            (swap! calls conj [:edit-block [block-id opts]])))

    (set! (.-selectBlock editor-obj)
          (fn [block-id]
            (reset! last-call [block-id])
            (swap! calls conj [:select-block [block-id]])))

    (set! (.-onInputSelectionEnd editor-obj)
          (fn [callback]
            (reset! last-call [callback])
            (swap! calls conj [:on-input-selection-end [callback]])))

    ;; App methods
    (set! (.-registerCommandPalette app-obj)
          (fn [command]
            (let [cmd (clj->js command)]
              (reset! last-call [cmd])
              (swap! calls conj [:register-command-palette [cmd]]))))

    (set! (.-getUserConfigs app-obj)
          (fn [] (js/Promise.resolve #js {:preferredLanguage "en"})))

    ;; Set App, Editor, and UI objects
    (set! (.-App plugin-obj) app-obj)
    (set! (.-Editor plugin-obj) editor-obj)
    (set! (.-UI plugin-obj) ui-obj)

    {:calls calls
     :last-call last-call
     :plugin plugin-obj}))

(defn get-calls [test-plugin]
  @(:calls test-plugin))

(defn get-last-call [test-plugin]
  @(:last-call test-plugin))

(defn clear-calls! [test-plugin]
  (reset! (:calls test-plugin) [])
  (reset! (:last-call test-plugin) nil))

;; Export mock for use in tests
(def LSPlugin logseq-libs-mock)