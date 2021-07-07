package Controller;

import java.util.ArrayList;
import java.util.List;

public class EnterOutCellCheck {

    private static List<Integer[]> celulasEntrada = new ArrayList<>();
    private static List<Integer[]> celulasSaida = new ArrayList<>();

   public EnterOutCellCheck(Integer[][] matrix, int rows, int cols) {
       setEnterOutCells(matrix, rows, cols);
    }

    public static List<Integer[]> getCelulasEntrada() {
        return celulasEntrada;
    }

    public static List<Integer[]> getCelulasSaida() {
        return celulasSaida;
    }

    public void setEnterOutCells(Integer[][] cells, int rows, int cols) {
        //encontra linhas de entrada e saída
        for (int i = 0; i < rows - 1; ++i) {

            if (cells[i][0] == 2) {
                celulasEntrada.add(new Integer[]{i, 0});
            } else if (cells[i][0] == 4) {
                celulasSaida.add(new Integer[]{i, 0});
            }

            if (cells[i][cols -1] == 4) {
                celulasEntrada.add(new Integer[]{i, cols - 1});
            } else if (cells[i][cols -1] == 2) {
                celulasSaida.add(new Integer[]{i, cols - 1});
            }
        }

        //encontra colunas de entrada e saída
        for (int i = 0; i < cols; ++i) {

            if (cells[0][i] == 3) {
                celulasEntrada.add(new Integer[]{0, i});
            } else if (cells[0][i] == 1) {
                celulasSaida.add(new Integer[]{0, i});
            }

            int a = cells[rows - 1][i];
            if (cells[rows - 1][i] == 1) {
                celulasEntrada.add(new Integer[]{rows - 1, i});
            } else if (cells[rows - 1][i] == 3) {
                celulasSaida.add(new Integer[]{rows - 1, i});
            }
        }
    }

    @Override
    public String toString() {
        String retorno="Entradas (col/row): ";
        for(int i =0;i<celulasEntrada.size();i++){
            Integer [] a = celulasEntrada.get(i);
            retorno+=a[0]+"-"+a[1]+" ";
        }
        retorno+="\nSaídas (col/row): ";
        for(int i =0;i<celulasSaida.size();i++){
            Integer [] a = celulasSaida.get(i);
            retorno+=a[0]+"-"+a[1]+" ";
        }
        return retorno;
    }
    
}
