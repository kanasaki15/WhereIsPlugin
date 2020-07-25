package xyz.n7mn.dev.whereisplugin.resource;

public class WhereIsLnResource {

    public String commandHelp;
    public String commandWhere;
    public String commandWhereAdd;
    public String commandWhereDel;
    public String commandWhereUpdate;
    public String commandSystem;
    public String x1;
    public String x2;
    public String z1;
    public String z2;
    public String Name;
    public String OldName;
    public String NewName;
    public String Here1;
    public String Here2;
    public String NoName;
    public String AddSuccess;
    public String AddError;
    public String UpdateSuccess;
    public String UpdateError;
    public String DelSuccess;
    public String DelError;
    public String PermError;

    public WhereIsLnResource (String language){
        if (language.equals("ja")){
            commandHelp = "コマンドヘルプ";
            commandWhere = "現在位置の名前を取得する";
            commandWhereAdd = "指定した範囲の名前を設定する";
            commandWhereDel = "指定した範囲の名前を解除する";
            commandWhereUpdate = "指定した名前を新しい名前に変更する";
            commandSystem = "現在の実行環境を表示";
            x1 = "始点X座標";
            x2 = "終点X座標";
            z1 = "始点Z座標";
            z2 = "終点Z座標";
            Name = "名称";
            OldName = "旧名称";
            NewName = "新名称";
            Here1 = "ここは";
            Here2 = "です。";
            NoName = "名称未設定";
            AddSuccess = "X:{startx} Z:{startz} ～ X:{endx} Z:{endz} の範囲を「{name}」に設定しました。";
            AddError = "範囲は2ブロック以上にしてください。";
            UpdateSuccess = "「{oldname}」を「{newname}」に設定しました。";
            UpdateError = "その名前は登録されていないか他の人が登録した名前です。";
            DelSuccess = "「{name}」を削除しました。";
            DelError = "その名前は削除済みか登録されていないか他の人が登録した名前です。";
            PermError = "実行する権限がありません。";
        }
    }
}
