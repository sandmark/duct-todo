(ns todo.handler.articles-test
  (:require [clojure.test :as t]
            [integrant.core :as ig]
            [shrubbery.core :as shrubbery]
            [todo.boundary.articles :as articles]
            [todo.handler.articles :as sut]))

(def database-stub
  (shrubbery/stub articles/Articles
                  {:index-articles [{:id 1 :content "foo"}]
                   :create-article 1}))


(t/deftest handler-articles-test
  (t/testing "GET /articles"
    (let [handler          (ig/init-key ::sut/index {:db database-stub})
          [status message] (handler {})]
      (t/is (= :ataraxy.response/ok status))
      (t/is (= [{:id 1 :content "foo"}]
               message))))

  (t/testing "POST /articles"
    (let [handler          (ig/init-key ::sut/create {:db database-stub})
          [status message] (handler {})]
      (t/is (= :ataraxy.response/created status))
      (t/is (= "/articles/1" message)))))
