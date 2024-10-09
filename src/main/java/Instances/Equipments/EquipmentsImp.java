package Instances.Equipments;

import estgconstroi.Equipment;
import estgconstroi.Equipments;
import estgconstroi.enums.EquipmentStatus;
import estgconstroi.enums.EquipmentType;
import estgconstroi.exceptions.ConstructionSiteException;

import java.util.Arrays;
import java.util.Objects;

import static Classes.Constantes.*;
/*
 * Nome: Willkie Bianchi Parahyba Filho
 * Número: 8230127
 * Turma: LEI1T1
 *
 */

public class EquipmentsImp implements Equipments {

    private EquipmentImp[] equipments = new EquipmentImp[INITIAL_ARRAY_SIZE];

    private int equipmentsCount = 0;

    public EquipmentsImp(EquipmentImp[] equipments) {
        this.equipments = equipments;
        equipmentsCount = equipments.length;
    }

    public EquipmentsImp() {
    }

    /**
     * Adiciona um equipamento a lista de equipamentos do canteiro de obras.
     *
     * @param equipment equipamento a adicionar.
     * @throws ConstructionSiteException se o equipamento for nulo ou ja existir neste grupo de equipamentos.
     */
    @Override
    public void addEquipment(Equipment equipment) throws ConstructionSiteException {
        //verifica se o equipamento é nulo
        if (equipment == null) {
            throw new ConstructionSiteException("Equipment" + CANNOT_BE_NULL);
        }

        //verifica se o equipamento ja existe
        if (equipmentsCount != 0) {
            for (int i = 0; i < equipmentsCount; i++) {
                if (equipments[i].equals(equipment)) {
                    throw new ConstructionSiteException("O equipamento " + equipment + ALREADY_EXISTS + " neste grupo de equipamentos");
                }
            }

            //aumenta o array se necessario
            if (equipmentsCount == equipments.length) {
                equipmentsAddSpace(ARRAY_SIZE_MULTIPLIER);
            }

        }

        //adiciona o equipamento
        equipments[equipmentsCount] = (EquipmentImp) equipment;

        //aumenta o contador
        equipmentsCount++;

    }

    /**
     * Remove uma Ferramenta do canteiro de obras.
     *
     * @param equipment O equipamento a ser removido.
     * @throws ConstructionSiteException caso o equipamento seja nulo ou não encontrado.
     */
    @Override
    public void removeEquipment(Equipment equipment) throws ConstructionSiteException {
        //verifica se o equipamento é nulo
        if (equipment == null) {
            throw new ConstructionSiteException("Equipment" + CANNOT_BE_NULL);
        }

        //verifica se o equipamento existe e o remove
        if (this.equipmentsCount != 0) {
            int index = -1;
            for (int i = 0; i < equipmentsCount; i++) {
                if (this.equipments[i].equals(equipment)) {
                    index = i;
                    break;
                }
            }
            if (index != -1) {
                for (int i = index; i < equipmentsCount - 1; i++) {
                    this.equipments[i] = this.equipments[i + 1];
                }
                // Set the last element to null
                this.equipments[--equipmentsCount] = null;
            }
        } else {
            throw new ConstructionSiteException("Equipment" + NOT_FOUND);
        }

    }

    /**
     * Filtra os equipamentos de acordo com um dado nome.
     * If matching equipment is found, it returns an array containing all matching equipment.
     * Se não houver equipamentos ou caso haja não encontra nenhum equipamento com o dado nome, retorna nulo.
     * Se a entrada é nula, é lançada uma IllegalArgumentException.
     *
     * @param name nome do equipamento procurado
     * @return um array com os equipamentos encontrados com o nome desejado, ou nulo caso não haja nenhum.
     * @throws IllegalArgumentException caso o a entrada seja nula
     */
    //********verificar se há necessidade de retornar uma deepcopy**********
    @Override
    public Equipment[] getEquipment(String name) {
        // Verifica se o nome recebido é nulo, caso seja, retorna uma excessão
        if (name == null) {
            throw new IllegalArgumentException("Name" + CANNOT_BE_NULL);
        }
        if (equipmentsCount != 0) {

            Equipment[] equipments = new Equipment[this.equipments.length];
            int index = 0;

            for (int i = 0; i < equipmentsCount; i++) {

                if (this.equipments[i].getName().equals(name)) {
                    equipments[index] = this.equipments[i];
                    index++;
                }

            }

            // retorna o array filtrado
            return equipments;
        }

        return null;
    }

