package View;

import javax.swing.*;

import Controller.TelaController;
import Controller.Observer.Observer;
import Utils.CarrosUtils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

public class Tela extends JFrame implements Observer {

    private TelaController telaController;
    private CarrosUtils carroUtils = new CarrosUtils();
    private Tabela tabela;
    Container menu;
    JButton btnStart;
    JButton btnStop;

    JLabel lbVeiculos;
    JLabel lbTimer;
    JLabel lbQtd;
    JLabel lbQtdCarros;
    JSpinner qtdCarros;
    JSpinner timer;

    String[] opcao = {"","Semaforo", "Monitor"};
    JComboBox<String> select;

    public Tela() throws IOException {

        telaController = TelaController.getInstance();
        telaController.attach(this);

        this.setSize(1200, 960);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
        tabela = new Tabela();

        menu = new Container();

        btnStart = new JButton("START");
        btnStart.addActionListener((ActionEvent e) -> {
            String value = qtdCarros.getValue() + "";
            String timeOut = timer.getValue() + "";
            int cars = Integer.parseInt(value);
            int timeOutValue = Integer.parseInt(timeOut);
            carroUtils.setQtdCarros(cars);
            carroUtils.setTimer(timeOutValue);
            carroUtils.start();
        });
        btnStart.setEnabled(false);

        btnStop = new JButton("END");
        btnStop.addActionListener((ActionEvent e) -> {
            telaController.stop();
        });
        btnStop.setEnabled(false);

        select = new JComboBox(opcao);
        select.addActionListener((ActionEvent e) -> {
            String resultado = (String) select.getSelectedItem();
            telaController.changeThreadMethodType(resultado);
        });

        lbVeiculos = new JLabel("Numero de veículos: ");
        qtdCarros = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));

        lbTimer = new JLabel("Tempo: ");
        timer = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));

        lbQtdCarros = new JLabel("Veículos: ");
        lbQtd = new JLabel("");
        menu.setLayout(new FlowLayout());

        menu.add(btnStart);
        menu.add(btnStop);
        menu.add(select);
        menu.add(lbVeiculos);
        menu.add(qtdCarros);
        menu.add(lbTimer);
        menu.add(timer);
        menu.add(lbQtdCarros);
        menu.add(lbQtd);

        //Add components to frame layout
        this.add(menu, BorderLayout.NORTH);
        this.add(tabela, BorderLayout.CENTER);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

    }

    @Override
    public void atualizaPosicaoCarros() {}

    @Override
    public void setOnOffStartButton(boolean status){
        this.btnStart.setEnabled(status);
    }

    @Override
    public void setOnOffStopButton(boolean status) {
        this.btnStop.setEnabled(status);
    }

    @Override
    public void qtdCarros(int value) {
        this.lbQtd.setText(value+"");
    }
}
