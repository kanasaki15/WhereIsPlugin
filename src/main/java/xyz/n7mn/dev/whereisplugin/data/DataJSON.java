package xyz.n7mn.dev.whereisplugin.data;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.bukkit.plugin.Plugin;
import xyz.n7mn.dev.whereisplugin.data.Result.JSONData;

import java.io.*;

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
        }finally {
            try{
                buffer.close();
                file.setWritable(true);
            } catch (IOException ee){
                plugin.getLogger().info("File I/O Error : " + ee.getMessage());
            }
        }

        if (jsonData != null){
            Data[] data = new Data[jsonData.length];
            for (int i = 0; i < jsonData.length; i++){
                Data tempData = new Data();
                tempData.Name = jsonData[i].getName();
                tempData.startX = jsonData[i].getStartX();
                tempData.endX = jsonData[i].getEndX();
                tempData.startZ = jsonData[i].getStartZ();
                tempData.endZ = jsonData[i].getEndZ();

                data[i] = tempData;
            }
            return data;
        }else{
            return null;
        }
    }

    @Override
    public boolean SetName(int startX, int endX, int startZ, int endZ, String name) {
        File file = new File(getFilePass());
        BufferedReader buffer = null;
        Gson gson = new Gson();
        JSONData[] jsonData = null;
        try {
            file.setWritable(false);
            FileInputStream input = new FileInputStream(file);
            InputStreamReader stream = new InputStreamReader(input,"UTF-8");
            buffer = new BufferedReader(stream);

            StringBuffer sb = new StringBuffer();
            int ch = buffer.read();
            while (ch != -1) {
                sb.append((char) ch);
                ch = buffer.read();
            }
            jsonData = gson.fromJson(sb.toString(), JSONData[].class);
            buffer.close();

            file.setWritable(true);
            PrintWriter p_writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8")));

            int newCount = 1;
            if (jsonData != null){
                newCount = jsonData.length + 1;
            }else{
                jsonData = new JSONData[0];
            }
            JSONData[] newData = new JSONData[newCount];
            for(int i = 0; i < jsonData.length; i++){
                newData[i] = jsonData[i];
            }

            int id = jsonData.length + 1;
            JSONData newData1 = new JSONData(id,name,startX,endX,startZ,endZ,true);
            newData[jsonData.length] = newData1;
            String json = gson.toJson(newData);
            for(int i = 0; i < json.length(); i++){
                p_writer.print(json.charAt(i));
            }
            p_writer.close();
            buffer.close();
            return true;
        } catch (FileNotFoundException e) {
            plugin.getLogger().info("File I/O Error : " + e.getMessage());
        } catch (IOException e) {
            plugin.getLogger().info("File I/O Error : " + e.getMessage());
        } finally {
            try{
                buffer.close();
                file.setWritable(true);
            } catch (IOException e) {
                plugin.getLogger().info("File I/O Error : " + e.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean UpdateName(String OldName, String NewName) {
        return true;
    }

    @Override
    public boolean DelName(String name) {
        return true;
    }

    @Override
    public Data[] GetListAll(){
        return null;
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
}
