(ns run.avelino.logseq-libs.assets
  "Assets related functions for Logseq plugin API"
  (:require ["@logseq/libs" :as ls]
            [applied-science.js-interop :as j]))

(defn list-files-of-current-graph!
  "List files of current graph"
  ([]
   (list-files-of-current-graph! nil))
  ([exts]
   (-> ls/logseq
       (j/get :Assets)
       (j/call :listFilesOfCurrentGraph (when exts (clj->js exts)))
       (.then #(js->clj % :keywordize-keys true)))))

(defn make-sandbox-storage!
  "Make sandbox storage"
  []
  (-> ls/logseq
      (j/get :Assets)
      (j/call :makeSandboxStorage)))

(defn make-url!
  "Make assets scheme url based on current graph"
  [path]
  (-> ls/logseq
      (j/get :Assets)
      (j/call :makeUrl path)))

(defn built-in-open!
  "Try to open asset type file in Logseq app"
  [path]
  (-> ls/logseq
      (j/get :Assets)
      (j/call :builtInOpen path))) 