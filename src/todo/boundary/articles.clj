(ns todo.boundary.articles
  (:require [clojure.java.jdbc :as jdbc]
            duct.database.sql))

(defprotocol Articles
  (index-articles [db])
  (create-article [db params]))


(extend-protocol Articles
  duct.database.sql.Boundary
  (index-articles [{db :spec}]
    (jdbc/query db ["SELECT * FROM articles"]))

  (create-article [{db :spec} {:keys [content]}]
    (let [result (jdbc/insert! db :articles {:content content})]
      (-> result first :id))))
