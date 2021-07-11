package Model;

public enum TipoMovimento {

    CIMA("img/cima.png", 1), DIREITA("img/direita.png", 2), BAIXO("img/baixo.png", 3),
    ESQUERDA("img/esquerda.png", 4);

    private String filePath;
    private int side;

    private TipoMovimento(String filePath, int side) {
        this.filePath = filePath;
        this.side = side;
    }

    public String toString() {
        return filePath;
    }

    public static String getMoveType(int side) {
        for (TipoMovimento moveType : TipoMovimento.values()) {
            if (moveType.side == side)
                return moveType.toString();
        }
        return null;
    }

    public static String convertMoveType(int side){
        switch(side){
            case 5:
            case 9 :
                return CIMA.toString();
            case 6:
            case 11:
                return DIREITA.toString();
            case 7:
            case 12:
                return BAIXO.toString();
            case 8:
            case 10:
                return ESQUERDA.toString();
        }
        return null;
    }
}
