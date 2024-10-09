package Instances.Incidents;

import Classes.Control.ControlEvents;
import estgconstroi.Equipment;
import estgconstroi.Failure;

import java.util.Objects;
/*
 * Nome: Willkie Bianchi Parahyba Filho
 * Número: 8230127
 * Turma: LEI1T1
 *
 */

public class FailureImp extends IncidentImp implements Failure {

    private Equipment failedEquipment;


    public FailureImp(String details, String notificationMessage, Equipment failedEquipment) {
        super(details, notificationMessage);
        this.failedEquipment = failedEquipment;

        ControlEvents.addFailure(this);
    }

    @Override
    public Equipment getEquipment() {
        return this.failedEquipment;
    }

    @Override
    public String getDetails() {
        return super.getDetails();
    }

    @Override
    public String getNotificationMessage() {
        return super.getNotificationMessage();
    }

    public String toString() {
        return "Failure: " + super.getDetails() + " - " + this.failedEquipment + " - " + super.getNotificationMessage();
    }

    public String toJson() {
        return "{\"details\":\"" + super.getDetails() + "\",\"equipment\":\"" + this.failedEquipment + "\",\"notificationMessage\":\"" + super.getNotificationMessage() + "\"}";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Failure)) {
            return false;
        }
        Failure other = (Failure) obj;
        return super.getDetails().equals(other.getDetails()) && this.failedEquipment.equals(other.getEquipment()) && super.getNotificationMessage().equals(other.getNotificationMessage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), failedEquipment);
    }
}
