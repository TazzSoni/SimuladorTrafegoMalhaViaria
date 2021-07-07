/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.WindowConstants;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Rodrigo
 */
public class Tabela extends JFrame {

    JTable table;
    Container menu;
    JButton btStart;
    JButton btStop;

    JTextArea txtTime;

    JLabel lbVeiculos;
    JLabel lbMetodo;
    JLabel lbTimer;
    JLabel lbCount;
    JLabel lbNumCarros;
    JSpinner numeroVeiculos;
    JSpinner timer;

    String[] vector = {"", "Semaforo", "Monitor"};
    JComboBox<String> select;

    public Tabela() throws FileNotFoundException {
        iniComponents();
        this.add(menu, BorderLayout.NORTH);
        this.add(table, BorderLayout.CENTER);

        this.setVisible(true);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }

    private void iniComponents() throws FileNotFoundException {

        menu = new Container();
        btStart = new JButton("Start");
        btStop = new JButton("Stop");

        select = new JComboBox(vector);
//        select.addActionListener((ActionEvent e) -> {
//            String resultado = (String) select.getSelectedItem();
//            controller.changeThreadMethodType(resultado);
//        });

        lbVeiculos = new JLabel("Numero de veículos: ");
        lbMetodo = new JLabel("Método: ");
        numeroVeiculos = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));

        lbTimer = new JLabel("Tempo: ");
        timer = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));

        lbNumCarros = new JLabel("Veículos: ");
        lbCount = new JLabel("");

        menu.setLayout(new FlowLayout());

        Scanner fileInput = new Scanner(new File("src/malhas/malha-exemplo-3.txt"));
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

        this.setSize(1200, 800);
        this.setLayout(new BorderLayout());

        table = new JTable(matrix, cabecario) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
                Component comp = super.prepareRenderer(renderer, row, col);
                comp.setForeground(Color.WHITE);
                Object value = getModel().getValueAt(row, col);
                if (value.equals(3) || value.equals(4)) {
                    comp.setBackground(Color.blue);
                } else if (value.equals(1) || value.equals(2)) {
                    comp.setBackground(Color.red);
                } else if (value.equals(0)) {
                    comp.setBackground(Color.gray);
                } else {
                    comp.setBackground(Color.DARK_GRAY);
                }
                return comp;
            }
        };

        table.getTableHeader().setUI(null);
        Font font = new Font("Verdana", Font.PLAIN, 12);
        table.setFont(font);
        table.setRowHeight(30);
        menu.add(btStart);
        menu.add(btStop);
        menu.add(lbMetodo);
        menu.add(select);
        menu.add(lbVeiculos);
        menu.add(numeroVeiculos);
        menu.add(lbTimer);
        menu.add(timer);
//        menu.add(lbNumCarros);
        menu.add(lbCount);

    }
}
