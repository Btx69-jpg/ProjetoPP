package org.example.ConstructionSiteTest;

import Enums.InspectionStatus;
import Instances.ConstructionSite.ConstructionSiteImp;
import Instances.ConstructionSite.Permit;
import Instances.Equipments.EquipmentImp;
import Instances.Equipments.Inspection;
import Instances.Management.ConstructionSiteManagerImp;
import Instances.Workers.EmployeeExt;
import Instances.Workers.TeamImp;
import estgconstroi.*;
import estgconstroi.enums.EmployeeType;
import estgconstroi.enums.EquipmentStatus;
import estgconstroi.enums.EquipmentType;
import estgconstroi.exceptions.ConstructionSiteException;
import estgconstroi.exceptions.ConstructionSiteManagerException;
import estgconstroi.exceptions.TeamException;

import java.time.LocalDate;
/*
 * Nome: Willkie Bianchi Parahyba Filho
 * Número: 8230127
 * Turma: LEI1T1
 *
 */

public class CSManagerTest {
    public static void main(String[] args) {
        // Criar uma instância de ConstructionSiteManager
        ConstructionSiteManager Csmanager = new ConstructionSiteManagerImp();

        Inspection inspection = new Inspection(LocalDate.now(), InspectionStatus.APROVED);
        // Criar alguns equipamentos
        Equipment equipment1 = new EquipmentImp("Excavator", EquipmentType.MATERIALS, inspection, EquipmentStatus.OPERATIVE);
        Equipment equipment2 = new EquipmentImp("Bulldozer", EquipmentType.MATERIALS, inspection, EquipmentStatus.OPERATIVE);

        // Criar algumas equipes
        Team team1 = new TeamImp("Team Alpha");
        Team team2 = new TeamImp("Team Beta");
        Team team3 = new TeamImp("Team Teta");

        // Criar alguns gerentes e líderes
        Employee leader1 = new EmployeeExt("Jhonnattan Dobberman", EmployeeType.TEAM_LEADER);
        Employee leader2 = new EmployeeExt("Mary Jane", EmployeeType.TEAM_LEADER);

        Employee manager1 = new EmployeeExt("Bob Sponge", EmployeeType.MANAGER);
        Employee manager2 = new EmployeeExt("Black Batman", EmployeeType.MANAGER);

        // Adicionar lideres às equipes
        System.out.println("Adicionando Lider1: ");
        try {
            System.out.println("Adicionado com sucesso");
            team1.setLeader(leader1);
        } catch (TeamException e) {
            System.out.println("Erro: ");
            throw new RuntimeException(e);
        }

        System.out.println("Adicionando Lider2: ");
        try {
            System.out.println("Adicionado com sucesso");
            team2.setLeader(leader2);
        } catch (TeamException e) {
            System.out.println("Erro: ");
            throw new RuntimeException(e);
        }

        // Criar alguns funcionários
        Employee employee1 = new EmployeeExt("Alice", EmployeeType.WORKER);
        Employee employee2 = new EmployeeExt("Manuel", EmployeeType.WORKER);

        // Adicionar funcionários às equipes
        System.out.println("Adicionando Funcionario1 na equipe 1: ");
        try {
            System.out.println("Adicionado com sucesso");
            team1.addEmployees(employee1);
        } catch (TeamException e) {
            System.out.println("Erro: ");
            throw new RuntimeException(e);
        }

        System.out.println("Adicionando Funcionario2 na equipe 2: ");
        try {
            System.out.println("Adicionado com sucesso");
            team2.addEmployees(employee2);
        } catch (TeamException e) {
            System.out.println("Erro: ");
            throw new RuntimeException(e);
        }

        //criação de permissões
        Permit permit1 = new Permit("123456", LocalDate.of(2024, 1, 1));
        Permit permit2 = new Permit("123456", LocalDate.of(2024, 12, 1));

        // Criar alguns sites de construção
        ConstructionSite constructionSite1 = new ConstructionSiteImp(
                "Campo de obras 1",
                manager1,
                team1,
                "Location A",
                LocalDate.of(2023, 2, 1),
                LocalDate.of(2024, 12, 31),
                permit1
        );

        ConstructionSite constructionSite2 = new ConstructionSiteImp(
                "Campo de obras 2",
                manager2,
                team2,
                "Location B",
                LocalDate.of(2023, 1, 1),
                LocalDate.of(2024, 10, 31),
                permit2
        );

        try {
            constructionSite1.addTeam(team2);
        } catch (ConstructionSiteException e) {
            throw new RuntimeException(e);
        }

        try {
            // Adicionar sites de construção ao gerenciador
            Csmanager.add(constructionSite1);
            Csmanager.add(constructionSite2);
        } catch (ConstructionSiteManagerException e) {
            e.printStackTrace();
        }


        System.out.println("\nIdle Teams:");
        Team[] idleTeams = Csmanager.getIddleTeams();
        if (idleTeams == null) {
            System.out.println(" não há times parados:");
        } else {
            for (Team team : idleTeams) {
                if (team != null) {
                    System.out.println(team.getName());
                }
            }
        }

        // Testar os métodos do gerenciador
        System.out.println("Working Teams:");
        Team[] workingTeams = Csmanager.getWorkingTeams();
        if (idleTeams == null) {
            System.out.println(" não há times parados:");
        } else {
            for (Team team : workingTeams) {
                if (team != null) {
                    System.out.println(team.getName());
                }
            }
        }

        System.out.println("\nIn Use Equipments:");
        Equipment[] inUseEquipments = Csmanager.getEquipmentsInUse();
        for (Equipment equipment : inUseEquipments) {
            if (equipment == null) {
                break;
            }
            System.out.println(equipment.toString());
        }

        System.out.println("\nIdle Equipments:");
        Equipment[] idleEquipments = Csmanager.getIddleEquipments();
        for (Equipment equipment : idleEquipments) {
            System.out.println(equipment.toString());
        }

        System.out.println("\nConstruction Sites with Permit Expired:");
        ConstructionSite[] expiredSites = Csmanager.getConstructionSitesWithPermitExpired();
        for (ConstructionSite site : expiredSites) {
            if (site == null) {
                break;
            }
            System.out.println(site.getName());
        }

        System.out.println("\nIs Valid:");
        boolean isValid = Csmanager.isValid();
        System.out.println(isValid);
    }
}
