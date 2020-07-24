package xyz.n7mn.dev.whereisplugin.data;

interface DataInterface {

    boolean NewConnect();
    Data[] GetList(int x,int z);
    boolean SetName(int startX, int endX,int startZ,int endZ,String name);
    boolean DelName(String name);
    boolean UpdateName(String OldName , String NewName);
}
