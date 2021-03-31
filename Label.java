package main.java.com.minaev.crud.model;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public class Label {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Label label = (Label) o;
        return id == label.id &&
                Objects.equals(category, label.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, category);
    }

    int id;
    String category;

    public Label(int id, String category) {
        this.id = id;
        this.category = category;
    }

    public Label() {

    }


    public static String labelToString(Label label) {
        String forRecording = label.getId() + "," + label.getCategory();
        return forRecording;
    }

    public static Label stringToLabel(String str) {
        String[] partsLabel = str.split(",");
        Label label = new Label();
        try {
            int id = Integer.parseInt(partsLabel[0]);
            label.setId(id);
        } catch (NumberFormatException e) {
            System.out.println("Не можем преобразовать строку в число" + e);
        }
        String category = partsLabel[1];
        label.setCategory(category);
        return label;
    }


}
