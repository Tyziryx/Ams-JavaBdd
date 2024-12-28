package main.java.ui.components;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import main.java.Test;
import main.java.data.entities.ContactAssocie;
import main.java.data.entities.Contrat;
import main.java.data.entities.Fournisseur;
import main.java.data.entities.IData;
import main.java.data.sql.FieldType;
import main.java.data.sql.Gestion;
import main.java.data.sql.Tables;
import main.java.ui.pages.Page;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class ModalEdit extends Modal {
    public ModalEdit(BorderPane page, Page oldPage, Table table, double spacing, String title, Tables tableType, ObservableList items, boolean isNew) throws SQLException {
        super(page, oldPage, spacing, title);

        VBox form = new VBox();
        HashMap<String, FieldType> data = Gestion.structTable(tableType, false);
        Object[] inputs = new Object[10];
        int i = 0;
        for (String key : data.keySet()) {
            String item = "";
            if(items != null) item = (String) items.get(i);
            FieldType type = data.get(key);
            Label label = new Label(key);
            if (type == FieldType.DATE) {
                DatePicker datePicker = new DatePicker();
                datePicker.setPromptText(key);
                if(!isNew) datePicker.setValue(LocalDate.parse(item));
                form.getChildren().add(label);
                form.getChildren().add(datePicker);
                inputs[i] = datePicker;
            } else {
                TextField textField = new TextField();
                textField.setPromptText(key);
                if(!isNew) textField.setText(item);
                if(key.equals("id_contrat") && tableType == Tables.CONTRAT) {
                    textField.setDisable(true);
                    if(isNew) textField.setText(UUID.randomUUID().toString());
                }
                if(key.equals("siret") && tableType == Tables.CONTACT_ASSOCIE) {
                    textField.setText(item);
                    textField.setDisable(true);
                }
                if (!isNew && key.equals("siret") && tableType == Tables.FOURNISSEUR) {
                    textField.setDisable(true);
                }
                form.getChildren().add(label);
                form.getChildren().add(textField);
                inputs[i] = textField;
            }
                i++;
        }
        Button submit = new Button("Valider");
        form.getChildren().add(submit);
        final Text actiontarget = new Text();
        actiontarget.getStyleClass().add("erreur-form");
        form.getChildren().add(actiontarget);

        this.contentBox.getChildren().add(form);
        submit.setOnAction(e -> {
            try {
                submit(isNew, inputs, actiontarget, tableType, table);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        page.addEventHandler(KeyEvent.KEY_PRESSED, ev -> {
            if (ev.getCode() == KeyCode.ENTER) {
                try {
                    submit(isNew, inputs, actiontarget, tableType, table);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void submit(boolean isNew, Object[] inputs, Text actionTarget, Tables tableType, Table table) throws SQLException {
        for(int j = 0; j < inputs.length; j++) {
            if(!isValid(inputs[j], actionTarget)) return;
            if (inputs[j] instanceof TextField) {
                TextField item = (TextField) inputs[j];
//                System.out.println(item.getText());

            } else if (inputs[j] instanceof DatePicker) {
                DatePicker item = (DatePicker) inputs[j];
//                System.out.println(item.getValue());
            }
        }

        switch (tableType) {
            case CONTRAT:
//                System.out.println(((TextField)inputs[6]).getText());
                try {
                    inputs[6] = UUID.fromString(((TextField)inputs[6]).getText());
                } catch (ClassCastException e) {
                    inputs[6] = UUID.randomUUID();
                }
                DatePicker dateDebut = (DatePicker) inputs[2];
                DatePicker dateFin = (DatePicker) inputs[3];
                if(dateDebut.getValue().isAfter(dateFin.getValue())) {
                    showError(actionTarget, "La date de début ne peut pas etre après la date de fin");
                    return;
                }
                Contrat contrat = new Contrat(inputs);
                if(isNew) {
                    Gestion.insert(contrat, Tables.CONTRAT);
                } else {
                    Gestion.update(contrat, Tables.CONTRAT);
                }
                break;
            case CONTACT_ASSOCIE:
                ContactAssocie contactAssocie = new ContactAssocie(inputs);
                if(isNew) {
                    Gestion.insert(contactAssocie, Tables.CONTACT_ASSOCIE);
                } else {
                    Gestion.update(contactAssocie, Tables.CONTACT_ASSOCIE);
                }
                break;
            case FOURNISSEUR:
                Fournisseur fournisseur = new Fournisseur(inputs);
                if(isNew) {
                    Gestion.insert(fournisseur, Tables.FOURNISSEUR);
                } else {
                    Gestion.update(fournisseur, Tables.FOURNISSEUR);
                }
        }
        fermer(table);
    }

    private void showError(Text actionTarget, String msg) {
        actionTarget.setText(msg);
    }

    private boolean isValid(Object object, Text actionTarget) {
        if (object instanceof TextField) {
            TextField item = (TextField) object;
            if(item.getText().equals("")) {
                showError(actionTarget, "L'input ne peut pas etre vide !");
                return false;
            }
        } else if (object instanceof DatePicker) {
            DatePicker item = (DatePicker) object;
            if(item.getValue() == null) {
                showError(actionTarget, "La date ne peut pas etre vide !");
                return false;
            }
        }
        return true;
    }

    public void fermer(Table table) throws SQLException {
        super.fermer();
        table.refreshDynamicTable();
        page.setCenter(oldPage);
    }
}
