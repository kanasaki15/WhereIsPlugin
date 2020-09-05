package xyz.n7mn.dev.whereisplugin.api;

@Deprecated
public class WhereIsDataDynmap {

    @Deprecated
    public WhereIsDataDynmap() throws DynmapNotFoundException {
        throw new DynmapNotFoundException();
    }
    @Deprecated
    public boolean addMarker (int DataID, String color) throws DynmapNotFoundException {
        return false;
    }

    @Deprecated
    public boolean addMarker(int DataID) throws DynmapNotFoundException {
        return addMarker(DataID, "#ff0000");
    }

    @Deprecated
    public boolean addMarker(String name) throws DynmapNotFoundException {
        return addMarker(new WhereIsData().getWhereData(name).getID());
    }

    @Deprecated
    public boolean delMarker(int DataID) throws DynmapNotFoundException {
        return false;
    }

    @Deprecated
    public boolean delMarker(String name) throws DynmapNotFoundException{

        return delMarker(new WhereIsData().getWhereData(name).getID());
    }

    @Deprecated
    public boolean updateMaker(int DataID) throws DynmapNotFoundException {
        return updateMarker(DataID);
    }

    @Deprecated
    public boolean updateMarker(int DataID) throws DynmapNotFoundException {
        return false;
    }

    @Deprecated
    public boolean updateMaker(String name) throws DynmapNotFoundException {

        return updateMarker(new WhereIsData().getWhereData(name).getID());
    }

    @Deprecated
    public boolean updateMarker(String name) throws DynmapNotFoundException {

        return updateMarker(new WhereIsData().getWhereData(name).getID());
    }

    @Deprecated
    public boolean isDataExists(int DataID){
        return false;
    }

    @Deprecated
    public void allDeleteMarker(){

    }
}
