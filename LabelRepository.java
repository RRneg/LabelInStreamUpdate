package main.java.com.minaev.crud.repository;

import main.java.com.minaev.crud.AppRunner;
import main.java.com.minaev.crud.model.Label;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LabelRepository {

    private final Path path = Paths.get("src/main/resources/files/Lables.txt");

    private Integer getNewId(List<Label> allLabel) {
        return allLabel.stream().
                max((a, b) -> Integer.compare(a.getId(), b.getId())).
                orElse(new Label(0, null)).getId() + 1;
    }


    private void createNewFileNIO() {
        try {
            if (!Files.exists(path))
                Files.createFile(path);
        } catch (IOException e) {
            System.out.println("Не удалось создать файл-репозиторий :" + e);
        }
    }

    public Label getById(int id) {
        return getAll().stream().
                filter((a) -> a.getId() == id).
                findFirst().orElse(null);
    }

    private List<Label> getAllInternal() {
        try {
            return Files.readAllLines(path).stream().map(str -> {
                return Label.stringToLabel(str);
            }).collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("Не удалось прочитать файл" + e);
            return Collections.emptyList();
        }
    }


    public Label update(Label label) {
        reSaveListLabel(getAll().stream().peek(label1 -> {
            if (label1.getId() == label.getId()) {
                label1.setCategory(label.getCategory());
            } else {
                System.out.println("Такого элемента не удалось найти");

            }
        }).collect(Collectors.toList()));
        return label;
    }

    private void reSaveListLabel(List<Label> labels) {
        try {
            Files.delete(path);
            createNewFileNIO();
        } catch (IOException e) {
            System.out.println("Не удалось обновить файл :" + e);
        }
        labels.stream().distinct().
                sorted((a, b) -> Integer.compare(a.getId(), b.getId())).
                collect(Collectors.toList()).stream().
                forEach(label -> saveLabel(label));
    }


    public void deleteById(int id) {
        List<Label> labels = getAllInternal();
        labels.removeIf(label -> label.getId() == id);
        reSaveListLabel(labels);
    }


    public List<Label> getAll() {
        return getAllInternal();
    }

    private void saveLabel(Label label) {
        String lable = Label.labelToString(label) + System.getProperty("line.separator");
        try {
            Files.writeString(path, lable, StandardOpenOption.APPEND);

        } catch (IOException e) {
            System.out.println("Не удалось записать в файл(((: " + e);
        }

    }

}


