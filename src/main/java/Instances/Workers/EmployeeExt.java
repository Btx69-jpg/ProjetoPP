package Instances.Workers;

import Classes.Control.ControlClass;
import Instances.EventETC.NotifierImp;
import estgconstroi.Employee;
import estgconstroi.Event;
import estgconstroi.enums.EmployeeType;

import java.util.Objects;
/*
 * Nome: Willkie Bianchi Parahyba Filho
 * Número: 8230127
 * Turma: LEI1T1
 *
 */

public class EmployeeExt extends Employee {
    private String name;
    private EmployeeType type;
    private NotifierImp notifi;

    public EmployeeExt(String name, EmployeeType employeeType) {
        this.name = name;
        this.type = employeeType;
        this.notifi = new NotifierImp(super.getUUID());
        ControlClass.addEmployee(this);
        ControlClass.addNotifier(notifi);
    }

    public EmployeeExt(Employee employee) {
        this.name = employee.getName();
        this.type = employee.getType();
        this.notifi = new NotifierImp(this.getUUID());

        ControlClass.addNotifier(notifi);
    }

    public EmployeeExt() {
    }

    public NotifierImp getNotifier() {
        return this.notifi;
    }

    public Event[] getEventsArray() {
        return this.notifi.getEvents();
    }

    public void addEvent(Event event) {
        this.notifi.addEventos(event);
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public EmployeeType getType() {
        return this.type;
    }

    @Override
    public void setType(EmployeeType employeeType) {
        this.type = employeeType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) {
            return false;
        }
        EmployeeExt that = (EmployeeExt) o;
        return getName().equals(that.getName()) && getType() == that.getType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getType(), notifi);
    }

    public String getEventsString() {
        this.notifi.getEvents();
        return this.notifi.toString();
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}
