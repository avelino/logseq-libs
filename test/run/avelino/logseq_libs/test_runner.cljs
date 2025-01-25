(ns run.avelino.logseq-libs.test-runner
  (:require [cljs.test :refer [run-tests]]
            [run.avelino.logseq-libs.utils-test]
            [run.avelino.logseq-libs.core-test]))

(defn ^:export main []
  (run-tests
   'run.avelino.logseq-libs.utils-test
   'run.avelino.logseq-libs.core-test))