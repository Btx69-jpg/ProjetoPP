package Classes.Logger;

import Classes.Control.ControlClass;
import Classes.Control.ControlEvents;
import Instances.EventETC.EventExt;
import Instances.Incidents.AccidentImp;
import Instances.Incidents.FailureImp;
import Instances.Incidents.IncidentImp;
import Instances.Incidents.MachineAccident;
import estgconstroi.ConstructionSite;
import estgconstroi.Employee;
import estgconstroi.Equipment;
import estgconstroi.enums.EventPriority;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.time.LocalDate;

/*
 * Nome: Willkie Bianchi Parahyba Filho
 * Número: 8230127
 * Turma: LEI1T1
 *
 */
public class EventLogger {
    public static final String LOG_FILE = "event_log.json"; // Caminho para o arquivo JSON

    // Método para adicionar um único evento sem sobrescrever o arquivo
    public static void addEvent(EventExt event, String path) throws IOException, ParseException {
        if (event == null) {
            return;
        }
        JSONArray eventsArray = loadExistingEvents(path);

        JSONObject jsonObject = createEventJson(event);
        eventsArray.add(jsonObject);

        // Sobrescreve o arquivo com os novos dados (eventos antigos + novo evento)
        try (FileWriter file = new FileWriter(path)) {
            file.write(eventsArray.toJSONString());
            file.flush();
        }
    }

    // Método para sobrescrever o arquivo JSON com um novo array de eventos
    public static void saveEvents(EventExt[] events, String path) throws IOException {
        JSONArray eventsArray = new JSONArray();

        // Adicionar cada evento ao JSONArray
        for (EventExt event : events) {
            if (event == null) {
                break;
            }
            JSONObject jsonObject = createEventJson(event);
            eventsArray.add(jsonObject);
        }

        // Escrever no arquivo JSON, sobrescrevendo o conteúdo anterior
        try (FileWriter file = new FileWriter(path)) {
            file.write(eventsArray.toJSONString());
            file.flush();
        }
    }

    // Método auxiliar para criar um objeto JSON a partir de um EventExt
    private static JSONObject createEventJson(EventExt event) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("uuid", event.getUuid());
        jsonObject.put("priority", event.getPriority().toString());
        jsonObject.put("title", event.getTitle());
        jsonObject.put("date", event.getDate().toString());
        jsonObject.put("constructionsitename", event.getConstructionSite().getName());
        jsonObject.put("employeename", event.getReporter().getName());
        jsonObject.put("details", event.getDetails());

        // Salvar o tipo de incidente e detalhes do incidente
        if (event.getIncident() instanceof AccidentImp) {
            jsonObject.put("incidentType", "AccidentImp");
            jsonObject.put("notificationMessage", event.getIncident().getNotificationMessage());
            jsonObject.put("injuriedEmp", ((AccidentImp) event.getIncident()).getEmployee().getName());
        } else if (event.getIncident() instanceof FailureImp) {
            jsonObject.put("incidentType", "FailureImp");
            jsonObject.put("notificationMessage", event.getIncident().getNotificationMessage());
            jsonObject.put("failedEquipment", ((FailureImp) event.getIncident()).getEquipment().getName());
        } else if (event.getIncident() instanceof MachineAccident) {
            jsonObject.put("incidentType", "MachineAccident");
            jsonObject.put("notificationMessage", event.getIncident().getNotificationMessage());
            jsonObject.put("injuriedEmp", ((MachineAccident) event.getIncident()).getEmployee().getName());
            jsonObject.put("equipment", ((MachineAccident) event.getIncident()).getEquipment().getName());
        }

        return jsonObject;
    }

    // Método para carregar os eventos existentes do arquivo JSON
    private static JSONArray loadExistingEvents(String path) throws IOException, ParseException {
        File file = new File(path);
        if (!file.exists()) {
            return new JSONArray(); // Retorna um array vazio se o arquivo não existir
        }

        // Ler o conteúdo do arquivo JSON
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader(file)) {
            return (JSONArray) parser.parse(reader);
        }
    }

    // Método para ler e reconstruir os eventos do arquivo JSON
    public static EventExt[] loadEvents(String path) throws IOException, ParseException {
        File file = new File(path);

        if (!file.exists()) {
            System.out.println("Arquivo de log de eventos não encontrado.");
            return new EventExt[0]; // Retorna um array vazio
        }

        // Primeiro, contar o número de eventos para alocar o array
        int eventCount = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while (reader.readLine() != null) {
                eventCount++;
            }
        }

        EventExt[] events = new EventExt[eventCount];
        int index = 0;

        // Agora, ler o arquivo novamente e parsear os eventos
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            JSONParser parser = new JSONParser();

            while ((line = reader.readLine()) != null) {
                JSONObject jsonObject = (JSONObject) parser.parse(line);

                // Extrair as informações básicas do evento
                String uuid = (String) jsonObject.get("uuid");
                String priorityString = (String) jsonObject.get("priority");
                EventPriority priority = ControlEvents.getPriority(priorityString);
                String title = (String) jsonObject.get("title");
                LocalDate date = LocalDate.parse((String) jsonObject.get("date"));
                String constructionSiteName = (String) jsonObject.get("constructionsitename");
                String employeeName = (String) jsonObject.get("employeename");
                String details = (String) jsonObject.get("details");

                // Buscar objetos Employee e ConstructionSite
                Employee reporter = ControlClass.getEmployeeByName(employeeName); // Busca o Employee pelo nome
                ConstructionSite constructionSite = ControlClass.getCSByName(constructionSiteName); // Busca o ConstructionSite pelo nome

                // Identificar o tipo de incidente e criar o incidente correspondente
                String incidentType = (String) jsonObject.get("incidentType");
                IncidentImp incident = null;

                if ("AccidentImp".equals(incidentType)) {
                    String injuriedEmpName = (String) jsonObject.get("injuriedEmp");
                    Employee injuriedEmployee = ControlClass.getEmployeeByName(injuriedEmpName);
                    incident = new AccidentImp(details, (String) jsonObject.get("notificationMessage"), injuriedEmployee);
                } else if ("FailureImp".equals(incidentType)) {
                    String failedEquipmentFJ = (String) jsonObject.get("failedEquipment");
                    Equipment equipment = ControlClass.getCSByName(constructionSiteName).getEquipments().getEquipment(failedEquipmentFJ)[0];
                    incident = new FailureImp(details, (String) jsonObject.get("notificationMessage"), equipment);
                } else if ("MachineAccident".equals(incidentType)) {
                    String injuriedEmpName = (String) jsonObject.get("injuriedEmp");
                    String equipmentName = (String) jsonObject.get("equipment");
                    Employee injuriedEmployee = ControlClass.getEmployeeByName(injuriedEmpName);
                    Equipment equipment = ControlClass.getCSByName(constructionSiteName).getEquipments().getEquipment(equipmentName)[0];
                    incident = new MachineAccident(details, (String) jsonObject.get("notificationMessage"), equipment, injuriedEmployee);
                }

                // Criar o objeto EventExt com os dados do JSON
                EventExt event = new EventExt(priority, title, reporter, constructionSite, date, incident);
                events[index++] = event;
            }
        }

        return events;
    }
}
