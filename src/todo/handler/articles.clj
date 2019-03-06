(ns todo.handler.articles
  (:require [ataraxy.response :as response]
            [integrant.core :as ig]))

(defmethod ig/init-key ::index [_ {:keys [db]}]
  (fn [request]
    [::response/ok "OK!"]))
