(ns todo.boundary.users
  (:require [buddy.hashers :as hashers]
            [clojure.java.jdbc :as jdbc]
            duct.database.sql))

(defprotocol Users
  (create-user [db params])
  (find-user-by-email [db email])
  (authenticate-user [db params]))


(extend-protocol Users
  duct.database.sql.Boundary
  (create-user [{db :spec} {:keys [email password]}]
    (let [password-digest (hashers/derive password)
          result          (jdbc/insert! db :users
                                        {:email           email
                                         :password_digest password-digest})]
      (-> result first :id)))

  (find-user-by-email [{db :spec} email]
    (->> ["SELECT * FROM users WHERE email = ?" email]
         (jdbc/query db)
         first))

  (authenticate-user [db {:keys [email password]}]
    (if-let [user (find-user-by-email db email)]
      (when (->> user :password_digest (hashers/check password))
        (dissoc user :password_digest)))))
