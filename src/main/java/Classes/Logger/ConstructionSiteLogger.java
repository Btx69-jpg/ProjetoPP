package Classes.Logger;

import Instances.ConstructionSite.ConstructionSiteImp;
import Instances.ConstructionSite.Permit;
import Instances.Workers.EmployeeExt;
import Instances.Workers.TeamImp;
import estgconstroi.Employee;
import estgconstroi.Team;
import estgconstroi.enums.EmployeeType;
import estgconstroi.exceptions.ConstructionSiteException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
/*
 * Nome: Willkie Bianchi Parahyba Filho
 * Número: 8230127
 * Turma: LEI1T1
 *
 */

public class ConstructionSiteLogger {
    public static final String LOG_FILE = "construction_site_log.json"; // Caminho do arquivo JSON

    // Método para adicionar novos ConstructionSiteImp ao arquivo JSON sem sobrescrever os dados
    public static void addConstructionSite(ConstructionSiteImp site, String filePath) throws IOException, ParseException, ConstructionSiteException {
        // Carregar os dados existentes do arquivo, se houver
        JSONArray sitesArray = loadExistingSites(filePath);

        // Criar um JSONObject para o novo site de construção
        JSONObject jsonObject = createConstructionSiteJson(site);

        // Adicionar o novo site ao array existente
        sitesArray.add(jsonObject);

        // Escrever o arquivo JSON com o novo conteúdo (incluindo os dados anteriores)
        try (FileWriter file = new FileWriter(filePath)) {
            file.write(sitesArray.toJSONString());
            file.flush();
        }
    }

    // Método para sobrescrever o arquivo JSON com um novo array de ConstructionSiteImp
    public static void saveConstructionSites(ConstructionSiteImp[] sites, String filePath) throws IOException {
        JSONObject obj = new JSONObject();
        JSONArray sitesArray = new JSONArray();

        // Adicionar cada site ao JSONArray
        for (ConstructionSiteImp site : sites) {
            if (site == null) {
                break;
            }
            JSONObject jsonObject = createConstructionSiteJson(site);
            sitesArray.add(jsonObject);
        }

        // Sobrescrever o arquivo JSON com o novo array de sites de construção
        try (FileWriter file = new FileWriter(filePath)) {
            file.write(sitesArray.toJSONString());
            file.flush();
        }
    }

    // Método auxiliar para criar um JSONObject a partir de um ConstructionSiteImp
    private static JSONObject createConstructionSiteJson(ConstructionSiteImp site) {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("name", site.getName());
        jsonObject.put("manager", site.getResponsible().getName());
        jsonObject.put("location", site.getLocation());
        jsonObject.put("startDate", site.getStartDate().toString());
        jsonObject.put("endDate", site.getEndDate().toString());
        jsonObject.put("permit", site.getPermit());
        jsonObject.put("permitExpiration", site.getPermitExpirationDate().toString());

        // Salvando as equipes
        JSONArray teamsArray = new JSONArray();
        for (Team team : site.getTeams()) {
            if (team != null) {
                JSONObject teamObject = new JSONObject();
                teamObject.put("teamName", team.getName());
                teamObject.put("leader", team.getLeader().getName());
                teamObject.put("leaderType", team.getLeader().getType().toString());

                // Salvando os membros da equipe
                JSONArray employeesArray = new JSONArray();
                for (Employee employee : team.getEmployees()) {
                    if (employee != null) {
                        JSONObject empObject = new JSONObject();
                        empObject.put("name", employee.getName());
                        empObject.put("type", employee.getType().toString());
                        employeesArray.add(empObject);
                    }
                }
                teamObject.put("employees", employeesArray);
                teamsArray.add(teamObject);
            }
        }
        jsonObject.put("teams", teamsArray);
        return jsonObject;
    }

    // Método para carregar os dados existentes do arquivo JSON, ou criar um novo array vazio se o arquivo não existir
    private static JSONArray loadExistingSites(String filePath) throws IOException, ParseException {
        File file = new File(filePath);
        if (!file.exists()) {
            return new JSONArray(); // Retorna um array vazio se o arquivo não existir
        }

        // Ler o conteúdo do arquivo JSON
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader(file)) {
            return (JSONArray) parser.parse(reader);
        }
    }

    // Método para carregar instâncias de ConstructionSiteImp a partir do arquivo JSON
    public static ConstructionSiteImp[] loadConstructionSites(String filePath) throws IOException, ParseException, ConstructionSiteException {
        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("Arquivo de log de sites de construção não encontrado.");
            return new ConstructionSiteImp[0]; // Retorna um array vazio
        }

        // Ler o conteúdo do arquivo JSON
        JSONParser parser = new JSONParser();
        JSONArray sitesArray;
        try (FileReader reader = new FileReader(file)) {
            sitesArray = (JSONArray) parser.parse(reader);
        }

        ConstructionSiteImp[] sites = new ConstructionSiteImp[sitesArray.size()];
        int index = 0;

        // Reconstruir os sites de construção a partir do JSON
        for (Object obj : sitesArray) {
            JSONObject jsonObject = (JSONObject) obj;

            // Extrair as informações básicas do site de construção
            String name = (String) jsonObject.get("name");
            String managerName = (String) jsonObject.get("manager");
            String location = (String) jsonObject.get("location");
            LocalDate startDate = LocalDate.parse((String) jsonObject.get("startDate"));
            LocalDate endDate = LocalDate.parse((String) jsonObject.get("endDate"));
            String permitId = (String) jsonObject.get("permit");
            LocalDate permitExpiration = LocalDate.parse((String) jsonObject.get("permitExpiration"));

            // Instanciar o objeto EmployeeExt para o manager
            EmployeeExt manager = new EmployeeExt(managerName, EmployeeType.MANAGER);

            // Instanciar o objeto Permit
            Permit permit = new Permit(permitId, permitExpiration);

            // Criar o site de construção
            ConstructionSiteImp site = new ConstructionSiteImp(manager, permit, name, startDate, endDate, location);

            // Carregar as equipes
            JSONArray teamsArray = (JSONArray) jsonObject.get("teams");
            for (Object teamObj : teamsArray) {
                JSONObject teamObject = (JSONObject) teamObj;
                String teamName = (String) teamObject.get("teamName");
                String leaderName = (String) teamObject.get("leader");

                // Instanciar o líder da equipe como EmployeeExt
                EmployeeExt teamLeader = new EmployeeExt(leaderName, EmployeeType.TEAM_LEADER);
                TeamImp team = new TeamImp(teamName);
                team.setLeader(teamLeader);

                // Carregar os membros da equipe
                JSONArray employeesArray = (JSONArray) teamObject.get("employees");
                for (Object empObj : employeesArray) {
                    JSONObject empObject = (JSONObject) empObj;
                    String employeeName = (String) empObject.get("name");
                    EmployeeType employeeType = EmployeeType.valueOf((String) empObject.get("type"));

                    // Instanciar EmployeeExt para cada membro da equipe
                    EmployeeExt employee = new EmployeeExt(employeeName, employeeType);
                    team.addEmployees(employee);
                }

                // Adicionar a equipe ao site de construção
                site.addTeam(team);
            }

            // Adicionar o site de construção ao array
            sites[index++] = site;
        }

        return sites;
    }
}