package org.example.Menus;

import Classes.Logger.EventLogger;
import Classes.Logger.ExceptionLogger;
import Instances.EventETC.NotifierImp;
import estgconstroi.Notifier;
import estgconstroi.exceptions.EventManagerException;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static java.lang.System.in;
import static org.example.Menus.Menu.eventManager;
import static org.example.Menus.Menu.scanner;

/*
 * Nome: Willkie Bianchi Parahyba Filho
 * Número: 8230127
 * Turma: LEI1T1
 *
 */
public class EventMenu {
    // Adiciona um novo notificador ao EventManager
    public static void addNotifierToEventManager() {
        try {
            System.out.println("Insira o UUID do novo notificador:");
            String notifierId = scanner.nextLine();
            Notifier notifier = new NotifierImp(notifierId);  // Cria um novo Notifier
            eventManager.addNotifier(notifier);
            System.out.println("Notificador adicionado com sucesso!");
        } catch (EventManagerException e) {
            ExceptionLogger.logException(e);
            System.err.println("Erro ao adicionar o notificador: " + e.getMessage());
        }
    }

    // Remove um notificador do EventManager
    public static void removeNotifierFromEventManager() {
        try {
            System.out.println("Insira o UUID do notificador para remover:");
            String notifierId = scanner.nextLine();
            Notifier notifier = new NotifierImp(notifierId);  // Cria um Notifier com o ID especificado
            eventManager.removeNotifier(notifier);
            System.out.println("Notificador removido com sucesso!");
        } catch (EventManagerException e) {
            ExceptionLogger.logException(e);
            System.err.println("Erro ao remover o notificador: " + e.getMessage());
        }
    }

    // Relatar um evento ao EventManager
    /*
    public static void reportEventToEventManager() {
        try {
            Event event =
            eventManager.reportEvent(event);
            System.out.println("Evento reportado com sucesso ao EventManager!");
        } catch (EventManagerException e) {
            ExceptionLogger.logException(e);
            System.err.println("Erro ao reportar o evento: " + e.getMessage());
        }
    }

     */

    // Gerenciamento de notificadores e eventos no EventManager
    public static void manageEventManager() {
        int userOp = -1;

        do {
            System.out.println("Escolha uma opção para gerenciar o EventManager:");
            System.out.println("1 - Adicionar um notificador");
            System.out.println("2 - Remover um notificador");
            System.out.println("3 - Relatar um evento");
            System.out.println("0 - Voltar ao menu principal");

            userOp = Menu.getIntInput();
            switch (userOp) {
                case 1:
                    addNotifierToEventManager();
                    break;
                case 2:
                    removeNotifierFromEventManager();
                    break;
                case 3:
                    //reportEventToEventManager();
                    break;
                case 0:
                    System.out.println("Voltando ao menu principal...");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        } while (userOp != 0);
    }

    // Método para carregar o menu principal de opções de dados
    public static void loadEventMenu() throws IOException {
        int userOp = -1;
        String file;
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        do {

            System.out.println("Escolha uma opção:");
            System.out.println("1 - Carregar eventos a partir de um arquivo específico");
            System.out.println("2 - Carregar eventos do arquivo base");
            System.out.println("3 - Gerenciar o EventManager");
            System.out.println("0 - Voltar ao menu principal");

            userOp = Menu.getIntInput();
            switch (userOp) {
                case 1:
                    System.out.println("Insira o caminho do arquivo para carregar eventos:");
                    String eventFile = reader.readLine();
                    OptionsLoggers.userEventPath = eventFile;
                    try {
                        EventLogger.loadEvents(eventFile);
                        System.out.println("Eventos carregados com sucesso do arquivo: " + eventFile);
                    } catch (IOException | ParseException e) {
                        ExceptionLogger.logException(e);
                        System.err.println("Erro ao carregar os eventos: " + e.getMessage());
                    }
                    break;
                case 2:
                    try {
                        OptionsLoggers.userEventPath = EventLogger.LOG_FILE;
                        EventLogger.loadEvents(OptionsLoggers.userEventPath);
                        System.out.println("Eventos carregados com sucesso do arquivo base.");

                    } catch (IOException | ParseException e) {
                        ExceptionLogger.logException(e);
                        System.err.println("Erro ao carregar os eventos do arquivo base: " + e.getMessage());
                    }
                    break;
                case 3:
                    manageEventManager();
                    break;
                case 0:
                    System.out.println("Avançando...");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        } while (userOp != 0);
    }
}
