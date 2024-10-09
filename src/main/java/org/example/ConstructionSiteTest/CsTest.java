package org.example.ConstructionSiteTest;

import Classes.Constantes;
import Enums.InspectionStatus;
import Instances.ConstructionSite.ConstructionSiteImp;
import Instances.ConstructionSite.Permit;
import Instances.Equipments.EquipmentImp;
import Instances.Equipments.EquipmentsImp;
import Instances.Equipments.Inspection;
import Instances.Workers.EmployeeExt;
import Instances.Workers.TeamImp;
import estgconstroi.ConstructionSite;
import estgconstroi.Equipments;
import estgconstroi.Team;
import estgconstroi.enums.EmployeeType;
import estgconstroi.enums.EquipmentStatus;
import estgconstroi.enums.EquipmentType;
import estgconstroi.exceptions.ConstructionSiteException;
import estgconstroi.exceptions.TeamException;

import java.time.LocalDate;
import java.util.Arrays;
/*
 * Nome: Willkie Bianchi Parahyba Filho
 * Número: 8230127
 * Turma: LEI1T1
 *
 */

public class CsTest {
    public static void main(String[] args) {
        // Configurar objetos necessários
        EmployeeExt manager = new EmployeeExt("Jhonattan Diogo", EmployeeType.MANAGER);
        Permit permit = new Permit("123456", LocalDate.of(2024, 1, 1));
        TeamImp team1 = new TeamImp("Team A");
        Team team2 = new TeamImp("Team B");

        System.out.println("Adicionando Lider da equipe A: ");
        try {
            System.out.println("Sucesso");
            team1.setLeader(new EmployeeExt("Leader A", EmployeeType.TEAM_LEADER));
        } catch (TeamException e) {
            System.out.println("Falha: ");
            throw new RuntimeException(e);
        }
        System.out.println("Adicionando Lider da equipe B: ");
        try {
            System.out.println("Sucesso");
            team2.setLeader(new EmployeeExt("Leader B", EmployeeType.TEAM_LEADER));
        } catch (TeamException e) {
            System.out.println("Falha: ");
            throw new RuntimeException(e);
        }

        // Criar instância de ConstructionSiteImp
        ConstructionSite constructionSite = new ConstructionSiteImp(
                "Project A",
                manager,
                team1,
                "Location A",
                LocalDate.of(2023, 1, 1),
                LocalDate.of(2023, 12, 31),
                permit
        );

        // Testar todos os métodos

        // Testar getName()
        System.out.println("Nome do projeto: " + constructionSite.getName());

        // Testar getLocation()
        System.out.println("Localização do projeto: " + constructionSite.getLocation());

        // Testar getPermit()
        System.out.println("Permissão do projeto: " + constructionSite.getPermit());

        // Testar getPermitExpirationDate()
        System.out.println("Data de expiração da permissão: " + constructionSite.getPermitExpirationDate());

        // Testar getStartDate()
        System.out.println("Data de início do projeto: " + constructionSite.getStartDate());

        // Testar getEndDate()
        System.out.println("Data de término do projeto: " + constructionSite.getEndDate());

        // Testar setPermit()
        constructionSite.setPermit("789012", LocalDate.of(2025, 1, 1));
        System.out.println("Nova permissão: " + constructionSite.getPermit());

        // Testar getResponsible()
        System.out.println("Responsável pelo projeto: " + constructionSite.getResponsible());

        // Testar setResponsible()
        try {
            EmployeeExt newManager = new EmployeeExt("Mary Jane", EmployeeType.MANAGER);
            constructionSite.setResponsible(newManager);
            System.out.println("Novo responsável pelo projeto: " + constructionSite.getResponsible());
        } catch (ConstructionSiteException e) {
            System.out.println("Erro ao definir novo responsável: " + e.getMessage());
        }

        // Testar addTeam()
        try {
            Team team3 = new TeamImp("Team C");
            team3.setLeader(new EmployeeExt("Leader C", EmployeeType.TEAM_LEADER));
            constructionSite.addTeam(team3);
            System.out.println("Time adicionado: " + team3.getName());
        } catch (ConstructionSiteException e) {
            System.out.println("Erro ao adicionar time: " + e.getMessage());
        }

        // Testar removeTeam()
        try {
            constructionSite.removeTeam(team1);
            System.out.println("Time removido: " + team1.getName());
        } catch (ConstructionSiteException e) {
            System.out.println("Erro ao remover time: " + e.getMessage());
        }

        // Testar getTeams(String)
        Team[] teamsWithName = constructionSite.getTeams("Team B");
        System.out.println("Times com nome 'Team B': " + Arrays.toString(teamsWithName));

        // Testar getTeams()
        Team[] allTeams = constructionSite.getTeams();
        System.out.println("Todos os times: " + Arrays.toString(allTeams));

        // Testar isValid()
        System.out.println("Validade do projeto: " + constructionSite.isValid());

        // Testar getEquipments()
        Equipments allEquipments = constructionSite.getEquipments();
        System.out.println("Todos os equipamentos: " + allEquipments);

        // Testar toString()
        System.out.println(constructionSite.toString());

        Inspection inspection = new Inspection(LocalDate.now(), InspectionStatus.APROVED);
        EquipmentsImp equipmentsImp = new EquipmentsImp();
        // Add equipment until the array needs to be resized
        for (int i = 0; i < Constantes.INITIAL_ARRAY_SIZE; i++) {
            EquipmentImp equipment = new EquipmentImp("Equipment " + i, EquipmentType.MATERIALS, inspection, EquipmentStatus.OPERATIVE);
            try {
                equipmentsImp.addEquipment(equipment);
            } catch (ConstructionSiteException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            EquipmentImp equipment = new EquipmentImp("Equipment " + Constantes.INITIAL_ARRAY_SIZE, EquipmentType.TOOLS, inspection, EquipmentStatus.OPERATIVE);
            equipmentsImp.addEquipment(equipment);
        } catch (ConstructionSiteException e) {
            throw new RuntimeException(e);
        }

        try {
            team1.setEquipments(equipmentsImp);
        } catch (TeamException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Equipamentos da equipe A: " + team1.getEquipments());

        System.out.println("Equipamentos da equipe A pelo Construction Site: " + constructionSite.getEquipments().toString());

    }
}
