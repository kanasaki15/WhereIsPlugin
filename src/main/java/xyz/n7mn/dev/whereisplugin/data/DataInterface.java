package xyz.n7mn.dev.whereisplugin.data;

import java.util.UUID;

interface DataInterface {

    boolean NewConnect();
    Data[] GetList(int x,int z);
    boolean SetName(int startX, int endX, int startZ, int endZ, String name, UUID uuid);
    boolean DelName(String name, UUID uuid);
    boolean UpdateName(String OldName , String NewName,UUID uuid);
    Data[] GetListAll();
    Data GetDataByID(int id);
}
