(ns run.avelino.logseq-libs.ui
  "UI related functions for Logseq plugin API"
  (:require ["@logseq/libs" :as ls]
            [applied-science.js-interop :as j])
  (:require-macros [run.avelino.logseq-libs.macros :refer [deflogseq-api]]))

;; Generate all UI API functions using the macro
;; the imports [ls j] "will be" unused until the macro is executed
(deflogseq-api :UI)

(defn show-msg!
  "Show a notification message"
  ([content]
   (show-msg! content nil nil))
  ([content status]
   (show-msg! content status nil))
  ([content status opts]
   (-> ls/logseq
       (j/get :UI)
       (j/call :showMsg content status (clj->js opts)))))