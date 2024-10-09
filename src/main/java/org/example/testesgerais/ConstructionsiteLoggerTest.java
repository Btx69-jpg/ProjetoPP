package org.example.testesgerais;

import Classes.Logger.ConstructionSiteLogger;
import Instances.ConstructionSite.ConstructionSiteImp;
import estgconstroi.exceptions.ConstructionSiteException;
import org.json.simple.parser.ParseException;

import java.io.IOException;
/*
 * Nome: Willkie Bianchi Parahyba Filho
 * Número: 8230127
 * Turma: LEI1T1
 *
 */

public class ConstructionsiteLoggerTest {
    public static void main(String[] args) {
        try {
//            // Instanciar um ConstructionSite
//            EmployeeExt manager = new EmployeeExt("Mary Jane", EmployeeType.MANAGER);
//            Permit permit = new Permit("P12345", LocalDate.of(2025, 5, 15));
//            ConstructionSiteImp site = new ConstructionSiteImp(manager, permit, "Obra A", LocalDate.of(2023, 1, 10), LocalDate.of(2024, 12, 15), "Rua A");
//
//            EmployeeExt manager2 = new EmployeeExt("Jhonattan Diogo", EmployeeType.MANAGER);
//            Permit permit2 = new Permit("123456", LocalDate.of(2024, 1, 1));
//            TeamImp team1 = new TeamImp("Team A");
//            Team team2 = new TeamImp("Team B");
//
//            try {
//                System.out.println("Sucesso");
//                team1.setLeader(new EmployeeExt("Leader A", EmployeeType.TEAM_LEADER));
//            } catch (TeamException e) {
//                System.out.println("Falha: ");
//                throw new RuntimeException(e);
//            }
//            System.out.println("Adicionando Lider da equipe B: ");
//            try {
//                System.out.println("Sucesso");
//                team2.setLeader(new EmployeeExt("Leader B", EmployeeType.TEAM_LEADER));
//            } catch (TeamException e) {
//                System.out.println("Falha: ");
//                throw new RuntimeException(e);
//            }

            // Criar instância de ConstructionSiteImp
//            ConstructionSiteImp constructionSite2 = new ConstructionSiteImp(
//                    "Project A",
//                    manager2,
//                    team1,
//                    "Location A",
//                    LocalDate.of(2023, 1, 1),
//                    LocalDate.of(2023, 12, 31),
//                    permit2
//            );
            // Salvar o site em um arquivo JSON


            //ConstructionSiteImp[] sites = new ConstructionSiteImp[2];
            //sites[0] = site;
            //sites[1] = constructionSite2;
            // ConstructionSiteLogger.saveConstructionSites(sites, "construction_sites.json");

            // Carregar os sites do arquivo JSON
            ConstructionSiteImp[] Loadedsites = ConstructionSiteLogger.loadConstructionSites("construction_sites.json");

            // Exibir os sites carregados
            for (ConstructionSiteImp s : Loadedsites) {
                System.out.println(s);
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        } catch (ConstructionSiteException e) {
            throw new RuntimeException(e);
        }
    }
}