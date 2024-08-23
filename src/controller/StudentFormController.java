package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import model.Student;
import view.tablemodel.StudentTM;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class StudentFormController {
    public AnchorPane studentFormContext;
    public JFXTextField txtId;
    public JFXTextField txtName;
    public JFXTextField txtContact;
    public JFXTextField txtAddress;
    public TableView<StudentTM> tblStudent;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colContact;
    public TableColumn colAddress;
    public TableColumn colDelete;

    static ArrayList<Student> studentList = new ArrayList<>();
    public JFXTextField txtSearch;
    public JFXButton btnSaveAndUpdate;

    public void initialize(){
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colDelete.setCellValueFactory(new PropertyValueFactory<>("btn"));

        txtSearch.textProperty().addListener((observable, oldValue, txt) -> {

            for (Student s : studentList){
                if (s.getId().contains(txt)||s.getContact().contains(txt)||s.getAddress().contains(txt)||s.getName().contains(txt)){
                    System.out.println(s);
                }
            }

        });

        tblStudent.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue==null){

            }else{
                try {
                    loadStudentData(newValue);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void loadStudentData(StudentTM tm) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/StudentDataForm.fxml"));
        Parent parent=loader.load();
        StudentDataFormController controller=loader.getController();
        controller.setData(tm);
        Scene scene=new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void backToHome(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) studentFormContext.getScene().getWindow();
        window.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/DashBoardForm.fxml"))));
    }

    public void newStudentOnAction(ActionEvent actionEvent) {


    }

    public boolean isExists(Student student) {
        for (Student t : studentList){
            if (t.getId().equalsIgnoreCase(student.getId())) { // C001,c001
                return false;
            }
        }
        return true;
    }

    public void saveUpdateOnAction(ActionEvent actionEvent) {
        Student student=new Student(txtId.getText(),txtName.getText(),txtContact.getText(),txtAddress.getText());
        if (btnSaveAndUpdate.getText().equalsIgnoreCase("Save")){
            if (isExists(student)) {
                if (studentList.add(student)) {
                    // alert-> save
                    loadAllStudents();
                    clearFields();
                    //new Alert(Alert.AlertType.CONFIRMATION, "Saved..", ButtonType.CLOSE).show();
                } else {
                    //alert -> try again
                    new Alert(Alert.AlertType.WARNING, "Try Again..", ButtonType.CLOSE).show();
                }
            } else {
                new Alert(Alert.AlertType.WARNING, "Already Exists..", ButtonType.CLOSE).show();
            }
        }else{
            // update
        }



    }

    private void loadAllStudents(){
        ObservableList<StudentTM> tmObservableList= FXCollections.observableArrayList();
        for(Student temp: studentList) {
            Button btn=new Button("Delete");
            btn.setStyle("-fx-background-color: #fed330");
            btn.setPrefWidth(colName.getWidth());
            tmObservableList.add(new StudentTM(temp.getId(),temp.getName(),temp.getContact(),temp.getAddress(),btn));

            btn.setOnAction((event) -> {
                ButtonType yes= new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                ButtonType no= new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure whether you want to delete this Student?",yes,no);
                alert.setTitle("Confirmation Alert");
                /*1.8-->(windows null pointer exception)-->java-1.8*/
                Optional<ButtonType> result = alert.showAndWait();

                if (result.orElse(no)==yes){
                    studentList.remove(temp);
                    loadAllStudents();
                }else{

                }

            });

        }
        tblStudent.setItems(tmObservableList);
    }
    private void clearFields(){
        txtId.clear();
        txtName.clear();
        txtContact.clear();
        txtAddress.clear();
    }

    public void moveToName(ActionEvent actionEvent) {
        txtName.requestFocus();
    }

    public void moveToContact(ActionEvent actionEvent) {
        txtContact.requestFocus();
    }

    public void moveToAddress(ActionEvent actionEvent) {
        txtAddress.requestFocus();
    }

    public void moveToSave(ActionEvent actionEvent) {
        //if (txtId.getText()!=null && txtName.getText()!=null && txtContact.getText()!=null && txtAddress.getText()!=null ){
        saveUpdateOnAction(new ActionEvent());
        //}
        txtId.requestFocus();
    }
}
