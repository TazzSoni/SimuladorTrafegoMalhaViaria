package Model;

public enum TipoMovimento {

    CIMA("img/car-cima.png", 1), DIREITA("img/car-dir.png", 2), BAIXO("img/car-baixo.png", 3),
    ESQUERDA("img/car-esq.png", 4);

    private String filePath;
    private int direcao;

    private TipoMovimento(String filePath, int direcao) {
        this.filePath = filePath;
        this.direcao = direcao;
    }

    public String toString() {
        return filePath;
    }

    public static String getTipoMovimento(int direcao) {
        for (TipoMovimento tipoMovimento : TipoMovimento.values()) {
            if (tipoMovimento.direcao == direcao)
                return tipoMovimento.toString();
        }
        return null;
    }

    public static String converteTipoMovimento(int direcao){
        switch(direcao){
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