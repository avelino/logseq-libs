(ns run.avelino.logseq_libs.core_test
  (:require [cljs.test :refer-macros [deftest is testing use-fixtures]]
            ["@logseq/libs" :as LSPlugin]))

;; Mock browser globals
(def global (js-obj))
(set! js/global global)
(set! js/self global)
(set! js/window global)
(set! js/document (js-obj))
(set! js/navigator (js-obj))
(set! js/localStorage (js-obj
                      "getItem" (fn [])
                      "setItem" (fn [])
                      "removeItem" (fn [])
                      "clear" (fn [])))

;; Mock variables to track function calls
(def mock-ready-called (atom nil))
(def mock-model-provided (atom nil))
(def mock-style-provided (atom nil))
(def mock-ui-provided (atom nil))
(def mock-settings-updated (atom nil))
(def mock-settings-changed (atom nil))
(def mock-before-unload (atom nil))

(defn reset-mocks! []
  (reset! mock-ready-called nil)
  (reset! mock-model-provided nil)
  (reset! mock-style-provided nil)
  (reset! mock-ui-provided nil)
  (reset! mock-settings-updated nil)
  (reset! mock-settings-changed nil)
  (reset! mock-before-unload nil))

(use-fixtures :each
  {:before reset-mocks!})

(deftest ready!-test
  (testing "ready! without model"
    (let [plugin (LSPlugin/setupPluginUserInstance #js {} #js {})]
      (.ready plugin)
      (is (some? @mock-ready-called))))

  (testing "ready! with model"
    (let [plugin (LSPlugin/setupPluginUserInstance #js {} #js {})]
      (.ready plugin #js {:model "test"})
      (is (some? @mock-ready-called)))))

(deftest provide-model!-test
  (let [plugin (LSPlugin/setupPluginUserInstance #js {} #js {})]
    (.provideModel plugin #js {:model "test"})
    (is (some? @mock-model-provided))))

(deftest provide-style!-test
  (let [plugin (LSPlugin/setupPluginUserInstance #js {} #js {})]
    (.provideStyle plugin #js {:style "test"})
    (is (some? @mock-style-provided))))

(deftest provide-ui!-test
  (let [plugin (LSPlugin/setupPluginUserInstance #js {} #js {})]
    (.provideUI plugin #js {:ui "test"})
    (is (some? @mock-ui-provided))))

(deftest update-settings!-test
  (let [plugin (LSPlugin/setupPluginUserInstance #js {} #js {})]
    (.updateSettings plugin #js {:settings "test"})
    (is (some? @mock-settings-updated))))

(deftest on-settings-changed!-test
  (let [plugin (LSPlugin/setupPluginUserInstance #js {} #js {})]
    (.onSettingsChanged plugin #js {:settings "test"})
    (is (some? @mock-settings-changed))))

(deftest before-unload!-test
  (let [plugin (LSPlugin/setupPluginUserInstance #js {} #js {})]
    (.beforeUnload plugin #js {:unload "test"})
    (is (some? @mock-before-unload)))) 