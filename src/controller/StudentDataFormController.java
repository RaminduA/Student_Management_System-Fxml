package controller;

import com.jfoenix.controls.JFXTextField;
import view.tablemodel.StudentTM;

public class StudentDataFormController {

    public JFXTextField txtName;

    public void setData(StudentTM tm){
        txtName.setText(tm.getName());
        System.out.println(tm);
    }
}
