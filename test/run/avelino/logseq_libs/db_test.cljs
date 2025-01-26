(ns run.avelino.logseq-libs.db-test
  (:require [cljs.test :refer [deftest testing is use-fixtures async]]
            [run.avelino.logseq-libs.test-utils :as test-utils]
            [run.avelino.logseq-libs.mocks :as mocks]
            [run.avelino.logseq-libs.db :as db]))

;; Mock @logseq/libs module
(set! js/LSPlugin mocks/LSPlugin)
(set! js/logseq mocks/logseq)

(def test-plugin (test-utils/create-test-plugin))

(use-fixtures :each
  {:before (fn [] (test-utils/clear-calls! test-plugin))})

(deftest test-q!
  (async done
         (let [dsl '[:find (pull ?b [*])
                     :where [?b :block/content]]]
           (-> (db/q! dsl)
               (.then (fn [result]
                        (is (= [{:id 1 :content "test"}] result))
                        (done)))))))

(deftest test-datascript-query!
  (async done
         (let [query '[:find ?b
                       :where [?b :block/content]]
               inputs ["param1" "param2"]]
           (-> (apply db/datascript-query! query inputs)
               (.then (fn [result]
                        (is (= [{:id 1 :result "test"}] result))
                        (done)))))))

(deftest test-on-changed!
  (let [callback-fn (fn [blocks tx-data tx-meta])]
    (db/on-changed! callback-fn)
    (is (fn? callback-fn))))

(deftest test-on-block-changed!
  (let [test-uuid "block-123"
        callback-fn (fn [block tx-data tx-meta])]
    (db/on-block-changed! test-uuid callback-fn)
    (is (fn? callback-fn))))