package org.example.testesgerais;

import Classes.Constantes;
import Instances.ConstructionSite.ConstructionSiteImp;
import Instances.ConstructionSite.Permit;
import Instances.EventETC.EventExt;
import Instances.Incidents.IncidentImp;
import Instances.Workers.EmployeeExt;
import Instances.Workers.TeamImp;
import estgconstroi.ConstructionSite;
import estgconstroi.InsuranceReporter;
import estgconstroi.enums.EmployeeType;
import estgconstroi.enums.EventPriority;
import estgconstroi.exceptions.TeamException;

import java.time.LocalDate;
/*
 * Nome: Willkie Bianchi Parahyba Filho
 * Número: 8230127
 * Turma: LEI1T1
 *
 */

public class InsuranceReportDemo {
    public static void main(String[] args) {

        // Definir parâmetros para o grupo

        String groupKey = Constantes.GROUPKEY; // Deve ter pelo menos 15 caracteres

        String groupName = Constantes.GROUPNAME;

        EmployeeExt manager = new EmployeeExt("Jhonattan Diogo", EmployeeType.MANAGER);
        Permit permit = new Permit("123456", LocalDate.of(2024, 1, 1));
        TeamImp team1 = new TeamImp("Team A");


        try {

            team1.setLeader(new EmployeeExt("Leader A", EmployeeType.TEAM_LEADER));
        } catch (TeamException e) {
            System.out.println("Falha: ");
            throw new RuntimeException(e);
        }

        ConstructionSite constructionSite = new ConstructionSiteImp(
                "Project A",
                manager,
                team1,
                "Location A",
                LocalDate.of(2023, 1, 1),
                LocalDate.of(2023, 12, 31),
                permit
        );
        // Definir um evento no formato JSON
        IncidentImp incident = new IncidentImp("Telhado caiu", "Novo incidente");
        EventExt evento1 = new EventExt(EventPriority.IMMEDIATE, "Avaria de máquina", Constantes.INSURANCE_REPORT, constructionSite, LocalDate.now(), incident);

        try {

            // 1. Adicionar um evento

            String addResponse = InsuranceReporter.addEvent(evento1.toString());

            System.out.println("Resposta do servidor (adicionar evento):");

            System.out.println(addResponse);

            // 2. Obter todos os eventos do grupo

            String getEventsResponse = InsuranceReporter.getEvents(groupKey, groupName);

            System.out.println("\nEventos retornados pelo servidor:");

            System.out.println(getEventsResponse);

            // 3. Resetar (eliminar) os eventos do grupo

            //String resetResponse = InsuranceReporter.resetEvents(groupKey, groupName);

            //System.out.println("\nResposta do servidor (resetar eventos):");

            //System.out.println(resetResponse);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }
}
