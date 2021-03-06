(ns todo.boundary.articles-test
  (:require [clojure.java.jdbc :as jdbc]
            [clojure.test :as t]
            [todo.boundary.articles :as sut]
            [todo.test-utils :as u]))

(t/use-fixtures :each u/db-cleanup)


(t/deftest boundary-articles-test
  (t/testing "create article"
    (let [id (sut/create-article u/db {:content "foo"})]
      (t/is (int? id))))

  (t/testing "index articles"
    (let [results (sut/index-articles u/db)]
      (t/is (= 1 (count results)))
      (t/is (= "foo" (-> results first :content))))))
