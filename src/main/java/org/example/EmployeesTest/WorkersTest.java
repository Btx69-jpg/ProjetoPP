package org.example.EmployeesTest;

import Enums.InspectionStatus;
import Instances.Equipments.EquipmentImp;
import Instances.Equipments.EquipmentsImp;
import Instances.Equipments.Inspection;
import Instances.Workers.EmployeeExt;
import Instances.Workers.TeamImp;
import estgconstroi.Employee;
import estgconstroi.Equipments;
import estgconstroi.enums.EmployeeType;
import estgconstroi.enums.EquipmentStatus;
import estgconstroi.enums.EquipmentType;
import estgconstroi.exceptions.ConstructionSiteException;
import estgconstroi.exceptions.TeamException;

import java.time.LocalDate;
import java.util.Arrays;
/*
 * Nome: Willkie Bianchi Parahyba Filho
 * Número: 8230127
 * Turma: LEI1T1
 *
 */

public class WorkersTest {
    public static void main(String[] args) {
        /*
        System.out.println("Testing Workers");
        EmployeeExt employeeExt = new EmployeeExt("John Doe", EmployeeType.TEAM_LEADER);
        EmployeeExt employeeExt2 = new EmployeeExt("John Doe", EmployeeType.TEAM_LEADER);
        EmployeeExt employeeExt3 = new EmployeeExt("John Doe", EmployeeType.WORKER);

        System.out.println(employeeExt);
        employeeExt.setType(EmployeeType.WORKER);
        System.out.println(employeeExt);
        System.out.println("emp2 equals emp3: " + employeeExt2.equals(employeeExt3));
        System.out.println("emp equals emp3: " + employeeExt3.equals(employeeExt));
        System.out.println("Testing Manager");


        EmployeeExt manager = new Manager("Carl Doe", ManagingBranchEnum.Construction_Site);
        System.out.println(manager);
        manager.setName("Jane Doe");
        System.out.println(manager.toString());


        System.out.println("Testing Event Manager");
        EmployeeExt eventManager = new Manager("John Doe", ManagingBranchEnum.event);
        System.out.println(eventManager);


         */
        System.out.println("Testing TeamImp");

        // Initialize objects
        EmployeeExt teamLeader = new EmployeeExt("Jhonattan Dobberman", EmployeeType.TEAM_LEADER);
        EmployeeExt worker1 = new EmployeeExt("Mary Jane", EmployeeType.WORKER);
        EmployeeExt worker2 = new EmployeeExt("Bob Johnson", EmployeeType.WORKER);
        EmployeeExt worker3 = new EmployeeExt("Charlis Brown", EmployeeType.WORKER);

        Inspection inspection = new Inspection(LocalDate.now(), InspectionStatus.APROVED);

        EquipmentsImp equipments = new EquipmentsImp();
        try {
            equipments.addEquipment(new EquipmentImp("Equipment 1", EquipmentType.MATERIALS, inspection, EquipmentStatus.OPERATIVE));
            equipments.addEquipment(new EquipmentImp("Equipment 2", EquipmentType.MATERIALS, inspection, EquipmentStatus.OPERATIVE));
            equipments.addEquipment(new EquipmentImp("Equipment 3", EquipmentType.MATERIALS, inspection, EquipmentStatus.OPERATIVE));
        } catch (ConstructionSiteException e) {
            System.out.println("addEquipment failed: " + e.getMessage());
        }

        TeamImp team = new TeamImp("Patrulha Pata");
        try {
            team.setEquipments(equipments);
            System.out.println("adding equipments passed in team");
        } catch (TeamException e) {
            System.out.println("setEquipments failed: " + e.getMessage());
        }


        // Test setLeader
        try {
            team.setLeader(teamLeader);
            System.out.println("setLeader passed.");
        } catch (TeamException e) {
            System.out.println("setLeader failed: " + e.getMessage());
        }

        // Test getLeader
        Employee leader = team.getLeader();
        System.out.println("getLeader result: " + leader);

        // Test getNumberOfEmployees
        int numEmployees = team.getNumberOfEmployees();
        System.out.println("getNumberOfEmployees result: " + numEmployees);

        // Test addEmployees
        try {
            team.addEmployees(worker1);
            team.addEmployees(worker2);
            team.addEmployees(worker3);
            System.out.println("addEmployees passed.");
        } catch (TeamException e) {
            System.out.println("addEmployees failed: " + e.getMessage());
        }

        // Test removeEmployee
        try {
            team.removeEmployee(worker1);
            System.out.println("removeEmployee passed.");
        } catch (TeamException e) {
            System.out.println("removeEmployee failed: " + e.getMessage());
        }

        // Test getEmployees by name
        Employee[] employeesByName = team.getEmployees("Bob Johnson");
        System.out.println("getEmployees by name: " + Arrays.toString(employeesByName));

        // Test getEmployees by type
        Employee[] employeesByType = team.getEmployees(EmployeeType.WORKER);
        System.out.println("getEmployees by type: " + Arrays.toString(employeesByType));

        // Test getEmployees (all)
        Employee[] allEmployees = team.getEmployees();
        System.out.println("getEmployees (all): " + Arrays.toString(allEmployees));

        // Test getEquipments
        Equipments teamEquipments = team.getEquipments();
        System.out.println("getEquipments result: " + teamEquipments);

        // Test equals
        TeamImp team2 = new TeamImp("Construction Team 2");
        try {
            team2.setEquipments(equipments);
            System.out.println("adding equipments passed in team2");
        } catch (TeamException e) {
            System.out.println("setEquipments failed: " + e.getMessage());
        }

        // teste adicionando os mesmos funcionarios do time 1
        try {
            team2.addEmployees(worker1);
            System.out.println("addEmployee 1 passed.");
        } catch (TeamException e) {
            System.out.println("addEmployee 1 failed: " + e.getMessage());
        }
        try {
            team2.addEmployees(worker2);
            System.out.println("addEmployee 2 passed.");
        } catch (TeamException e) {
            System.out.println("addEmployee 2 failed: " + e.getMessage());
        }
        try {
            team2.addEmployees(worker3);
            System.out.println("addEmployee 3 passed.");
        } catch (TeamException e) {
            System.out.println("addEmployee 3 failed: " + e.getMessage());
        }
        System.out.println("equals result: " + team.equals(team2));

        // teste adicionando o mesmo lider a 2 equipe
        try {
            team2.setLeader(teamLeader);
            System.out.println("setLeader team 2 passed.");
        } catch (TeamException e) {
            System.out.println("setLeader team 2 failed: " + e.getMessage());
        }

        // Test toString
        System.out.println("toString result: " + team2);
    }
}
