package org.example.Menus;

import Classes.Control.ControlClass;
import Classes.Logger.ConstructionSiteLogger;
import Classes.Logger.ExceptionLogger;
import Enums.InspectionStatus;
import Instances.ConstructionSite.ConstructionSiteImp;
import Instances.ConstructionSite.Permit;
import Instances.Equipments.EquipmentImp;
import Instances.Equipments.Inspection;
import Instances.Workers.EmployeeExt;
import Instances.Workers.TeamImp;
import estgconstroi.ConstructionSite;
import estgconstroi.Employee;
import estgconstroi.Equipment;
import estgconstroi.Team;
import estgconstroi.enums.EmployeeType;
import estgconstroi.enums.EquipmentStatus;
import estgconstroi.enums.EquipmentType;
import estgconstroi.exceptions.ConstructionSiteException;
import estgconstroi.exceptions.ConstructionSiteManagerException;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.time.LocalDate;

import static org.example.Menus.Menu.*;

/*
 * Nome: Willkie Bianchi Parahyba Filho
 * Número: 8230127
 * Turma: LEI1T1
 *
 */
public class CsOptions {
    public static void runCsMenu() {
        while (true) {
            System.out.println("\n--- Construction Site Menu ---");
            System.out.println("1. Criar novo campo de obras");
            System.out.println("2. Visualizar campos de obras existentes");
            System.out.println("3. Editar um campo de obras");
            System.out.println("4. Gestão de Sites");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");

            int option = getIntInput();

            switch (option) {
                case 1:
                    createConstructionSite();
                    break;
                case 2:
                    viewConstructionSites();
                    break;
                case 3:
                    editConstructionSite();
                    break;
                case 4:
                    runManagementMenu();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    // Criar um novo campo de obras
    private static void createConstructionSite() {
        System.out.println("\n--- Criar Novo Campo de Obras ---");

        System.out.print("Nome do Campo de Obras: ");
        scanner.nextLine();
        String name = scanner.nextLine();

        System.out.print("Nome do Gestor: ");
        String managerName = scanner.nextLine();

        Employee manager = new EmployeeExt(managerName, EmployeeType.MANAGER);

        System.out.print("Localização: ");
        String location = scanner.nextLine();

        System.out.print("Data de Início das obras(YYYY-MM-DD): ");
        LocalDate startDate = getDateInput();

        System.out.print("Data de Fim das obras (YYYY-MM-DD): ");
        LocalDate endDate = getDateInput();

        System.out.print("Número da Licença: ");
        String permitNumber = scanner.nextLine();

        System.out.print("Data de Expiração da Licença (YYYY-MM-DD): ");
        LocalDate permitExpirationDate = getDateInput();

        Permit permit = new Permit(permitNumber, permitExpirationDate);

        ConstructionSiteImp site = new ConstructionSiteImp(manager, permit, name, startDate, endDate, location);

        try {
            Menu.manager.add(site);  // Adiciona ao Sitemanager
            System.out.println("Campo de Obras criado com sucesso e adicionado à gestão!\n");
            ConstructionSiteLogger.addConstructionSite(site, OptionsLoggers.userDataPath);
        } catch (ConstructionSiteManagerException e) {
            ExceptionLogger.logException(e);
            System.out.println("Erro ao adicionar campo de obras: " + e.getMessage());
        } catch (ConstructionSiteException e) {
            ExceptionLogger.logException(e);
            System.out.println("Erro ao criar campo de obras: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("Erro ao salvar dados do campo de obras: " + e.getMessage());
            ExceptionLogger.logException(e);
            throw new RuntimeException(e);
        } catch (ParseException e) {
            ExceptionLogger.logException(e);
            System.out.println("Erro ao salvar dados do campo de obras: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    // Visualizar campos de obras
    private static void viewConstructionSites() {
        System.out.println("\n--- Visualizar Campos de Obras ---");
        ConstructionSite[] sites = ControlClass.getAllCs();

        if (sites == null || sites.length == 0) {
            System.out.println("Nenhum campo de obras encontrado.");
            return;
        }

        for (int i = 0; i < sites.length; i++) {
            if (sites[i] == null) {
                break;
            }
            System.out.println((i + 1) + ". " + sites[i].getName() + " - " + sites[i].getLocation());
        }
    }

    // Editar um campo de obras
    private static void editConstructionSite() {
        System.out.println("\n--- Editar Campo de Obras ---");

        // Mostrar campos de obras
        ConstructionSite[] sites = Menu.manager.getConstructionSitesWithPermitExpired();
        if (sites == null || sites.length == 0) {
            System.out.println("Nenhum campo de obras disponível para editar.");
            return;
        }

        for (int i = 0; i < sites.length; i++) {
            System.out.println((i + 1) + ". " + sites[i].getName());
        }

        System.out.print("Escolha o campo de obras para editar: ");
        int siteIndex = getIntInput() - 1;
        if (siteIndex < 0 || siteIndex >= sites.length) {
            System.out.println("Opção inválida.");
            return;
        }

        ConstructionSiteImp site = (ConstructionSiteImp) sites[siteIndex];

        while (true) {
            System.out.println("\n--- Editar: " + site.getName() + " ---");
            System.out.println("1. Adicionar Equipe");
            System.out.println("2. Adicionar Equipamento");
            System.out.println("3. Remover Equipe");
            System.out.println("4. Atualizar Gestor");
            System.out.println("5. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            int option = getIntInput();

            switch (option) {
                case 1:
                    addTeam(site);
                    break;
                case 2:
                    addEquipment(site);
                    break;
                case 3:
                    removeTeam(site);
                    break;
                case 4:
                    updateManager(site);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    // Menu de gestão de sites
    private static void runManagementMenu() {
        while (true) {
            System.out.println("\n--- Gestão de Sites ---");
            System.out.println("1. Adicionar novo campo de obras ao sistema");
            System.out.println("2. Visualizar equipas em trabalho");
            System.out.println("3. Visualizar equipas ociosas");
            System.out.println("4. Visualizar equipamentos em uso");
            System.out.println("5. Visualizar equipamentos ociosos");
            System.out.println("6. Visualizar campos de obras com licenças expiradas");
            System.out.println("7. Verificar se todos os campos de obras são válidos");
            System.out.println("8. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            int option = getIntInput();

            switch (option) {
                case 1:
                    createConstructionSite();
                    break;
                case 2:
                    displayWorkingTeams();
                    break;
                case 3:
                    displayIddleTeams();
                    break;
                case 4:
                    displayEquipmentsInUse();
                    break;
                case 5:
                    displayIddleEquipments();
                    break;
                case 6:
                    displaySitesWithExpiredPermits();
                    break;
                case 7:
                    checkSitesValidity();
                    break;
                case 8:
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    // Exibir equipes em trabalho
    private static void displayWorkingTeams() {
        Team[] teams = Menu.manager.getWorkingTeams();
        System.out.println("\n--- Equipas em Trabalho ---");
        if (teams.length == 0) {
            System.out.println("Nenhuma equipe em trabalho.");
        } else {
            for (Team team : teams) {
                System.out.println(team.getName());
            }
        }
    }

    // Exibir equipes ociosas
    private static void displayIddleTeams() {
        Team[] teams = Menu.manager.getIddleTeams();
        System.out.println("\n--- Equipas Ociosas ---");
        if (teams.length == 0) {
            System.out.println("Nenhuma equipe ociosa.");
        } else {
            for (Team team : teams) {
                System.out.println(team.getName());
            }
        }
    }

    // Exibir equipamentos em uso
    private static void displayEquipmentsInUse() {
        Equipment[] equipments = Menu.manager.getEquipmentsInUse();
        System.out.println("\n--- Equipamentos em Uso ---");
        if (equipments.length == 0) {
            System.out.println("Nenhum equipamento em uso.");
        } else {
            for (Equipment equipment : equipments) {
                System.out.println(equipment.getName());
            }
        }
    }

    // Exibir equipamentos ociosos
    private static void displayIddleEquipments() {
        Equipment[] equipments = Menu.manager.getIddleEquipments();
        System.out.println("\n--- Equipamentos Ociosos ---");
        if (equipments.length == 0) {
            System.out.println("Nenhum equipamento ocioso.");
        } else {
            for (Equipment equipment : equipments) {
                System.out.println(equipment.getName());
            }
        }
    }

    // Exibir campos de obras com licença expirada
    private static void displaySitesWithExpiredPermits() {
        ConstructionSite[] sites = Menu.manager.getConstructionSitesWithPermitExpired();
        System.out.println("\n--- Campos de Obras com Licenças Expiradas ---");
        if (sites == null || sites.length == 0) {
            System.out.println("Nenhum campo de obras com licença expirada.");
        } else {
            for (ConstructionSite site : sites) {
                System.out.println(site.getName() + " - Licença expirada em: " + site.getPermitExpirationDate());
            }
        }
    }

    // Verificar validade dos sites
    private static void checkSitesValidity() {
        boolean valid = Menu.manager.isValid();
        if (valid) {
            System.out.println("Todos os campos de obras são válidos.");
        } else {
            System.out.println("Alguns campos de obras têm problemas.");
        }
    }

    // Adicionar equipe
    private static void addTeam(ConstructionSiteImp site) {
        System.out.print("Nome da Equipe: ");
        String teamName = scanner.nextLine();
        TeamImp team = new TeamImp(teamName);

        try {
            site.addTeam(team);
            System.out.println("Equipe adicionada com sucesso.");
        } catch (ConstructionSiteException e) {
            ExceptionLogger.logException(e);
            System.out.println("Erro ao adicionar equipe: " + e.getMessage());
        }
    }

    // Adicionar equipamento
    private static void addEquipment(ConstructionSiteImp site) {
        System.out.print("Nome do Equipamento: ");
        String equipmentName = scanner.nextLine();
        System.out.print("Tipo do Equipamento(Transport , Tools, Materials, Equipment, Heavy Duty, Other): ");
        String equipmentType = scanner.nextLine();
        System.out.print("Status do Equipamento(Operative , Manutenção , Inoperativa): ");
        String equipmentStatus = scanner.nextLine();
        System.out.print("Insira a data da proxima inspeção(YYYY-MM-DD): ");
        LocalDate inspectionExpirationDate = getDateInput();
        System.out.print("Insira o status da Inspeção anterior(Aprovado , Reprovado): ");
        String inspSts = scanner.nextLine();
        Inspection inspection = new Inspection(inspectionExpirationDate, InspectionStatus.valueOf(inspSts));
        EquipmentImp equipment = new EquipmentImp(equipmentName, EquipmentType.valueOf(equipmentType), inspection, EquipmentStatus.valueOf(equipmentStatus));

        try {
            site.addEquipment(equipment);
            System.out.println("Equipamento adicionado com sucesso.");
        } catch (ConstructionSiteException e) {
            ExceptionLogger.logException(e);
            System.out.println("Erro ao adicionar equipamento: " + e.getMessage());
        }
    }

    // Remover equipe
    private static void removeTeam(ConstructionSiteImp site) {
        System.out.print("Nome da Equipe a remover: ");
        String teamName = scanner.nextLine();

        try {
            Team[] teams = site.getTeams(teamName);
            if (teams.length > 0) {
                site.removeTeam(teams[0]);
                System.out.println("Equipe removida com sucesso.");
            } else {
                System.out.println("Equipe não encontrada.");
            }
        } catch (ConstructionSiteException e) {
            ExceptionLogger.logException(e);
            System.out.println("Erro ao remover equipe: " + e.getMessage());
        }
    }

    // Atualizar gestor
    private static void updateManager(ConstructionSiteImp site) {
        System.out.print("Nome do novo Gestor: ");
        String managerName = scanner.nextLine();
        Employee newManager = new EmployeeExt(managerName, EmployeeType.MANAGER);

        try {
            site.setResponsible(newManager);
            System.out.println("Gestor atualizado com sucesso.");
        } catch (ConstructionSiteException e) {
            ExceptionLogger.logException(e);
            System.out.println("Erro ao atualizar gestor: " + e.getMessage());
        }
    }


}

