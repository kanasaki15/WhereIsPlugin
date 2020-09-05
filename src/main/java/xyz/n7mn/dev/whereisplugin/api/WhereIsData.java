package xyz.n7mn.dev.whereisplugin.api;

import org.bukkit.Location;

import java.util.*;

@Deprecated
public class WhereIsData {

    @Deprecated
    public WhereIsData(){

    }

    @Deprecated
    public WhereIsData(String MySQLServerName, String Username, String Password, String Database, String ConnectOption){

    }

    @Deprecated
    public List<WhereData> getDataListByALL(){
        return Collections.EMPTY_LIST;
    }

    @Deprecated
    public List<WhereData> getDataListByUser(UUID uuid){
        return Collections.EMPTY_LIST;
    }

    @Deprecated
    public List<WhereData> getWhereListByLocation(Location loc){
        return Collections.EMPTY_LIST;
    }

    @Deprecated
    public WhereData getWhereData(int id){
        return null;
    }

    @Deprecated
    public WhereData getWhereData(String name){
        return null;
    }

    @Deprecated
    public int getWhereDataID(String name){
        return -1;
    }

    @Deprecated
    public int getWhereDataID(String name, UUID uuid){
        return -1;
    }

    @Deprecated
    public boolean addWhereData(WhereData data){
        return false;
    }

    @Deprecated
    public boolean addWhereData(String name, UUID CreateUser, String WorldName, int StartX, int EndX, int StartZ, int EndZ, boolean Active){
        return addWhereData(new WhereData(0, name, CreateUser, WorldName, StartX, EndX, StartZ, EndZ, Active));
    }

    @Deprecated
    public boolean updateWhereData(WhereData data){
        return false;
    }

    @Deprecated
    public boolean updateWhereData(int id, String name, UUID CreateUser, String WorldName, int StartX, int EndX, int StartZ, int EndZ, boolean Active){
        return updateWhereData(new WhereData(id, name, CreateUser, WorldName, StartX, EndX, StartZ, EndZ, Active));
    }

    @Deprecated
    public boolean deleteWhereData(int id){
        WhereData whereData = this.getWhereData(id);
        whereData.setActive(false);

        return updateWhereData(whereData);
    }

    @Deprecated
    public boolean deleteWhereData(String name){
        WhereData whereData = this.getWhereData(name);
        whereData.setActive(false);
        return updateWhereData(whereData);
    }

    @Deprecated
    public boolean deleteWhereData(String name, UUID uuid){
        WhereData whereData = this.getWhereData(getWhereDataID(name, uuid));
        whereData.setActive(false);
        return updateWhereData(whereData);
    }

    @Deprecated
    public String getUseSystem(){
        return "Unknown";
    }

    @Deprecated
    public String getVersion(){
        return "Ver 2.0-final";
    }

    @Deprecated
    public void Close(){

    }

    @Deprecated
    public String getErrorMessage(){

        return "This API is deprecated.";
    }
}