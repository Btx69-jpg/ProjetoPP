package IdeiaDescartada;
/*
 * Nome: Willkie Bianchi Parahyba Filho
 * Número: 8230127
 * Turma: LEI1T1
 *
 */

public class ManagingBranch {
    private static final int ARRAYCAPACITY = 10;
    private static final ManagingBranch ConstructionSite = new ManagingBranch("Construction Site");
    private static final ManagingBranch Event = new ManagingBranch("Event");
    private static int TypeCnt = 0;
    private static ManagingBranch[] ALL_TYPES = new ManagingBranch[ARRAYCAPACITY];
    private static int currentIndex = 0;
    private final String name;
    private int IndividualTypeNumber = 0;

    public ManagingBranch(String name) {
        this.name = name;
        this.IndividualTypeNumber = TypeCnt;
        addBranch(this);
        TypeCnt++;
    }

    public static int getTypeCnt() {
        return TypeCnt;
    }

    public static ManagingBranch getManagingBranchByName(String name) {
        for (ManagingBranch type : ALL_TYPES) {
            if (type.getType().equals(name)) {
                return type;
            }
        }
        return null;
    }

    public static ManagingBranch[] getAllTypes() {
        // Copia do array com todos os tipos
        ManagingBranch[] allTypes = new ManagingBranch[currentIndex];
        if (ALL_TYPES != null) {
            for (int i = 0; i < currentIndex; i++) {
                allTypes[i] = ALL_TYPES[i];
            }
        }

        return allTypes;
    }

    private static void addBranch(ManagingBranch containerType) {
        if (currentIndex >= ALL_TYPES.length) {
            expandArray();
        }
        ALL_TYPES[currentIndex++] = containerType;
    }

    private static void expandArray() {
        ManagingBranch[] newArray = new ManagingBranch[currentIndex + ARRAYCAPACITY];
        for (int i = 0; i < ALL_TYPES.length; i++) {
            newArray[i] = ALL_TYPES[i];
        }
        ALL_TYPES = newArray;
    }

    //Retorna true se o  ramo de gerencia for encontrado e false caso não seja. reArranja o array com os ramos de atuação evitando espaços vazios no meio do array e
    //colocando os individualnumbers por ordem
    private static boolean deleteManagingBranch(String branchName) {
        for (int i = 0; i < currentIndex; i++) {
            if (branchName.equals(ALL_TYPES[i].getType())) {
                ALL_TYPES[i] = null;
                currentIndex--;
                for (int j = i + 1; j < currentIndex; j++) {
                    if (ALL_TYPES[j].getType() != null) {
                        ALL_TYPES[j].setIndividualTypeNumber(ALL_TYPES[j].IndividualTypeNumber - 1);
                        ALL_TYPES[j - 1] = ALL_TYPES[j];
                    }
                }
                return true;
            }
        }
        return false;
    }

    public String getType() {
        return this.name;
    }

    public int getIndividualTypeNumber() {
        return this.IndividualTypeNumber;
    }

    private void setIndividualTypeNumber(int number) {
        this.IndividualTypeNumber = number;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null && this.name != null || obj != null && this.name == null) {
            return false;
        }
        if (obj instanceof String) {
            String contType = (String) obj;
            int n = getType().length();
            if (n == contType.length()) {
                char[] v1 = this.getType().toCharArray();
                char[] v2 = contType.toCharArray();
                int i = 0;
                while (n-- != 0) {
                    if (v1[i] != v2[i]) {
                        return false;
                    }
                    i++;
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
