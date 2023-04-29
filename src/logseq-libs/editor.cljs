(ns logseq-libs/editor
  (:require ["@logseq/libs"]))

(def get-current-block js/logseq.Editor.getCurrentBlock)
(def get-editing-block-content js/logseq.Editor.getEditingBlockContent)
(def update-block js/logseq.Editor.updateBlock)
