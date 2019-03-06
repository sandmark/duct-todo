(ns todo.handler.users
  (:require [ataraxy.response :as response]
            [buddy.sign.jwt :as jwt]
            [integrant.core :as ig]
            [todo.boundary.users :as users]))

(defmethod ig/init-key ::create [_ {:keys [db]}]
  (fn [{:keys [body-params]}]
    (let [id (users/create-user db body-params)]
      [::response/created (str "/users/" id)])))


(defmethod ig/init-key ::signin [_ {:keys [db jwt-secret]}]
  (fn [{:keys [body-params]}]
    (letfn [(with-token [user]
              (->> (jwt/sign (select-keys user [:email]) jwt-secret)
                   (assoc user :token)))]
      (if-let [user (users/authenticate-user db body-params)]
        [::response/ok {:user (with-token user)}]
        [::response/forbidden "Not authorized"]))))
