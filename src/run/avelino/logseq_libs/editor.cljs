(ns run.avelino.logseq-libs.editor
  "Editor related functions for Logseq plugin API"
  (:require ["@logseq/libs" :as ls]
            [applied-science.js-interop :as j])
  (:require-macros [run.avelino.logseq-libs.macros :refer [deflogseq-api]]))

;; Generate all Editor API functions using the macro
;; the imports [ls j] "will be" unused until the macro is executed
(deflogseq-api :Editor)
