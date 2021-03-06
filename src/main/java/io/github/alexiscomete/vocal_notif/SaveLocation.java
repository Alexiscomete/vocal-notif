package io.github.alexiscomete.vocal_notif;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Function;

/**
 * @param <E> type of content, think to add a toString() method in E!
 */
public class SaveLocation<E> {

    public final static String pathStatic = getPathStatic();

    public static String getPathStatic() {
        return new File("").getAbsolutePath();
    }

    String sep;
    ArrayList<E> content = new ArrayList<>();
    String path;
    File file;
    Function<String, E> a;
    Function<E, String> ar;

    public SaveLocation(String separator, String path, Function<String, E> a, Function<E, String> ar) throws IOException {
        this.sep = separator;
        this.path = pathStatic + path;
        this.file = new File(pathStatic + path);
        this.a = a;
        this.ar = ar;
        if (path.endsWith("/")) {
            System.out.println(file.mkdirs());
        } else if (!file.exists()) {
            System.out.println(file.createNewFile());
        }
    }

    public void saveAll() {
        StringBuilder save = new StringBuilder();
        for (int i = 0; i < content.size(); i++) {
            E e = content.get(i);
            if (e != null) {
                save.append(ar.apply(e));
                if (i != content.size() -1) {
                    save.append(" ");
                }
            }
        }
        System.out.println("Save : " + save);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(save.toString().getBytes());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public void loadAll() {
        Scanner sc = null;
        try {
            sc = new Scanner(file);
            StringBuilder answer = new StringBuilder();
            boolean nextLine = sc.hasNextLine();
            while (nextLine) {
                answer.append(sc.nextLine());
                nextLine = sc.hasNextLine();
                if (nextLine) answer.append("\n");
            }
            String[] str = String.valueOf(answer).split(sep);
            for (String s : str) {
                E e = a.apply(s);
                if (e != null) {
                    content.add(e);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (sc != null) {
            sc.close();
        }
    }

    public ArrayList<E> getContent() {
        return content;
    }

    public static void create(String path) throws IOException {
        File file = new File(pathStatic + path);
        if (path.endsWith("/")) {
            System.out.println(file.mkdirs());
        } else if (!file.exists()) {
            System.out.println(file.createNewFile());
        }
    }
}

