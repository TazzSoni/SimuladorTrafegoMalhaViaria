package Controller;

import Controller.Observer.Observer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import Model.AbstractFactory.AbstractCell;
import Model.Carro;
import Model.Estrada;
import Model.TipoMovimento;
import Utils.MatrizUtils;

public class TelaController {

    private static TelaController instance;
    private final MatrizUtils matrizUtils = MatrizUtils.getInstance();
    private List<Carro> carros = new ArrayList();
    private AbstractCell[][] celulas;
    private List<Observer> observers = new ArrayList();
    private String tipoThread;
    private final String filename = "malhas/malha-exemplo-3.txt";
    private boolean stopped = false;

    private TelaController() {
        try {
            this.matrizUtils.print(filename);
            this.matrizUtils.loadEntriesAndExits();
        } catch (IOException var2) {
            var2.printStackTrace();
        }

        this.initRoadCells();
    }

    public static TelaController getInstance() {
        if (instance == null) {
            instance = new TelaController();
        }

        return instance;
    }

    public void attach(Observer obs) {
        this.observers.add(obs);
    }

    public void detach(Observer obs) {
        this.observers.remove(obs);
    }

    public void start() {
        notificarStartButton(false);
        notificarEndButton(true);
        Carro newCarro = new Carro(this);

        Integer[] pos = getFirstCell();
        newCarro.setFirstPosition(pos[0], pos[1]);

        this.carros.add(newCarro);
        notificarQtdCarros();
        this.updateRoadView(newCarro);
        newCarro.start();
    }

    public void stop() {
        this.stopped = true;
        notificarStartButton(true);
        notificarEndButton(false);
    }

    public void changeThreadMethodType(String opt) {
        this.tipoThread = opt;
        try {
            matrizUtils.changeMethodType(filename, tipoThread);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        notificarStartButton(true);
    }

    public String getTipoThread() {
        return tipoThread;
    }

    public MatrizUtils getMatrizUtils() {
        return this.matrizUtils;
    }

    private boolean setLastCell(Integer[] array) {
        for (Integer[] aValue
                : this.matrizUtils.getExits()) {
            if (Arrays.equals(aValue, array)) {
                return true;
            }
        }
        return false;
    }

    private void initRoadCells() {
        this.celulas = matrizUtils.getMatriz();
        List<Integer> stopCells = Estrada.getStopCells();

        int row = this.matrizUtils.getRows();
        int col = this.matrizUtils.getCols();

        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < col; ++j) {
                if (setLastCell(new Integer[]{i, j})) {
                    this.celulas[i][j].setLastCell(true);
                }

                if (stopCells.contains(celulas[i][j].getTipoMovimento())) {
                    celulas[i][j].setStopCell(true);
                }
            }
        }
    }

    public void setStopped(boolean status) {
        this.stopped = status;
    }

    public boolean isStopped() {
        return stopped;
    }

    public Icon renderCell(int row, int col) {
        return this.celulas[row][col].getIcon();
    }

    public void updateCarroContador(Carro c) {
        this.carros.remove(c);
        notificarQtdCarros();
    }

    private Integer[] getFirstCell() {
        Collections.shuffle(this.matrizUtils.getEntries());
        return this.matrizUtils.getEntries().get(0);
    }

    public void updateRoadView(Carro c) {
        int i = c.getRow();
        int j = c.getColumn();

        int tipoMovimento = this.matrizUtils.getValueAtPosition(i, j);
        if (tipoMovimento >= 5) {
            this.celulas[i][j].setIcon(new ImageIcon(TipoMovimento.converteTipoMovimento(tipoMovimento)));
        } else {
            this.celulas[i][j].setIcon(new ImageIcon(TipoMovimento.getTipoMovimento(tipoMovimento)));
        }

        notificarPosicaoCarros();
    }

    public int getCarros() {
        return this.carros.size();
    }

    public AbstractCell getCellAtPosition(int row, int col) {
        return celulas[row][col];
    }

    public void notificarPosicaoCarros() {
        for (Observer observer : observers) {
            observer.atualizaPosicaoCarros();
        }
    }

    public void notificarStartButton(boolean status) {
        for (Observer observer : observers) {
            observer.setOnOffStartButton(status);
        }
    }

    public void notificarEndButton(boolean status) {
        for (Observer observer : observers) {
            observer.setOnOffStopButton(status);
        }
    }

    public void notificarQtdCarros() {
        for (Observer observer : observers) {
            observer.qtdCarros(this.getCarros());
        }
    }

}
