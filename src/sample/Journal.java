package sample;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

//Todo Сделать журнал в табличном виде с возможностью сортировки как в том проекте

public class Journal {
    public static ArrayList<String> list = new ArrayList<>();

    public static void setList(Note n){
        list.add(n.getNote());
    }

    public static void removeList(){
        list.clear();
    }

    public static void Save() throws IOException {
        Files.write(Paths.get("src/sample/demo.txt"), list, StandardCharsets.UTF_8,StandardOpenOption.CREATE); // Сохранение в текстовик
    }

    public static void download() throws Exception {
        Path path = Paths.get("src/sample/demo.txt");
        list.clear();
        list.add(Files.readString(path, StandardCharsets.UTF_8));
    }
    public static ArrayList getList(){
        return list;
    }
}
