(ns run.avelino.logseq-libs.test-runner
  (:require [cljs.test :refer-macros [run-tests]]
            [run.avelino.logseq-libs.core-test]
            [run.avelino.logseq-libs.editor-test]
            [run.avelino.logseq-libs.ui-test]
            #_[run.avelino.logseq-libs.db-test]))

(defn ^:export run []
  (run-tests 'run.avelino.logseq-libs.core-test
             'run.avelino.logseq-libs.editor-test
             'run.avelino.logseq-libs.ui-test
             ;; TODO: fix issue with db-test
             #_'run.avelino.logseq-libs.db-test))
