(ns run.avelino.logseq-libs.mocks
  (:require [run.avelino.logseq-libs.test-utils :as test-utils]))

;; Define LSPlugin mock
(def LSPlugin-mock
  #js {:baseInfo (fn [] #js {:id "test-plugin"})})

;; Define DB mock
(def DB-mock
  #js {:q (fn [dsl]
            (js/Promise.resolve #js [#js {:id 1 :content "test"}]))
       :datascriptQuery (fn [query & inputs]
                          (js/Promise.resolve #js [#js {:id 1 :result "test"}]))
       :onChanged (fn [callback] callback)
       :onBlockChanged (fn [uuid callback] callback)})

;; Define Editor mock
(def Editor-mock
  #js {:removeBlock (fn [block-id]
                      (js/Promise.resolve #js {:ok true}))
       :insertBlock (fn [block-id content opts]
                      (js/Promise.resolve #js {:uuid block-id :content content}))
       :updateBlock (fn [block-id content]
                      (js/Promise.resolve #js {:uuid block-id :content content}))
       :getBlock (fn [block-id]
                   (js/Promise.resolve #js {:uuid block-id :content "test content"}))
       :getPage (fn [page-name]
                  (js/Promise.resolve #js {:name page-name :properties #js {}}))
       :createPage (fn [name props]
                     (js/Promise.resolve #js {:name name :properties props}))
       :deletePage (fn [name]
                     (js/Promise.resolve #js {:ok true}))
       :renamePage (fn [old-name new-name]
                     (js/Promise.resolve #js {:ok true}))
       :getAllPages (fn []
                      (js/Promise.resolve #js [#js {:name "page1"} #js {:name "page2"}]))
       :getPagesTreeData (fn []
                           (js/Promise.resolve #js [{:name "root" :children #js []}]))
       :checkEditing (fn []
                       (js/Promise.resolve true))
       :exitEditingMode (fn []
                          (js/Promise.resolve #js {:ok true}))
       :restoreEditingCursor (fn []
                               (js/Promise.resolve #js {:ok true}))
       :getEditingCursorPosition (fn []
                                   (js/Promise.resolve #js {:line 1 :ch 10}))
       :getEditingContentSlateValue (fn []
                                      (js/Promise.resolve #js {:type "paragraph"}))
       :getCurrentPage (fn []
                         (js/Promise.resolve #js {:name "current-page"}))
       :getCurrentBlock (fn []
                          (js/Promise.resolve #js {:uuid "current-block"}))
       :getSelectedBlocks (fn []
                            (js/Promise.resolve #js [#js {:uuid "block1"} #js {:uuid "block2"}]))
       :openInRightSidebar (fn [id]
                             (js/Promise.resolve #js {:ok true}))
       :scrollToBlockInPage (fn [page-name block-id opts]
                              (js/Promise.resolve #js {:ok true}))
       :editBlock (fn [block-id opts]
                    (js/Promise.resolve #js {:ok true}))
       :selectBlock (fn [block-id]
                      (js/Promise.resolve #js {:ok true}))
       :onInputSelectionEnd (fn [callback] callback)})

;; Define UI mock
(def UI-mock
  #js {:showMsg (fn [content status opts] (js/Promise.resolve))
       :closeMsg (fn [key] (js/Promise.resolve))
       :queryElementRect (fn [selector] (js/Promise.resolve #js {:top 0 :left 0 :width 100 :height 100}))
       :checkSlotValid (fn [slot] (js/Promise.resolve true))})

;; Define App mock
(def App-mock
  #js {:registerCommandPalette (fn [])
       :getUserConfigs (fn [] (js/Promise.resolve #js {:preferredLanguage "en"}))})

;; Define logseq mock with all functionality
(def logseq-mock
  (let [base-obj #js {:ready (fn [])
                      :provideModel (fn [])
                      :provideStyle (fn [])
                      :provideUI (fn [])
                      :updateSettings (fn [])
                      :onSettingsChanged (fn [])
                      :beforeunload (fn [])
                      :useSettingsSchema (fn [])}]
    (doto base-obj
      (js/Object.defineProperty "DB" #js {:value DB-mock
                                          :writable true
                                          :enumerable true
                                          :configurable true})
      (js/Object.defineProperty "Editor" #js {:value Editor-mock
                                              :writable true
                                              :enumerable true
                                              :configurable true})
      (js/Object.defineProperty "UI" #js {:value UI-mock
                                          :writable true
                                          :enumerable true
                                          :configurable true})
      (js/Object.defineProperty "App" #js {:value App-mock
                                           :writable true
                                           :enumerable true
                                           :configurable true}))))

;; Export mocks for use in tests
(def ^:export LSPlugin LSPlugin-mock)
(def ^:export logseq logseq-mock)

;; Set up global objects for Node.js environment
(when (exists? js/global)
  (set! js/LSPlugin LSPlugin-mock)
  (set! js/logseq logseq-mock))

;; Set up global objects for browser environment
(when (exists? js/window)
  (set! js/LSPlugin LSPlugin-mock)
  (set! js/logseq logseq-mock))
