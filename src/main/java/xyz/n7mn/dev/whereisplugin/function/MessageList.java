package xyz.n7mn.dev.whereisplugin.function;

import org.bukkit.Location;

public class MessageList {

    private String CommandHelp;
    private String CommandWhere;
    private String CommandWhereAdd;
    private String CommandWhereDel;
    private String CommandWhereUpdate;
    private String CommandSystem;
    private String CommandAdmin;
    private String CommandImport;
    private String CommandList;
    private String StartX;
    private String EndX;
    private String StartZ;
    private String EndZ;
    private String Name;
    private String NoName;
    private String OldName;
    private String NewName;
    private String Here;
    private String AddSuccess;
    private String AddError;
    private String UpdateSuccess;
    private String UpdateError1;
    private String UpdateError2;
    private String DelSuccess;
    private String DelError1;
    private String DelError2;
    private String PermError;
    private String CommandSyntaxError;
    private String List;

    // Ver 1.3からのDynmap関係
    private String DynmapNotFound;
    private String DynmapAddSuccess;
    private String DynmapAddError;
    private String DynmapUpdateSuccess;
    private String DynmapUpdateError;
    private String DynmapDeleteSuccess;
    private String DynmapDeleteError;

    public MessageList(){

        String lang = Language.getLanguage();
        lang = "ja"; // 現時点では日本語しかないし。

        if (lang.equals("ja")){
            CommandHelp = "コマンドヘルプ";
            CommandWhere = "現在位置の名前を取得する";
            CommandWhereAdd = "指定した範囲の名前を設定する";
            CommandWhereDel = "指定した範囲の名前を解除する";
            CommandWhereUpdate = "指定した名前を新しい名前に変更する";
            CommandSystem = "現在の実行環境を表示";
            CommandAdmin = "現在の登録されている一覧を表示したり削除する";
            CommandImport = "ファイル -> MySQL または MySQL -> ファイルへのインポートをする";
            CommandList = "My登録リストを表示する";
            StartX = "始点X座標";
            EndX = "終点X座標";
            StartZ = "始点Z座標";
            EndZ = "終点Z座標";
            Name = "名称";
            OldName = "旧名称";
            NewName = "新名称";
            Here = "ここは[name]です。";
            NoName = "名称未設定";
            AddSuccess = "X:[startx] Z:[startz] ～ X:[endx] Z:[endz] の範囲を「[name]」に設定しました。";
            AddError = "範囲は2ブロック以上にしてください。";
            UpdateSuccess = "「[oldname]」を「[newname]」に設定しました。";
            UpdateError1 = "その名前は他の人が登録した名前です。";
            UpdateError2 = "その名前は登録されていません。";
            DelSuccess = "「[name]」を削除しました。";
            DelError1 = "その名前は他の人が登録した名前です。";
            DelError2 = "その名前は登録されていません。";
            PermError = "実行する権限がありません。";
            CommandSyntaxError = "構文エラーです。 /where helpを実行してヘルプを参照してください。";
            List = "My登録リスト";

            DynmapNotFound = "dynmapが導入されていない環境では使用できません。";
            DynmapAddSuccess = "dynmapに登録しました。";
            DynmapAddError = "dynmapに登録失敗しました。";
            DynmapUpdateSuccess = "dynmapの更新が完了しました。";
            DynmapUpdateError = "dynmapの更新に失敗しました。";
            DynmapDeleteSuccess = "dynmapの登録削除が完了しました。";
            DynmapDeleteError = "dynmapの登録削除に失敗しました。";
        }
    }

    public String getCommandHelpMessage() {
        return CommandHelp;
    }

    public String getCommandWhereMessage() {
        return CommandWhere;
    }

    public String getCommandWhereAddMessage() {
        return CommandWhereAdd;
    }

    public String getCommandWhereDelMessage() {
        return CommandWhereDel;
    }

    public String getCommandWhereUpdateMessage() {
        return CommandWhereUpdate;
    }

    public String getCommandSystemMessage() {
        return CommandSystem;
    }

    public String getCommandAdminMessage() {
        return CommandAdmin;
    }

    public String getCommandImportMessage() {
        return CommandImport;
    }

    public String getCommandListMessage() {
        return CommandList;
    }

    public String getStartXMessage() {
        return StartX;
    }

    public String getEndXMessage() {
        return EndX;
    }

    public String getStartZMessage() {
        return StartZ;
    }

    public String getEndZMessage() {
        return EndZ;
    }

    public String getNameMessage() {
        return Name;
    }

    public String getNoNameMessage() {
        return NoName;
    }

    public String getOldNameMessage() {
        return OldName;
    }

    public String getNewNameMessage() {
        return NewName;
    }

    public String getHereMessage(String LocationName) {

        if (LocationName == null){
            return Here.replaceAll("\\[name\\]",this.NoName);
        }

        if (LocationName.length() != 0){
            return Here.replaceAll("\\[name\\]",LocationName);
        } else {
            return Here.replaceAll("\\[name\\]",this.NoName);
        }

    }

    public String getAddSuccessMessage(int StartX, int EndX, int StartZ, int EndZ, String LocationName) {
        if (LocationName == null){
            LocationName = NoName;
        }
        if (LocationName != null && LocationName.length() == 0){
            LocationName = NoName;
        }
        return AddSuccess.replaceAll("\\[startx\\]",new String().valueOf(StartX)).replaceAll("\\[startz\\]",new String().valueOf(StartZ)).replaceAll("\\[endx\\]",new String().valueOf(EndX)).replaceAll("\\[endz\\]",new String().valueOf(EndZ)).replaceAll("\\[name\\]",LocationName);
    }

    public String getAddErrorMessage() {
        return AddError;
    }

    public String getUpdateSuccessMessage(String oldName, String newName) {

        if (oldName == null){
            oldName = "";
        }
        if (newName == null){
            newName = "";
        }

        return UpdateSuccess.replaceAll("\\[oldname\\]",oldName).replaceAll("\\[newname\\]",newName);
    }

    public String getUpdateUserErrorMessage() {
        return UpdateError1;
    }

    public String getUpdateNotFoundErrorMessage() {
        return UpdateError2;
    }

    public String getDelSuccess(String LocationName) {
        return DelSuccess.replaceAll("\\[name\\]",LocationName);
    }

    public String getDelUserErrorMessage() {
        return DelError1;
    }

    public String getDelNotFoundErrorMessage() {
        return DelError2;
    }

    public String getPermErrorMessage() {
        return PermError;
    }

    public String getCommandSyntaxError() {
        return CommandSyntaxError;
    }

    public String getListMessage() {
        return List;
    }

    public String getDynmapNotFound() {
        return DynmapNotFound;
    }

    public String getDynmapAddSuccess() {
        return DynmapAddSuccess;
    }

    public String getDynmapAddError() {
        return DynmapAddError;
    }

    public String getDynmapUpdateSuccess() {
        return DynmapUpdateSuccess;
    }

    public String getDynmapUpdateError() {
        return DynmapUpdateError;
    }

    public String getDynmapDeleteSuccess() {
        return DynmapDeleteSuccess;
    }

    public String getDynmapDeleteError() {
        return DynmapDeleteError;
    }
}
