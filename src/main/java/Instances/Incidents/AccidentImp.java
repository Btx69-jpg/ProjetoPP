package Instances.Incidents;

import Classes.Control.ControlEvents;
import estgconstroi.Accident;
import estgconstroi.Employee;

import java.util.Objects;
/*
 * Nome: Willkie Bianchi Parahyba Filho
 * Número: 8230127
 * Turma: LEI1T1
 *
 */

public class AccidentImp extends IncidentImp implements Accident {
    private Employee injuriedEmp;

    public AccidentImp(String details, String notificationMessage, Employee Injuried) {
        super(details, notificationMessage);
        this.injuriedEmp = Injuried;

        ControlEvents.addAccident(this);
    }

    @Override
    public Employee getEmployee() {
        return injuriedEmp;
    }

    @Override
    public String getDetails() {
        return super.getDetails();
    }

    @Override
    public String getNotificationMessage() {
        return super.getNotificationMessage();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Accident)) {
            return false;
        }
        Accident other = (Accident) obj;
        return super.getDetails().equals(other.getDetails()) && this.injuriedEmp.equals(other.getEmployee()) && super.getNotificationMessage().equals(other.getNotificationMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), injuriedEmp);
    }

    @Override
    public String toString() {
        return "Accident: " + super.getDetails() + " - " + this.injuriedEmp.getName() + " - " + super.getNotificationMessage();
    }
}
