(defproject run.avelino/logseq-libs "0.1.4-0.0.17"
  :url "https://github.com/avelino/logseq-libs"
  :description "@logseq/libs wrapper for cljs"
  :license {:url "http://opensource.org/licenses/MIT"
            :name "MIT"}
  :scm {:name "git" :url "https://github.com/avelino/logseq-libs"}

  :dependencies [[org.clojure/clojurescript "1.11.60"]]

  :npm-deps {"@logseq/libs" "0.0.17"
             :shadow-cljs "2.26.2"}
  :install-deps true

  :plugins [[lein-cljsbuild "1.1.8"]
            [lein-codox "0.10.8"]]

  :codox {:language :clojurescript
          :source-paths ["src"]
          :doc-paths []}

  :source-paths ["src"])
