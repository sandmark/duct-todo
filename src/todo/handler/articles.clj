(ns todo.handler.articles
  (:require [ataraxy.response :as response]
            [clojure.java.jdbc :as jdbc]
            [integrant.core :as ig]))

(defmethod ig/init-key ::index [_ {:keys [db]}]
  (fn [request]
    (let [articles (jdbc/query (:spec db)
                               ["SELECT * FROM articles"])]
      [::response/ok articles])))
