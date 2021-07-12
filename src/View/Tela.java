package View;

import javax.swing.*;

import Controller.TelaController;
import Controller.Observer.Observer;
import Utils.CarrosUtils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class Tela extends JFrame implements Observer {

    private TelaController controller;
    private CarrosUtils carsThreadController = new CarrosUtils();
    private Tabela tabela;
    Container menu;
    JButton btnStart;
    JButton btnEnd;
    JTextArea txtTime;

    JLabel lbVeiculos;
    JLabel lbTimer;
    JLabel lbCount;
    JLabel lbNumCars;
    JSpinner numeroVeiculos;
    JSpinner timer;

    String[] vector = {"","Semaforo", "Monitor"};
    JComboBox<String> select;

    public Tela() throws IOException {

        controller = TelaController.getInstance();
        controller.attach(this);

        this.setSize(1200, 960);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
        tabela = new Tabela();

        //Menu
        menu = new Container();

        btnStart = new JButton("START");
        btnStart.addActionListener((ActionEvent e) -> {
            String value = numeroVeiculos.getValue() + "";
            String timeOut = timer.getValue() + "";
            int cars = Integer.parseInt(value);
            int timeOutValue = Integer.parseInt(timeOut);
            carsThreadController.setQtdCarros(cars);
            carsThreadController.setTempo(timeOutValue);
            carsThreadController.start();
        });
        btnStart.setEnabled(false);

        btnEnd = new JButton("END");
        btnEnd.addActionListener((ActionEvent e) -> {
            controller.stop();
        });
        btnEnd.setEnabled(false);

        select = new JComboBox(vector);
        select.addActionListener((ActionEvent e) -> {
            String resultado = (String) select.getSelectedItem();
            controller.changeThreadMethodType(resultado);
        });

        lbVeiculos = new JLabel("Numero de veículos: ");
        numeroVeiculos = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));

        lbTimer = new JLabel("Tempo: ");
        timer = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));

        lbNumCars = new JLabel("Veículos: ");
        lbCount = new JLabel("");
        menu.setLayout(new FlowLayout());

        //Criando menu
        menu.add(btnStart);
        menu.add(btnEnd);
        menu.add(select);
        menu.add(lbVeiculos);
        menu.add(numeroVeiculos);
        menu.add(lbTimer);
        menu.add(timer);
        menu.add(lbNumCars);
        menu.add(lbCount);

        //Criando tela
        this.add(menu, BorderLayout.NORTH);
        this.add(tabela, BorderLayout.CENTER);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }

    @Override
    public void updateCarPosition() {}

    @Override
    public void changeStartButtonStatus(boolean status){
        this.btnStart.setEnabled(status);
    }

    @Override
    public void changeEndButtonStatus(boolean status) {
        this.btnEnd.setEnabled(status);
    }

    @Override
    public void changeCounter(int value) {
        this.lbCount.setText(value+"");
    }
}
