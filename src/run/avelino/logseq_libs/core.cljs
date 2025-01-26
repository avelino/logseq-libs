(ns run.avelino.logseq-libs.core
  "Core namespace for Logseq plugin API wrapper"
  (:require ["@logseq/libs" :refer [LSPluginCore] :as LSPlugin]))

(def logseq js/logseq)

;; Core initialization
(defn ready!
  "Initialize plugin and register model"
  ([callback]
   (-> logseq
       (.ready callback)))
  ([model callback]
   (-> logseq
       (.ready model callback))))

;; Model provider
(defn provide-model!
  "Provide a model object to Logseq"
  [model]
  (-> logseq
      (.provideModel (clj->js model))))

;; Style provider
(defn provide-style!
  "Provide CSS styles to Logseq"
  [styles]
  (-> logseq
      (.provideStyle styles)))

;; UI provider
(defn provide-ui!
  "Provide UI elements to Logseq"
  [ui]
  (-> logseq
      (.provideUI (clj->js ui))))

;; Settings
(defn update-settings!
  "Update plugin settings"
  [settings]
  (-> logseq
      (.updateSettings (clj->js settings))))

;; Event handlers
(defn on-settings-changed!
  "Register settings change handler"
  [callback]
  (-> logseq
      (.onSettingsChanged callback)))

(defn before-unload!
  "Register before unload handler"
  [callback]
  (-> logseq
      (.beforeUnload callback)))

;; Settings Schema
(defn use-settings-schema!
  "Define the settings schema for the plugin.
   The schema should be a map of setting keys to their configurations.
   Each configuration can include:
   - type: The type of setting (string, number, boolean, enum, etc.)
   - default: The default value
   - title: Display title for the setting
   - description: Description of what the setting does
   - enumChoices: For enum type, the possible choices
   Example:
   {:theme {:type :enum
           :default \"light\"
           :title \"Theme\"
           :description \"Select the theme for the plugin\"
           :enumChoices [\"light\" \"dark\"]}}"
  [schema]
  (-> logseq
      (.useSettingsSchema (clj->js schema))))

;; User Configs
(defn get-user-configs!
  "Get Logseq user configurations.
   Returns a promise that resolves to a map containing user configs like:
   - :preferred-language - User's preferred language
   - :preferred-date-format - User's preferred date format
   - :preferred-workflow - User's preferred workflow
   - :preferred-theme - User's preferred theme
   - :preferred-format - User's preferred format (markdown/org)
   And many other Logseq app configurations."
  []
  (-> logseq
      .-App
      (.getUserConfigs)))

;; Command Palette
(defn register-command-palette!
  "Register a command in the Logseq command palette.
   The command configuration should include:
   - :key - Unique identifier for the command
   - :label - Display label in the command palette
   - :keybinding - Optional keyboard shortcut (e.g. 'mod+shift+p')
   - :callback - Function to execute when command is triggered
   Example:
   {:key \"plugin-command\"
    :label \"My Plugin Command\"
    :keybinding \"mod+shift+p\"
    :callback #(js/console.log \"Command executed!\")}"
  [command]
  (-> logseq
      .-App
      (.registerCommandPalette
       (clj->js command))))

;; Export functions for npm module
(def exports
  #js {:ready ready!
       :provideModel provide-model!
       :provideStyle provide-style!
       :provideUI provide-ui!
       :updateSettings update-settings!
       :onSettingsChanged on-settings-changed!
       :beforeUnload before-unload!
       :useSettingsSchema use-settings-schema!
       :getUserConfigs get-user-configs!
       :registerCommandPalette register-command-palette!})

;; Export all functions for ClojureScript usage
(def register-command-palette register-command-palette!)
(def get-user-configs get-user-configs!)
(def use-settings-schema use-settings-schema!)