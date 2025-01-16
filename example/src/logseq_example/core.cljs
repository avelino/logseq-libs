(ns logseq-example.core
  (:require [run.avelino.logseq-libs.core :as ls]
            [run.avelino.logseq-libs.ui :as ls-ui]))

;; Provide model with some example functions
(ls/provide-model!
  {:openCalendar (fn [] 
                   (ls-ui/show-msg! "Calendar opened!"))
   :sayHello (fn [name]
               (ls-ui/show-msg! (str "Hello, " name "!")))})

;; Add some custom styles
(ls/provide-style! "
  .example-plugin { 
    background: #f0f0f0;
    padding: 1rem;
    border-radius: 4px;
  }")

(defn init []
  (ls/ready! 
    (fn []
      (ls-ui/show-msg! "Example plugin is ready!")))) 