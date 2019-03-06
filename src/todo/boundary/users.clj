(ns todo.boundary.users
  (:require [buddy.hashers :as hashers]
            [clojure.java.jdbc :as jdbc]
            duct.database.sql))

(defprotocol Users
  (create-user [db params]))


(extend-protocol Users
  duct.database.sql.Boundary
  (create-user [{db :spec} {:keys [email password]}]
    (let [password-digest (hashers/derive password)
          result          (jdbc/insert! db :users
                                        {:email           email
                                         :password_digest password-digest})]
      (-> result first :id))))
