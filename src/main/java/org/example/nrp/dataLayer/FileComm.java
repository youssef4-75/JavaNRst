package org.example.nrp.dataLayer;

import org.example.nrp.functionalLayer.Pointage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public final class FileComm {
    private static final String passwordPath = "files/passwordHash.txt";
    private static final String lastSessionPath = "files/lastSession.txt";
    private static final String allSessionsDirectory = "files/allSessions/session";

    public static void setPassword(String password) {
        write(HashIt(password));
    }
    public static void write(int number){
        try {
            Path p = Paths.get(passwordPath);

            // Create the file if it doesn't exist
            if (!Files.exists(p)) {
                Files.createFile(p);
            }

            String content = Integer.toString(number);

            Files.writeString(p, content, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

            System.out.println("Number written successfully to: " + passwordPath);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to write number to the file.");
        }

    }

    public static String getPasswordHash() {
        File file = new File(passwordPath);
        try{
            if (file.exists()) {
                // Read the content of the file and return it as a string
                return new String(Files.readAllBytes(Paths.get(passwordPath)));
            } else {
                // Create the file if it does not exist
                file.getParentFile().mkdirs(); // Create parent directories if needed
                file.createNewFile();         // Create the file
                return "";                    // Return an empty string
            }
        } catch (IOException e) {
            return null;
        }
    }

    public static int HashIt(String password) {
        if (password == null || password.isEmpty()) {
            return 0; // Return 0 for null or empty strings
        }

        int hash = 0;
        for (int i = 0; i < password.length(); i++) {
            hash = 31 * hash + password.charAt(i); // Use a prime number (31) for better distribution
        }

        return Math.abs(hash); // Ensure the hash is non-negative
    }

    public static int getLastSessionId(boolean increment){

        File file = new File(lastSessionPath);

        // Check if the file exists
        if (!file.exists()) {
            initLastSession(file);
            return 0;
        }

        String line;
        int sessionId = 0;
        try {
            line = new String(Files.readAllBytes(Paths.get(lastSessionPath)));;
            System.out.println(line);
            System.out.println(200);
            try {
                sessionId = Integer.parseInt(line) + 1;
            }catch (NumberFormatException e) {}
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            return 0; // Return 0 if there's an error reading or parsing
        }

        if(increment){incrementSessionId(file, sessionId);}
        return sessionId;
    }

    public static void incrementSessionId(File file, int newSession){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            System.out.println(202);
            writer.write(Integer.toString(newSession));
            System.out.println(newSession);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void initLastSession(File file){
        try {
            // Create the file and write 0 if it doesn't exist
            file.getParentFile().mkdirs(); // Create the directories if they don't exist
            file.createNewFile(); // Create the file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                System.out.println(201);
                writer.write("0");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Path getSessionLocation(int sid){
        String ssid = SSidify(sid);
        return Paths.get(allSessionsDirectory + ssid + ".txt");
    }

    public static Object extract(Path path){
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path.toFile()))) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error reading the object from file: " + e.getMessage());
        }
        return null;
    }

    public static void pickle(Object obj, Path path){
        createPath(path);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path.toFile()))) {
            oos.writeObject(obj);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error writing the object to the file: " + e.getMessage());
        }
    }

    public static List<Pointage> getPointageList(int sid){
        Path p = getSessionLocation(sid);
        Object obj = extract(p);
        if(obj instanceof List) return (List<Pointage>) obj;
        else return new ArrayList<Pointage>();
    }

    public static void pushPL(List<Pointage> pointages, int sid){
        Path p = getSessionLocation(sid);
        createPath(p);
        pickle(pointages, p);
    }

    public static String SSidify(int sid){
        String res = Integer.toString(sid);
        for(int i = res.length(); i<3; i++){
            res = "0" + res;
        }
        return res;
    }

    public static void createPath(Path p){
        try{
            File f = p.toFile();
            f.getParentFile().mkdirs();
            f.createNewFile();
        }catch (IOException e) {
            System.err.println("Failed to create the file: " + e.getMessage());
        }

    }

}
