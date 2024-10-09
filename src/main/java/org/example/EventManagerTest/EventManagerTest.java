package org.example.EventManagerTest;

import Enums.InspectionStatus;
import Instances.ConstructionSite.ConstructionSiteImp;
import Instances.ConstructionSite.Permit;
import Instances.Equipments.EquipmentImp;
import Instances.Equipments.Inspection;
import Instances.EventETC.EventExt;
import Instances.Incidents.AccidentImp;
import Instances.Incidents.FailureImp;
import Instances.Incidents.IncidentImp;
import Instances.Incidents.MachineAccident;
import Instances.Management.EventManagerImp;
import Instances.Workers.EmployeeExt;
import Instances.Workers.TeamImp;
import estgconstroi.Event;
import estgconstroi.Team;
import estgconstroi.enums.EmployeeType;
import estgconstroi.enums.EquipmentStatus;
import estgconstroi.enums.EquipmentType;
import estgconstroi.enums.EventPriority;
import estgconstroi.exceptions.ConstructionSiteException;
import estgconstroi.exceptions.TeamException;

import java.time.LocalDate;
/*
 * Nome: Willkie Bianchi Parahyba Filho
 * Número: 8230127
 * Turma: LEI1T1
 *
 */

public class EventManagerTest {

    public static void main(String[] args) {
        // Example data
        EmployeeExt reporter = new EmployeeExt("Mary Jane", EmployeeType.WORKER);
        EmployeeExt manager = new EmployeeExt("Jhonattan Diogo", EmployeeType.MANAGER);
        Permit permit = new Permit("123456", LocalDate.of(2024, 1, 1));
        TeamImp team1 = new TeamImp("Team A");
        Team team2 = new TeamImp("Team B");


        try {
            System.out.println("Sucesso");
            team1.setLeader(new EmployeeExt("Leader A", EmployeeType.TEAM_LEADER));
        } catch (TeamException e) {
            System.out.println("Falha: ");
            throw new RuntimeException(e);
        }

        try {
            System.out.println("Sucesso");
            team1.addEmployees(reporter);
        } catch (TeamException e) {
            System.out.println("Falha: ");
            throw new RuntimeException(e);
        }

        try {
            System.out.println("Sucesso");
            team2.setLeader(new EmployeeExt("Leader B", EmployeeType.TEAM_LEADER));
        } catch (TeamException e) {
            System.out.println("Falha: ");
            throw new RuntimeException(e);
        }

        // Criar instância de ConstructionSiteImp
        ConstructionSiteImp constructionSite = new ConstructionSiteImp(
                "Project A",
                manager,
                team1,
                "Location A",
                LocalDate.of(2023, 1, 1),
                LocalDate.of(2023, 12, 31),
                permit
        );
        Inspection insp = new Inspection(LocalDate.of(2027, 1, 1), InspectionStatus.APROVED);
        try {
            constructionSite.addEquipment(new EquipmentImp("Empilhadeira", EquipmentType.TRANSPORT, insp, EquipmentStatus.OPERATIVE));
        } catch (ConstructionSiteException e) {
            throw new RuntimeException(e);
        }
        try {
            constructionSite.addTeam(team2);
        } catch (ConstructionSiteException e) {
            System.out.println("Falha: ");
            throw new RuntimeException(e);
        }

        IncidentImp incident = new IncidentImp("Lider da equipe 1 partiu o braço e morreu", "Notification message");
        IncidentImp machineAccident = new MachineAccident("Lider da equipe 1 bateu a empilhadeira partiu o braço e morreu", "Notification message", constructionSite.getEquipments().getEquipment("Empilhadeira")[0], team1.getLeader());
        IncidentImp failure = new FailureImp("Pneu da empilhadeira furou", "Notification message", constructionSite.getEquipments().getEquipment("Empilhadeira")[0]);
        IncidentImp accident = new AccidentImp("Lider da equipe 1 morreu novamente", "Notification message", team1.getLeader());

        LocalDate date = LocalDate.of(2023, 10, 1);


        // Create an EventExt object
        EventExt originalEvent = new EventExt(EventPriority.LOW, "Incident", reporter, constructionSite, date, incident);

        //cria um evento com o incidente do tipo machineAccident
        EventExt machineAccidentEvent = new EventExt(EventPriority.HIGH, "Machine Accident", reporter, constructionSite, LocalDate.now(), machineAccident);

        //cria um evento com o incidente do tipo Accident
        EventExt accidentEvent = new EventExt(EventPriority.HIGH, "Machine Accident", reporter, constructionSite, LocalDate.now(), failure);

        //cria um evento com o incidente do tipo machineAccident
        EventExt failureAccidentEvent = new EventExt(EventPriority.HIGH, "Machine Accident", reporter, constructionSite, LocalDate.now(), accident);


        // Create an EventManagerImp instance
        EventManagerImp eventManager = new EventManagerImp();

        // Add a Notifier
        try {
            //adicionando quem reportou para receber o evento tambem
            eventManager.addNotifier(reporter.getNotifier());
            System.out.println("Notifier added successfully.");
        } catch (Exception e) {
            System.out.println("Error adding notifier: " + e.getMessage());
        }

        // Report an event
        try {
            eventManager.reportEvent(originalEvent);
            System.out.println("Event reported successfully.");
        } catch (Exception e) {
            System.out.println("Error reporting event: " + e.getMessage());
        }

        //reporta o accident de maquina
        try {
            eventManager.reportEvent(machineAccidentEvent);
            System.out.println("Event reported successfully.");
        } catch (Exception e) {
            System.out.println("Error reporting event: " + e.getMessage());
        }

        try {
            eventManager.reportEvent(accidentEvent);
            System.out.println("Event reported successfully.");
        } catch (Exception e) {
            System.out.println("Error reporting event: " + e.getMessage());
        }

        try {
            eventManager.reportEvent(failureAccidentEvent);
            System.out.println("Event reported successfully.");
        } catch (Exception e) {
            System.out.println("Error reporting event: " + e.getMessage());
        }
        // Get events by priority
        EventPriority priority = EventPriority.HIGH;
        Event[] eventsByPriority = eventManager.getEvent(priority);
        System.out.println("Events with priority " + priority + ":");
        for (Event event : eventsByPriority) {
            if (event != null) {
                System.out.println(event.toString());
            }
        }

        // Get events by date
        LocalDate fromDate = LocalDate.of(2023, 9, 30);
        LocalDate toDate = LocalDate.of(2023, 10, 2);
        Event[] eventsByDateRange = eventManager.getEvent(fromDate, toDate);
        System.out.println("Events between " + fromDate + " and " + toDate + ":");

        for (Event event : eventsByDateRange) {
            if (event != null) {
                System.out.println(event.toString());
            }
        }

        // Remove an event
        try {
            eventManager.removeEvent(accidentEvent);
            System.out.println("Event removed successfully.");
        } catch (Exception e) {
            System.out.println("Error removing event: " + e.getMessage());
        }

        // Remove a notifier
        try {
            eventManager.removeNotifier(reporter.getNotifier());
            System.out.println("Notifier removed successfully.");
        } catch (Exception e) {
            System.out.println("Error removing notifier: " + e.getMessage());
        }

        // Get all events
        Event[] allEvents = eventManager.getAllEvents();
        System.out.println("All events:");
        if (allEvents != null) {
            for (Event event : allEvents) {
                if (event == null) {
                    break;
                }
                System.out.println(event.toString());
            }
        }

        // Remove all events
        //eventManager.removeAllEvents();
        //System.out.println("All events removed.");
    }
}