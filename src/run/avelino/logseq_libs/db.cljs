(ns run.avelino.logseq-libs.db
  "Database related functions for Logseq plugin API"
  (:require ["@logseq/libs" :as ls]
            [applied-science.js-interop :as j]))

;; Query operations
(defn q!
  "Run a DSL query"
  [dsl]
  (-> ls/logseq
      (j/get :DB)
      (j/call :q dsl)
      (.then #(js->clj % :keywordize-keys true))))

(defn datascript-query!
  "Run a datascript query"
  [query & inputs]
  (-> ls/logseq
      (j/get :DB)
      (j/apply :datascriptQuery (clj->js (into [query] inputs)))
      (.then #(js->clj % :keywordize-keys true))))

;; Change subscriptions
(defn on-changed!
  "Subscribe to DB changes"
  [callback]
  (-> ls/logseq
      (j/get :DB)
      (j/call :onChanged
              (fn [blocks tx-data tx-meta]
                (callback (js->clj blocks :keywordize-keys true)
                         (js->clj tx-data :keywordize-keys true)
                         (js->clj tx-meta :keywordize-keys true))))))

(defn on-block-changed!
  "Subscribe to specific block changes"
  [uuid callback]
  (-> ls/logseq
      (j/get :DB)
      (j/call :onBlockChanged
              uuid
              (fn [block tx-data tx-meta]
                (callback (js->clj block :keywordize-keys true)
                         (js->clj tx-data :keywordize-keys true)
                         (js->clj tx-meta :keywordize-keys true)))))) 