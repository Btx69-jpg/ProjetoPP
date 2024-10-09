package org.example.Menus;

import Classes.Control.ControlClass;
import Classes.Control.ControlEvents;
import Classes.Logger.ConstructionSiteLogger;
import Classes.Logger.EventLogger;
import Classes.Logger.ExceptionLogger;
import Instances.EventETC.EventExt;
import Instances.Incidents.AccidentImp;
import Instances.Incidents.FailureImp;
import Instances.Incidents.IncidentImp;
import Instances.Incidents.MachineAccident;
import estgconstroi.ConstructionSite;
import estgconstroi.Employee;
import estgconstroi.Equipment;
import estgconstroi.enums.EventPriority;
import estgconstroi.exceptions.ConstructionSiteException;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import static java.lang.System.in;
/*
 * Nome: Willkie Bianchi Parahyba Filho
 * Número: 8230127
 * Turma: LEI1T1
 *
 */

public class OptionsLoggers {
    protected static String userDataPath = null;
    protected static String userEventPath = null;

    // Carregar dados de obras de construção do arquivo
    public static void loadDataFromFile(int options) {
        String file;
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        switch (options) {
            case 1:
                System.out.println("Insira o caminho do arquivo para carregar os sites de construção:");
                try {
                    file = reader.readLine();
                    ConstructionSiteLogger.loadConstructionSites(file);
                    System.out.println("Sites de construção carregados com sucesso do arquivo: " + file);
                } catch (IOException | ParseException | ConstructionSiteException e) {
                    ExceptionLogger.logException(e);
                    System.err.println("Erro ao carregar os sites de construção: " + e.getMessage());
                }
                break;
            case 2:
                try {
                    ConstructionSiteLogger.loadConstructionSites(ConstructionSiteLogger.LOG_FILE);
                    System.out.println("Sites de construção carregados com sucesso do arquivo base.");
                } catch (IOException | ParseException | ConstructionSiteException e) {
                    ExceptionLogger.logException(e);
                    System.err.println("Erro ao carregar os sites de construção do arquivo base: " + e.getMessage());
                }
                break;
        }
    }


