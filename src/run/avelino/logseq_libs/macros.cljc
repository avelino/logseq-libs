(ns run.avelino.logseq-libs.macros
  "Macros for generating Logseq API wrapper functions"
  #?(:clj (:require [clojure.string :as str])
     :cljs (:require [clojure.string :as str]
                     [cljs.core :refer [js->clj]])))

(defn- camel->kebab
  "Convert camelCase to kebab-case"
  [s]
  (-> s
      (str/replace #"([a-z0-9])([A-Z])" "$1-$2")
      str/lower-case))

(defn- generate-docstring
  "Generate a detailed docstring for a Logseq API function"
  [ns-kw method-name]
  (let [fn-name (camel->kebab method-name)
        action (-> fn-name
                   (str/replace #"-" " ")
                   str/trim)]
    (str "Wrapper for Logseq " ns-kw " " method-name " method.\n"
         "Automatically converts JavaScript objects to ClojureScript data structures.\n"
         "Returns a promise that resolves to a ClojureScript data structure if the method is async.")))

(def editor-methods
  "Methods for the :Editor namespace"
  ["removeBlock" "insertBlock" "insertBatchBlock" "updateBlock" "getBlock"
   "getPage" "createPage" "deletePage" "renamePage" "getAllPages" "getPagesTreeData"
   "checkEditing" "exitEditingMode" "getEditingBlockContent" "restoreEditingCursor"
   "getEditingCursorPosition" "getEditingContentSlateValue" "getCurrentPage"
   "getCurrentBlock" "getSelectedBlocks" "openInRightSidebar" "scrollToBlockInPage"
   "editBlock" "selectBlock" "onInputSelectionEnd"])

(def db-methods
  "Methods for the :DB namespace"
  ["q" "datascriptQuery"])

(def git-methods
  "Methods for the :Git namespace"
  ["execCommand" "loadIgnoreFile" "saveIgnoreFile"])

(def ui-methods
  "Methods for the :UI namespace"
  ["closeMsg" "queryElementRect" "checkSlotValid"])

(defn discover-methods
  "Discover all methods in a Logseq namespace at compile time.
   Returns a vector of method names as strings.

   Example:
   (discover-methods :Editor)
   => [\"removeBlock\" \"insertBlock\" ...]"
  [ns-kw]
  ;; TODO: not working, needs a better solution
  ;; keeping commented to use again in the future
  #?(:cljs
     (let [api-obj (-> js/logseq (aget (name ns-kw)))]
       (->> (js/Object.getOwnPropertyNames api-obj)
            (filter #(fn? (aget api-obj %)))
            (remove #(or (str/starts-with? % "_")
                         (= "constructor" %)))
            ;; (into [])
            (mapv str)))
     :clj []))

(defmacro deflogseq-api
  "Define wrapper functions for Logseq API methods.
   Takes a namespace keyword (e.g. :Editor) and a vector of method names.
   Generates wrapper functions for all provided methods in the namespace.

   For each method, generates a ClojureScript wrapper function that:
   - Has a kebab-case name with ! suffix (e.g. removeBlock -> remove-block!)
   - Automatically converts between JS and CLJS data structures
   - Handles promises appropriately by converting results to CLJS data
   - Includes detailed documentation

   Example:
   (deflogseq-api :Editor [\"removeBlock\" \"insertBlock\"])

   Generates functions remove-block! and insert-block! that wrap
   the corresponding methods from logseq.Editor namespace."
  [ns-kw]
  (let [ns-sym (symbol (name ns-kw))
        ;; methods editor-methods
        methods (cond
                  (= ns-kw :Editor) editor-methods
                  (= ns-kw :DB) db-methods
                  (= ns-kw :Git) git-methods
                  (= ns-kw :UI) ui-methods
                  :else [])]
    `(do
       ~@(for [method methods
               :let [method-name method
                     fn-name (symbol (str (camel->kebab method-name) "!"))
                     docstring (generate-docstring ns-kw method-name)]]
           `(defn ~fn-name
              ~docstring
              [& args#]
              (let [result# (-> ls/logseq
                                (j/get ~ns-kw)
                                (j/apply ~(keyword method-name) (into-array args#)))]
                (if (instance? js/Promise result#)
                  (.then result# (fn [x#] (cljs.core/js->clj x# :keywordize-keys true)))
                  result#)))))))
