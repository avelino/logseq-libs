(ns run.avelino.logseq-libs.editor-test
  (:require [cljs.test :refer [deftest testing is use-fixtures async]]
            [run.avelino.logseq-libs.test-utils :as test-utils]
            [run.avelino.logseq-libs.mocks :as mocks]
            [run.avelino.logseq-libs.editor :as editor]
            ["@logseq/libs" :as ls]))

;; Mock @logseq/libs module
(set! js/LSPlugin mocks/LSPlugin)
(set! js/logseq mocks/logseq)

(def test-plugin (test-utils/create-test-plugin))

(use-fixtures :each
  {:before (fn [] (test-utils/clear-calls! test-plugin))})

;; Block operations tests
(deftest block-operations-test
  (testing "remove-block!"
    (async done
           (with-redefs [ls/logseq mocks/logseq-mock]
             (-> (editor/remove-block! "block-123")
                 (.then (fn [result]
                          (is (= {:ok true} (js->clj result :keywordize-keys true)))
                          (done)))))))

  (testing "insert-block!"
    (testing "with required params only"
      (async done
             (with-redefs [ls/logseq mocks/logseq-mock]
               (-> (editor/insert-block! "block-123" "content")
                   (.then (fn [result]
                            (is (= {:uuid "block-123" :content "content"} (js->clj result :keywordize-keys true)))
                            (done)))))))

    (testing "with optional params"
      (async done
             (with-redefs [ls/logseq mocks/logseq-mock]
               (-> (editor/insert-block! "block-123" "content" {:before true})
                   (.then (fn [result]
                            (is (= {:uuid "block-123" :content "content"} (js->clj result :keywordize-keys true)))
                            (done))))))))

  (testing "update-block!"
    (async done
           (with-redefs [ls/logseq mocks/logseq-mock]
             (-> (editor/update-block! "block-123" "new content")
                 (.then (fn [result]
                          (is (= {:uuid "block-123" :content "new content"} (js->clj result :keywordize-keys true)))
                          (done)))))))

  (testing "get-block!"
    (async done
           (with-redefs [ls/logseq mocks/logseq-mock]
             (-> (editor/get-block! "block-123")
                 (.then (fn [result]
                          (is (= {:uuid "block-123" :content "test content"} result))
                          (done))))))))

