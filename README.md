# duct-todo
[Clojure製WebフレームワークDuct入門](https://www.amazon.co.jp/Clojure製Webフレームワーク-Duct入門-ダックタイピングブックス-馬場-一樹-ebook/dp/B07J3KJZYS)の写経です。オリジナルソースコードは<https://github.com/kbaba1001/todo>にあります。

## Developing

### Dockerの準備
```sh
docker-compose build
docker-compose run repl lein run duct todo +api +ataraxy +postgres
sudo chown sandmark:docker todo/ -R
mv todo/* todo/.* .
rm -r todo/
```

### REPLの起動
```sh
docker-compose run --service-ports repl
```

### Setup

When you first clone this repository, run:

```sh
lein duct setup
```

This will create files for local configuration, and prep your system
for the project.

### Environment

To begin developing, start with a REPL.

```sh
lein repl
```

Then load the development environment.

```clojure
user=> (dev)
:loaded
```

Run `go` to prep and initiate the system.

```clojure
dev=> (go)
:duct.server.http.jetty/starting-server {:port 3000}
:initiated
```

By default this creates a web server at <http://localhost:3000>.

When you make changes to your source files, use `reset` to reload any
modified files and reset the server.

```clojure
dev=> (reset)
:reloading (...)
:resumed
```

### Testing

Testing is fastest through the REPL, as you avoid environment startup
time.

```clojure
dev=> (test)
...
```

But you can also run tests through Leiningen.

```sh
lein test
```

## Legal

Copyright © 2019 sandmark
