package Model;

import java.util.ArrayList;
import java.util.List;

public enum Estrada {

    ZERO(0,"img/background.png"),
    ONE(1,"img/setaC.png"),
    TWO(2,"img/setaD.png"),
    THREE(3,"img/setaB.png"), 
    FOUR(4,"img/setaE.png"),
    FIVE(5,"img/cruzamento.png"),
    SIX(6,"img/cruzamento.png"),
    SEVEN(7,"img/cruzamento.png"),
    EIGHT(8,"img/cruzamento.png"),
    NINE(9,"img/cruzamento.png"),
    TEN(10,"img/cruzamento.png"),
    ELEVEN(11,"img/cruzamento.png"),
    TWELVE(12,"img/cruzamento.png");


    private String filePath;
    private int numero;

    private Estrada(int number, String filePath) {
        this.filePath = filePath;
        this.numero   = number;
    }

    @Override
    public String toString() {
        return this.filePath;
    }
    
   public static String getRoadType(int number) {
      for (Estrada roadType : Estrada.values()) {
          if (roadType.numero == number)
              return roadType.toString();
      }
        return null;
   }

   public static List<Integer> getStopCells(){
       List<Integer> list = new ArrayList<>();
       for (Estrada type:
            Estrada.values()) {
           if( (type.numero == 5) || (type.numero == 6) || (type.numero == 7) || (type.numero == 8) ||
                   (type.numero == 9) || (type.numero == 10) || (type.numero == 11) || (type.numero == 12) ){
               list.add(type.numero);
           }
       }
        return list;

   }
}