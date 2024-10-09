package Classes.Control;

import Classes.Constantes;
import Classes.Logger.ExceptionLogger;
import Instances.ConstructionSite.ConstructionSiteImp;
import Instances.EventETC.NotifierImp;
import Instances.Management.EventManagerImp;
import Instances.Notifications.dataAlert;
import Instances.Workers.EmployeeExt;
import Instances.Workers.TeamImp;
import estgconstroi.*;
import estgconstroi.enums.EmployeeType;
import estgconstroi.exceptions.EventManagerException;

import java.time.LocalDate;
/*
 * Nome: Willkie Bianchi Parahyba Filho
 * Número: 8230127
 * Turma: LEI1T1
 *
 */

/*
 * classe para ter certeza de que não ha campos de obras com equipes repetidas
 * não ha um funcionario em duas equipes
 * um equipamento não é utilizado por mais que uma equipe
 */
public class ControlClass {
    /**
     * Array de gerenciadores de eventos em verificação.
     */
    private static final EventManagerImp[] eventManagerChecker = new EventManagerImp[Constantes.INITIAL_ARRAY_SIZE];
    /**
     * Array de funcionários em verificação.
     */
    private static EmployeeExt[] employeeChecker = new EmployeeExt[Constantes.INITIAL_ARRAY_SIZE];
    /**
     * Array de equipes em verificação.
     */
    private static Team[] teamsChecker = new TeamImp[Constantes.INITIAL_ARRAY_SIZE];
    /**
     * Array de campos de obras em verificação.
     */
    private static ConstructionSiteImp[] constructionCheker = new ConstructionSiteImp[Constantes.INITIAL_ARRAY_SIZE];
    /**
     * Array de equipamentos em verificação.
     */
    private static Equipment[] equipmentChecker = new Equipment[Constantes.INITIAL_ARRAY_SIZE];
    /**
     * Array de notificadores em verificação.
     */
    private static NotifierImp[] notifierChecker = new NotifierImp[Constantes.INITIAL_ARRAY_SIZE];
    /**
     * Contador total de funcionários.
     */
    private static int totalEmployeeCount = 0;

    /**
     * Contador total de equipes.
     */
    private static int totalTeamsCnt;

    /**
     * Contador total de campos de obras.
     */
    private static int totalConstructionCnt;

    /**
     * Contador total de equipamentos.
     */
    private static int totalEquipmentCnt;

    /**
     * Contador total de notificadores.
     */
    private static int totalNotifierCnt;

    /**
     * Contador total de gerenciadores de eventos.
     */
    private static int totalEventManagerCnt;

    public static int getTotalNotifierCnt() {
        return totalNotifierCnt;
    }

    /**
     * Remove um campo de obras da lista de verificação.
     *
     * @param construction O campo de obras a ser removido.
     */
    public static void removeConstructionChecker(ConstructionSiteImp construction) {
        if (construction == null) {
            return;
        }
        if (totalConstructionCnt == 0) {
            return;
        }
        for (int i = 0; i < totalConstructionCnt; i++) {
            if (constructionCheker[i] == null) {
                break;
            }
            if (constructionCheker[i].equals(construction)) {
                // Shift elements to the left
                for (int removeIndex = i; removeIndex < totalConstructionCnt - 1; removeIndex++) {
                    constructionCheker[removeIndex] = constructionCheker[removeIndex + 1];
                }
                constructionCheker[totalConstructionCnt - 1] = null;
                totalConstructionCnt--;
                return;
            }
        }
    }

    /**
     * Remove uma equipe da lista de verificação.
     *
     * @param team A equipe a ser removida.
     */
    public static void removeTeamsChecker(Team team) {
        if (team == null) {
            return;
        }
        if (totalTeamsCnt == 0) {
            return;
        }
        for (int i = 0; i < totalTeamsCnt; i++) {
            if (teamsChecker[i] == null) {
                break;
            }
            if (teamsChecker[i].equals(team)) {
                // Shift elements to the left
                for (int removeIndex = i; removeIndex < totalTeamsCnt - 1; removeIndex++) {
                    teamsChecker[removeIndex] = teamsChecker[removeIndex + 1];
                }
                teamsChecker[totalTeamsCnt - 1] = null;
                totalTeamsCnt--;
                return;
            }
        }
    }

    /**
     * Remove um notificador da lista de verificação,caso o mesmo exista.
     *
     * @param notifier O notificador a ser removido.
     */
    public static void removeNotifier(NotifierImp notifier) {
        if (notifier == null) {
            return;
        }
        if (totalNotifierCnt == 0) {
            return;
        }
        for (int i = 0; i < totalNotifierCnt; i++) {
            if (notifierChecker[i] == null) {
                break;
            }
            if (notifierChecker[i].equals(notifier)) {
                for (int removeIndex = i; removeIndex < totalNotifierCnt; removeIndex++) {
                    notifierChecker[removeIndex] = notifierChecker[removeIndex + 1];
                }
                notifierChecker[i] = null;
                totalNotifierCnt--;
                return;
            }
        }
    }

