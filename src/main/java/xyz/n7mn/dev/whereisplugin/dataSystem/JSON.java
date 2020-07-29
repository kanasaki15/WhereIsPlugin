package xyz.n7mn.dev.whereisplugin.dataSystem;

import com.google.gson.Gson;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import xyz.n7mn.dev.whereisplugin.WhereIsPlugin;
import xyz.n7mn.dev.whereisplugin.dataSystem.Result.DataResult;
import xyz.n7mn.dev.whereisplugin.dataSystem.Result.JsonResult;
import xyz.n7mn.dev.whereisplugin.function.MessageList;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JSON implements DataSystemInterface {

    final private String version = "1.1";
    final private String systemName = "File (JSON)";

    private String FilePass;
    private boolean isConnect = false;

    public JSON(WhereIsPlugin plugin){
        FilePass = "./" + plugin.getDataFolder().toString() + "/DataList.json";
        if (System.getProperty("os.name").toLowerCase().startsWith("windows")){
            FilePass = FilePass.replaceAll("/", "\\\\");
        }

        File file = new File(FilePass);
        file.setWritable(false);
        try {
            FileReader filereader = new FileReader(file);
            filereader.close();
            file.setWritable(true);
            isConnect = true;
        } catch (FileNotFoundException e) {
            try {
                file.createNewFile();
                isConnect = writeFile("[]").isError();
            } catch (IOException ioException) {
                isConnect = false;
            }
        } catch (IOException e) {
            isConnect = false;
            // e.printStackTrace();
        } finally {
            file.setWritable(true);
        }
    }

    @Override
    public List<DataResult> getAllList() {

        if (!isConnect){
            return new ArrayList<>();
        }

        String JsonData = getFileJSON();
        // System.out.println("Debug : " + JsonData);

        Gson gson = new Gson();
        JsonResult[] result = gson.fromJson(JsonData, JsonResult[].class);

        List<DataResult> list = new ArrayList<>();

        for (int i = 0; i < result.length; i++){
            DataResult data = new DataResult();
            data.ID = result[i].getID();
            data.LocationName = result[i].getLocationName();
            data.UUID = result[i].getUuid();
            data.StartX = result[i].getStartX();
            data.EndX = result[i].getEndX();
            data.StartZ = result[i].getStartZ();
            data.EndZ = result[i].getEndZ();
            data.Active = result[i].isActive();

            list.add(data);

            // System.out.println("Debug : " + data.ID);
            // System.out.println("Debug : " + data.LocationName);
            // System.out.println("Debug : " + data.UUID.toString());
            // System.out.println("Debug : " + data.StartX);
            // System.out.println("Debug : " + data.EndX);
            // System.out.println("Debug : " + data.StartZ);
            // System.out.println("Debug : " + data.EndZ);

        }

        return list;
    }

    @Override
    public List<DataResult> getListBySearch(String word) {
        List<DataResult> list = getAllList();
        for (int i = 0; i < list.size(); i++){
            if (list.get(i).LocationName.equals(word)){
                continue;
            }

            list.remove(i);
        }

        return list;
    }

    @Override
    public DataResult getLocationName(int ID) {
        List<DataResult> list = getAllList();

        for (int i = 0; i < list.size(); i++){
            if (list.get(i).ID == ID){
                return list.get(i);
            }
        }

        DataResult result = new DataResult();
        result.LocationName = "";

        return result;
    }

    @Override
    public List<DataResult> getLocationName(int X, int Z) {
        List<DataResult> list = getAllList();

        for (int i = 0; i < list.size(); i++){
            if (list.get(i).StartX <= X && X <= list.get(i).EndX && list.get(i).StartZ <= Z && Z <= list.get(i).EndZ && list.get(i).Active){
                continue;
            }

            if (list.get(i).StartX >= X && X >= list.get(i).EndX && list.get(i).StartZ >= Z && Z >= list.get(i).EndZ && list.get(i).Active) {
                continue;
            }

            list.remove(i);
        }

        return list;
    }

    @Override
    public List<DataResult> getLocationName(Location loc) {
        return getLocationName(loc.getBlockX(), loc.getBlockZ());
    }

    @Override
    public List<DataResult> getLocationName(Player player) {
        return getLocationName(player.getLocation());
    }

    @Override
    public DataSystemResult addList(DataResult newData) {

        if (!isConnect){
            return new DataSystemResult(new MessageList().getAddErrorMessage(), true);
        }

        List<DataResult> list = getAllList();
        List<JsonResult> NewList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++){
            NewList.add(new JsonResult(list.get(i).ID, list.get(i).LocationName, list.get(i).UUID, list.get(i).StartX, list.get(i).EndX, list.get(i).StartZ, list.get(i).EndZ, list.get(i).Active));
        }
        NewList.add(new JsonResult(list.size() + 1, newData.LocationName, newData.UUID, newData.StartX, newData.EndX, newData.StartZ, newData.EndZ, true));


        String json = new Gson().toJson(NewList);
        // System.out.println(json);

        return writeFile(json);
    }

    @Override
    public DataSystemResult updateData(int ID, String NewName, UUID uuid) {

        if (!isConnect){
            return new DataSystemResult(new MessageList().getUpdateUserErrorMessage(), true);
        }

        List<DataResult> list = getAllList();

        for (int i = 0; i < list.size(); i++){
            if (list.get(i).Active && list.get(i).ID == ID && list.get(i).UUID.toString().equals(uuid.toString())){
                list.get(i).LocationName = NewName;
                return writeFile(new Gson().toJson(list));
            }
        }

        return new DataSystemResult(new MessageList().getUpdateUserErrorMessage(),true);
    }

    @Override
    public DataSystemResult updateData(String OldName, String NewName, UUID uuid) {
        List<DataResult> list = getAllList();

        for (int i = 0; i < list.size(); i++){
            if (list.get(i).Active && list.get(i).LocationName.equals(OldName) && list.get(i).UUID.toString().equals(uuid.toString())){
                return updateData(list.get(i).ID, NewName, uuid);
            }
        }

        return new DataSystemResult(new MessageList().getUpdateUserErrorMessage(),true);
    }

    @Override
    public DataSystemResult deleteData(int ID, UUID uuid) {

        if (!isConnect){
            return new DataSystemResult(new MessageList().getDelUserErrorMessage(), true);
        }

        List<DataResult> list = getAllList();

        List<JsonResult> jsonList = new ArrayList<>();

        for (int i = 0; i < list.size(); i++){
            jsonList.add(new JsonResult(list.get(i).ID, list.get(i).LocationName, list.get(i).UUID, list.get(i).StartX, list.get(i).EndX, list.get(i).StartZ, list.get(i).EndZ, list.get(i).Active));
        }

        for (int i = 0; i < jsonList.size(); i++){
            if (jsonList.get(i).getID() == ID && jsonList.get(i).getUuid().toString().equals(uuid.toString())){

                if (jsonList.get(i).isActive()){
                    jsonList.get(i).setActive(false);
                    return writeFile(new Gson().toJson(jsonList));
                }else{
                    return new DataSystemResult(new MessageList().getDelNotFoundErrorMessage(),true);
                }
            }
        }

        return new DataSystemResult(new MessageList().getDelUserErrorMessage(),true);
    }

    @Override
    public DataSystemResult deleteData(String Name, UUID uuid) {
        List<DataResult> list = getAllList();

        for (int i = 0; i < list.size(); i++){
            if (list.get(i).LocationName.equals(Name)){
                return deleteData(list.get(i).ID, uuid);
            }
        }

        return new DataSystemResult(new MessageList().getDelUserErrorMessage(),true);
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public String getDataSystemName() {
        return systemName;
    }

    @Override
    public boolean isConnect() {
        return isConnect;
    }

    private String getFileJSON(){
        if (!isConnect){ return "[]"; }

        BufferedReader buffer = null;
        File file = new File(FilePass);
        try {
            FileInputStream input = new FileInputStream(file);
            InputStreamReader stream = new InputStreamReader(input, "UTF-8");
            buffer = new BufferedReader(stream);
            file.setWritable(false);
            StringBuffer sb = new StringBuffer();
            int ch = buffer.read();
            while (ch != -1) {
                sb.append((char) ch);
                ch = buffer.read();
            }
            file.setWritable(true);
            buffer.close();

            return sb.toString();
        } catch (IOException e) {
            try {
                if (buffer != null){
                    buffer.close();
                }
            } catch (IOException ex) {
                // ex.printStackTrace();
            }

            isConnect = false;
            return "[]";
        } finally {
            file.setWritable(true);

            try {
                if (buffer != null){
                    buffer.close();
                }
            } catch (IOException e) {
                // e.printStackTrace();
            }
        }
    }

    private DataSystemResult writeFile(String Contents){

        File file = new File(FilePass);
        PrintWriter p_writer = null;

        try{
            p_writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8")));
            p_writer.print(Contents);
            p_writer.close();
            isConnect = true;
            return new DataSystemResult("",false);
        } catch (FileNotFoundException e) {
            isConnect = false;
            return new DataSystemResult("File I/O Error : " + e.getMessage(),true);
        } catch (UnsupportedEncodingException e) {
            isConnect = false;
            return new DataSystemResult("File I/O Error : " + e.getMessage(),true);
        } finally {
            if (p_writer != null){
                p_writer.close();
            }
        }
    }
}
