package io;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

public class PackDecompiler {
    private static HashMap<String, String> fileMap = new HashMap<>();
    private static JsonReader jsonReader;
    private static String hash;
    private static String path;
    private static String putPath;
    private static boolean readHash = false;
    private static int progress = 0;
    private static int indexLength = 0;
    private static int readLength = 0;
    private static File minecraftPath;
    private static String version;

    public int getProgress() {
        return progress;
    }

    public void decompile(File minecraftPath, String version, String putPath) {
        // TODO 自动生成的构造函数存根
        try {
            String regVersion = "(\\d)+\\.+(\\d)+(\\.+(\\d))?";//x.x.x形式
            if (version.matches(regVersion)) {
                System.out.println("Version pass:" + "\"" + version + "\"");
                if (minecraftPath.isDirectory()) {
                    PackDecompiler.minecraftPath=minecraftPath;
                    PackDecompiler.version=version;
                    PackDecompiler.putPath =putPath;
                    System.out.println("Path pass:" + "\"" + minecraftPath.getAbsolutePath() + "\"");
                    String[] jsonVersion = version.split("\\.");
                    File indexJson = new File(minecraftPath.getAbsolutePath() + "\\assets\\indexes\\" + jsonVersion[0] + "." + jsonVersion[1] + ".json");

                    FileInputStream fin = new FileInputStream(indexJson);
                    InputStreamReader json = new InputStreamReader(fin);
                    jsonReader = new JsonReader(json);
                    System.out.println("Start decompile");

                    JarThread jarThread = new JarThread();
                    HashThread hashThread = new HashThread();

                    jarThread.start();
                    hashThread.start();

                    //System.out.println("Over");
                }
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }


    }

    private class JarThread extends Thread{
        @Override
        public void run(){
            System.out.println("Unzip Minecraft jar");
            try {
                zipUncompress(minecraftPath.getAbsolutePath()+"\\versions\\"+version+"\\"+version+".jar",putPath);
                System.out.println("Jar over");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class HashThread extends Thread{
        @Override
        public void run(){
            try {
                handleJsonObject(jsonReader);
                File objects=new File(minecraftPath.getAbsolutePath()+"\\assets\\objects");
                System.out.println("Read information done");

                indexLength = fileMap.size();
                System.out.println("size:"+indexLength);
                filesDirs(objects);
                System.out.println("Hash over");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void filesDirs(File file){
        if(file!=null){


            if(file.isDirectory()){
                File[] files=file.listFiles();
                assert files != null;
                for (File flies2:files) {
                    filesDirs(flies2);
                }
            }else{

                //System.out.println("文件名字"+file);
                try {
                    //System.out.println(fileMap.get(file.getName()));
                    if (fileMap.get(file.getName())!=null){
                        readLength++;
                        progress = readLength*100/indexLength;

                        File put = new File(putPath+"\\"+fileMap.get(file.getName()));
                        put.getParentFile().mkdirs();
                        copyFile(file,put);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else{
            System.out.println("文件不存在");
        }
    }

    public void copyFile(File source, File targetFile) throws IOException {
        try (FileChannel inputChannel = new FileInputStream(source).getChannel(); FileChannel outputChannel = new FileOutputStream(targetFile).getChannel()) {
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        }
    }

    private void handleJsonObject(JsonReader reader) throws IOException {
        reader.beginObject();
        String name;

        while (reader.hasNext()) {
            JsonToken token = reader.peek();

            if (token.equals(JsonToken.BEGIN_ARRAY)) {
                handleJsonArray(reader);
            } else if (token.equals(JsonToken.NAME)) {
                name = reader.nextName();
                eqaqlsName(name);
            } else if (token.equals(JsonToken.STRING)) {
                // get the current token
                String information = reader.nextString();
                if (readHash){
                    hash = information;
                    path = reader.getPath();
                    path = path.replace(".hash","");
                    path = path.replace(".size","");
                    path = path.replace("/","\\");
                    String[] temp= path.split("\\.",3);
                    path = temp[2];
                    //System.out.println(path);
                    fileMap.put(hash,path);
                }
            } else if (token.equals(JsonToken.NUMBER)) {
                reader.nextInt();
            } else if (token.equals(JsonToken.BEGIN_OBJECT)) {
                handleJsonArray(reader);
            }
        }
    }

    private void handleJsonArray(JsonReader reader) throws IOException {
        reader.beginObject();
        while (true) {
            JsonToken token = reader.peek();
            if (token.equals(JsonToken.END_ARRAY)) {
                reader.endArray();
                break;
            } else if (token.equals(JsonToken.BEGIN_OBJECT)) {
                handleJsonObject(reader);
            } else if (token.equals(JsonToken.END_OBJECT)) {
                reader.endObject();
            } else if (token.equals(JsonToken.NAME)) {
                String name = reader.nextName();
                eqaqlsName(name);
            } else {
                break;
            }
        }
    }
    // 更多请阅读：https://www.yiibai.com/gson/gson_streaming.html

    private void eqaqlsName(String name) {
        switch (name) {
            case "hash":
                readHash = true;
                break;
            case "size":
            	readHash = false;
                break;
            default:
                path=name;
                break;
        }
    }

    public void zipUncompress(String inputFile, String destDirPath) throws Exception {
        File srcFile = new File(inputFile);//获取当前压缩文件
        // 判断源文件是否存在
        if (!srcFile.exists()) {
            throw new Exception(srcFile.getPath() + "所指文件不存在");
        }
        //System.out.println("Start unzip jar");
        ZipFile zipFile = new ZipFile(srcFile);//创建压缩文件对象
        //开始解压
        Enumeration<?> entries = zipFile.entries();
        while (entries.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) entries.nextElement();
            //System.out.println(entry+"size:"+entry.getSize());
            if (entry.isDirectory()){
                System.out.println(entry);
            }
            if (entry.getName().startsWith("assets")){
                //System.out.println(entry);
                // 如果是文件夹，就创建个文件夹
                if (entry.isDirectory()) {
                    //srcFile.mkdirs();
                } else {
                    // 如果是文件，就先创建一个文件，然后用io流把内容copy过去
                    String path = entry.getName().replace("assets/","");
                    path = path.replace("/","\\");
                    File targetFile = new File(destDirPath+"\\"+path);
                    // 保证这个文件的父文件夹必须要存在
                    if (!targetFile.getParentFile().exists()) {
                        targetFile.getParentFile().mkdirs();
                    }
                    targetFile.createNewFile();
                    // 将压缩文件内容写入到这个文件中
                    InputStream is = zipFile.getInputStream(entry);
                    FileOutputStream fos = new FileOutputStream(targetFile);
                    int len;
                    byte[] buf = new byte[1024];
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }
                    fos.close();
                    is.close();
                }
            }
        }
    }

}