    public static ConstructionSiteImp[] getAllCs() {
        return constructionCheker;
    }

    /**
     * Obtém um funcionário pelo UUID.
     *
     * @param UUid O UUID do funcionário.
     * @return O funcionário correspondente ao UUID, ou null se não encontrado.
     */
    public static Employee getEmployeeByUUid(String UUid) {
        if (totalEmployeeCount == 0) {
            return null;
        }
        for (int i = 0; i < totalEmployeeCount; i++) {
            if (employeeChecker[i] == null) {
                break;
            }
            if (employeeChecker[i].getUUID().equals(UUid)) {
                return employeeChecker[i];
            }

        }
        //caso não haja o emplooye na empresa
        return null;
    }

    /**
     * Adiciona um gerenciador de eventos à lista de verificação.
     *
     * @param eventManager O gerenciador de eventos a ser adicionado.
     */
    public static void addEventManager(EventManagerImp eventManager) {
        if (eventManager == null) {
            return;
        }
        if (totalEventManagerCnt == 0) {
            eventManagerChecker[0] = eventManager;
            totalEventManagerCnt++;
            return;
        }
        for (int i = 0; i < totalEventManagerCnt; i++) {
            if (eventManagerChecker[i] == null) {
                break;
            }
            if (eventManagerChecker[i].equals(eventManager)) {
                return;
            }
        }
        eventManagerChecker[totalEventManagerCnt] = eventManager;
        totalEventManagerCnt++;
    }

    /**
     * Adiciona um funcionário à lista de verificação.
     *
     * @param employee O funcionário a ser adicionado.
     */
    public static void addEmployee(Employee employee) {

        if (employee == null) {
            return;
        }
        if (totalEmployeeCount == 0) {
            employeeChecker[0] = (EmployeeExt) employee;
            totalEmployeeCount++;
        }

        for (int i = 0; i < totalEmployeeCount; i++) {
            if (employeeChecker[i].equals(employee)) {
                return;
            }
        }

        if (employeeChecker.length == totalEmployeeCount) {
            employeeChecker = employeeAddSpace(Constantes.ARRAY_SIZE_MULTIPLIER);
        }
        employeeChecker[totalEmployeeCount] = (EmployeeExt) employee;
        totalEmployeeCount++;
    }

    /**
     * Obtém um funcionário pelo nome.
     * procura atravez do array de campos de obras existentes.
     *
     * @param name O nome do funcionário.
     * @return O funcionário correspondente ao nome, ou null se não encontrado.
     */
    public static Employee getEmployeeByName(String name) {
        if (totalConstructionCnt == 0) {
            return null;
        }
        for (int i = 0; i < totalConstructionCnt; i++) {
            if (constructionCheker[i] == null) {
                break;
            }
            if (constructionCheker[i].getResponsible() != null) {
                if (constructionCheker[i].getResponsible().getName().equals(name)) {
                    return constructionCheker[i].getResponsible();
                }
            }
            if (constructionCheker[i].getTeams() != null) {
                for (int temIndex = 0; temIndex < constructionCheker[i].getTeams().length; temIndex++) {
                    if (constructionCheker[i].getTeams()[temIndex] == null) {
                        break;
                    }
                    if (constructionCheker[i].getTeams()[temIndex].getLeader() != null) {
                        if (constructionCheker[i].getTeams()[temIndex].getLeader().getName().equals(name)) {
                            return constructionCheker[i].getTeams()[temIndex].getLeader();
                        }
                    }
                    if (constructionCheker[i].getTeams()[temIndex].getEmployees() != null) {
                        for (int empIndex = 0; empIndex < constructionCheker[i].getTeams()[temIndex].getEmployees().length; empIndex++) {
                            if (constructionCheker[i].getTeams()[temIndex].getEmployees()[empIndex] == null) {
                                break;
                            }
                            if (constructionCheker[i].getTeams()[temIndex].getEmployees()[empIndex].getName().equals(name)) {
                                return constructionCheker[i].getTeams()[temIndex].getEmployees()[empIndex];
                            }
                        }
                    }
                }

            }
        }
        //caso não haja o emplooye na empresa
        return null;
    }

    /**
     * Obtém um campo de obras pelo nome.
     *
     * @param name O nome do campo de obras.
     * @return O campo de obras correspondente ao nome, ou null se não encontrado.
     */
    public static ConstructionSite getCSByName(String name) {
        if (totalConstructionCnt == 0) {
            return null;
        }
        for (int i = 0; i < totalConstructionCnt; i++) {
            if (constructionCheker[i] == null) {
                break;
            }
            if (constructionCheker[i].getName().equals(name)) {
                return constructionCheker[i];
            }
        }

        return null;
    }

