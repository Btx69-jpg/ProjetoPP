package org.example.Menus;

import Classes.Control.ControlClass;
import Classes.Control.ControlEvents;
import Classes.Logger.ConstructionSiteLogger;
import Classes.Logger.EventLogger;
import Instances.Management.ConstructionSiteManagerImp;
import Instances.Management.EventManagerImp;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
/*
 * Nome: Willkie Bianchi Parahyba Filho
 * Número: 8230127
 * Turma: LEI1T1
 *
 */

public class Menu {
    protected static Scanner scanner = new Scanner(System.in);
    protected static ConstructionSiteManagerImp manager = new ConstructionSiteManagerImp();  // Instancia o gerenciador
    protected static EventManagerImp eventManager = new EventManagerImp();

    public static void main(String[] args) {

        try {
            mainMenu();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void mainMenu() throws IOException {

        OptionsLoggers.loadDataMenu();

        System.out.println("Bem vindo ao menu principal!");
        int userOp;

        do {
            System.out.println("Escolha uma opção:");
            System.out.println("1 - Ver opções do log de exceções");
            System.out.println("2 - Carregar Eventos");
            System.out.println("3 - Construction Site Menu ");
            System.out.println("0 - Encerrar programa");
            userOp = getIntInput();
            if (userOp < 0 || userOp > 3) {
                System.out.println("Opção inválida!");
            }
            switch (userOp) {
                case 1:
                    OptionsLoggers.showExceptionLGOptions();
                    break;
                case 2:
                    EventMenu.loadEventMenu();
                    break;
                case 3:
                    CsOptions.runCsMenu();
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        } while (userOp != 0);

        EventLogger.saveEvents(ControlEvents.getAllEvents(), OptionsLoggers.userEventPath);
        ConstructionSiteLogger.saveConstructionSites(ControlClass.getAllCs(), OptionsLoggers.userDataPath);
        System.out.println("Programa encerrado!");

    }

    // Funções auxiliares
    protected static int getIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.print("Entrada inválida, tente novamente: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    protected static LocalDate getDateInput() {
        while (true) {
            try {
                return LocalDate.parse(scanner.nextLine());
            } catch (DateTimeParseException e) {
                System.out.print("Data inválida, tente novamente (YYYY-MM-DD): ");
            }
        }
    }
}
