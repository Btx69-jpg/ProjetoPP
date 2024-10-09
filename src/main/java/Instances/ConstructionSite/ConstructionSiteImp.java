package Instances.ConstructionSite;

import Classes.Constantes;
import Classes.Control.ControlClass;
import Instances.Equipments.EquipmentImp;
import Instances.Equipments.EquipmentsImp;
import Instances.Workers.TeamImp;
import estgconstroi.*;
import estgconstroi.enums.EmployeeType;
import estgconstroi.enums.EquipmentStatus;
import estgconstroi.exceptions.ConstructionSiteException;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

/*
 * Nome: Willkie Bianchi Parahyba Filho
 * Número: 8230127
 * Turma: LEI1T1
 *
 */
public class ConstructionSiteImp implements ConstructionSite {
    private Team[] team = new TeamImp[Constantes.INITIAL_ARRAY_SIZE];
    private EquipmentsImp equipments;
    private int teamCount = 0;
    private Employee manager;
    private Permit permit;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String location;

    public ConstructionSiteImp(String name, Employee manager, Team team, String location, LocalDate startDate, LocalDate endDate, Permit permit) {
        this.name = name;
        this.manager = manager;
        this.team[teamCount] = team;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.permit = permit;
        this.equipments = new EquipmentsImp();
        this.teamCount++;

        ControlClass.addConstructionSite(this);

    }

    public ConstructionSiteImp(Employee manager, Permit permit, String name, LocalDate startDate, LocalDate endDate, String location) {
        this.manager = manager;
        this.permit = permit;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.equipments = new EquipmentsImp();

        ControlClass.addConstructionSite(this);
    }

    public ConstructionSiteImp() {
        //ControlClass.addConstructionSite(this);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getLocation() {
        return this.location;
    }

    @Override
    public String getPermit() {
        return this.permit.getIdentifier();
    }

    @Override
    public LocalDate getPermitExpirationDate() {
        return this.permit.getExpirationDate();
    }

    @Override
    public LocalDate getStartDate() {
        return this.startDate;
    }

    @Override
    public LocalDate getEndDate() {
        return this.endDate;
    }

    @Override
    public void setPermit(String s, LocalDate localDate) {
        this.permit = new Permit(s, localDate);
    }

    @Override
    public Employee getResponsible() {
        return this.manager;
    }

    @Override
    public void setResponsible(Employee employee) throws ConstructionSiteException {
        //dataAlert alert;
        if (employee == null) {
            throw new ConstructionSiteException("Funcionario" + Constantes.CANNOT_BE_NULL);
        }
        if (employee.getType() != EmployeeType.MANAGER) {
            throw new ConstructionSiteException("Funcionario deve ser um gestor");
        }
        if (this.team != null) {
            for (int i = 0; i < this.team.length; i++) {
                if (this.team[i] == null) {
                    break;
                }
                for (int empIndex = 0; empIndex < this.team[i].getEmployees().length; empIndex++) {
                    if (this.team[i].getEmployees()[empIndex] == null) {
                        break;
                    }
                    if (this.team[i].getEmployees()[empIndex] == employee) {
                        throw new ConstructionSiteException("O Gerente pertence á uma equipa neste campo de obras");
                    }
                }

            }
        }
        this.manager = employee;
    }

    @Override
    public void addTeam(Team team) throws ConstructionSiteException {
        Equipment[] NewTEquip;
        Equipment[] ThatTEquip;
        if (team == null) {
            throw new ConstructionSiteException("a equipa " + Constantes.CANNOT_BE_NULL);
        }
        if (teamCount > 0) {
            for (int teamIndex = 0; teamIndex < teamCount; teamIndex++) {
                if (this.team[teamIndex] == null) {
                    break;
                }
                if (this.team[teamIndex].equals(team)) {
                    throw new ConstructionSiteException("a equipa " + Constantes.ALREADY_EXISTS);
                }

                if (team.getEquipments() == null) {
                    break;
                }

                if (this.team[teamIndex].getEquipments() == null) {
                    continue;
                }

                if (this.team[teamIndex].getEquipments().equals(team.getEquipments())) {
                    throw new ConstructionSiteException("a equipa possui as mesmas ferramentas da equipa" + this.team[teamIndex].getName() + ", logo, é invalida.");
                }

                NewTEquip = team.getEquipments().getEquipment();

                for (int NewtequiIndex = 0; NewtequiIndex < NewTEquip.length; NewtequiIndex++) {

                    if (NewTEquip[NewtequiIndex] == null) {
                        break;
                    }

                    ThatTEquip = this.team[teamIndex].getEquipments().getEquipment();
                    for (int TequipIndex = 0; TequipIndex < ThatTEquip.length; TequipIndex++) {

                        if (ThatTEquip[TequipIndex] == null) {
                            break;
                        }

                        if (ThatTEquip[TequipIndex].equals(NewTEquip[NewtequiIndex])) {
                            throw new ConstructionSiteException("a equipa possui a ferramenta " + NewTEquip[NewtequiIndex] + " da equipa " + this.team[NewtequiIndex].getName() + ", logo, é invalida.");
                        }
                    }
                }
            }
        }

        if (this.team.length == teamCount) {
            teamsAddSpace(Constantes.ARRAY_SIZE_MULTIPLIER);
        }

        this.team[teamCount] = team;
        teamCount++;
        ControlClass.addTeam(team);

    }

    @Override
    public void removeTeam(Team team) throws ConstructionSiteException {
        boolean exist = false;
        if (team == null) {
            throw new ConstructionSiteException("O time a remover " + Constantes.CANNOT_BE_NULL);
        }

        for (int i = 0; i < teamCount; i++) {
            if (this.team[i].equals(team)) {
                exist = true;
                this.team[teamCount] = null;
                teamCount--;
            }
        }
        ControlClass.removeTeamsChecker(team);
        if (!exist) {
            throw new ConstructionSiteException("O time inserido não existe neste campo de obras.");
        }

    }

