(ns todo.boundary.articles-test
  (:require [clojure.java.jdbc :as jdbc]
            [clojure.test :as t]
            [todo.boundary.articles :as sut]
            [todo.test-utils :as u]))

(t/use-fixtures :each u/db-cleanup)


(t/deftest boundary-articles-test
  (t/testing "create article"
    (let [id (sut/create-article u/db {:content "foo"})]
      (t/is (int? id)))))
