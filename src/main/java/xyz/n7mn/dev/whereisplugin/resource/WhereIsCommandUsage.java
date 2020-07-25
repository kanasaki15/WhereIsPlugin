package xyz.n7mn.dev.whereisplugin.resource;

public class WhereIsCommandUsage {
    static public String Msg(String mode){
        WhereIsLnResource ln = new WhereIsLnResource("ja");
        if (mode.equals("add")){
            return "/where add <"+ln.x1+"> <"+ln.x2+"> <"+ln.z1+"> <"+ln.z2+"> <"+ln.Name+">";
        }
        if (mode.equals("del")){
            return "/where del <"+ln.Name+">";
        }
        if (mode.equals("update")){
            return "/where update <"+ln.OldName+"> <"+ln.NewName+">";
        }

        return "/where";
    }
}
