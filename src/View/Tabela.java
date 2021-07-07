package View;

import Controller.EnterOutCellCheck;
import Controller.Observer.Observer;
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

public class Tabela extends JFrame implements Observer{

    int rows;
    int cols;
    
    Scanner fileInput;
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
        fileInput = new Scanner(new File("src/malhas/malha-exemplo-1.txt"));

        iniComponents();
        this.add(menu, BorderLayout.NORTH);
        this.add(table, BorderLayout.CENTER);

        this.setVisible(true);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setExtendedState(MAXIMIZED_BOTH);
    }

    private void iniComponents() throws FileNotFoundException {

        menu = new Container();
        btStart = new JButton("Start");
        btStart.addActionListener((ActionEvent e) -> {
//            String value = numeroVeiculos.getValue() + "";
//            String timeOut = timer.getValue() + "";
//            int cars = Integer.parseInt(value);
//            int timeOutValue = Integer.parseInt(timeOut);
//            carsThreadController.setQtdCarros(cars);
//            carsThreadController.setTimer(timeOutValue);
//            carsThreadController.start();
        });
        btStart.setEnabled(false);
        btStop = new JButton("Stop");
        btStop.addActionListener((ActionEvent e) -> {
//            controller.stop();
        });
        btStop.setEnabled(false);

        select = new JComboBox(vector);
        select.addActionListener((ActionEvent e) -> {
            String resultado = (String) select.getSelectedItem();
//            controller.changeThreadMethodType(resultado);
        });

        lbVeiculos = new JLabel("Numero de veículos: ");
        lbMetodo = new JLabel("Método: ");
        numeroVeiculos = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));

        lbTimer = new JLabel("Tempo: ");
        timer = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));

        lbNumCarros = new JLabel("Veículos: ");
        lbCount = new JLabel("");

        menu.setLayout(new FlowLayout());

         rows = fileInput.nextInt();
         cols = fileInput.nextInt();
        Integer[][] matrix = new Integer[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = fileInput.nextInt();
            }
        }
        Integer[] cabecario = new Integer[cols];
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
        
        //chamada apenas para verificar e imprimir as coordenadas das entradas e saídas
        EnterOutCellCheck ver = new EnterOutCellCheck(matrix, rows, cols);
        System.out.println(ver.toString());
        

    }

    //início de implementação de observer, que vai cuidar do status dos botões(ativos ou não)
    @Override
    public void setOnOffStartButton(boolean pos) {
        this.btStart.setEnabled(pos);
    }

    @Override
    public void setOnOffStopButton(boolean pos) {
        this.btStop.setEnabled(pos);
    }

   
}
