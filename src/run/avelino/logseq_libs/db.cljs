(ns run.avelino.logseq-libs.db
  "Database related functions for Logseq plugin API"
  (:require ["@logseq/libs" :as ls]
            [applied-science.js-interop :as j])
  (:require-macros [run.avelino.logseq-libs.macros :refer [deflogseq-api]]))

;; Generate all DB API functions using the macro
;; the imports [ls j] "will be" unused until the macro is executed
(deflogseq-api :DB)

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