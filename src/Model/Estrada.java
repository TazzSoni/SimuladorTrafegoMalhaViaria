package Model;

import java.util.ArrayList;
import java.util.List;

public enum Estrada {

    ZERO(0,"img/background.png"),
    ONE(1,"img/setacima.png"),
    TWO(2,"img/setadir.png"),
    THREE(3,"img/setabaixo.png"), 
    FOUR(4,"img/setaesq.png"),
    FIVE(5,"img/cruzamento.png"),
    SIX(6,"img/cruzamento.png"),
    SEVEN(7,"img/cruzamento.png"),
    EIGHT(8,"img/cruzamento.png"),
    NINE(9,"img/cruzamento.png"),
    TEN(10,"img/cruzamento.png"),
    ELEVEN(11,"img/cruzamento.png"),
    TWELVE(12,"img/cruzamento.png");


    private String filePath;
    private int number;

    private Estrada(int number, String filePath) {
        this.filePath = filePath;
        this.number   = number;
    }

    @Override
    public String toString() {
        return this.filePath;
    }
    
   public static String getRoadType(int number) {
      for (Estrada roadType : Estrada.values()) {
          if (roadType.number == number)
              return roadType.toString();
      }
        return null;
   }

   public static List<Integer> getStopCells(){
       List<Integer> list = new ArrayList<>();
       for (Estrada type:
            Estrada.values()) {
           if( (type.number == 5) || (type.number == 6) || (type.number == 7) || (type.number == 8) ||
                   (type.number == 9) || (type.number == 10) || (type.number == 11) || (type.number == 12) ){
               list.add(type.number);
           }
       }
        return list;

   }
}