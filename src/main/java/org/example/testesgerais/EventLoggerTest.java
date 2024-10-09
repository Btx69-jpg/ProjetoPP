package org.example.testesgerais;

import Classes.Control.ControlEvents;
import Classes.Logger.EventLogger;
import Enums.InspectionStatus;
import Instances.ConstructionSite.ConstructionSiteImp;
import Instances.ConstructionSite.Permit;
import Instances.Equipments.EquipmentImp;
import Instances.Equipments.Inspection;
import Instances.EventETC.EventExt;
import Instances.Workers.EmployeeExt;
import Instances.Workers.TeamImp;
import estgconstroi.Team;
import estgconstroi.enums.EmployeeType;
import estgconstroi.enums.EquipmentStatus;
import estgconstroi.enums.EquipmentType;
import estgconstroi.exceptions.TeamException;

import java.time.LocalDate;
/*
 * Nome: Willkie Bianchi Parahyba Filho
 * Número: 8230127
 * Turma: LEI1T1
 *
 */

public class EventLoggerTest {
    public static void main(String[] args) {
        try {
            // Criando funcionários e equipamentos para testes
            EmployeeExt employee1 = new EmployeeExt("John Doe", EmployeeType.WORKER);
            EmployeeExt employee2 = new EmployeeExt("Jane Smith", EmployeeType.MANAGER);
            Inspection inspection = new Inspection(LocalDate.now(), InspectionStatus.APROVED);
            EquipmentImp equipment1 = new EquipmentImp("Excavator", EquipmentType.TOOLS, inspection, EquipmentStatus.OPERATIVE);
            EquipmentImp equipment2 = new EquipmentImp("Bulldozer", EquipmentType.TOOLS, inspection, EquipmentStatus.OPERATIVE);

            // Criando um site de construção fictício
            EmployeeExt manager = new EmployeeExt("Mary Jane", EmployeeType.MANAGER);
            Permit permit = new Permit("P12345", LocalDate.of(2025, 5, 15));
            ConstructionSiteImp site = new ConstructionSiteImp(employee2, permit, "Obra A", LocalDate.of(2023, 1, 10), LocalDate.of(2024, 12, 15), "Rua A");

            EmployeeExt manager2 = new EmployeeExt("Jhonattan Diogo", EmployeeType.MANAGER);
            Permit permit2 = new Permit("123456", LocalDate.of(2024, 1, 1));
            TeamImp team1 = new TeamImp("Team A");
            Team team2 = new TeamImp("Team B");

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
            team1.addEmployees(employee1);

            // Criar instância de ConstructionSiteImp
            ConstructionSiteImp constructionSite2 = new ConstructionSiteImp(
                    "Project A",
                    manager2,
                    team1,
                    "Location A",
                    LocalDate.of(2023, 1, 1),
                    LocalDate.of(2023, 12, 31),
                    permit2
            );
        /*
            // Criando alguns incidentes e eventos
            IncidentImp accident = new AccidentImp("Queda", "Funcionário caiu de uma altura", employee1);
            IncidentImp failure = new FailureImp("Falha de motor", "O motor da grua parou de funcionar", equipment2);
            IncidentImp machineAccident = new MachineAccident("Acidente com Bulldozer", "Funcionário foi ferido", equipment1, employee2);

            EventExt event1 = new EventExt(EventPriority.HIGH, "Acidente grave", employee1, constructionSite2, LocalDate.now(), "Detalhes do acidente", accident);
            EventExt event2 = new EventExt(EventPriority.NORMAL, "Falha de equipamento", employee2, constructionSite2, LocalDate.now(), "Detalhes da falha", failure);
            EventExt event3 = new EventExt(EventPriority.IMMEDIATE, "Acidente com Máquina", employee2, constructionSite2, LocalDate.now(), "Detalhes do acidente", machineAccident);

            EventLogger.saveEvent(event1, "events.json");
            EventLogger.saveEvent(event2, "events.json");
            EventLogger.saveEvent(event3, "events.json");

         */
            constructionSite2.addEquipment(equipment1);
            constructionSite2.addEquipment(equipment2);


            EventLogger.loadEvents("events.json");
            EventExt[] loadedEvents = ControlEvents.getAllEvents();
            for (EventExt event : loadedEvents) {
                if (event == null) {
                    break;
                }
                System.out.println(event);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