    /**
     * Adiciona um notificador à lista de verificação.
     * ao ser criado um employee do tipo gerente/lider e haja um event manager criado, ele é adicionado automaticamente
     *
     * @param notifier O notificador a ser adicionado.
     */
    public static void addNotifier(NotifierImp notifier) {
        boolean haveNotifier = false;

        if (notifier == null) {
            return;
        }

        if (notifier.getNotifier() == null) {
            return;
        }

        for (int i = 0; i < totalNotifierCnt; i++) {
            if (notifierChecker[i].equals(notifier)) {
                return;
            }
        }

        //metodo que ao ser criado um employee do tipo gerente/lider e haja um event manager criado, ele é adicionado automaticamente
        if (totalEventManagerCnt != 0 && (notifier.getNotifier().getType().equals(EmployeeType.MANAGER) || notifier.getNotifier().getType().equals(EmployeeType.TEAM_LEADER))) {
            for (int i = 0; i < totalEventManagerCnt; i++) {
                if (eventManagerChecker[i] == null) {
                    break;
                }

                for (int j = 0; j < eventManagerChecker[i].getNotifiers().length; j++) {
                    if (eventManagerChecker[i].getNotifiers()[j] == null) {
                        break;
                    }
                    if (eventManagerChecker[i].getNotifiers()[j].equals(notifier)) {
                        haveNotifier = true;
                    }
                }

                if (!haveNotifier) {
                    try {
                        eventManagerChecker[i].addNotifier(notifier);
                    } catch (EventManagerException e) {
                        ExceptionLogger.logException(e);
                        throw new RuntimeException(e);
                    }

                }

            }

        }

        if (notifierChecker.length == totalNotifierCnt) {
            notifierChecker = notifierAddSpace(Constantes.ARRAY_SIZE_MULTIPLIER);
        }

        notifierChecker[totalNotifierCnt] = notifier;
        totalNotifierCnt++;
    }

    /**
     * Adiciona um campo de obras à lista de verificação.
     * Adiciona tambem os times do campo de obras adicionado caso os mesmos sejam validos
     *
     * @param Cs O campo de obras a ser adicionado.
     */
    public static void addConstructionSite(ConstructionSiteImp Cs) {


        for (int i = 0; i < totalConstructionCnt; i++) {
            if (constructionCheker[i].equals(Cs)) {
                return;
            }
        }

        if (Cs.getTeams() != null) {
            for (int i = 0; i < Cs.getTeams().length; i++) {
                if (Cs.getTeams()[i] != null) {
                    addTeam(Cs.getTeams()[i]);

                }
            }
        }

        if (constructionCheker.length == totalConstructionCnt) {
            constructionCheker = constructionAddSpace(Constantes.ARRAY_SIZE_MULTIPLIER);
        }

        constructionCheker[totalConstructionCnt] = Cs;
        totalConstructionCnt++;
    }

    /**
     * Adiciona uma equipe à lista de verificação.
     *
     * @param team A equipe a ser adicionada.
     */
    public static void addTeam(Team team) {
        if (teamsChecker.length == totalTeamsCnt) {
            teamsChecker = teamAddSpace(teamsChecker, Constantes.ARRAY_SIZE_MULTIPLIER);
        }
        for (int i = 0; i < totalTeamsCnt; i++) {
            if (teamsChecker[i].equals(team)) {
                return;
            }
        }
        teamsChecker[totalTeamsCnt] = team;
        totalTeamsCnt++;
    }

    /**
     * Adiciona um equipamento à lista de verificação.
     *
     * @param equipment O equipamento a ser adicionado.
     */
    public static void addEquipment(Equipment equipment) {
        if (equipmentChecker.length == totalEquipmentCnt) {
            equipmentChecker = equipmentAddSpace(equipmentChecker, 2);
        }

        for (int i = 0; i < totalEquipmentCnt; i++) {
            if (equipmentChecker[i].equals(equipment)) {
                return;
            }
        }
        equipmentChecker[totalEquipmentCnt] = equipment;
        totalEquipmentCnt++;
    }

    /**
     * Obtém um notifier por meio de um funcionário.
     *
     * @param notifierImp O funcionário que é um notifier.
     * @return O notificador correspondente ao funcionário, ou null se não encontrado.
     */
    public static NotifierImp getNotifierImp(Employee notifierImp) {
        if (totalNotifierCnt == 0) {
            return null;
        }

        for (int i = 0; i < totalNotifierCnt; i++) {
            if (notifierChecker[i].getNotifier().equals(notifierImp)) {
                return notifierChecker[i];
            }
        }
        return null;
    }

