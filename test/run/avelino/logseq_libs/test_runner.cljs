(ns run.avelino.logseq-libs.test-runner
  (:require [cljs.test :refer-macros [run-tests]]
            [run.avelino.logseq-libs.core-test]
            [run.avelino.logseq-libs.editor-test]))

(defn ^:export run []
  (run-tests 'run.avelino.logseq-libs.core-test
             'run.avelino.logseq-libs.editor-test))
