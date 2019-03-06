(ns todo.test-utils
  (:require [clojure.java.jdbc :as jdbc]
            [duct.core.env :as e]
            [duct.database.sql :refer :all]))

(def db-spec
  {:connection (jdbc/get-connection
                {:connection-uri (e/env "TEST_DB_URL")})})


(def db (->Boundary db-spec))


(defn drop-all-tables []
  (let [table-names (->> ["SELECT relname FROM pg_stat_user_tables
                           WHERE relname <> 'ragtime_migrations'"]
                         (jdbc/query db-spec)
                         (map :relname))]
    (doseq [t table-names]
      (jdbc/delete! db-spec t []))))


(defn db-cleanup [f]
  (f)
  (drop-all-tables))
