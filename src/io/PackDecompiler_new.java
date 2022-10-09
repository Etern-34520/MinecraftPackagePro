package io;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class PackDecompiler_new {
    private static final HashMap<String, String> fileMap = new HashMap<>();
    private static JsonReader jsonReader;
    private static String hash;
    private static String path;
    private static String putPath;
    private static boolean readHash = false;
    private static int libraryProgress = 0;
    private static int jarProgress = 0;
    private static int indexLength = 0;
    private static int readLength = 0;
    private static File minecraftPath;
    private static String version;

    public int getProgress() {
        int progress = (libraryProgress+jarProgress)/2;
        return progress;
    }

    public void decompile(File minecraftPath, String version, String putPath) {
        // TODO �Զ����ɵĹ��캯�����
        try {
            String regVersion = "(\\d)+\\.+(\\d)+(\\.+(\\d))?";//x.x.x��ʽ
            if (version.matches(regVersion)) {
                System.out.println("Version pass:" + "\"" + version + "\"");
                if (minecraftPath.isDirectory()) {
                    PackDecompiler_new.minecraftPath=minecraftPath;
                    PackDecompiler_new.version=version;
                    PackDecompiler_new.putPath =putPath;
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

                //System.out.println("�ļ�����"+file);
                try {
                    //System.out.println(fileMap.get(file.getName()));
                    if (fileMap.get(file.getName())!=null){
                        readLength++;
                        libraryProgress = readLength*100/indexLength;

                        File put = new File(putPath+"\\"+fileMap.get(file.getName()));
                        put.getParentFile().mkdirs();
                        copyFile(file,put);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else{
            System.out.println("�ļ�������");
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
    // �������Ķ���https://www.yiibai.com/gson/gson_streaming.html

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
        File srcFile = new File(inputFile);//��ȡ��ǰѹ���ļ�
        // �ж�Դ�ļ��Ƿ����
        if (!srcFile.exists()) {
            throw new Exception(srcFile.getPath() + "��ָ�ļ�������");
        }
        //System.out.println("Start unzip jar");
        ZipFile zipFile = new ZipFile(srcFile);//����ѹ���ļ�����
        //��ʼ��ѹ
        long allSize = 0;
        {
            Enumeration<?> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                //System.out.println(entry+"size:"+entry.getSize());
                if (entry.isDirectory()) {
                    System.out.println(entry);
                }
                if (entry.getName().startsWith("assets")) {
                    allSize=allSize+entry.getSize();
                }
            }
        }
        {
            long overSize = 0;
            Enumeration<?> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                //System.out.println(entry+"size:"+entry.getSize());
                if (entry.isDirectory()){
                    System.out.println(entry);
                }
                if (entry.getName().startsWith("assets")){
                    //System.out.println(entry);
                    // ������ļ��У��ʹ������ļ���
                    if (entry.isDirectory()) {
                        //srcFile.mkdirs();
                    } else {
                        // ������ļ������ȴ���һ���ļ���Ȼ����io��������copy��ȥ
                        String path = entry.getName().replace("assets/","");
                        path = path.replace("/","\\");
                        File targetFile = new File(destDirPath+"\\"+path);
                        // ��֤����ļ��ĸ��ļ��б���Ҫ����
                        if (!targetFile.getParentFile().exists()) {
                            targetFile.getParentFile().mkdirs();
                        }
                        targetFile.createNewFile();
                        // ��ѹ���ļ�����д�뵽����ļ���
                        InputStream is = zipFile.getInputStream(entry);
                        FileOutputStream fos = new FileOutputStream(targetFile);
                        int len;
                        byte[] buf = new byte[1024];
                        while ((len = is.read(buf)) != -1) {
                            fos.write(buf, 0, len);
                        }
                        overSize = overSize + entry.getSize();
                        fos.close();
                        is.close();
                    }
                    jarProgress = (int) (Math.round(overSize*100/allSize)/100.0);
                }
            }
        }

    }

}