    /**
     * Obtém uma matriz de notificadores que são gerentes ou líderes de equipe.
     *
     * @return Uma matriz de notificadores que são gerentes ou líderes de equipe.
     */
    public static NotifierImp[] getNotifierManagerLeader() {
        NotifierImp[] notifier = new NotifierImp[totalNotifierCnt];
        int notifierIndex = 0;

        if (totalNotifierCnt == 0) {
            return null;
        }

        for (int i = 0; i < totalNotifierCnt; i++) {
            if (notifierChecker[i].getNotifier().getType().equals(EmployeeType.MANAGER) || notifierChecker[i].getNotifier().getType().equals(EmployeeType.TEAM_LEADER)) {

                notifier[notifierIndex] = notifierChecker[i];
                notifierIndex++;

            }
        }
        return notifier;
    }

    /**
     * Verifica a disponibilidade de todos os equipamentos num campo de obras.
     *
     * @param constructionSite O campo de obras a ser verificado.
     * @return Um objeto dataAlert contendo informações sobre conflitos de equipamentos, ou null se não houver conflito.
     */
    public static dataAlert checkEquipment(ConstructionSite constructionSite) {
        dataAlert alert = new dataAlert();
        Equipments thisEquip;
        Equipments thatEquip;

        if (constructionSite == null) {
            return null;
        }

        thatEquip = constructionSite.getEquipments();

        if (thatEquip == null) {
            return null;
        }

        if (constructionCheker == null) {
            return null;
        }

        for (int constructionIndex = 0; constructionIndex < totalConstructionCnt; constructionIndex++) {

            if (constructionCheker[constructionIndex] == null) {
                break;
            }

            if (constructionCheker[constructionIndex].equals(constructionSite)) {
                continue;
            }

            if (constructionCheker[constructionIndex].getTeams() == null) {
                continue;
            }

            if (constructionCheker[constructionIndex].getStartDate().isAfter(constructionSite.getEndDate())) {
                continue;
            }

            if (constructionSite.getStartDate().isAfter(constructionCheker[constructionIndex].getEndDate())) {
                continue;
            }

            if (constructionCheker[constructionIndex].equals(constructionSite)) {
                continue;
            }

            thisEquip = constructionCheker[constructionIndex].getEquipments();

            if (thisEquip == null) {
                break;
            }

            for (int thisEquipIndex = 0; thisEquipIndex < thisEquip.getEquipment().length; thisEquipIndex++) {

                if (thisEquip.getEquipment()[thisEquipIndex] == null) {
                    break;
                }

                for (int thatIndex = 0; thatIndex < thatEquip.getEquipment().length; thatIndex++) {
                    if (thatEquip.getEquipment()[thatIndex] == null) {
                        break;
                    }
                    if (thisEquip.getEquipment()[thisEquipIndex].equals(thatEquip.getEquipment()[thatIndex])) {

                        if (constructionCheker[constructionIndex].getStartDate().isBefore(constructionSite.getStartDate())) {
                            alert.setMessage("O equipamento" + thisEquip.getEquipment()[thisEquipIndex].toString() + " já pertence a uma equipe no campo de obras " + constructionCheker[constructionIndex].getName() + " (até " + constructionCheker[constructionIndex].getEndDate() + ")");
                        } else {
                            alert.setMessage("O equipamento" + thatEquip.getEquipment()[thatIndex].toString() + " já pertence a uma equipe no campo de obras " + constructionSite.getName() + " (até " + constructionSite.getEndDate() + ")");
                        }

                        return alert;
                    }
                }


            }
        }

        return null;
    }

