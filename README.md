# WhereIsPlugin
## なんですの？
今いる位置の名前が設定されている場合結果を表示してくれるSpigotプラグイン

## 動作環境は？
Spigot 1.15以降<br>
MySQL 5.6以降のMySQLサーバー(必須ではない)

## jarでよこせ。
[配布場所](https://github.com/kanasaki15/WhereIsPlugin/releases)

## 簡易コマンド説明
<pre>
/where                            -- 今いる位置を取得
/where add [name] [x] [z] [x] [z] -- 指定した範囲の名前を設定
/where del [name]                 -- 指定した設定されている名前を解除する
/where update [OldName] [NewName] -- 指定した今までの名前から新しい名前にする
/where help                       -- コマンドヘルプ
/where system                     -- プラグインの動作状態 (要OP権限 or whereis.systemパーミッション所持)
/where admin [list or del] [id]   -- 登録されている一覧を表示 または 指定したIDのものを削除 (要OP権限 or whereis.adminパーミッション所持)
/where import                     -- MySQLへのインポート または ファイルへのインポート (Ver 1.1～、要OP権限 or whereis.adminパーミッション所持)
</pre>

## LuckPerm環境でのパーミッション設定
<pre>
whereis.check  -- /whereの権限
whereis.add    -- /where addの権限
whereis.del    -- /where delの権限
whereis.update -- /where updateの権限
whereis.system -- /where systemの権限
whereis.admin -- /where admin、/where import (Ver 1.1～)の権限
</pre>

## 独自イベント一覧 (Ver 1.1～)
<pre>
xyz.n7mn.dev.whereisplugin.event.WhereisExecuteCommandEvent -- コマンドを実行するときに発生(setCancelで実行自体をキャンセルできる。)
xyz.n7mn.dev.whereisplugin.event.WhereisCompleteCommandEvent -- コマンドを実行した後メッセージを表示するときに発生(setCancelで結果メッセージ表示をキャンセルできる。)
</pre>
Eventがどのタイミングで発生してなにが取得できるかのサンプルプラグインは下記から<br>
[WhereIsPluginEventTest-1.0.jar](https://n7mn.xyz/WhereIsPluginEventTest-1.0.jar) <br>
[上のソースコード](https://n7mn.xyz/WhereIsPluginEventTest.zip) (Gradleプロジェクト)

## 設定ファイル
<pre>
# WhereIsPlugin SettingFile

# Language
ln: 'ja' (言語設定、あるだけで今の所何を設定しても変わらない。)

# MySQL Server
mysqlServer: '' (MySQLの接続先。使わない場合は空白)
mysqlUser: '' (MySQLのユーザー名)
mysqlPassWord: '' (MySQLのパスワード)
mysqlDatabase: '' (MySQLのデータベース名)
</pre>
