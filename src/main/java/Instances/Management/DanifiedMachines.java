package Instances.Management;

import Classes.Constantes;
import Classes.Control.ControlEvents;
import Instances.Equipments.EquipmentImp;
import Instances.Incidents.MachineAccident;
import estgconstroi.Equipment;
import estgconstroi.Equipments;

public class DanifiedMachines extends EventManagerImp implements Defesa {

    @Override
    public Equipment[] getAccidentEquipments() {
        Equipment[] equip = new Equipment[Constantes.INITIAL_ARRAY_SIZE];
        int index = 0;
        MachineAccident[] accident;
        accident = ControlEvents.getMachineAccident();

        if (accident == null){
            return null;
        }

        for (int i = 0; i < accident.length; i++){
            if (accident[i] == null){
                break;
            }
            if (accident[i].getEquipment() == null){
                continue;
            }

            if (index == equip.length){
                equip = equipmentsAddSpace(equip, Constantes.ARRAY_SIZE_MULTIPLIER);
            }
            equip[index] = accident[i].getEquipment();
            index++;
        }

        return equip;
    }

    private Equipment[] equipmentsAddSpace(Equipment[] equip,int times) {
        Equipment[] temp = new Equipment[equip.length * times];

        for (int i = 0; i < equip.length; i++) {
            temp[i] = equip[i];
        }

        equip = temp;
        return equip;
    }

}
