package Instances.Equipments;

import Classes.Control.ControlClass;
import estgconstroi.enums.EquipmentStatus;
import estgconstroi.enums.EquipmentType;

import java.util.Objects;
/*
 * Nome: Willkie Bianchi Parahyba Filho
 * Número: 8230127
 * Turma: LEI1T1
 *
 */

public class EquipmentImp implements estgconstroi.Equipment {
    private final String name;
    private final EquipmentType type;
    private EquipmentStatus status;
    private Inspection inspection;


    public EquipmentImp(String name, EquipmentType type, Inspection inspection, EquipmentStatus status) {
        this.name = name;
        this.type = type;
        this.inspection = new Inspection(inspection.getInspectionEndDate(), inspection.getStatus());
        this.status = status;


        ControlClass.addEquipment(this);
    }

    //Construtor para deep copy
    public EquipmentImp(EquipmentImp equipment) {
        this.name = equipment.getName();
        this.type = equipment.getType();
        this.status = equipment.getStatus();
        this.inspection = equipment.getInspection();

        ControlClass.addEquipment(this);
    }

    public Inspection getInspection() {
        return this.inspection;
    }


    public void doMaintence(Inspection inspection) {
        this.inspection = inspection;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public EquipmentType getType() {
        return this.type;
    }

    @Override
    public EquipmentStatus getStatus() {
        /*
        if (inspection.getStatus() == InspectionStatus.APROVED){
            this.status = EquipmentStatus.OPERATIVE;
        }
        if (inspection.getStatus() == InspectionStatus.REPROVED){
            this.status = EquipmentStatus.MAINTENANCE;
        }

         */
        return this.status;
    }

    @Override
    public void setStatus(EquipmentStatus equipmentStatus) {
        this.status = equipmentStatus;
    }

    @Override
    public boolean equals(Object equipment) {
        if (this == equipment) {
            return true;
        }
        if (!(equipment instanceof EquipmentImp)) {
            return false;
        }
        EquipmentImp equipmentImp = (EquipmentImp) equipment;
        return this.name.equals(equipmentImp.getName()) && this.type.toString().equals(equipmentImp.getType().toString()) && this.getStatus().toString().equals(equipmentImp.getStatus().toString()) && this.inspection.equals(equipmentImp.getInspection());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getType(), getStatus(), getInspection());
    }

    @Override
    public String toString() {
        return "Equipment{" +
                "name='" + name + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", End of Inspection =" + inspection.getInspectionEndDate() +
                '}';
    }

    public void toJson() {
        System.out.println("Equipment to Json");
        System.out.println("Name: " + this.name);
        System.out.println("Type: " + this.type);
        System.out.println("Status: " + this.status);

    }
}
