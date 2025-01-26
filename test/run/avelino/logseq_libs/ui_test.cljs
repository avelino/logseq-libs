(ns run.avelino.logseq-libs.ui-test
  (:require [cljs.test :refer [deftest testing is use-fixtures async]]
            [run.avelino.logseq-libs.test-utils :as test-utils]
            [run.avelino.logseq-libs.mocks :as mocks]
            [run.avelino.logseq-libs.ui :as ui]
            ["@logseq/libs" :as ls]))

;; Mock @logseq/libs module
(set! js/LSPlugin mocks/LSPlugin)
(set! js/logseq mocks/logseq)

(def test-plugin (test-utils/create-test-plugin))

(use-fixtures :each
  {:before (fn [] (test-utils/clear-calls! test-plugin))})

(deftest show-msg-test
  (testing "show-msg! function"
    (testing "with content only"
      (with-redefs [ls/logseq (:plugin test-plugin)]
        (ui/show-msg! "Test message")
        (let [[content status opts] (test-utils/get-last-call test-plugin)]
          (is (= "Test message" content))
          (is (nil? status))
          (is (nil? opts)))))

    (testing "with content and status"
      (with-redefs [ls/logseq (:plugin test-plugin)]
        (ui/show-msg! "Test message" "success")
        (let [[content status opts] (test-utils/get-last-call test-plugin)]
          (is (= "Test message" content))
          (is (= "success" status))
          (is (nil? opts)))))

    (testing "with content, status and options"
      (with-redefs [ls/logseq (:plugin test-plugin)]
        (ui/show-msg! "Test message" "success" {:timeout 3000})
        (let [[content status opts] (test-utils/get-last-call test-plugin)]
          (is (= "Test message" content))
          (is (= "success" status))
          (is (test-utils/js-equal? #js {:timeout 3000} opts)))))))

(deftest close-msg-test
  (testing "close-msg! function"
    (with-redefs [ls/logseq (:plugin test-plugin)]
      (ui/close-msg! "msg-key")
      (let [[key] (test-utils/get-last-call test-plugin)]
        (is (= "msg-key" key))))))

(deftest query-element-rect-test
  (testing "query-element-rect! function"
    (async done
           (let [mock-rect #js {:top 0 :left 0 :width 100 :height 100}]
             (with-redefs [ls/logseq #js {:UI #js {:queryElementRect (fn [] (js/Promise.resolve mock-rect))}}]
               (-> (ui/query-element-rect! "#test-element")
                   (.then (fn [result]
                            (is (= {:top 0 :left 0 :width 100 :height 100} (js->clj result :keywordize-keys true)))
                            (done)))))))))

(deftest check-slot-valid-test
  (testing "check-slot-valid! function"
    (async done
           (let [mock-result true]
             (with-redefs [ls/logseq #js {:UI #js {:checkSlotValid (fn [] (js/Promise.resolve mock-result))}}]
               (-> (ui/check-slot-valid! "test-slot")
                   (.then (fn [result]
                            (is (= true result))
                            (done)))))))))