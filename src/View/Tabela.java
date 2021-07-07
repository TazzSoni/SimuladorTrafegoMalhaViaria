/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;

/**
 *
 * @author Rodrigo
 */
public class Tabela extends JFrame{
    
    JTable table;
    Container menu;
    JButton btStart;
    JButton btEnd;

    public Tabela() throws FileNotFoundException {
        iniComponents();
    }

    private void iniComponents() throws FileNotFoundException {
        
        menu = new Container();
        btStart = new JButton("Start");
        btEnd = new JButton("End");
        menu.add(btStart);
        menu.add(btEnd);

        Scanner fileInput = new Scanner(new File("src/malhas/malha-exemplo-1.txt"));
        int n1 = fileInput.nextInt();
        int n2 = fileInput.nextInt();
        Integer[][] matrix = new Integer[n1][n2];

        for (int i = 0; i < n1; i++) {
            for (int j = 0; j < n2; j++) {
                matrix[i][j] = fileInput.nextInt();
            }
        }
        Integer[] cabecario = new Integer[n2];
        for (int i = 0; i < cabecario.length; i++) {
            cabecario[i] = i;
        }

        this.setSize(800, 600);
        this.setLayout(new BorderLayout());
        
        table = new JTable(matrix, cabecario);
        table.getTableHeader().setUI(null);
        Font font = new Font("Verdana", Font.PLAIN, 12);
        table.setFont(font);
        table.setRowHeight(30);
        this.add(menu, BorderLayout.NORTH);
        this.add(table, BorderLayout.CENTER);

        this.setVisible(true);
        
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }
}
