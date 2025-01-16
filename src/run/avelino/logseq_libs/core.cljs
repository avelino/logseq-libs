(ns run.avelino.logseq-libs.core
  "Core namespace for Logseq plugin API wrapper"
  (:require ["@logseq/libs" :as ls]
            [applied-science.js-interop :as j]))

;; Core initialization
(defn ready!
  "Initialize plugin and register model"
  ([callback]
   (-> ls/logseq
       (j/call :ready callback)))
  ([model callback]
   (-> ls/logseq 
       (j/call :ready model callback))))

;; Model provider
(defn provide-model!
  "Provide a model object to Logseq"
  [model]
  (-> ls/logseq
      (j/call :provideModel (clj->js model))))

;; Style provider
(defn provide-style!
  "Provide CSS styles to Logseq"
  [styles]
  (-> ls/logseq
      (j/call :provideStyle styles)))

;; UI provider
(defn provide-ui!
  "Provide UI elements to Logseq"
  [ui]
  (-> ls/logseq
      (j/call :provideUI (clj->js ui))))

;; Settings
(defn update-settings!
  "Update plugin settings"
  [settings]
  (-> ls/logseq
      (j/call :updateSettings (clj->js settings))))

;; Event handlers
(defn on-settings-changed!
  "Register settings change handler"
  [callback]
  (-> ls/logseq
      (j/call :onSettingsChanged callback)))

(defn before-unload!
  "Register before unload handler"
  [callback]
  (-> ls/logseq
      (j/call :beforeunload callback)))

;; Export functions for npm module
(def exports
  #js {:ready ready!
       :provideModel provide-model!
       :provideStyle provide-style!
       :provideUI provide-ui!
       :updateSettings update-settings!
       :onSettingsChanged on-settings-changed!
       :beforeUnload before-unload!}) 