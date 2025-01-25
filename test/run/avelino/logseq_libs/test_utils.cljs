(ns run.avelino.logseq-libs.test-utils)

(defn create-test-plugin []
  (let [calls (atom [])
        plugin-obj (js-obj)]
    (set! (.-ready plugin-obj)
          (fn [& args] (swap! calls conj [:ready args])))
    (set! (.-provideModel plugin-obj)
          (fn [& args] (swap! calls conj [:provide-model args])))
    (set! (.-provideStyle plugin-obj)
          (fn [& args] (swap! calls conj [:provide-style args])))
    (set! (.-provideUI plugin-obj)
          (fn [& args] (swap! calls conj [:provide-ui args])))
    {:calls calls
     :plugin plugin-obj}))

(defn get-calls [test-plugin]
  @(:calls test-plugin))

(defn clear-calls! [test-plugin]
  (reset! (:calls test-plugin) []))