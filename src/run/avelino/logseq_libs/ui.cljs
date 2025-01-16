(ns run.avelino.logseq-libs.ui
  "UI related functions for Logseq plugin API"
  (:require ["@logseq/libs" :as ls]
            [applied-science.js-interop :as j]))

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

(defn close-msg!
  "Close a notification message"
  [key]
  (-> ls/logseq
      (j/get :UI)
      (j/call :closeMsg key)))

(defn query-element-rect!
  "Query element rectangle by selector"
  [selector]
  (-> ls/logseq
      (j/get :UI)
      (j/call :queryElementRect selector)))

(defn check-slot-valid!
  "Check if a UI slot is valid"
  [slot]
  (-> ls/logseq
      (j/get :UI)
      (j/call :checkSlotValid slot))) 