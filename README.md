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
<pre>
/where
 -- 今いる位置の名前を取得する
/where axeget
 -- 範囲指定用の斧を取得する(追加/範囲更新共通)
/where axeclear
 -- ダイヤ斧による範囲指定解除(追加/範囲更新共通)
/where add [名前]
 -- ダイヤ斧で指定していない場合は現在位置を 開始位置 または 終了位置 にして指定した名前で登録する
    ダイヤ斧で指定している場合はダイヤ斧で指定している範囲で指定した名前で登録する
/where add [名前] [ワールド名] [開始X] [開始Z] [終了X] [終了Z]
 -- 指定したワールドや座標の範囲で登録する (プレーヤーからはワールド名は省略可能)
/where update [古い名前] [新しい名前]
 -- 古い名前から新しい名前に変更する
/where update [名前]
 -- ダイヤ斧で指定していない場合は現在位置を 開始位置 または 終了位置 に設定して指定した名前の新しい範囲にする
    ダイヤ斧で指定している場合はダイヤ斧で指定している範囲で指定した名前の新しい範囲にする

</pre>
## 設定ファイルの説明 (Ver 2.x)

## 他プラグインからの利用方法 (簡易)
[![](https://jitpack.io/v/kanasaki15/WhereIsPlugin.svg)](https://jitpack.io/#kanasaki15/WhereIsPlugin)

- plugin.ymlのdependに「WhereIsPlugin」を追加
- 上のJitPackリンクの書いてある内容に沿って外部ライブラリとして追加する
- xyz.n7mn.dev.whereisplugin.apiの中のクラスを必要に応じてインスタンス生成して使う