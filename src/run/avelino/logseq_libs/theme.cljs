(ns run.avelino.logseq-libs.theme
  "Theme related functions for Logseq plugin API"
  (:require ["@logseq/libs" :as ls]
            [applied-science.js-interop :as j]))

(defn register-theme!
  "Register a new theme"
  [id opt]
  (-> ls/logseq
      (j/get :Theme)
      (j/call :registerTheme id (clj->js opt))))

(defn unregister-theme!
  "Unregister a theme"
  ([id]
   (unregister-theme! id nil))
  ([id effect]
   (-> ls/logseq
       (j/get :Theme)
       (j/call :unregisterTheme id effect))))

(defn select-theme!
  "Select a theme"
  ([opt]
   (select-theme! opt nil))
  ([opt options]
   (-> ls/logseq
       (j/get :Theme)
       (j/call :selectTheme (clj->js opt) (clj->js options))))) 