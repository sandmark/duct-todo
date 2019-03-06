# duct-todo
[Clojure製WebフレームワークDuct入門](https://www.amazon.co.jp/Clojure製Webフレームワーク-Duct入門-ダックタイピングブックス-馬場-一樹-ebook/dp/B07J3KJZYS)の写経です。オリジナルソースコードは<https://github.com/kbaba1001/todo>にあります。

## Developing

### プロジェクト準備時にしたこと
```sh
docker-compose build
docker-compose run repl lein run duct todo +api +ataraxy +postgres

sudo chown sandmark:docker todo/ -R
mv todo/* todo/.* .
rm -r todo/
```

### Setup

初めて `git clone` したときは以下のコマンドを実行。

```sh
docker-compose run repl lein duct setup
```

コード管理に含まれない `local` 設定ファイルが作られるので、自分の環境に合わせて変えること。

### Environment

開発するときは以下のコマンドでREPLを起動する。

```sh
docker-compose run --service-ports repl
```

それから `dev` environmentをロード。

```clojure
user=> (dev)
:loaded
```

`go` でシステムを準備・起動できる。

```clojure
dev=> (go)
:duct.server.http.jetty/starting-server {:port 3000}
:initiated
```

デフォルトでは <http://localhost:3000> でListenするようになってるけど、 `docker-compose.yml` で変更できるよ。

ソースコードを変更したら `reset` でファイルをリロードして、サーバをリセットすること。

```clojure
dev=> (reset)
:reloading (...)
:resumed
```

### Testing

環境をロードする時間がないぶん、REPLからテストするのが一番速い。

```clojure
dev=> (test)
...
```

でもLeiningenから実行することもできる。

```sh
lein test
```

## Legal

Copyright © 2019 sandmark
