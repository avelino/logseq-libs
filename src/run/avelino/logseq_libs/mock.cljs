(ns run.avelino.logseq-libs.mock)

(defn ready! [& _])
(defn provide-model! [& _])
(defn provide-style! [& _])
(defn provide-ui! [& _])

(def exports
  #js {:ready ready!
       :provideModel provide-model!
       :provideStyle provide-style!
       :provideUI provide-ui!})