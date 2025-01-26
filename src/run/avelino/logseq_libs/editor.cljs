(ns run.avelino.logseq-libs.editor
  "Editor related functions for Logseq plugin API"
  (:require ["@logseq/libs" :as ls]
            [applied-science.js-interop :as j]))

;; Block operations
(defn remove-block!
  "Remove a block by its UUID or reference"
  [block-id]
  (-> ls/logseq
      (j/get :Editor)
      (j/call :removeBlock block-id)))

(defn insert-block!
  "Insert a new block"
  ([block-id content]
   (insert-block! block-id content nil))
  ([block-id content opts]
   (-> ls/logseq
       (j/get :Editor)
       (j/call :insertBlock block-id content (clj->js opts))
       (.then #(js->clj % :keywordize-keys true)))))

(defn update-block!
  "Update block content"
  [block-id content]
  (-> ls/logseq
      (j/get :Editor)
      (j/call :updateBlock block-id content)
      (.then #(js->clj % :keywordize-keys true))))

(defn get-block!
  "Get block by UUID or reference"
  [block-id]
  (-> ls/logseq
      (j/get :Editor)
      (j/call :getBlock block-id)
      (.then #(js->clj % :keywordize-keys true))))

;; Page operations
(defn get-page!
  "Get page by name"
  [page-name]
  (-> ls/logseq
      (j/get :Editor)
      (j/call :getPage page-name)
      (.then #(js->clj % :keywordize-keys true))))

(defn create-page!
  "Create a new page"
  ([name]
   (create-page! name nil))
  ([name properties]
   (-> ls/logseq
       (j/get :Editor)
       (j/call :createPage name (clj->js properties))
       (.then #(js->clj % :keywordize-keys true)))))

(defn delete-page!
  "Delete a page by name"
  [name]
  (-> ls/logseq
      (j/get :Editor)
      (j/call :deletePage name)))

(defn rename-page!
  "Rename a page"
  [old-name new-name]
  (-> ls/logseq
      (j/get :Editor)
      (j/call :renamePage old-name new-name)))

(defn get-all-pages!
  "Get all pages"
  []
  (-> ls/logseq
      (j/get :Editor)
      (j/call :getAllPages)
      (.then #(js->clj % :keywordize-keys true))))

(defn get-pages-tree-data!
  "Get pages tree data"
  []
  (-> ls/logseq
      (j/get :Editor)
      (j/call :getPagesTreeData)
      (.then #(js->clj % :keywordize-keys true))))

;; Editing state
(defn check-editing!
  "Check if in editing mode"
  []
  (-> ls/logseq
      (j/get :Editor)
      (j/call :checkEditing)))

(defn exit-editing-mode!
  "Exit editing mode"
  []
  (-> ls/logseq
      (j/get :Editor)
      (j/call :exitEditingMode)))

(defn get-editing-block-content!
  "Get the content of the block currently being edited"
  []
  (-> ls/logseq
      (j/get :Editor)
      (j/call :getEditingBlockContent)
      (.then #(js->clj % :keywordize-keys true))))

(defn restore-editing-cursor!
  "Restore editing cursor"
  []
  (-> ls/logseq
      (j/get :Editor)
      (j/call :restoreEditingCursor)))

(defn get-editing-cursor-position!
  "Get editing cursor position"
  []
  (-> ls/logseq
      (j/get :Editor)
      (j/call :getEditingCursorPosition)
      (.then #(js->clj % :keywordize-keys true))))

(defn get-editing-content-slate-value!
  "Get editing content slate value"
  []
  (-> ls/logseq
      (j/get :Editor)
      (j/call :getEditingContentSlateValue)
      (.then #(js->clj % :keywordize-keys true))))

;; Current state
(defn get-current-page!
  "Get current page"
  []
  (-> ls/logseq
      (j/get :Editor)
      (j/call :getCurrentPage)
      (.then #(js->clj % :keywordize-keys true))))

(defn get-current-block!
  "Get current block"
  []
  (-> ls/logseq
      (j/get :Editor)
      (j/call :getCurrentBlock)
      (.then #(js->clj % :keywordize-keys true))))

(defn get-selected-blocks!
  "Get selected blocks"
  []
  (-> ls/logseq
      (j/get :Editor)
      (j/call :getSelectedBlocks)
      (.then #(js->clj % :keywordize-keys true))))

;; Navigation
(defn open-in-right-sidebar!
  "Open block or page in right sidebar"
  [id]
  (-> ls/logseq
      (j/get :Editor)
      (j/call :openInRightSidebar id)))

(defn scroll-to-block-in-page!
  "Scroll to block in page"
  ([page-name block-id]
   (scroll-to-block-in-page! page-name block-id nil))
  ([page-name block-id opts]
   (-> ls/logseq
       (j/get :Editor)
       (j/call :scrollToBlockInPage page-name block-id (clj->js opts)))))

;; Block editing
(defn edit-block!
  "Edit block"
  ([block-id]
   (edit-block! block-id nil))
  ([block-id opts]
   (-> ls/logseq
       (j/get :Editor)
       (j/call :editBlock block-id (clj->js opts)))))

(defn select-block!
  "Select block"
  [block-id]
  (-> ls/logseq
      (j/get :Editor)
      (j/call :selectBlock block-id)))

;; Events
(defn on-input-selection-end!
  "Register input selection end handler"
  [callback]
  (-> ls/logseq
      (j/get :Editor)
      (j/call :onInputSelectionEnd callback)))