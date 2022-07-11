package lt.codeacademy.data;

import org.bson.types.ObjectId;

public class User {
    private ObjectId id;
    private String name;
    private String surname;
    private double money;

    public User(){

    }

    public User(ObjectId id, String name, String surname, double money) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.money = money;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", money=" + money +
                '}';
    }
}