;; Page operations tests
(deftest page-operations-test
  (testing "get-page!"
    (async done
           (let [mock-page #js {:name "test-page" :properties #js {}}]
             (with-redefs [ls/logseq #js {:Editor #js {:getPage (fn [] (js/Promise.resolve mock-page))}}]
               (-> (editor/get-page! "test-page")
                   (.then (fn [result]
                            (is (= {:name "test-page" :properties {}} result))
                            (done))))))))

  (testing "create-page!"
    (testing "with name only"
      (async done
             (let [mock-page #js {:name "new-page"}]
               (with-redefs [ls/logseq #js {:Editor #js {:createPage (fn [] (js/Promise.resolve mock-page))}}]
                 (-> (editor/create-page! "new-page")
                     (.then (fn [result]
                              (is (= {:name "new-page"} result))
                              (done))))))))

    (testing "with properties"
      (async done
             (let [mock-page #js {:name "new-page" :properties #js {:tags ["test"]}}]
               (with-redefs [ls/logseq #js {:Editor #js {:createPage (fn [] (js/Promise.resolve mock-page))}}]
                 (-> (editor/create-page! "new-page" {:tags ["test"]})
                     (.then (fn [result]
                              (is (= {:name "new-page" :properties {:tags ["test"]}} result))
                              (done)))))))))

  (testing "delete-page!"
    (with-redefs [ls/logseq (:plugin test-plugin)]
      (editor/delete-page! "test-page")
      (let [[page-name] (test-utils/get-last-call test-plugin)]
        (is (= "test-page" page-name)))))

  (testing "rename-page!"
    (with-redefs [ls/logseq (:plugin test-plugin)]
      (editor/rename-page! "old-name" "new-name")
      (let [[old-name new-name] (test-utils/get-last-call test-plugin)]
        (is (= "old-name" old-name))
        (is (= "new-name" new-name)))))

  (testing "get-all-pages!"
    (async done
           (let [mock-pages #js [#js {:name "page1"} #js {:name "page2"}]]
             (with-redefs [ls/logseq #js {:Editor #js {:getAllPages (fn [] (js/Promise.resolve mock-pages))}}]
               (-> (editor/get-all-pages!)
                   (.then (fn [result]
                            (is (= [{:name "page1"} {:name "page2"}] result))
                            (done))))))))

  (testing "get-pages-tree-data!"
    (async done
           (let [mock-tree #js [{:name "root" :children #js []}]]
             (with-redefs [ls/logseq #js {:Editor #js {:getPagesTreeData (fn [] (js/Promise.resolve mock-tree))}}]
               (-> (editor/get-pages-tree-data!)
                   (.then (fn [result]
                            (is (= [{:name "root" :children []}] result))
                            (done)))))))))

;; Editing state tests
(deftest editing-state-test
  (testing "check-editing!"
    (with-redefs [ls/logseq (:plugin test-plugin)]
      (editor/check-editing!)
      (is (test-utils/was-called? test-plugin))))

  (testing "exit-editing-mode!"
    (with-redefs [ls/logseq (:plugin test-plugin)]
      (editor/exit-editing-mode!)
      (is (test-utils/was-called? test-plugin))))

  (testing "restore-editing-cursor!"
    (with-redefs [ls/logseq (:plugin test-plugin)]
      (editor/restore-editing-cursor!)
      (is (test-utils/was-called? test-plugin))))

  (testing "get-editing-block-content!"
    (async done
           (let [mock-content "test block content"]
             (with-redefs [ls/logseq #js {:Editor #js {:getEditingBlockContent (fn [] (js/Promise.resolve mock-content))}}]
               (-> (editor/get-editing-block-content!)
                   (.then (fn [result]
                            (is (= "test block content" result))
                            (done))))))))

  (testing "get-editing-cursor-position!"
    (async done
           (let [mock-position #js {:line 1 :ch 10}]
             (with-redefs [ls/logseq #js {:Editor #js {:getEditingCursorPosition (fn [] (js/Promise.resolve mock-position))}}]
               (-> (editor/get-editing-cursor-position!)
                   (.then (fn [result]
                            (is (= {:line 1 :ch 10} result))
                            (done))))))))

  (testing "get-editing-content-slate-value!"
    (async done
           (let [mock-value #js {:type "paragraph"}]
             (with-redefs [ls/logseq #js {:Editor #js {:getEditingContentSlateValue (fn [] (js/Promise.resolve mock-value))}}]
               (-> (editor/get-editing-content-slate-value!)
                   (.then (fn [result]
                            (is (= {:type "paragraph"} result))
                            (done)))))))))

;; Current state tests
(deftest current-state-test
  (testing "get-current-page!"
    (async done
           (let [mock-page #js {:name "current-page"}]
             (with-redefs [ls/logseq #js {:Editor #js {:getCurrentPage (fn [] (js/Promise.resolve mock-page))}}]
               (-> (editor/get-current-page!)
                   (.then (fn [result]
                            (is (= {:name "current-page"} result))
                            (done))))))))

  (testing "get-current-block!"
    (async done
           (let [mock-block #js {:uuid "current-block"}]
             (with-redefs [ls/logseq #js {:Editor #js {:getCurrentBlock (fn [] (js/Promise.resolve mock-block))}}]
               (-> (editor/get-current-block!)
                   (.then (fn [result]
                            (is (= {:uuid "current-block"} result))
                            (done))))))))

  (testing "get-selected-blocks!"
    (async done
           (let [mock-blocks #js [#js {:uuid "block1"} #js {:uuid "block2"}]]
             (with-redefs [ls/logseq #js {:Editor #js {:getSelectedBlocks (fn [] (js/Promise.resolve mock-blocks))}}]
               (-> (editor/get-selected-blocks!)
                   (.then (fn [result]
                            (is (= [{:uuid "block1"} {:uuid "block2"}] result))
                            (done)))))))))

;; Navigation tests
(deftest navigation-test
  (testing "open-in-right-sidebar!"
    (with-redefs [ls/logseq (:plugin test-plugin)]
      (editor/open-in-right-sidebar! "block-123")
      (let [[id] (test-utils/get-last-call test-plugin)]
        (is (= "block-123" id)))))

  (testing "scroll-to-block-in-page!"
    (testing "with required params only"
      (with-redefs [ls/logseq (:plugin test-plugin)]
        (editor/scroll-to-block-in-page! "page-name" "block-123")
        (let [[page-name block-id opts] (test-utils/get-last-call test-plugin)]
          (is (= "page-name" page-name))
          (is (= "block-123" block-id))
          (is (nil? opts)))))

    (testing "with optional params"
      (with-redefs [ls/logseq (:plugin test-plugin)]
        (editor/scroll-to-block-in-page! "page-name" "block-123" {:behavior "smooth"})
        (let [[page-name block-id opts] (test-utils/get-last-call test-plugin)]
          (is (= "page-name" page-name))
          (is (= "block-123" block-id))
          (is (test-utils/js-equal? #js {:behavior "smooth"} opts)))))))

;; Block editing tests
(deftest block-editing-test
  (testing "edit-block!"
    (testing "with block-id only"
      (with-redefs [ls/logseq (:plugin test-plugin)]
        (editor/edit-block! "block-123")
        (let [[block-id opts] (test-utils/get-last-call test-plugin)]
          (is (= "block-123" block-id))
          (is (nil? opts)))))

    (testing "with options"
      (with-redefs [ls/logseq (:plugin test-plugin)]
        (editor/edit-block! "block-123" {:pos 0})
        (let [[block-id opts] (test-utils/get-last-call test-plugin)]
          (is (= "block-123" block-id))
          (is (test-utils/js-equal? #js {:pos 0} opts))))))

  (testing "select-block!"
    (with-redefs [ls/logseq (:plugin test-plugin)]
      (editor/select-block! "block-123")
      (let [[block-id] (test-utils/get-last-call test-plugin)]
        (is (= "block-123" block-id))))))

;; Events tests
(deftest events-test
  (testing "on-input-selection-end!"
    (let [callback-called (atom false)
          callback #(reset! callback-called true)]
      (with-redefs [ls/logseq (:plugin test-plugin)]
        (editor/on-input-selection-end! callback)
        (is (= callback (first (test-utils/get-last-call test-plugin))))))))