package xyz.n7mn.dev.whereisplugin.data;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.bukkit.plugin.Plugin;
import xyz.n7mn.dev.whereisplugin.data.Result.JSONData;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class DataJSON implements DataInterface {
    Plugin plugin;
    DataJSON(Plugin p){
        plugin = p;
    }

    @Override
    public boolean NewConnect() {

        File file = new File(getFilePass());
        try {
            file.setWritable(false);
            FileReader filereader = new FileReader(file);
            filereader.close();
            file.setWritable(true);
            return true;
        } catch (FileNotFoundException e) {
            try {
                file.createNewFile();
                return true;
            } catch (IOException ex) {
                plugin.getLogger().info("File I/O Error : " + e.getMessage());
                return false;
            }
        } catch (IOException e) {
            plugin.getLogger().info("File I/O Error : " + e.getMessage());
            return false;
        } finally {
            file.setWritable(true);
        }
    }

    @Override
    public Data[] GetList(int x, int z) {
        Data[] data = GetListAll();
        List<Data> temp = new ArrayList<>();
        for (int i = 0; i < data.length; i++){
            if (data[i].startX <= x && x <= data[i].endX && data[i].startZ <= z && z <= data[i].endZ){
                temp.add(data[i]);
                continue;
            }
            if (data[i].startX >= x && x >= data[i].endX && data[i].startZ >= z && z >= data[i].endZ) {
                temp.add(data[i]);
            }
        }
        data = new Data[temp.size()];
        for (int i = 0; i < data.length; i++){
            data[i] = temp.get(i);
        }
        return data;
    }

    @Override
    public boolean SetName(int startX, int endX, int startZ, int endZ, String name, UUID uuid) {
        List<Data> list = getAllListByList(false);
        Gson gson = new Gson();

        int newID = list.size() + 1;

        JSONData[] jsonData = new JSONData[newID];
        for (int i = 0; i < list.size(); i++){
            jsonData[i] = new JSONData(i + 1, list.get(i).Name, list.get(i).uuid, list.get(i).startX, list.get(i).endX, list.get(i).startZ,list.get(i).endZ,list.get(i).Active);
        }
        jsonData[list.size()] = new JSONData(list.size(), name, uuid, startX,endX,startZ,endZ,true);

        String json = gson.toJson(jsonData);

        File file = new File(getFilePass());
        PrintWriter p_writer = null;
        try{
            p_writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8")));
            p_writer.print(json);
            p_writer.close();
            return true;
        } catch (FileNotFoundException e) {
            plugin.getLogger().info("File I/O Error : " + e.getMessage());
            return false;
        } catch (UnsupportedEncodingException e) {
            plugin.getLogger().info("File I/O Error : " + e.getMessage());
            return false;
        } finally {
            if (p_writer != null){
                p_writer.close();
            }
        }
    }

    @Override
    public boolean UpdateName(String OldName, String NewName, UUID uuid) {
        List<Data> list = getAllListByList(false);

        for (int i = 0; i < list.size(); i++){
            Data data = list.get(i);
            if (!data.Active){
                continue;
            }
            if (data.Name.equals(OldName) && data.uuid.toString().equals(uuid.toString())){
                list.get(i).Name = NewName;
            }
        }

        JSONData[] jsonData = new JSONData[list.size()];
        for (int i = 0; i < jsonData.length; i++){

            jsonData[i] = new JSONData(i + 1, list.get(i).Name, list.get(i).uuid, list.get(i).startX, list.get(i).endX, list.get(i).startZ, list.get(i).endZ, list.get(i).Active);
        }
        String json = new Gson().toJson(jsonData);

        File file = new File(getFilePass());
        PrintWriter p_writer = null;
        try{
            p_writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8")));
            p_writer.print(json);
            p_writer.close();
            return true;
        } catch (FileNotFoundException e) {
            plugin.getLogger().info("File I/O Error : " + e.getMessage());
            return false;
        } catch (UnsupportedEncodingException e) {
            plugin.getLogger().info("File I/O Error : " + e.getMessage());
            return false;
        } finally {
            if (p_writer != null){
                p_writer.close();
            }
        }
    }

    @Override
    public boolean DelName(String name, UUID uuid) {
        List<Data> list = getAllListByList(false);

        for (int i = 0; i < list.size(); i++){
            Data data = list.get(i);
            if (!data.Active){
                continue;
            }
            if (data.Name.equals(name) && data.uuid.toString().equals(uuid.toString())){
                list.get(i).Active = false;
            }
        }

        JSONData[] jsonData = new JSONData[list.size()];
        for (int i = 0; i < jsonData.length; i++){

            jsonData[i] = new JSONData(i + 1, list.get(i).Name, list.get(i).uuid, list.get(i).startX, list.get(i).endX, list.get(i).startZ, list.get(i).endZ, list.get(i).Active);
        }
        String json = new Gson().toJson(jsonData);

        File file = new File(getFilePass());
        PrintWriter p_writer = null;
        try{
            p_writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8")));
            p_writer.print(json);
            p_writer.close();
            return true;
        } catch (FileNotFoundException e) {
            plugin.getLogger().info("File I/O Error : " + e.getMessage());
            return false;
        } catch (UnsupportedEncodingException e) {
            plugin.getLogger().info("File I/O Error : " + e.getMessage());
            return false;
        } finally {
            if (p_writer != null){
                p_writer.close();
            }
        }
    }

    @Override
    public Data[] GetListAll(){
        List<Data> list = getAllListByList(true);

        Data[] data = new Data[list.size()];
        for (int i = 0; i < data.length; i++){
            data[i] = list.get(i);
        }
        return data;
    }

    private String getFilePass(){
        String os = System.getProperty("os.name").toLowerCase();
        File folder = plugin.getDataFolder();

        String FilePass = null;
        if (os.startsWith("windows")){
            FilePass = ".\\"+folder.toString()+"\\DataList.json";
        }else{
            FilePass = "./"+folder.toString()+"/DataList.json";
        }
        return FilePass;
    }

    private List<Data> getAllListByList(boolean TrueOnly){
        List<Data> temp = new ArrayList<>();

        File file = new File(getFilePass());
        BufferedReader buffer = null;
        Gson gson = new Gson();
        JSONData[] jsonData = null;
        try {
            FileInputStream input = new FileInputStream(file);
            InputStreamReader stream = new InputStreamReader(input,"UTF-8");
            buffer = new BufferedReader(stream);
            file.setWritable(false);
            StringBuffer sb = new StringBuffer();
            int ch = buffer.read();
            while (ch != -1) {
                sb.append((char) ch);
                ch = buffer.read();
            }
            jsonData = gson.fromJson(sb.toString(), JSONData[].class);
            file.setWritable(true);
            buffer.close();

        } catch (FileNotFoundException e) {
            plugin.getLogger().info("File I/O Error : " + e.getMessage());
            return null;
        } catch (IOException e) {
            plugin.getLogger().info("File I/O Error : " + e.getMessage());
            return null;
        } catch (JsonSyntaxException e){
            plugin.getLogger().info("File I/O Error : " + e.getMessage());
            return null;
        } finally {
            try{
                buffer.close();
                file.setWritable(true);
            } catch (IOException ee){
                plugin.getLogger().info("File I/O Error : " + ee.getMessage());
            }
        }

        if (jsonData == null){
            jsonData = new JSONData[0];
        }

        for (int i = 0; i < jsonData.length; i++){
            if (TrueOnly && !jsonData[i].isActive()){
                continue;
            }
            Data data = new Data();
            data.ID = jsonData[i].getID();
            data.Name = jsonData[i].getName();
            data.uuid = jsonData[i].getUuid();
            data.startX = jsonData[i].getStartX();
            data.endX = jsonData[i].getEndX();
            data.startZ = jsonData[i].getStartZ();
            data.endZ = jsonData[i].getEndZ();
            data.Active = jsonData[i].isActive();
            temp.add(data);
        }

        return temp;
    }
}
