package Instances.EventETC;

import Classes.Constantes;
import Classes.Control.ControlClass;
import Classes.Control.ControlEvents;
import Instances.Incidents.IncidentImp;
import Instances.Incidents.MachineAccident;
import estgconstroi.*;
import estgconstroi.enums.EventPriority;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.time.LocalDate;
import java.util.Objects;
/*
 * Nome: Willkie Bianchi Parahyba Filho
 * Número: 8230127
 * Turma: LEI1T1
 *
 */

public class EventExt extends Event {

    private final LocalDate date;
    private final IncidentImp incident;

    public EventExt(EventPriority priority, String title, Employee reporter, ConstructionSite constructionSite, LocalDate date, IncidentImp incident) {
        super(priority, title, reporter, constructionSite);
        this.date = date;
        this.incident = incident;
        ControlEvents.addEventExt(this);
    }

    public static EventExt[] fromJson(String json) throws ParseException {
        JSONParser parser = new JSONParser();

        // Faz o parse da string json em um json object ou array
        Object parsedObject = parser.parse(json);

        //verifica de o parsedobject é um array um um json
        if (parsedObject instanceof org.json.simple.JSONArray) {
            org.json.simple.JSONArray jsonArray = (org.json.simple.JSONArray) parsedObject;
            EventExt[] events = new EventExt[jsonArray.size()];

            // cicla o array e cria um novo EventExt para cada elemento
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject eventObject = (JSONObject) jsonArray.get(i);

                // cria o evento e adiciona ao array
                events[i] = fromjsonToObj(eventObject);
            }

            // retorna o array de eventos
            return events;
        } else if (parsedObject instanceof JSONObject) {
            // trata de um unico evento
            JSONObject rootObject = (JSONObject) parsedObject;
            JSONObject eventObject = (JSONObject) rootObject.get("event");

            EventExt[] singleEvent = new EventExt[1];
            singleEvent[0] = fromjsonToObj(eventObject);
            return singleEvent;
        } else {
            throw new IllegalArgumentException("Invalid JSON format. Expected an array or object.");
        }
    }

    private static EventExt fromjsonToObj(JSONObject eventObject) {

        if (eventObject == null) {
            return null;
        }
        //extrai os dados do evento
        String title = (String) eventObject.get("title");
        String priorityString = (String) eventObject.get("priority");
        EventPriority priority = EventPriority.valueOf(priorityString);
        String details = (String) eventObject.get("details");
        String employeeName = (String) eventObject.get("employeename");
        String constructionSiteName = (String) eventObject.get("constructionsitename");
        LocalDate date = LocalDate.parse((String) eventObject.get("date"));
        String eventType = (String) eventObject.get("eventtype");

        // instancia o reporter e o constructionsite
        Employee reporter = ControlClass.getEmployeeByName(employeeName);
        ConstructionSite constructionSite = ControlClass.getCSByName(constructionSiteName);

        // Create the incident based on the event type
        IncidentImp incident;
        switch (eventType) {
            case "Failure":
                incident = ControlEvents.getFailureByData(details, Constantes.INSURANCE_NOTIFICATION_MESSAGE);
                break;
            case "Accident":
                incident = ControlEvents.getAccidentByData(details, Constantes.INSURANCE_NOTIFICATION_MESSAGE);
                break;
            case "Machine Accident":
                incident = ControlEvents.getMachineAccidentByData(details, Constantes.INSURANCE_NOTIFICATION_MESSAGE);
                break;
            default:
                incident = new IncidentImp(details, Constantes.INSURANCE_NOTIFICATION_MESSAGE);
                break;
        }
        return new EventExt(priority, title, reporter, constructionSite, date, incident);
    }

    public EventExt deepCopy() {
        return new EventExt(this.getPriority(), this.getTitle(), this.getReporter(), this.getConstructionSite(), this.getDate(), this.incident);
    }

    public String getType() {
        if (this.incident instanceof Failure) {
            return "Failure";
        }

        if (this.incident instanceof MachineAccident) {
            return "Machine Acident";
        }

        if (this.incident instanceof Accident) {
            return "Accident";
        }
        return "Incident";
    }

    public IncidentImp getIncident() {
        return this.incident;
    }

    @Override
    public String getDetails() {
        return this.incident.getDetails();
    }

    @Override
    public String getNotificationMessage() {
        return this.incident.getNotificationMessage();
    }

    public LocalDate getDate() {
        return this.date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventExt)) return false;
        EventExt eventExt = (EventExt) o;
        return Objects.equals(getDetails(), eventExt.getDetails()) && Objects.equals(getDate(), eventExt.getDate()) && Objects.equals(getIncident(), eventExt.getIncident());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDetails(), getDate(), getIncident());
    }

    @Override
    public String toString() {
        return "{\n" +
                "  \"groupname\": \"" + Constantes.GROUPNAME + "\",\n" +
                "  \"groupkey\": \"" + Constantes.GROUPKEY + "\",\n" +
                "  \"event\": {\n" +
                "    \"uuid\": \"" + this.getUuid() + "\",\n" +
                "    \"data\": \"" + this.getDate() + "\",\n" +
                "    \"priority\": \"" + this.getPriority() + "\",\n" +
                "    \"eventtype\": \"" + this.getType() + "\",\n" +
                "    \"title\": \"" + this.getTitle() + "\",\n" +
                "    \"constructionsitename\": \"" + this.getConstructionSite().getName() + "\",\n" +
                "    \"details\": \"" + this.getDetails() + "\",\n" +
                "    \"employeename\": \"" + this.getReporter().getName() + "\"\n" +
                "  }\n" +
                "}";
    }

}
