package Instances.Incidents;

import Classes.Control.ControlEvents;
import estgconstroi.Incident;

import java.util.Objects;
/*
 * Nome: Willkie Bianchi Parahyba Filho
 * Número: 8230127
 * Turma: LEI1T1
 *
 */

public class IncidentImp implements Incident {
    private String details;
    //é o que vai aparecer antes de mostrar os detalhes do incidente
    private String notificationMessage;

    public IncidentImp(String details, String notificationMessage) {
        this.details = details;
        this.notificationMessage = notificationMessage;

        ControlEvents.addIncident(this);
    }

    @Override
    public String getDetails() {
        return this.details;
    }

    @Override
    public String getNotificationMessage() {
        return notificationMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IncidentImp)) return false;
        IncidentImp that = (IncidentImp) o;
        return Objects.equals(getDetails(), that.getDetails()) && Objects.equals(getNotificationMessage(), that.getNotificationMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDetails(), getNotificationMessage());
    }
}
