(defproject todo "0.1.0"
  :description "An example todo application built with Duct"
  :url "https://github.com/sandmark/duct-todo/"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [duct/core "0.7.0"]
                 [duct/module.logging "0.4.0"]
                 [duct/module.web "0.7.0"]
                 [duct/module.ataraxy "0.3.0"]
                 [duct/module.sql "0.5.0"]
                 [duct/handler.sql "0.4.0"]
                 [duct/middleware.buddy "0.1.0"]
                 [org.postgresql/postgresql "42.2.5"]
                 [buddy/buddy-hashers "1.3.0"]
                 [funcool/struct "1.3.0"]]
  :plugins [[duct/lein-duct "0.11.2"]]
  :main ^:skip-aot todo.main
  :resource-paths ["resources" "target/resources"]
  :prep-tasks     ["javac" "compile" ["run" ":duct/compiler"]]
  :middleware     [lein-duct.plugin/middleware]
  :profiles
  {:dev          [:project/dev :profiles/dev]
   :repl         {:prep-tasks   ^:replace ["javac" "compile"]
                  :repl-options {:init-ns user
                                 :host    "0.0.0.0"
                                 :port    39998}
                  :plugins      [[cider/cider-nrepl "0.21.1"]
                                 [refactor-nrepl "2.4.0"]]}
   :uberjar      {:aot :all}
   :profiles/dev {}
   :project/dev  {:source-paths   ["dev/src"]
                  :resource-paths ["dev/resources"]
                  :dependencies   [[integrant/repl "0.3.1"]
                                   [eftest "0.5.7"]
                                   [kerodon "0.9.0"]
                                   [alembic "0.3.2"]
                                   [com.gearswithingears/shrubbery "0.4.1"]]
                  :plugins        [[lein-ancient "0.6.15"]]}})
