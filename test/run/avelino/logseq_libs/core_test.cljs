(ns run.avelino.logseq-libs.core-test
  (:require [cljs.test :refer [deftest testing is use-fixtures]]
            [run.avelino.logseq-libs.test-utils :as test-utils]))

(def test-plugin (test-utils/create-test-plugin))

(use-fixtures :each
  {:before (fn [] (test-utils/clear-calls! test-plugin))})

(deftest plugin-interface-test
  (testing "Plugin interface"
    (let [^js plugin (:plugin test-plugin)]
      (testing "has required methods"
        (is (fn? (.-ready plugin)))
        (is (fn? (.-provideModel plugin)))
        (is (fn? (.-provideStyle plugin)))
        (is (fn? (.-provideUI plugin))))

      (testing "methods record calls"
        (.ready plugin "test-arg")
        (is (= [[:ready ["test-arg"]]]
               (test-utils/get-calls test-plugin)))

        (.provideModel plugin "model-arg")
        (is (= [[:ready ["test-arg"]]
                [:provide-model ["model-arg"]]]
               (test-utils/get-calls test-plugin)))

        (.provideStyle plugin "style-arg")
        (is (= [[:ready ["test-arg"]]
                [:provide-model ["model-arg"]]
                [:provide-style ["style-arg"]]]
               (test-utils/get-calls test-plugin)))

        (.provideUI plugin "ui-arg")
        (is (= [[:ready ["test-arg"]]
                [:provide-model ["model-arg"]]
                [:provide-style ["style-arg"]]
                [:provide-ui ["ui-arg"]]]
               (test-utils/get-calls test-plugin)))))))