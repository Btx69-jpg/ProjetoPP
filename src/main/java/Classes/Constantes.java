package Classes;

import Instances.Equipments.EquipmentImp;
import Instances.Workers.EmployeeExt;
import estgconstroi.enums.EmployeeType;
/*
 * Nome: Willkie Bianchi Parahyba Filho
 * Número: 8230127
 * Turma: LEI1T1
 *
 */

public class Constantes {
    public static final int INITIAL_ARRAY_SIZE = 10;
    public static final String NOT_FOUND = " Não encontrado";
    public static final String ALREADY_EXISTS = " Já existe";
    public static final String CANNOT_BE_NULL = " Não pode ser nulo/a";
    public static final String LOGFILENAME = "Exeptions.log";
    public static final EmployeeExt INSURANCE_REPORT = new EmployeeExt("Insurance Report", EmployeeType.MANAGER);
    public static final String INSURANCE_REPORT_ID = INSURANCE_REPORT.getUUID();
    public static final String INSURANCE_NOTIFICATION_MESSAGE = "Insurance Report";
    public static final String GROUPKEY = "121314151617181";
    public static final String GROUPNAME = "Grupo23127";
    //ao fazer menus permitir alterar este valor
    public static int ARRAY_SIZE_MULTIPLIER = 2;

    public static EquipmentImp[] equipmentAddSpace(EquipmentImp[] equipment, int times) {
        EquipmentImp[] temp = new EquipmentImp[equipment.length * times];

        for (int i = 0; i < equipment.length; i++) {
            temp[i] = equipment[i];
        }

        return temp;
    }

}
