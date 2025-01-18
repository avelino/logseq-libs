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
(def basis (b/create-basis {:project "deps.edn"}))
(def jar-file (format "target/%s-%s.jar" (name lib) version))
(def pom-file (format "target/classes/META-INF/maven/%s/pom.xml" lib))

(defn clean [_]
  (b/delete {:path "target"}))

(defn jar [_]
  (b/create-dirs {:path class-dir})
  (b/write-pom {:class-dir class-dir
                :lib lib
                :version version
                :basis basis
                :src-dirs ["src"]
                :scm {:url "https://github.com/avelino/logseq-libs"
                      :connection "scm:git:git://github.com/avelino/logseq-libs.git"
                      :developerConnection "scm:git:ssh://git@github.com/avelino/logseq-libs.git"
                      :tag version}
                :licenses [{:name "MIT"
                            :url "https://github.com/avelino/logseq-libs/blob/main/LICENSE"}]
                :description "ClojureScript wrapper for @logseq/libs"})
  (b/copy-dir {:src-dirs ["src" "resources"]
               :target-dir class-dir})
  (b/jar {:class-dir class-dir
          :jar-file jar-file}))

(defn deploy [_]
  (jar nil)  ; ensure jar is built with fresh POM
  (dd/deploy {:installer :remote
              :artifact jar-file
              :pom-file pom-file}))