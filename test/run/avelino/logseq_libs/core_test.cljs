(ns run.avelino.logseq-libs.core-test
  (:require [cljs.test :refer [deftest testing is use-fixtures async]]
            [run.avelino.logseq-libs.test-utils :as test-utils]
            [run.avelino.logseq-libs.mocks :as mocks]
            [run.avelino.logseq-libs.core :as core]
            [goog.object :as gobj]))

;; Mock @logseq/libs module
(set! js/LSPlugin mocks/LSPlugin)
(set! js/logseq mocks/logseq)

(def test-plugin (test-utils/create-test-plugin))

(use-fixtures :each
  {:before (fn [] (test-utils/clear-calls! test-plugin))})

(deftest core-initialization-test
  (testing "ready! function"
    (testing "with callback only"
      (let [callback-called (atom false)
            callback #(reset! callback-called true)]
        (with-redefs [core/logseq (:plugin test-plugin)]
          (core/ready! callback)
          (is (= callback (first (test-utils/get-last-call test-plugin)))))))

    (testing "with model and callback"
      (let [callback-called (atom false)
            callback #(reset! callback-called true)
            model {:key "value"}]
        (with-redefs [core/logseq (:plugin test-plugin)]
          (core/ready! model callback)
          (let [[model-arg callback-arg] (test-utils/get-last-call test-plugin)]
            (is (= model model-arg))
            (is (= callback callback-arg))))))))

(deftest model-provider-test
  (testing "provide-model! function"
    (let [model {:key "value"}]
      (with-redefs [core/logseq (:plugin test-plugin)]
        (core/provide-model! model)
        (is (test-utils/js-equal?
             #js {:key "value"}
             (first (test-utils/get-last-call test-plugin))))))))

(deftest style-provider-test
  (testing "provide-style! function"
    (let [styles ".my-class { color: red; }"]
      (with-redefs [core/logseq (:plugin test-plugin)]
        (core/provide-style! styles)
        (is (= styles (first (test-utils/get-last-call test-plugin))))))))

(deftest ui-provider-test
  (testing "provide-ui! function"
    (let [ui {:type "div" :content "Hello"}]
      (with-redefs [core/logseq (:plugin test-plugin)]
        (core/provide-ui! ui)
        (is (test-utils/js-equal?
             #js {:type "div" :content "Hello"}
             (first (test-utils/get-last-call test-plugin))))))))

(deftest settings-test
  (testing "update-settings! function"
    (let [settings {:theme "dark"}]
      (with-redefs [core/logseq (:plugin test-plugin)]
        (core/update-settings! settings)
        (is (test-utils/js-equal?
             #js {:theme "dark"}
             (first (test-utils/get-last-call test-plugin))))))))

(deftest event-handlers-test
  (testing "on-settings-changed! function"
    (let [callback (fn [])]
      (with-redefs [core/logseq (:plugin test-plugin)]
        (core/on-settings-changed! callback)
        (is (= callback (first (test-utils/get-last-call test-plugin)))))))

  (testing "before-unload! function"
    (let [callback (fn [])]
      (with-redefs [core/logseq (:plugin test-plugin)]
        (core/before-unload! callback)
        (is (= callback (first (test-utils/get-last-call test-plugin))))))))

(deftest settings-schema-test
  (testing "use-settings-schema! function"
    (let [schema {:theme {:type "enum"
                          :default "light"
                          :title "Theme"
                          :description "Select theme"
                          :enumChoices ["light" "dark"]}}]
      (with-redefs [core/logseq (:plugin test-plugin)]
        (core/use-settings-schema! schema)
        (is (test-utils/js-equal?
             #js {:theme #js {:type "enum"
                              :default "light"
                              :title "Theme"
                              :description "Select theme"
                              :enumChoices #js ["light" "dark"]}}
             (first (test-utils/get-last-call test-plugin))))))))

(deftest user-configs-test
  (testing "get-user-configs! function"
    (async done
           (let [mock-configs #js {:preferredLanguage "en"
                                   :preferredDateFormat "yyyy-MM-dd"}]
             (with-redefs [core/logseq #js {:App #js {:getUserConfigs (fn [] (js/Promise.resolve mock-configs))}}]
               (-> (core/get-user-configs!)
                   (.then (fn [result]
                            (is (test-utils/js-equal? mock-configs result))
                            (done)))))))))

(deftest command-palette-test
  (testing "register-command-palette! function"
    (let [command {:key "test-command"
                   :label "Test Command"
                   :keybinding "mod+shift+p"
                   :callback (fn [])}]
      (with-redefs [core/logseq (:plugin test-plugin)]
        (core/register-command-palette! command)
        (let [registered-command (first (test-utils/get-last-call test-plugin))]
          (is (test-utils/js-equal? (dissoc command :callback) (js->clj registered-command :keywordize-keys true)))
          (is (fn? (gobj/get registered-command "callback"))))))))

(deftest exports-test
  (testing "exports object contains all required functions"
    (is (fn? (.-ready core/exports)))
    (is (fn? (.-provideModel core/exports)))
    (is (fn? (.-provideStyle core/exports)))
    (is (fn? (.-provideUI core/exports)))
    (is (fn? (.-updateSettings core/exports)))
    (is (fn? (.-onSettingsChanged core/exports)))
    (is (fn? (.-beforeUnload core/exports)))
    (is (fn? (.-useSettingsSchema core/exports)))
    (is (fn? (.-getUserConfigs core/exports)))
    (is (fn? (.-registerCommandPalette core/exports)))))