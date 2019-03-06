(ns todo.handler.users
  (:require [ataraxy.response :as response]
            [buddy.sign.jwt :as jwt]
            [integrant.core :as ig]
            [struct.core :as st]
            [todo.boundary.users :as users]))

(defn- create-form-schema [db]
  (let [email-uniqueness {:message  "already used"
                          :optional true
                          :validate (fn [v db]
                                      (nil? (users/find-user-by-email db v)))}]
    {:email    [st/required st/email [email-uniqueness db]]
     :password [st/required st/string
                [st/min-count 8] [st/max-count 100]]}))


(defmethod ig/init-key ::create [_ {:keys [db]}]
  (fn [{:keys [body-params]}]
    (if-let [errors (->> (create-form-schema db) (st/validate body-params) first)]
      [::response/bad-request errors]
      (let [id (users/create-user db body-params)]
        [::response/created (str "/users/" id)]))))


(defmethod ig/init-key ::signin [_ {:keys [db jwt-secret]}]
  (fn [{:keys [body-params]}]
    (letfn [(with-token [user]
              (->> (jwt/sign (select-keys user [:email]) jwt-secret)
                   (assoc user :token)))]
      (if-let [user (users/authenticate-user db body-params)]
        [::response/ok {:user (with-token user)}]
        [::response/forbidden "Not authorized"]))))