    @Override
    public Team[] getTeams(String s) {
        Team[] teams = new TeamImp[teamCount];
        int teamIndex = 0;

        for (int i = 0; i < teamCount; i++) {
            if (this.team[i].getName().equals(s)) {
                teams[teamIndex] = this.team[i];
                teamIndex++;
            }
        }

        return teams;
    }

    @Override
    public Team[] getTeams() {
        return this.team;
    }

    @Override
    public boolean isValid() {
        Equipment[] teamXequipments;
        boolean isValid = false;

        if (this.manager == null || this.teamCount == 0) {
            return isValid;
        }
        for (int i = 0; i < teamCount; i++) {
            if (this.team[i].getLeader() == null) {
                return isValid;
            }


            if (this.team[i].getEquipments() != null) {
                teamXequipments = this.team[i].getEquipments().getEquipment();
                if (teamXequipments != null) {

                    for (Equipment teamXequipment : teamXequipments) {
                        if (teamXequipment.getStatus() == EquipmentStatus.OPERATIVE) {
                            isValid = true;
                        }
                    }
                }
            }
        }

        if (!(this.permit.getExpirationDate().isAfter(this.startDate) && this.permit.getExpirationDate().isBefore(this.endDate))) {
            return false;
        }

        return isValid;

    }

    /**
     * Obtém os equipamentos de todas as equipes e do proprio campo de obras
     * Este método itera por todas as equipes e coleta os seus equipamentos num único array.
     * Os equipamentos são coletados de cada equipe e adicionados a um array principal.
     *
     * @return Um objeto Equipments contendo todos os equipamentos das equipes
     */
    @Override
    public Equipments getEquipments() {
        Equipments equipments;
        Equipment[] teamEquipmentsArray;
        EquipmentImp[] equipmentsArray = new EquipmentImp[Constantes.INITIAL_ARRAY_SIZE];
        int index = 0;

        for (int teamIndex = 0; teamIndex < teamCount; teamIndex++) {
            if (this.team[teamIndex].getEquipments() != null) {
                teamEquipmentsArray = this.team[teamIndex].getEquipments().getEquipment();
                if (teamEquipmentsArray != null) {
                    for (int equipIndex = 0; equipIndex < teamEquipmentsArray.length; equipIndex++) {
                        if (teamEquipmentsArray[equipIndex] == null) {
                            break;
                        }
                        if (index == equipmentsArray.length) {
                            equipmentsArray = Constantes.equipmentAddSpace(equipmentsArray, Constantes.ARRAY_SIZE_MULTIPLIER);
                        }
                        equipmentsArray[index] = (EquipmentImp) this.team[teamIndex].getEquipments().getEquipment()[equipIndex];
                        index++;
                    }
                }
            }
        }

        if (index == 0 && this.equipments.getEquipmentsCnt() == 0) {
            return null;
        }
        if (this.equipments.getEquipmentsCnt() != 0) {
            for (int i = 0; i < this.equipments.getEquipmentsCnt(); i++) {
                if (index == equipmentsArray.length) {
                    equipmentsArray = Constantes.equipmentAddSpace(equipmentsArray, Constantes.ARRAY_SIZE_MULTIPLIER);
                }
                equipmentsArray[index] = (EquipmentImp) this.equipments.getEquipment()[i];
                index++;
            }
        }

        EquipmentImp[] finalArray = new EquipmentImp[index];

        for (int i = 0; i < index; i++) {
            finalArray[i] = equipmentsArray[i];
        }

        equipments = new EquipmentsImp(finalArray);
        return equipments;

    }

    public void addEquipment(Equipment equipment) throws ConstructionSiteException {

        if (this.equipments.getEquipmentsCnt() == 0) {
            this.equipments.addEquipment(equipment);
            return;
        }
        for (int i = 0; i < this.equipments.getEquipmentsCnt(); i++) {
            if (this.equipments.getEquipment()[i] == null) {
                break;
            }
            if (this.equipments.getEquipment()[i].equals(equipment)) {
                throw new ConstructionSiteException("O objeto já pertence ao campo de obras");
            }
        }

        this.equipments.addEquipment(equipment);

    }

    private void teamsAddSpace(int times) {
        Team[] temp = new Team[this.teamCount * times];

        for (int i = 0; i < this.teamCount; i++) {
            temp[i] = this.team[i];
        }

        this.team = temp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConstructionSiteImp)) return false;
        ConstructionSiteImp that = (ConstructionSiteImp) o;
        return teamCount == that.teamCount && Objects.deepEquals(team, that.team) && Objects.equals(manager, that.manager) && Objects.equals(getPermit(), that.getPermit()) && Objects.equals(getName(), that.getName()) && Objects.equals(getStartDate(), that.getStartDate()) && Objects.equals(getEndDate(), that.getEndDate()) && Objects.equals(getLocation(), that.getLocation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(team), teamCount, manager, permit, name, startDate, endDate, location);
    }

    @Override
    public String toString() {

        StringBuilder teamsString = new StringBuilder("[");
        for (Team team : this.team) {
            if (team != null) {
                if (teamsString.length() > 1) {
                    teamsString.append(", ");
                }
                teamsString.append(team);
            }
        }
        teamsString.append("]");
        return "ConstructionSiteImp{" +
                "team=" + teamsString +
                ", teamCount=" + teamCount +
                ", manager=" + manager +
                ", permit=" + permit +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", location='" + location + '\'' +
                '}';
    }
}