    /**
     * Verifica a disponibilidade de funcionários em um campo de obras, comparando com todos os outros criados.
     *
     * @param constructionSite O campo de obras a ser verificado.
     * @return Um objeto dataAlert contendo informações sobre conflitos de funcionários, ou null se não houver conflito.
     */
    public static dataAlert checkEmployee(ConstructionSite constructionSite) {
        dataAlert alert = new dataAlert();
        Team[] thisTeam;
        Team[] thatTeam;

        if (constructionSite == null) {
            return null;
        }

        thatTeam = constructionSite.getTeams();

        if (thatTeam == null) {
            return null;
        }

        if (constructionCheker == null) {
            return null;
        }

        for (int constructionIndex = 0; constructionIndex < totalConstructionCnt; constructionIndex++) {

            if (constructionCheker[constructionIndex] == null) {
                break;
            }

            if (constructionCheker[constructionIndex].equals(constructionSite)) {
                continue;
            }

            thisTeam = constructionCheker[constructionIndex].getTeams();

            if (thisTeam == null) {
                continue;
            }

            if (constructionCheker[constructionIndex].getStartDate().isAfter(constructionSite.getEndDate())) {
                continue;
            }

            if (constructionSite.getStartDate().isAfter(constructionCheker[constructionIndex].getEndDate())) {
                continue;
            }

            if (constructionCheker[constructionIndex].equals(constructionSite)) {
                continue;
            }

            for (Team team : thisTeam) {

                if (team == null) {
                    break;
                }

                for (Team value : thatTeam) {
                    if (value == null) {
                        break;
                    }


                    if (team.equals(value)) {
                        if (constructionCheker[constructionIndex].getStartDate().isBefore(constructionSite.getStartDate())) {
                            alert.setMessage("A equipe " + team.getName() + " já pertence ao campo de obras: " + constructionCheker[constructionIndex].getName() + " (até " + constructionCheker[constructionIndex].getEndDate() + ")");
                        } else {
                            alert.setMessage("A equipe " + value.getName() + " já pertence ao campo de obras: " + constructionSite.getName() + " (até " + constructionSite.getEndDate() + ")");
                        }

                        return alert;
                    }

                    for (int EmpIndex = 0; EmpIndex < team.getNumberOfEmployees(); EmpIndex++) {
                        if (team.getEmployees()[EmpIndex] == null) {
                            break;
                        }
                        for (int EmpIndex2 = 0; EmpIndex2 < value.getNumberOfEmployees(); EmpIndex2++) {

                            if (value.getEmployees()[EmpIndex2] == null) {
                                break;
                            }

                            if (team.getEmployees()[EmpIndex].equals(value.getEmployees()[EmpIndex2])) {
                                if (constructionCheker[constructionIndex].getStartDate().isBefore(constructionSite.getStartDate())) {
                                    alert.setMessage("O " + value.getEmployees()[EmpIndex2].getType().toString() + " " + value.getEmployees()[EmpIndex2].getName() + " já está na equipe " + team.getName() + " do campo de obras: " + constructionCheker[constructionIndex].getName() + " (até " + constructionCheker[constructionIndex].getEndDate() + ")");
                                } else {
                                    alert.setMessage("O " + value.getEmployees()[EmpIndex2].getType().toString() + " " + value.getEmployees()[EmpIndex2].getName() + " já está na equipe " + value.getName() + " do campo de obras: " + constructionSite.getName() + " (até " + constructionSite.getEndDate() + ")");
                                }
                                return alert;
                            }
                        }
                    }
                }
            }


        }

        return null;
    }

    /**
     * Verifica a disponibilidade de gerentes em um campo de obras.
     *
     * @param Cs O campo de obras a ser verificado.
     * @return Um objeto dataAlert contendo informações sobre conflitos de gerentes, ou null se não houver conflito.
     */
    public static dataAlert checkManager(ConstructionSite Cs) {
        dataAlert alert = new dataAlert();

        if (totalConstructionCnt == 0) {
            return null;
        }

        if (Cs == null) {
            return null;
        }

        if (Cs.getResponsible() == null) {
            return null;
        }

        for (int CsIndex = 0; CsIndex < totalConstructionCnt; CsIndex++) {

            if (constructionCheker[CsIndex] == null) {
                break;
            }

            if (constructionCheker[CsIndex].getResponsible() == null) {
                continue;
            }

            if (constructionCheker[CsIndex].getStartDate().isAfter(Cs.getEndDate())) {
                continue;
            }

            if (Cs.getStartDate().isAfter(constructionCheker[CsIndex].getEndDate())) {
                continue;
            }

            if (constructionCheker[CsIndex].equals(Cs)) {
                continue;
            }

            if (constructionCheker[CsIndex].getResponsible().equals(Cs.getResponsible())) {
                if (constructionCheker[CsIndex].getStartDate().isBefore(Cs.getStartDate())) {
                    alert.setMessage("O gerente " + constructionCheker[CsIndex].getResponsible().getName() + " já é responsável no campo de obras " + constructionCheker[CsIndex].getName() + " (até " + constructionCheker[CsIndex].getEndDate() + ")");
                    return alert;
                } else {
                    alert.setMessage("O gerente " + constructionCheker[CsIndex].getResponsible().getName() + " já é responsável no campo de obras " + Cs.getName() + " (até " + Cs.getEndDate() + ")");
                    return alert;
                }
            }

            for (int teamIndex = 0; teamIndex < constructionCheker[CsIndex].getTeams().length; teamIndex++) {
                if (constructionCheker[CsIndex].getTeams() == null) {
                    break;
                }
                if (constructionCheker[CsIndex].getTeams()[teamIndex] == null) {
                    break;
                }
                for (int empIndex = 0; empIndex < constructionCheker[CsIndex].getTeams()[teamIndex].getNumberOfEmployees(); empIndex++) {
                    if (constructionCheker[CsIndex].getTeams()[teamIndex].getEmployees()[empIndex] == null) {
                        break;
                    }
                    if (constructionCheker[CsIndex].getTeams()[teamIndex].getEmployees()[empIndex].equals(Cs.getResponsible())) {
                        if (constructionCheker[CsIndex].getStartDate().isBefore(Cs.getStartDate())) {
                            alert.setMessage("O gerente " + constructionCheker[CsIndex].getResponsible().getName() + " pertence a equipe " + constructionCheker[CsIndex].getTeams()[teamIndex] + " do campo de obras " + constructionCheker[CsIndex].getName() + " logo não pode ser gerente do campo de obras: " + Cs.getName() + " (até " + constructionCheker[CsIndex].getEndDate() + ")");
                        } else {
                            alert.setMessage("O gerente " + constructionCheker[CsIndex].getResponsible().getName() + " já é responsável no campo de obras " + Cs.getName() + " logo não pode pertencer a equipe " + constructionCheker[CsIndex].getTeams()[teamIndex] + " do campo de obras " + constructionCheker[CsIndex].getName() + " (até " + Cs.getEndDate() + ")");
                        }
                        return alert;
                    }
                }

            }

        }
        return null;

    }

