(ns build
  (:require [clojure.tools.build.api :as b]
            [deps-deploy.deps-deploy :as dd]
            [clojure.data.json :as json]))

(def lib 'run.avelino/logseq-libs)
(def version
  (-> (slurp "package.json")
      (json/read-str :key-fn keyword)
      :version))
(def class-dir "target/classes")
(def basis (delay (b/create-basis {:project "deps.edn"})))
(def jar-file (format "target/%s-%s.jar" (name lib) version))

(def pom-template
  [[:description "ClojureScript wrapper for @logseq/libs"]
   [:url "https://github.com/avelino/logseq-libs"]
   [:licenses
    [:license
     [:name "MIT License"]
     [:url "https://opensource.org/licenses/MIT"]]]
   [:scm
    [:url "https://github.com/avelino/logseq-libs"]
    [:connection "scm:git:https://github.com/avelino/logseq-libs.git"]
    [:developerConnection "scm:git:ssh:git@github.com:avelino/logseq-libs.git"]
    [:tag (str "v" version)]]])

(def options
  {:class-dir class-dir
   :lib lib
   :version version
   :basis @basis
   :jar-file jar-file
   :src-dirs ["src"]
   :pom-data pom-template})

(defn clean [_]
  (b/delete {:path "target"}))

(defn jar [_]
  (clean nil)
  (b/write-pom options)
  (b/copy-dir {:src-dirs (:paths @basis)
               :target-dir class-dir})
  (b/jar options))

(defn deploy [_]
  (jar nil)
  (dd/deploy {:installer :remote
              :artifact jar-file
              :pom-file (b/pom-path {:lib lib :class-dir class-dir})}))