package org.example.Equipment_EquipmentsTest;

import Classes.Constantes;
import Enums.InspectionStatus;
import Instances.Equipments.EquipmentImp;
import Instances.Equipments.EquipmentsImp;
import Instances.Equipments.Inspection;
import estgconstroi.enums.EquipmentStatus;
import estgconstroi.enums.EquipmentType;

import java.time.LocalDate;
import java.util.Arrays;

/*
 * Nome: Willkie Bianchi Parahyba Filho
 * Número: 8230127
 * Turma: LEIT1
 *
 */

public class Main {
    public static void main(String[] args) {
        try {
            /*
            EquipmentImp equipment = new EquipmentImp("BATETA", EquipmentType.MATERIALS, EquipmentStatus.OPERATIVE);
            EquipmentImp equipment2 = new EquipmentImp("Esmerilhadeira", EquipmentType.MATERIALS, EquipmentStatus.OPERATIVE);
            EquipmentImp equipment3 = new EquipmentImp("Marreta", EquipmentType.TOOLS, EquipmentStatus.INOPERATIVE);

            System.out.println(equipment.toString());
            System.out.println(equipment.equals(equipment2));
            equipment2.setStatus(EquipmentStatus.MAINTENANCE);
            System.out.println(equipment2.toString());
            System.out.println(equipment.equals(equipment3));
            System.out.println(equipment3.toString());
            equipment.toJson();

            EquipmentsImp equipments = new EquipmentsImp();
            equipments.addEquipment(equipment);
            equipments.addEquipment(equipment2);
            equipments.addEquipment(equipment3);
            System.out.println(Arrays.toString(equipments.getEquipment()));
            System.out.println("From operative status: " + Arrays.toString(equipments.getEquipment(EquipmentStatus.OPERATIVE)));
            System.out.println("From tools type: " + Arrays.toString(equipments.getEquipment(EquipmentType.TOOLS)));
            System.out.println(Arrays.toString(equipments.getEquipment("Marreta")));
            equipments.removeEquipment(equipment);
            System.out.println("After removal: " + equipments.toString());

            Equipments equipments2 = new EquipmentsImp();

             */
            EquipmentsImp equipmentsImp = new EquipmentsImp();
            Inspection inspection = new Inspection(LocalDate.now(), InspectionStatus.APROVED);

            System.out.println("Initial equipment count: " + equipmentsImp.getEquipmentsCnt());

            // Add equipment until the array needs to be resized
            for (int i = 0; i < Constantes.INITIAL_ARRAY_SIZE; i++) {
                EquipmentImp equipment = new EquipmentImp("Equipment " + i, EquipmentType.MATERIALS, inspection, EquipmentStatus.OPERATIVE);
                equipmentsImp.addEquipment(equipment);
            }

            // Add one more equipment to trigger resizing
            EquipmentImp equipment = new EquipmentImp("Equipment " + Constantes.INITIAL_ARRAY_SIZE, EquipmentType.TOOLS, inspection, EquipmentStatus.OPERATIVE);
            equipmentsImp.addEquipment(equipment);

            // Check the new equipment count
            System.out.println("Equipment count after resizing: " + equipmentsImp.getEquipmentsCnt()); // Expected: 6 (assuming INITIAL_ARRAY_SIZE = 10)

            // Verify the contents of the array
            System.out.println("Equipments: " + Arrays.toString(equipmentsImp.getEquipment()));


            System.out.println("----------------------------------------------------------------------------------------------");
            EquipmentImp equipment1 = new EquipmentImp("Excavator", EquipmentType.TOOLS, inspection, EquipmentStatus.OPERATIVE);
            EquipmentImp equipment2 = new EquipmentImp("Bulldozer", EquipmentType.TOOLS, inspection, EquipmentStatus.OPERATIVE);
            EquipmentImp equipment3 = new EquipmentImp("Furadeira", EquipmentType.TOOLS, inspection, EquipmentStatus.OPERATIVE);
            EquipmentImp equipment4 = new EquipmentImp(equipment1); // Deep copy


            // Test equality
            System.out.println("equipment1 equals equipment1: " + equipment1.equals(equipment1)); // Should be true
            System.out.println("equipment1 equals equipment3: " + equipment1.equals(equipment3)); // Should be true
            System.out.println("equipment1 equals equipment2: " + equipment1.equals(equipment2)); // Should be false
            System.out.println("equipment1 equals equipment4: " + equipment1.equals(equipment4)); // Should be true

            // Create an instance of EquipmentsImp and add some equipment
            EquipmentsImp equipments = new EquipmentsImp();
            equipments.addEquipment(equipment1);
            equipments.addEquipment(equipment2);
            equipments.addEquipment(equipment3);


            // Create another instance of EquipmentsImp and add the same equipment
            EquipmentsImp equipmentsCopy = new EquipmentsImp();

            equipmentsCopy.addEquipment(new EquipmentImp("Excavator", EquipmentType.TOOLS, inspection, EquipmentStatus.OPERATIVE));
            equipmentsCopy.addEquipment(new EquipmentImp("Bulldozer", EquipmentType.TOOLS, inspection, EquipmentStatus.OPERATIVE));
            equipmentsCopy.addEquipment(new EquipmentImp("Furadeira", EquipmentType.TOOLS, inspection, EquipmentStatus.OPERATIVE));


            // Test equality between EquipmentsImp instances
            System.out.println("equipments equals equipmentsCopy: " + equipments.equals(equipmentsCopy)); // Should be true

            // Print the contents of the EquipmentsImp instances
            System.out.println("equipments: " + equipments);
            System.out.println("equipmentsCopy: " + equipmentsCopy);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }
}
