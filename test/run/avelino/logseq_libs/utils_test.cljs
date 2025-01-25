(ns run.avelino.logseq-libs.utils-test
  (:require [cljs.test :refer [deftest testing is]]
            [run.avelino.logseq-libs.utils :as utils]))

(deftest format-path-test
  (testing "format-path function"
    (testing "removes leading dots and slashes"
      (is (= "path/to/file" (utils/format-path "./path/to/file")))
      (is (= "path/to/file" (utils/format-path "/path/to/file"))))

    (testing "normalizes backslashes to forward slashes"
      (is (= "path/to/file" (utils/format-path "path\\to\\file"))))

    (testing "handles nil input"
      (is (= "" (utils/format-path nil))))))

(deftest join-paths-test
  (testing "join-paths function"
    (testing "joins paths with forward slashes"
      (is (= "path/to/file" (utils/join-paths "path" "to" "file"))))

    (testing "filters out nil and empty paths"
      (is (= "path/file" (utils/join-paths "path" nil "" "file"))))

    (testing "formats each path segment"
      (is (= "path/to/file" (utils/join-paths "./path" "/to/" "/file"))))

    (testing "handles empty input"
      (is (= "" (utils/join-paths))))))