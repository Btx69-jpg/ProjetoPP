package Instances.Incidents;

import Classes.Control.ControlEvents;
import estgconstroi.Employee;
import estgconstroi.Equipment;
import estgconstroi.Incident;

import java.util.Objects;
/*
 * Nome: Willkie Bianchi Parahyba Filho
 * Número: 8230127
 * Turma: LEI1T1
 *
 */

public class MachineAccident extends AccidentImp implements Incident {
    private Equipment equipment;

    public MachineAccident(String details, String notificationMessage, Equipment equipment, Employee injuriedEmployee) {
        super(details, notificationMessage, injuriedEmployee);
        this.equipment = equipment;

        ControlEvents.addMachineAccident(this);
    }

    public Equipment getEquipment() {
        return this.equipment;
    }

    public String toString() {
        return "MachineAccident: " + super.getDetails() + " - " + this.equipment.getName() + " - " + super.getNotificationMessage() + " - " + super.getEmployee();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MachineAccident)) return false;
        if (!super.equals(o)) return false;
        MachineAccident that = (MachineAccident) o;
        return super.equals(that) && this.equipment.equals(that.getEquipment());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getEquipment());
    }
}