    /**
     * Verifica a disponibilidade de líderes em equipes de um campo de obras.
     *
     * @param Cs O campo de obras a ser verificado.
     * @return Um objeto dataAlert contendo informações sobre conflitos de líderes, ou null se não houver conflito.
     */
    public static dataAlert checkLeader(ConstructionSite Cs) {
        dataAlert alert = new dataAlert();
        Team[] thatTeams;
        Team[] thisTeams;

        if (totalConstructionCnt == 0) {
            return null;
        }

        if (Cs == null) {
            return null;
        }

        thatTeams = Cs.getTeams();

        if (Cs.getTeams() == null) {
            return null;
        }

        for (int CsIndex = 0; CsIndex < totalConstructionCnt; CsIndex++) {

            if (constructionCheker[CsIndex] == null) {
                break;
            }

            if (constructionCheker[CsIndex].equals(Cs)) {
                continue;
            }

            if (constructionCheker[CsIndex].getStartDate().isAfter(Cs.getEndDate())) {
                continue;
            }

            if (Cs.getStartDate().isAfter(constructionCheker[CsIndex].getEndDate())) {
                continue;
            }

            thisTeams = constructionCheker[CsIndex].getTeams();

            if (thisTeams == null) {
                continue;
            }

            for (Team thisTeam : thisTeams) {
                if (thisTeam == null) {
                    break;
                }

                for (int thatIndex = 0; thatIndex < Cs.getTeams().length; thatIndex++) {
                    if (thatTeams[thatIndex] == null) {
                        break;
                    }
                    if (thatTeams[thatIndex].getLeader() == null) {
                        continue;
                    }
                    if (thisTeam.getLeader() != null && thisTeam.getLeader().equals(thatTeams[thatIndex].getLeader())) {
                        if (constructionCheker[CsIndex].getStartDate().isBefore(Cs.getStartDate())) {
                            alert.setMessage("O/A Lider " + thisTeam.getLeader().getName() + " já é responsável pela equipe " + thisTeam.getName() + " na obra " + constructionCheker[CsIndex].getName());
                            return alert;
                        } else {
                            alert.setMessage("O/A Lider " + thatTeams[thatIndex].getLeader().getName() + " já é responsável pela equipe " + thatTeams[thatIndex].getName() + " na obra " + Cs.getName() + " (até " + Cs.getEndDate() + ")");
                            return alert;
                        }

                    }

                }
            }
        }


        return null;
    }

    /**
     * Verifica vários aspectos de um campo de obras, incluindo gerentes, líderes, funcionários e equipamentos.
     *
     * @param Cs O campo de obras a ser verificado.
     * @return Um array de objetos dataAlert contendo informações sobre conflitos em diferentes aspectos.
     */
    public static dataAlert[] checkConstructionSite(ConstructionSite Cs) {
        dataAlert[] Alerts = new dataAlert[4];
        Alerts[0] = checkManager(Cs);
        Alerts[1] = checkLeader(Cs);
        Alerts[2] = checkEmployee(Cs);
        Alerts[3] = checkEquipment(Cs);
        return Alerts;
    }

