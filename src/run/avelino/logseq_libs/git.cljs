(ns run.avelino.logseq-libs.git
  "Git related functions for Logseq plugin API"
  (:require ["@logseq/libs" :as ls]
            [applied-science.js-interop :as j]))

(defn exec-command!
  "Execute a git command"
  [args]
  (-> ls/logseq
      (j/get :Git)
      (j/call :execCommand (clj->js args))
      (.then #(js->clj % :keywordize-keys true))))

(defn load-ignore-file!
  "Load git ignore file"
  []
  (-> ls/logseq
      (j/get :Git)
      (j/call :loadIgnoreFile)))

(defn save-ignore-file!
  "Save git ignore file"
  [content]
  (-> ls/logseq
      (j/get :Git)
      (j/call :saveIgnoreFile content))) 