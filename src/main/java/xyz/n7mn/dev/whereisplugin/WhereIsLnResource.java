package xyz.n7mn.dev.whereisplugin;

public class WhereIsLnResource {

    String commandHelp;
    String commandWhere;
    String commandWhereAdd;
    String commandWhereDel;
    String commandWhereUpdate;
    String x1;
    String x2;
    String z1;
    String z2;
    String Here1;
    String Here2;

    WhereIsLnResource (String language){
        if (language.equals("ja")){
            commandHelp = "コマンドヘルプ";
            commandWhere = "現在位置の名前を取得する";
            commandWhereAdd = "指定した範囲の名前を設定する";
            commandWhereDel = "指定した範囲の名前を解除する";
            commandWhereUpdate = "指定した名前を新しい名前に変更する";
            x1 = "始点X座標";
            x2 = "終点X座標";
            z1 = "始点Z座標";
            z2 = "終点Z座標";
            Here1 = "ここは";
            Here2 = "です。";
        }
    }
}