    /**
     * Obtém uma lista de equipes que não estão ativas em nenhum projeto de construção.
     * Este método verifica todas as equipes associadas a projetos de construção e identifica aquelas que não estão em uso.
     * Uma equipe é considerada ociosa se a data de início do projeto é posterior à data atual ou se a data de término é anterior à data atual.
     *
     * @return Um array de equipes ociosas. Se não houver equipes ociosas, retorna um array vazio.
     */
    public static Team[] getIdleeTeams() {
        Team[] teams = new Team[totalTeamsCnt];
        Team[] associatedTeams = new Team[totalTeamsCnt];
        int teamsIndex = 0;
        int ASTIndex = 0;

        if (constructionCheker == null && totalTeamsCnt != 0) {
            return teamsChecker;
        }

        if (totalTeamsCnt == 0) {
            return null;
        }

        for (int i = 0; i < totalConstructionCnt; i++) {
            if (constructionCheker[i] == null) {
                break;
            }

            Team[] thisTeams = constructionCheker[i].getTeams();

            if (thisTeams == null) {
                continue;
            }

            for (Team thisTeam : thisTeams) {
                if (thisTeam == null) {
                    break;
                }

                if (constructionCheker[i].getStartDate().isAfter(LocalDate.now()) || constructionCheker[i].getEndDate().isBefore(LocalDate.now())) {
                    teams[teamsIndex] = thisTeam;
                    teamsIndex++;
                }

                associatedTeams[ASTIndex] = thisTeam;
                ASTIndex++;
            }
        }

        // Add remaining teams that are not associated
        for (int teamIndex = 0; teamIndex < totalTeamsCnt; teamIndex++) {
            if (teamsChecker[teamIndex] == null) {
                break;
            }
            if (!containsTeam(associatedTeams, teamsChecker[teamIndex])) {
                teams[teamsIndex] = teamsChecker[teamIndex];
                teamsIndex++;
            }
        }

        return teams;
    }

