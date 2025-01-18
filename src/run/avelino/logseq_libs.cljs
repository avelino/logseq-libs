(ns run.avelino.logseq-libs
  "Core namespace for Logseq plugin API wrapper"
  (:require ["@logseq/libs" :refer [LSPluginCore] :as LSPlugin]
            [run.avelino.logseq-libs.core :as core]))

(def logseq js/logseq)

;; Re-export core functions
(def ready! core/ready!)
(def provide-model! core/provide-model!)
(def provide-style! core/provide-style!)
(def provide-ui! core/provide-ui!)
(def update-settings! core/update-settings!)
(def on-settings-changed! core/on-settings-changed!)
(def before-unload! core/before-unload!)
(def use-settings-schema! core/use-settings-schema!)
(def get-user-configs! core/get-user-configs!)
(def register-command-palette! core/register-command-palette!)

;; Editor namespace
(defn register-slash-command!
  "Register a new slash command"
  [name callback]
  (-> logseq
      .-Editor
      (.registerSlashCommand name callback)))

;; UI namespace
(defn show-msg!
  "Show a notification message"
  ([content]
   (show-msg! content nil nil))
  ([content status]
   (show-msg! content status nil))
  ([content status opts]
   (-> logseq
       .-UI
       (.showMsg content status (clj->js opts)))))