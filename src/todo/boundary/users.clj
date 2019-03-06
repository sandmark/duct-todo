(ns todo.boundary.users
  (:require [clojure.java.jdbc :as jdbc]
            duct.database.sql))

(defprotocol Users)


(extend-protocol Users
  duct.database.sql.Boundary)