    /**
     * Verifica se uma equipe específica está contida em um array de equipes.
     *
     * @param teams O array de equipes a ser verificado.
     * @param team  A equipe a ser procurada.
     * @return true se a equipe estiver no array, false caso contrário.
     */
    private static boolean containsTeam(Team[] teams, Team team) {
        for (Team t : teams) {
            if (t != null && t.equals(team)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Obtém uma lista de equipes que estão ativas em projetos de construção.
     * Este método utiliza o método `getIdleeTeams` para obter as equipes ociosas e, em seguida, identifica as equipes que não estão nessa lista.
     *
     * @return Um array de equipes que estão trabalhando. Se não houver equipes trabalhando, retorna um array vazio.
     */
    public static Team[] getWorkingTeams() {
        Team[] workingTeams = new Team[totalTeamsCnt];
        Team[] idleeTeams = getIdleeTeams();
        int workingIndex = 0;

        if (teamsChecker == null) {
            return null;
        }

        if (idleeTeams == null) {
            return teamsChecker;
        }


        for (int i = 0; i < totalTeamsCnt; i++) {
            if (teamsChecker[i] == null) {
                break;
            }
            for (Team idleeTeam : idleeTeams) {
                if (idleeTeam == null) {
                    break;
                }
                if (!teamsChecker[i].equals(idleeTeam)) {
                    workingTeams[workingIndex] = teamsChecker[i];
                    workingIndex++;
                }
            }

        }
        return workingTeams;
    }

    /**
     * Obtém um array de equipamentos que não estão atualmente em uso em nenhum projeto de construção.
     * Este método percorre todos os projetos de construção, verificando se cada equipamento está em uso.
     * Um equipamento é considerado ocioso se a data de início do projeto é posterior à data atual ou se a data de término é anterior à data atual ou se não pertencer a nenhum campo de obras.
     *
     * @return Um array de equipamentos ociosos. Se não houver equipamentos ociosos, retorna null.
     */
    public static Equipment[] getIdleeEquipments() {
        Equipment[] idleEquipments = new Equipment[totalEquipmentCnt];
        Equipment[] associatedEq = new Equipment[totalEquipmentCnt];
        int equipmentIndex = 0;
        int ASEIndex = 0;

        if (constructionCheker == null && totalEquipmentCnt != 0) {
            return equipmentChecker;
        }

        if (totalEquipmentCnt == 0) {
            return null;
        }

        for (int i = 0; i < totalConstructionCnt; i++) {
            if (constructionCheker[i] == null) {
                break;
            }

            Equipments thisEquipments = constructionCheker[i].getEquipments();

            if (thisEquipments == null) {
                continue;
            }

            for (int j = 0; j < thisEquipments.getEquipment().length; j++) {
                if (thisEquipments.getEquipment()[j] == null) {
                    break;
                }

                if (constructionCheker[i].getStartDate().isAfter(LocalDate.now()) ||
                        constructionCheker[i].getEndDate().isBefore(LocalDate.now())) {
                    idleEquipments[equipmentIndex] = thisEquipments.getEquipment()[j];
                    equipmentIndex++;
                }

                associatedEq[ASEIndex] = thisEquipments.getEquipment()[j];
                ASEIndex++;
            }
        }

        // Add remaining equipment that is not associated
        for (int eqIndex = 0; eqIndex < totalEquipmentCnt; eqIndex++) {
            if (equipmentChecker[eqIndex] == null) {
                break;
            }
            if (!containsEquipment(associatedEq, equipmentChecker[eqIndex])) {
                idleEquipments[equipmentIndex] = equipmentChecker[eqIndex];
                equipmentIndex++;
            }
        }

        return idleEquipments;
    }

    /**
     * Verifica se um equipamento específico está contido em um array de equipamentos.
     *
     * @param equipments O array de equipamentos a ser verificado.
     * @param equipment  O equipamento a ser procurado.
     * @return true se o equipamento estiver no array, false caso contrário.
     */
    private static boolean containsEquipment(Equipment[] equipments, Equipment equipment) {
        for (Equipment e : equipments) {
            if (e != null && e.equals(equipment)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Obtém um array de equipamentos que estão em uso em projetos de construção.
     * Este método utiliza o método `getIdleeEquipments` para obter os equipamentos ociosos e, em seguida, identifica os equipamentos que não estão nessa lista.
     *
     * @return Um array de equipamentos que estão em uso. Se não houver equipamentos em uso, retorna um array vazio.
     */
    public static Equipment[] getInUseEquipments() {

        Equipment[] inUseEquipments = new Equipment[totalTeamsCnt];
        Equipment[] idleeEquipments = getIdleeEquipments();
        int inUseIndex = 0;

        if (idleeEquipments == null) {
            return equipmentChecker;
        }

        for (int i = 0; i < totalEquipmentCnt; i++) {
            if (equipmentChecker[i] == null) {
                break;
            }
            for (Equipment idleeEquipment : idleeEquipments) {
                if (idleeEquipment == null) {
                    break;
                }
                if (!equipmentChecker[i].equals(idleeEquipment)) {
                    inUseEquipments[inUseIndex] = equipmentChecker[i];
                    inUseIndex++;
                }
            }

        }
        return inUseEquipments;
    }

    /**
     * Cria um novo array de equipes com um tamanho aumentado por um fator especificado.
     * Este método é útil para aumentar o tamanho do array quando necessário.
     *
     * @param array O array original de equipes.
     * @param times O fator de aumento para o tamanho do array.
     * @return Um novo array de equipes com o tamanho aumentado.
     */
    private static Team[] teamAddSpace(Team[] array, int times) {
        int newSize = array.length * times;
        Team[] temp = new TeamImp[newSize];

        for (int i = 0; i < array.length; i++) {
            temp[i] = array[i];
        }

        return temp;
    }

    /**
     * Cria um novo array de sites de construção com um tamanho aumentado por um fator especificado.
     * Este método é útil para aumentar o tamanho do array quando necessário.
     *
     * @param times O fator de aumento para o tamanho do array.
     * @return Um novo array de sites de construção com o tamanho aumentado.
     */
    private static ConstructionSiteImp[] constructionAddSpace(int times) {
        int newSize = constructionCheker.length * times;
        ConstructionSiteImp[] temp = new ConstructionSiteImp[newSize];

        for (int i = 0; i < constructionCheker.length; i++) {
            temp[i] = constructionCheker[i];
        }

        return temp;
    }

    /**
     * Cria um novo array de funcionários com um tamanho aumentado por um fator especificado.
     * Este método é útil para aumentar o tamanho do array quando necessário.
     *
     * @param times O fator de aumento para o tamanho do array.
     * @return Um novo array de funcionários com o tamanho aumentado.
     */
    private static EmployeeExt[] employeeAddSpace(int times) {
        int newSize = totalEmployeeCount * times;
        EmployeeExt[] temp = new EmployeeExt[newSize];

        for (int i = 0; i < totalEmployeeCount; i++) {
            temp[i] = employeeChecker[i];
        }

        return temp;
    }

    /**
     * Cria um novo array de equipamentos com um tamanho aumentado por um fator especificado.
     * Este método é útil para aumentar o tamanho do array quando necessário.
     *
     * @param array O array original de equipamentos.
     * @param times O fator de aumento para o tamanho do array.
     * @return Um novo array de equipamentos com o tamanho aumentado.
     */
    private static Equipment[] equipmentAddSpace(Equipment[] array, int times) {
        int newSize = array.length * times;
        Equipment[] temp = new Equipment[newSize];

        for (int i = 0; i < array.length; i++) {
            temp[i] = array[i];
        }

        return temp;
    }

    /**
     * Cria um novo array de notificadores com um tamanho aumentado por um fator especificado.
     * Este método é útil para aumentar o tamanho do array quando necessário.
     *
     * @param times O fator de aumento para o tamanho do array.
     * @return Um novo array de notificadores com o tamanho aumentado.
     */
    private static NotifierImp[] notifierAddSpace(int times) {
        int newSize = notifierChecker.length * times;
        NotifierImp[] temp = new NotifierImp[newSize];

        for (int i = 0; i < notifierChecker.length; i++) {
            temp[i] = notifierChecker[i];
        }

        return temp;
    }

}