    // Criar e salvar um novo evento
    public static void createAndSaveNewEvent() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        try {
            //alterar para criar todos os tipos de incident
            System.out.println("Digite os detalhes do novo evento:");
            System.out.println("Título:");
            String title = reader.readLine();

            System.out.println("Tipo de incidente(MACHINE ACCIDENT, ACCIDENT, FAILURE, INCIDENT):");
            String eventType = reader.readLine();

            System.out.println("Prioridade (LOW, MEDIUM, HIGH, CRITICAL):");
            String priorityStr = reader.readLine();
            EventPriority priority = ControlEvents.getPriority(priorityStr.toUpperCase());

            System.out.println("Nome do site de construção:");
            String siteName = reader.readLine();
            ConstructionSite site = ControlClass.getCSByName(siteName);

            System.out.println("Nome do funcionário que reportou:");
            String reporterName = reader.readLine();
            Employee reporter = ControlClass.getEmployeeByName(reporterName);

            System.out.println("Data do evento (YYYY-MM-DD):");
            LocalDate date = LocalDate.parse(reader.readLine());

            System.out.println("Detalhes do evento:");
            String details = reader.readLine();

            IncidentImp incident;
            if (eventType.equalsIgnoreCase("MACHINE ACCIDENT")) {
                System.out.println("Nome da máquina que causou o acidente:");
                String machineName = reader.readLine();
                Equipment machine = site.getEquipments().getEquipment(machineName)[0];
                System.out.println("Nome do funcionario acidentado:");
                String empname = reader.readLine();
                Employee injuriedEmployee = ControlClass.getEmployeeByName(empname);
                incident = new MachineAccident(details, "Acidente causado por máquina", machine, injuriedEmployee);
            } else if (eventType.equalsIgnoreCase("ACCIDENT")) {
                System.out.println("Nome do funcionario acidentado:");
                String empname = reader.readLine();
                Employee injuriedEmployee = ControlClass.getEmployeeByName(empname);
                incident = new AccidentImp(details, "Acidente registrado", injuriedEmployee);
            } else if (eventType.equalsIgnoreCase("FAILURE")) {
                System.out.println("Nome da maquina que falhou:");
                String machineName = reader.readLine();
                Equipment machine = site.getEquipments().getEquipment(machineName)[0];
                incident = new FailureImp(details, "Falha registrada", machine);
            } else {
                incident = new IncidentImp(details, "Incidente registrado");
            }

            EventExt event = new EventExt(priority, title, reporter, site, date, incident);

            // Salvando o evento
            EventLogger.addEvent(event, userEventPath);
            System.out.println("Evento salvo com sucesso!");

        } catch (IOException | IllegalArgumentException e) {
            ExceptionLogger.logException(e);
            System.err.println("Erro ao criar o evento: " + e.getMessage());
        } catch (ParseException e) {
            ExceptionLogger.logException(e);
            throw new RuntimeException(e);
        }
    }

    // Menu de gerenciamento de construção sites
    public static void loadDataMenu() {
        int userOp = -1;
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        do {
            System.out.println("Escolha uma opção:");
            System.out.println("1 - Carregar dados a partir de um arquivo específico");
            System.out.println("2 - Carregar dados do diretorio local");
            System.out.println("3 - Criar arquivo no diretorio local");
            System.out.println("0 - Avançar");

            try {
                userOp = Menu.getIntInput();
                if (userOp < 0 || userOp > 2) {
                    System.out.println("Opção inválida!");
                }

                switch (userOp) {
                    case 1:
                        System.out.println("Insira o caminho do arquivo:");
                        String file = reader.readLine();
                        loadDataFromFile(1);
                        break;

                    case 2:
                        userDataPath = ConstructionSiteLogger.LOG_FILE;
                        loadDataFromFile(2);
                        break;

                    case 3:
                        userDataPath = ConstructionSiteLogger.LOG_FILE;
                        loadDataFromFile(2);
                        break;

                    case 0:
                        break;
                }
            } catch (IOException | NumberFormatException e) {
                ExceptionLogger.logException(e);
                System.err.println("Erro na entrada: " + e.getMessage());
            }
        } while (userOp != 0);


    }

    public static void showExceptionLGOptions() {
        System.out.println("1. Mostrar todas as exceções");
        System.out.println("2. Mostrar exceções entre duas datas");
        System.out.println("3. Exportar exceções para JSON");
        System.out.println("4. Limpar log de exceções");
        System.out.println("5. Mostrar exceções de arquivo");
        System.out.println("6. Voltar ao menu principal");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            int option = Integer.parseInt(reader.readLine());
            switch (option) {
                case 1:
                    showAllExceptions();
                    break;
                case 2:
                    showExceptionsBetweenDates();
                    break;
                case 3:
                    exportExceptionsToJSON();
                    break;
                case 4:
                    clearExceptionLog();
                    break;
                case 5:
                    showExceptionsFromFile();
                    break;
                case 6:

                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        } catch (IOException | NumberFormatException e) {
            ExceptionLogger.logException(e);
            System.err.println("Erro na entrada: " + e.getMessage());
        }
    }

    // Método para mostrar todas as exceções do log
    public static void showAllExceptions() {
        ExceptionLogger.showLogFromFile();
    }

    // Método para mostrar exceções entre duas datas
    public static void showExceptionsBetweenDates() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            System.out.println("Digite a data de início (yyyy-MM-dd): ");
            String startDateInput = reader.readLine();
            Date startDate = dateFormat.parse(startDateInput);

            System.out.println("Digite a data de término (yyyy-MM-dd): ");
            String endDateInput = reader.readLine();
            Date endDate = dateFormat.parse(endDateInput);

            ExceptionLogger.showExceptionsBetween(startDate, endDate);

        } catch (IOException e) {
            ExceptionLogger.logException(e);
            System.err.println("Erro ao ler as datas: " + e.getMessage());
        } catch (java.text.ParseException e) {
            ExceptionLogger.logException(e);
            System.err.println("Erro ao parsear a data: " + e.getMessage());
        }
    }

    // Método para exportar exceções para um arquivo JSON
    public static void exportExceptionsToJSON() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.println("Digite o caminho para salvar o arquivo JSON: ");
            String jsonFilePath = reader.readLine();
            ExceptionLogger.exportLogToJSON(jsonFilePath);
        } catch (IOException e) {
            ExceptionLogger.logException(e);
            System.err.println("Erro ao exportar para JSON: " + e.getMessage());
        }
    }

    // Método para limpar o arquivo de log de exceções
    public static void clearExceptionLog() {
        ExceptionLogger.clearLogFile();
    }

    // Método para mostrar exceções do arquivo de log
    public static void showExceptionsFromFile() {
        ExceptionLogger.showLogFromFile();
    }
}
