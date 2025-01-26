(ns run.avelino.logseq-libs.test-utils
  (:require [cljs.test :refer [is]]))

;; Mock LSPluginCore
(def LSPluginCore
  #js {:baseInfo (fn [] #js {:id "test-plugin"})})

;; Mock @logseq/libs module
(def logseq-libs-mock
  #js {:LSPluginCore LSPluginCore})

;; Mock require for @logseq/libs
(when (exists? js/jest)
  (.mock js/jest "@logseq/libs" (fn [] logseq-libs-mock)))

(defn js-equal? [a b]
  (let [a-clj (js->clj a :keywordize-keys true)
        b-clj (js->clj b :keywordize-keys true)]
    (= (dissoc a-clj :callback) (dissoc b-clj :callback))))

(defn create-test-plugin []
  (let [calls (atom [])
        app-obj (js-obj)
        plugin-obj (js-obj)
        last-call (atom nil)]

    ;; Core methods
    (set! (.-ready plugin-obj)
          (fn [& args]
            (reset! last-call args)
            (swap! calls conj [:ready (vec args)])))

    (set! (.-provideModel plugin-obj)
          (fn [model]
            (reset! last-call [model])
            (swap! calls conj [:provide-model [(clj->js model)]])))

    (set! (.-provideStyle plugin-obj)
          (fn [style]
            (reset! last-call [style])
            (swap! calls conj [:provide-style [style]])))

    (set! (.-provideUI plugin-obj)
          (fn [ui]
            (reset! last-call [ui])
            (swap! calls conj [:provide-ui [(clj->js ui)]])))

    ;; Settings methods
    (set! (.-updateSettings plugin-obj)
          (fn [settings]
            (reset! last-call [settings])
            (swap! calls conj [:update-settings [(clj->js settings)]])))

    (set! (.-onSettingsChanged plugin-obj)
          (fn [callback]
            (reset! last-call [callback])
            (swap! calls conj [:on-settings-changed [callback]])))

    (set! (.-beforeUnload plugin-obj)
          (fn [callback]
            (reset! last-call [callback])
            (swap! calls conj [:before-unload [callback]])))

    (set! (.-useSettingsSchema plugin-obj)
          (fn [schema]
            (reset! last-call [schema])
            (swap! calls conj [:use-settings-schema [(clj->js schema)]])))

    ;; App methods
    (set! (.-registerCommandPalette app-obj)
          (fn [command]
            (let [cmd (clj->js command)]
              (reset! last-call [cmd])
              (swap! calls conj [:register-command-palette [cmd]]))))

    (set! (.-getUserConfigs app-obj)
          (fn [] (js/Promise.resolve #js {:preferredLanguage "en"})))

    ;; Set App object
    (set! (.-App plugin-obj) app-obj)

    {:calls calls
     :last-call last-call
     :plugin plugin-obj}))

(defn get-calls [test-plugin]
  @(:calls test-plugin))

(defn get-last-call [test-plugin]
  @(:last-call test-plugin))

(defn clear-calls! [test-plugin]
  (reset! (:calls test-plugin) [])
  (reset! (:last-call test-plugin) nil))

;; Export mock for use in tests
(def LSPlugin logseq-libs-mock)