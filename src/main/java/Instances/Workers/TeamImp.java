package Instances.Workers;

import Classes.Constantes;
import Classes.Control.ControlClass;
import Instances.Equipments.EquipmentsImp;
import estgconstroi.Employee;
import estgconstroi.Equipments;
import estgconstroi.Team;
import estgconstroi.enums.EmployeeType;
import estgconstroi.exceptions.TeamException;

import java.util.Arrays;
import java.util.Objects;

import static Classes.Constantes.INITIAL_ARRAY_SIZE;

public class TeamImp implements Team {
    private final String teamName;
    private Employee teamLeader;
    private Employee[] employees = new EmployeeExt[INITIAL_ARRAY_SIZE];
    private EquipmentsImp equipments;
    private int teamSize;

    public TeamImp(String teamName) {
        this.teamName = teamName;
        ControlClass.addTeam(this);
    }

    @Override
    public String getName() {
        return this.teamName;
    }

    @Override
    public Employee getLeader() {
        return this.teamLeader;
    }

    //verificar se é necessario que um lider so pode pertencer a uma equipe
    @Override
    public void setLeader(Employee employee) throws TeamException {

        if (employee == null) {
            throw new TeamException("O lider não pode ser nulo.");
        } else if (employee.getType() != EmployeeType.TEAM_LEADER) {
            throw new TeamException("O funcionario não é um lider de equipe.");
        }

        this.teamLeader = employee;
    }

    @Override
    public int getNumberOfEmployees() {
        return this.teamSize;
    }

    @Override
    public void addEmployees(Employee employee) throws TeamException {

        if (employee == null) {
            throw new TeamException("O funcionario não pode ser nulo.");
        }
        for (int i = 0; i < this.teamSize; i++) {
            if (this.employees[i] == employee) {
                throw new TeamException("O funcionario já existe nesta equipa.");
            }
        }

        if (this.teamSize == this.employees.length) {
            employeesAddSpace(Constantes.ARRAY_SIZE_MULTIPLIER);
        }
        ControlClass.addEmployee(employee);
        this.employees[this.teamSize] = employee;
        this.teamSize++;


    }

    @Override
    public void removeEmployee(Employee employee) throws TeamException {
        if (this.teamSize == 0) {
            throw new TeamException("A equipa está vazia.");
        }
        if (employee == null) {
            throw new TeamException("O funcionario não pode ser nulo.");
        }

        boolean found = false;
        for (int i = 0; i < this.teamSize; i++) {
            if (this.employees[i] == employee) {
                for (int j = i; j < this.teamSize - 1; j++) {
                    this.employees[j] = this.employees[j + 1];
                }
                this.employees[this.teamSize - 1] = null;
                this.teamSize--;
                found = true;
                break;
            }
        }

        if (!found) {
            throw new TeamException("O funcionario não existe na equipa.");
        }

    }

    @Override
    public Employee[] getEmployees(String name) {

        Employee[] matches = new Employee[this.teamSize];
        int index = 0;

        for (int i = 0; i < this.teamSize; i++) {

            if (this.employees[i].getName().equals(name)) {
                matches[index] = this.employees[i];
                index++;
            }

        }
        return matches;
    }

    @Override
    public Employee[] getEmployees(EmployeeType employeeType) {

        Employee[] matches = new Employee[this.teamSize];
        int index = 0;

        for (int i = 0; i < this.teamSize; i++) {

            if (this.employees[i].getType().equals(employeeType)) {
                matches[index] = this.employees[i];
                index++;
            }

        }
        return matches;
    }

    @Override
    public Employee[] getEmployees() {
        return this.employees;
    }

    @Override
    public Equipments getEquipments() {
        return this.equipments.deepCopy();
    }

    public void setEquipments(EquipmentsImp equipments) throws TeamException {
        if (equipments == null) {
            throw new NullPointerException("O equipamentos não pode ser nulo.");
        }

        this.equipments = equipments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TeamImp)) {
            return false;
        }

        TeamImp teamImp = (TeamImp) o;
        if ((this.teamLeader == null && teamImp.getLeader() != null) && (this.teamLeader != null && teamImp.getLeader() == null)) {
            return false;
        }
        return teamSize == teamImp.teamSize && Objects.deepEquals(getEmployees(), teamImp.getEmployees()) && Objects.equals(getEquipments(), teamImp.getEquipments()) && Objects.equals(teamName, teamImp.teamName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamLeader, Arrays.hashCode(getEmployees()), getEquipments(), teamName, teamSize);
    }

    @Override
    public String toString() {
        StringBuilder employeesString = new StringBuilder("[");
        for (Employee employee : this.employees) {
            if (employee != null) {
                if (employeesString.length() > 1) {
                    employeesString.append(", ");
                }
                employeesString.append(employee);
            }
        }
        employeesString.append("]");
        return "Team{" +
                " teamName='" + teamName + '\'' +
                ",teamLeader=" + teamLeader +
                ", employees=" + employeesString +
                ", equipments=" + equipments +
                ", teamSize=" + teamSize +
                '}';
    }

    private void employeesAddSpace(int times) {
        Employee[] temp = new Employee[teamSize * times];

        for (int i = 0; i < this.teamSize; i++) {
            temp[i] = this.employees[i];
        }

        this.employees = temp;
    }
}
