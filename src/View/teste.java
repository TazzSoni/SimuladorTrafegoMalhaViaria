/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.awt.Font;
import java.io.File;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.WindowConstants;

/**
 *
 * @author Rodrigo
 */
public class teste {

    public static void main(String[] argv) throws Exception {
//      DefaultTableModel tableModel = new DefaultTableModel(new Object[][] {
//         { "Mobile Phones", "100" }, { "RAM", "200" }, { "Caps", "50" },
//         { "Tablet", "80" }, { "LED", "400" }, { "Trousers", "350" },
//         { "T-Shirt", "500" }, { "Hoodie", "650" }, { "Jeans", "900" } },
//         new Object[] { "Items", "Quantity" }
//      );
//      JTable table = new JTable(tableModel);
//      Font font = new Font("Verdana", Font.PLAIN, 12);
//      table.setFont(font);
//      table.setRowHeight(30);
//      JFrame frame = new JFrame();
//      frame.setSize(600, 400);
//      frame.add(new JScrollPane(table));
//      frame.setVisible(true);
//
//      JTable table = new JTable(16,10);
//      JFrame frame = new JFrame();
//      frame.setSize(600, 400);
//      frame.add(new JScrollPane(table));
//      frame.setVisible(true);

        Scanner fileInput = new Scanner(new File("src/malhas/malha-exemplo-1.txt"));
        int n1 = fileInput.nextInt();
        int n2 = fileInput.nextInt();
        System.out.print("matrix is " + n1 + "x" + n2 + "\n");
        Integer[][] matrix = new Integer[n1][n2];

        for (int i = 0; i < n1; i++) {
            for (int j = 0; j < n2; j++) {
                matrix[i][j] = fileInput.nextInt();
//                if(j < (n2-1)){
//                    System.out.print("-");
//                }
            }
        }
        Integer[] cabecario = new Integer[n2];
        for (int i = 0; i< cabecario.length; i++){
            cabecario[i] = i;
        }
        
        JTable table = new JTable(matrix, cabecario);
        table.getTableHeader().setUI(null);
        Font font = new Font("Verdana", Font.PLAIN, 12);
        table.setFont(font);
        table.setRowHeight(30);
        JFrame frame = new JFrame();
        frame.setSize(600, 400);
        frame.add(new JScrollPane(table));
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

    }
}
