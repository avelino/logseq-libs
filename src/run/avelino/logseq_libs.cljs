(ns run.avelino.logseq-libs
  "Core namespace for Logseq plugin API wrapper"
  (:require ["@logseq/libs" :as ls]
            [applied-science.js-interop :as j]))

;; Core API functions
(defn ready!
  "Initialize plugin and register model"
  ([callback]
   (-> ls/logseq
       (j/call :ready callback)))
  ([model callback]
   (-> ls/logseq 
       (j/call :ready model callback))))

;; Editor namespace
(defn register-slash-command!
  "Register a new slash command"
  [name callback]
  (-> ls/logseq
      (j/get :Editor)
      (j/call :registerSlashCommand name callback)))

;; UI namespace  
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