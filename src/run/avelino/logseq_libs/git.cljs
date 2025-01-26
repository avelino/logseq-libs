(ns run.avelino.logseq-libs.git
  "Git related functions for Logseq plugin API"
  (:require ["@logseq/libs" :as ls]
            [applied-science.js-interop :as j])
  (:require-macros [run.avelino.logseq-libs.macros :refer [deflogseq-api]]))

;; Generate all Git API functions using the macro
;; the imports [ls j] "will be" unused until the macro is executed
(deflogseq-api :Git)