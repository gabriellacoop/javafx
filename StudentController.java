package com.example.lab2;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.scene.paint.Paint;
import javafx.scene.layout.AnchorPane;


import java.net.URL;
import java.util.ResourceBundle;

public class StudentController implements Initializable {
    @FXML
    private TableView<Student> studentTable;

    @FXML
    private TableColumn<Student, Integer> idColumn;

    @FXML
    private TableColumn<Student, String> nameColumn;

    @FXML
    private TableColumn<Student, String> majorColumn;

    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField majorField;

    @FXML
    private AnchorPane mainAnchorPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        majorColumn.setCellValueFactory(cellData -> cellData.getValue().majorProperty());
    }

    @FXML
    void submit(ActionEvent event) {
        try {
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            String major = majorField.getText();

            if (!isValidId(id)) {
                showErrorAlert("Use only numbers for ID.");
                return;
            }

            if (!isValidName(name)) {
                showErrorAlert("Use only letters for name.");
                return;
            }

            if (!isValidMajor(major)) {
                showErrorAlert("Use only letters for Major.");
                return;
            }

            Student student = new Student(id, name, major);
            ObservableList<Student> students = studentTable.getItems();
            students.add(student);
            studentTable.setItems(students);

            idField.clear();
            nameField.clear();
            majorField.clear();

        } catch (NumberFormatException e) {
            showErrorAlert("Invalid ID. Use only numbers.");
        }
    }

    @FXML
    void changeColor(ActionEvent event) {
        System.out.println("Change Color button clicked");

        Color redColor = Color.rgb(255, 0, 0);
        Paint redPaint = redColor;

        mainAnchorPane.setBackground(new Background(new BackgroundFill(redPaint, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    @FXML
    void removeStudent(ActionEvent event) {
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            ObservableList<Student> students = studentTable.getItems();
            students.remove(selectedStudent);
            studentTable.setItems(students);
        } else {
            showErrorAlert("Select a student to remove.");
        }
    }

    @FXML
    void updateStudent(ActionEvent event) {
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                String major = majorField.getText();

                if (!isValidId(id)) {
                    showErrorAlert("Use only numbers for ID.");
                    return;
                }

                if (!isValidName(name)) {
                    showErrorAlert("Use only letters for name.");
                    return;
                }

                if (!isValidMajor(major)) {
                    showErrorAlert("Use only letters for Major.");
                    return;
                }

                selectedStudent.setId(id);
                selectedStudent.setName(name);
                selectedStudent.setMajor(major);

                studentTable.refresh();
            } catch (NumberFormatException e) {
                showErrorAlert("Invalid ID. Use only numbers.");
            }
        } else {
            showErrorAlert("Select a student to update.");
        }
    }

    private boolean isValidId(int id) {
        return id > 0;
    }

    private boolean isValidName(String name) {
        return name.matches("[a-zA-Z]+");
    }

    private boolean isValidMajor(String major) {
        return major.matches("[a-zA-Z]+");
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
