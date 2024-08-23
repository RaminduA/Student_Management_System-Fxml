package view.tablemodel;

import javafx.scene.control.Button;

public class StudentTM {
    private String id;
    private String name;
    private String contact;
    private String address;
    private Button btn;

    public StudentTM() {
    }
    public StudentTM(String id, String name, String contact, String address, Button btn) {
        this.setId(id);
        this.setName(name);
        this.setContact(contact);
        this.setAddress(address);
        this.setBtn(btn);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Button getBtn() {
        return btn;
    }

    public void setBtn(Button btn) {
        this.btn = btn;
    }
}
