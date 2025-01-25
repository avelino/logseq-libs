(ns run.avelino.logseq-libs.utils
  (:require [clojure.string :as str]))

(defn format-path [path]
  (if path
    (-> path
        (str/replace #"^\./" "")
        (str/replace #"^/" "")
        (str/replace #"\\" "/")
        (str/replace #"/+" "/"))
    ""))

(defn join-paths [& paths]
  (let [formatted-paths (->> paths
                             (remove nil?)
                             (remove empty?)
                             (map format-path))]
    (if (seq formatted-paths)
      (format-path (str/join "/" formatted-paths))
      "")))