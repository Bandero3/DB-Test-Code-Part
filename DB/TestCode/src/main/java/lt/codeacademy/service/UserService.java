package lt.codeacademy.service;

import com.mongodb.MongoClient;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import lt.codeacademy.client.MongoClientProvider;
import lt.codeacademy.data.User;
import org.bson.Document;

import java.util.Scanner;

public class UserService {
    MongoClient client = MongoClientProvider.getMongoClient();
    MongoDatabase mongoDB = client.getDatabase("LBA");
    private MongoCollection<User> collection = mongoDB.getCollection("user", User.class);
    private MongoCollection<Document> userCollection = mongoDB.getCollection("user");

    public void selectAction(Scanner scanner) {
        String action;
        action = scanner.nextLine();
        do {
            menu();
            switch (action) {
                case "1" -> register(scanner);
                case "2" -> login(scanner);
                case "3" -> System.out.println("Programa baige darba");
                default -> System.out.println("Tokio veiksmo nera");
            }
        } while (!action.equals("3"));
    }

    private void menu() {
        System.out.println("""
                1 - Registruotis
                2 - Prisijungti
                3 - Uzdaryti applikacija
                """);
    }

    private void register(Scanner scanner) {
        System.out.println("Iveskite savo varda");
        String name = scanner.nextLine();

        System.out.println("Iveskite savo pavarde");
        String surname = scanner.nextLine();

        System.out.println("Irasykite kiek turite pinigu0");
        Double money = scanner.nextDouble();

        collection.insertOne(new User(null, name, surname, money));
    }

    private void login(Scanner scanner) {
        System.out.println("Iveskite savo varda");
        String name = scanner.nextLine();

        System.out.println("IIveskite savo pavarde");
        String surname = scanner.nextLine();

        User user = getUser(name, surname);
        if (user != null) {
            userAction(name, surname, user, scanner);
        }
    }

    private User getUser(String name, String surname) {
        FindIterable<User> users = collection.find();
        for (User user : users) {
            if (user.getName().equals(name) && user.getSurname().equals(surname)) {
                return user;
            } else {
                System.out.println("Tokio vartotojo nera");
            }
        }
        return null;
    }

    private void userAction(String name, String surname, User user, Scanner scanner) {
        String action;
        action = scanner.nextLine();
        do {
            userMenu();
            switch (action) {
                case "1" -> transferMoney(user, scanner);
                case "2" -> checkBalance(name, surname);
                case "3" -> System.out.println("");
                default -> System.out.println("Tokio pasirinkimo nera");
            }
        } while (!action.equals("3"));
    }

    private void userMenu() {
        System.out.println("""
                1 - Pervesti pinigus
                2 - Patikrinti saskaita
                3 - Grizti atgal
                """);
    }

    private void checkBalance(String name, String surname) {
        System.out.println(getUser(name, surname).getMoney());
    }

    private void transferMoney(User user, Scanner scanner) {
        System.out.println("Iveskite varda vartotojo kuriam norite persiusti pinigus");
        String name = scanner.nextLine();

        System.out.println("Iveskite pavarde vartotojo kuriam norite persiusti pinigus");
        String surname = scanner.nextLine();

        User sendTo = getUser(name, surname);
        if (sendTo != null) {
            System.out.println("Kiek norite pervesti?");
            Double money = scanner.nextDouble();

            if (sendTo.getMoney() > money) {
                double newMoney = sendTo.getMoney() + money;
                double minusMoney = sendTo.getMoney() - money;
                userCollection.updateOne(Filters.eq("name", user.getName()), Updates.set("money", minusMoney));
                userCollection.updateOne(Filters.eq("name", name), Updates.set("money", newMoney));
                System.out.println("Pervedete Pinigus");
            } else {
                System.out.println("Nepakankamai pinigu turite");
            }
        }
    }
}

