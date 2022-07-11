import lt.codeacademy.service.UserService;

import java.util.Scanner;

public class Applikacija {
    public static void main(String[] args) {
        UserService service = new UserService();
        Scanner scanner = new Scanner(System.in);
        service.selectAction(scanner);
    }
}
