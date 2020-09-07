# WhereIsPlugin
[Ver 1.xのときの説明](https://github.com/kanasaki15/WhereIsPlugin/tree/master) <br>
Ver 1.x -> 2.0 ToDo (対応したものは取り消し線)
- 更新機能強化(名前だけではなく範囲も)
- 座標を指定しなくても追加などを可能にする
- <del>SQLite対応</del>

## プラグインの説明
- プレーヤーがいる位置の座標を取得し、予め設定した場所の範囲ならば場所名を表示するプラグイン
- 場所名はコマンドで追加が可能

## 必要なものは？
- Spigot 1.15以降 (PaperMCも可)
- MySQLサーバー (なければSQLiteで動作します)
- Luckperms 5.0以降 (なくても動作します)

## コマンドの説明 (Ver 2.x)

## 設定ファイルの説明 (Ver 2.x)

## 他プラグインからの利用方法 (簡易)
[![](https://jitpack.io/v/kanasaki15/WhereIsPlugin.svg)](https://jitpack.io/#kanasaki15/WhereIsPlugin)

- plugin.ymlのdependに「WhereIsPlugin」を追加
- 上のJitPackリンクの書いてある内容に沿って外部ライブラリとして追加する
- xyz.n7mn.dev.whereisplugin.apiの中のクラスを必要に応じてインスタンス生成して使う