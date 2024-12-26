package main.java.ui.components;

import javafx.collections.ObservableList;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import main.java.data.entities.IData;
import main.java.data.sql.FieldType;
import main.java.data.sql.Gestion;
import main.java.data.sql.Tables;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ModalEdit extends Modal {
    public ModalEdit(BorderPane page, double spacing, String title, Tables tableType, ObservableList items, boolean isNew) throws SQLException {
        super(page, spacing, title);

        VBox form = new VBox();
        HashMap<String, FieldType> data = Gestion.structTable(tableType.toString(), false);
        int i = 0;
        for (String key : data.keySet()) {
            String item = (String) items.get(i);
            i++;
            FieldType type = data.get(key);
            Label label = new Label(key);
            if (type == FieldType.VARCHAR || type == FieldType.INT4 || type == FieldType.FLOAT8 || type == FieldType.NUMERIC) {
                TextField textField = new TextField();
                textField.setPromptText(key);
                textField.setText(item);
                form.getChildren().add(label);
                form.getChildren().add(textField);
            } else if (type == FieldType.DATE) {
                DatePicker datePicker = new DatePicker();
                datePicker.setPromptText(key);
                datePicker.setValue(LocalDate.parse(item));
                form.getChildren().add(label);
                form.getChildren().add(datePicker);
            }
        }
        this.contentBox.getChildren().add(form);

    }
}
