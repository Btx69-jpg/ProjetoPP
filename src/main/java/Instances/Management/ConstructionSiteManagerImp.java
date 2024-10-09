package Instances.Management;

import Classes.Constantes;
import Classes.Control.ControlClass;
import Instances.ConstructionSite.ConstructionSiteImp;
import Instances.Notifications.dataAlert;
import estgconstroi.ConstructionSite;
import estgconstroi.ConstructionSiteManager;
import estgconstroi.Equipment;
import estgconstroi.Team;
import estgconstroi.exceptions.ConstructionSiteManagerException;

import java.time.LocalDate;

/*
 * Nome: Willkie Bianchi Parahyba Filho
 * Número: 8230127
 * Turma: LEI1T1
 *
 */

public class ConstructionSiteManagerImp implements ConstructionSiteManager {
    private ConstructionSite[] constructionSites = new ConstructionSite[Constantes.INITIAL_ARRAY_SIZE];
    private int CsCnt = 0;

    @Override
    public void add(ConstructionSite constructionSite) throws ConstructionSiteManagerException {
        if (constructionSite == null) {
            throw new ConstructionSiteManagerException("ConstructionSite " + Constantes.CANNOT_BE_NULL);
        }
        if (this.CsCnt == 0) {
            this.constructionSites[CsCnt] = constructionSite;
            CsCnt++;
            return;
        }
        for (int i = 0; i < CsCnt; i++) {
            if (this.constructionSites[i].equals(constructionSite)) {
                throw new ConstructionSiteManagerException("ConstructionSite " + Constantes.ALREADY_EXISTS);
            }
        }

        this.constructionSites[CsCnt] = constructionSite;
        CsCnt++;

    }

    @Override
    public Team[] getWorkingTeams() {
        return ControlClass.getWorkingTeams();
    }

    @Override
    public Team[] getIddleTeams() {
        return ControlClass.getIdleeTeams();
    }

    @Override
    public Equipment[] getEquipmentsInUse() {
        return ControlClass.getInUseEquipments();
    }

    @Override
    public Equipment[] getIddleEquipments() {
        return ControlClass.getIdleeEquipments();
    }

    @Override
    public ConstructionSite[] getConstructionSitesWithPermitExpired() {
        ConstructionSite[] temp = new ConstructionSite[CsCnt];
        int tempindex = 0;

        if (CsCnt == 0) {
            return null;
        }

        for (int i = 0; i < CsCnt; i++) {
            if (this.constructionSites[i] == null) {
                break;
            }

            if (this.constructionSites[i].getPermitExpirationDate() == null) {
                temp[tempindex] = this.constructionSites[i];
                tempindex++;
                continue;
            }

            //comparar com a data atual em vez da data de fim do projeto pois ha sempre a possibilidade de receber uma nova permissão
            if (this.constructionSites[i].getPermitExpirationDate().isBefore(LocalDate.now())) {
                temp[tempindex] = this.constructionSites[i];
                tempindex++;
            }

        }

        return temp;
    }

    @Override
    public boolean isValid() {
        dataAlert[] alert;
        boolean gotAlerts = false;


        if (this.constructionSites == null) {
            return true;
        }

        for (int i = 0; i < CsCnt; i++) {
            if (this.constructionSites[i] == null) {
                break;
            }

            alert = ControlClass.checkConstructionSite(this.constructionSites[i]);

            for (Instances.Notifications.dataAlert dataAlert : alert) {
                if (dataAlert != null) {
                    System.out.println("ConstructionSite " + i + " (" + this.constructionSites[i].getName() + "): ");
                    System.out.println("Error: ");
                    System.out.println(dataAlert.getMessage());
                    gotAlerts = true;
                }

            }
            if (!gotAlerts) {
                return false;
            }
        }

        return true;
    }

    private ConstructionSite[] constructionAddSpace(int times) {
        int newSize = this.constructionSites.length * times;
        ConstructionSite[] temp = new ConstructionSiteImp[newSize];

        for (int i = 0; i < this.constructionSites.length; i++) {
            temp[i] = this.constructionSites[i];
        }

        return temp;
    }

}
