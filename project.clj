(defproject run.avelino/logseq-libs "0.1.1-0.0.14"
  :url "https://github.com/avelino/logseq-libs"
  :description "@logseq/libs wrapper for cljs"
  :license {:name "MIT"
            :url "http://opensource.org/licenses/MIT"}
  :scm {:name "git" :url "https://github.com/avelino/logseq-libs"}

  :dependencies [[org.clojure/clojurescript "1.11.60"]]

  :npm-deps {"@logseq/libs" "0.0.14"}

  :plugins [[lein-cljsbuild "1.1.7"]
            [lein-codox "0.10.7"]]

  :codox {:language :clojurescript
          :source-paths ["src"]
          :doc-paths []}

  :source-paths ["src"])
