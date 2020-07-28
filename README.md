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
</pre>

## LuckPerm環境でのパーミッション設定
<pre>
whereis.check  -- /whereの権限
whereis.add    -- /where addの権限
whereis.del    -- /where delの権限
whereis.update -- /where updateの権限
whereis.system -- /where systemの権限
whereis.admin -- /where adminの権限
</pre>

## 独自イベント一覧
メッセージを独自のメッセージにしたい時に
<pre>
CheckWhereLocationEvent                       -- プレーヤーが/whereを実行した後に発生
CreateCompleteWhereLocationEvent              -- プレーヤーが/where addを実行した後に発生
DeleteCompleteWhereLocationEvent              -- プレーヤーが/where delを実行した後に発生
UpdateCompleteWhereLocationEvent              -- プレーヤーが/where updateを実行した後に発生
CreateCompleteWhereLocationEvent              -- プレーヤーが/where createを実行した後に発生

CheckWhereLocationServerCommandEvent          -- サーバーコンソールにて/whereを実行した後に発生
CreateCompleteWhereLocationServerCommandEvent -- サーバーコンソールにて/where addを実行した後に発生
DeleteCompleteWhereLocationServerCommandEvent -- サーバーコンソールにて/where delを実行した後に発生
UpdateCompleteWhereLocationServerCommandEvent -- サーバーコンソールにて/where updateを実行した後に発生
CreateCompleteWhereLocationServerCommandEvent -- サーバーコンソールにて/where createを実行した後に発生
</pre>
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
