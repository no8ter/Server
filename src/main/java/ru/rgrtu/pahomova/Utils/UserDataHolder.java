package ru.rgrtu.pahomova.Utils;

public class UserDataHolder {

    public Integer id;
    public String firstName;
    public String secondName;
    public String lastName;
    public String phone;
    public String profCode;
    public boolean isWorker;
    public String username;

    public UserDataHolder(Integer id, String firstName, String secondName, String lastName, String phone) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.phone = phone;
    }

    public UserDataHolder(Integer id, String firstName, String secondName, String lastName, String phone, String profCode) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.phone = phone;
        this.profCode = profCode;
    }

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getProfCode() {
        return profCode;
    }

    public boolean isWorker() {
        return isWorker;
    }
}
