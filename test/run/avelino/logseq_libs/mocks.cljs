(ns run.avelino.logseq-libs.mocks
  (:require [run.avelino.logseq-libs.test-utils :as test-utils]))

;; Re-export test utils mocks
(def LSPlugin test-utils/LSPlugin)
(def logseq test-utils/logseq-libs-mock)