    /**
     * Obtém um array de equipamentos com base no status do equipamento
     *
     * @param equipmentStatus O status do equipamento para filtrar os equipamentos
     * @return Um array de equipamentos que correspondem ao status fornecido Se nenhum equipamento corresponder ao status fornecido, retorna null
     * @throws IllegalArgumentException Se o status do equipamento fornecido for null, lança esta exceção
     */
    @Override
    public Equipment[] getEquipment(EquipmentStatus equipmentStatus) {
        if (equipmentStatus == null) {
            throw new IllegalArgumentException("Equipment status" + CANNOT_BE_NULL);
        }
        if (equipmentsCount != 0) {

            Equipment[] equipments = new Equipment[this.equipments.length];
            int index = 0;

            for (int i = 0; i < equipmentsCount; i++) {

                if (this.equipments[i].getStatus().equals(equipmentStatus)) {
                    equipments[index] = this.equipments[i];
                    index++;
                }

            }

            return equipments;
        }

        return null;
    }

    /**
     * Obtém um array de equipamentos com base no tipo do equipamento
     *
     * @param equipmentType O tipo do equipamento para filtrar os equipamentos
     * @return Um array de equipamentos que correspondem ao tipo fornecido Se nenhum equipamento corresponder ao tipo fornecido, retorna null
     * @throws IllegalArgumentException Se o tipo do equipamento fornecido for null, lança esta exceção
     */
    @Override
    public Equipment[] getEquipment(EquipmentType equipmentType) {
        if (equipmentType == null) {
            throw new IllegalArgumentException("Equipment type" + CANNOT_BE_NULL);
        }
        if (equipmentsCount != 0) {

            Equipment[] equipments = new Equipment[this.equipments.length];
            int index = 0;

            for (int i = 0; i < equipmentsCount; i++) {

                if (this.equipments[i].getType().equals(equipmentType)) {
                    equipments[index] = this.equipments[i];
                    index++;
                }

            }

            return equipments;
        }

        return null;
    }

    /**
     * @return Um array com todos os equipamentos do canteiro de obras.
     */
    @Override
    public Equipment[] getEquipment() {
        // Create a new array to hold the copied equipment
        Equipment[] equipmentCopy = new Equipment[this.equipments.length];

        // Manually copy each element from the original array to the new array
        for (int i = 0; i < this.equipments.length; i++) {
            if (this.equipments[i] != null) {
                equipmentCopy[i] = new EquipmentImp(this.equipments[i]);
            }

        }

        return equipmentCopy;
    }

    public int getEquipmentsCnt() {
        return this.equipmentsCount;
    }

    /**
     * Aumenta "dinamicamente" o espaço da lista de equipamentos
     * Este método é usado para expandir a capacidade da lista de equipamentos quando ela está cheia, permitindo a adição de mais equipamentos.
     * Ele substitui o array de equipamentos existente por um maior, atribuindo os valores ja existentes a este mesmo array.
     */
    private void equipmentsAddSpace(int times) {
        EquipmentImp[] temp = new EquipmentImp[equipmentsCount * times];

        for (int i = 0; i < this.equipmentsCount; i++) {
            temp[i] = this.equipments[i];
        }

        this.equipments = temp;
    }

    // Método para realizar cópia profunda de um gp de equipamentos
    public EquipmentsImp deepCopy() {
        EquipmentsImp copy = new EquipmentsImp();
        copy.equipmentsCount = this.equipmentsCount;
        copy.equipments = new EquipmentImp[this.equipments.length];
        for (int i = 0; i < this.equipmentsCount; i++) {
            copy.equipments[i] = new EquipmentImp(this.equipments[i]);
        }
        return copy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EquipmentsImp)) {
            return false;
        }
        EquipmentsImp that = (EquipmentsImp) o;
        //confirmar depois a testar se é comparado o endereço de memoria ou o conteudo dos equipments utilizando o objects.deepequals
        return equipmentsCount == that.equipmentsCount && Objects.deepEquals(equipments, that.equipments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(equipments), equipmentsCount);
    }

    @Override
    public String toString() {
        StringBuilder equipmentsString = new StringBuilder("[");
        for (EquipmentImp equipment : this.equipments) {
            if (equipment != null) {
                if (equipmentsString.length() > 1) {
                    equipmentsString.append(", ");
                }
                equipmentsString.append(equipment);
            }
        }
        equipmentsString.append("]");

        return "EquipmentsImp{" +
                "equipments=" + equipmentsString +
                ", equipmentsCount=" + equipmentsCount +
                '}';
    }
}
