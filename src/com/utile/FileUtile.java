package com.utile;

import java.io.*;

public class FileUtile {
    public static boolean saveObj(Serializable obj, String filename) {
        boolean isSuccess = false;
        try {
            saveObjectToFile(obj, filename);
            isSuccess = true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return isSuccess;
    }

    public static Object getObj(String filename) {
        Object object = null;
        try {
            object = loadObjectFromFile(filename);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return object;
    }

    private static void saveObjectToFile(Serializable obj, String filename) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(obj);
        }
    }

    private static Object loadObjectFromFile(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return in.readObject();
        }
    }

    public static boolean isExists(String path) {
        File file = new File(path);
        return file.exists();
    }

    public static boolean isFile(String path) {
        File file = new File(path);
        while (!file.exists()) {
            String path1 = file.getPath();
            if (path1.equals("")) {
                break;
            }
            file = file.getParentFile();
            String path2 = file.getPath();
            if (path1.equals(path2) || file == null) {
                break;
            }
        }
        return file.exists();
    }
}
