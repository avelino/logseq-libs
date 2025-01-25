(ns run.avelino.logseq-libs.mocks)

(def ^:export LSPluginUser
  (js/Object.))

(set! (.-prototype LSPluginUser)
      #js {:ready (fn [])
           :provideModel (fn [])
           :provideStyle (fn [])
           :provideUI (fn [])})

(def logseq (LSPluginUser.))