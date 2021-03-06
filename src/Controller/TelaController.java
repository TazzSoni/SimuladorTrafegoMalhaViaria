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
import Model.TipoMovimento;
import Utils.MatrizUtils;
import Model.Estrada;

public class TelaController {

    private static TelaController instance;
    private final MatrizUtils matrizUtils = MatrizUtils.getInstance();
    private List<Carro> cars = new ArrayList();
    private AbstractCell[][] cells;
    private List<Observer> observers = new ArrayList();
    private String tipoMetodo;
    private final String filename = "malhas/malha-exemplo-2.txt";
    private boolean stopped = false;

    private TelaController() {
        try {
            this.matrizUtils.print(filename);
            this.matrizUtils.loadEntradasSaidas();
        } catch (IOException var2) {
            var2.printStackTrace();
        }

        this.initiCelulasPista();
    }

    public static TelaController getInstance() {
        if (instance == null) {
            instance = new TelaController();
        }

        return instance;
    }

    public void addObserver(Observer obs) {
        this.observers.add(obs);
    }

    public void removeObserver(Observer obs) {
        this.observers.remove(obs);
    }

    public void trocaTipoMetodo(String opt) {
        this.tipoMetodo = opt;
        try {
            matrizUtils.trocaTipoMetodo(filename, tipoMetodo);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        notifyStartButton(true);
    }

    public String getTipoMetodo() {
        return tipoMetodo;
    }

    public void start() {
        notifyStartButton(false);
        notifyEndButton(true);
        Carro newCar = new Carro(this);

        Integer[] pos = getFirstCell();
        newCar.setFirstPosition(pos[0], pos[1]);

        this.cars.add(newCar);
        notifyCounter();
        this.atualizaViewPista(newCar);
        newCar.start();
    }

    public void stop() {
        this.stopped = true;
        notifyStartButton(true);
        notifyEndButton(false);
    }

    public MatrizUtils getMatrizUtils() {
        return this.matrizUtils;
    }

    private void initiCelulasPista() {
        this.cells = matrizUtils.getMatriz();
        List<Integer> stopCells = Estrada.getStopCells();

        int row = this.matrizUtils.getRows();
        int col = this.matrizUtils.getCols();

        for (int i = 0; i < row; ++i) {
            for (int j = 0; j < col; ++j) {
                if (setLastCell(new Integer[]{i, j})) {
                    this.cells[i][j].setLastCell(true);
                }

                if (stopCells.contains(cells[i][j].getMoveType())) {
                    cells[i][j].setStopCell(true);
                }
            }
        }
    }

    private boolean setLastCell(Integer[] array) {
        for (Integer[] aValue
                : this.matrizUtils.getSaidas()) {
            if (Arrays.equals(aValue, array)) {
                return true;
            }
        }
        return false;
    }

    public void setStopped(boolean status) {
        this.stopped = status;
    }

    public boolean isStopped() {
        return stopped;
    }

    public void updateCarCount(Carro c) {
        this.cars.remove(c);
        notifyCounter();
    }

    public void atualizaViewPista(Carro c) {
        int i = c.getRow();
        int j = c.getColumn();

        int moveType = this.matrizUtils.getValueAtPosition(i, j);
        if (moveType >= 5) {
            this.cells[i][j].setIcon(new ImageIcon(TipoMovimento.convertMoveType(moveType)));
        } else {
            this.cells[i][j].setIcon(new ImageIcon(TipoMovimento.getMoveType(moveType)));
        }

        notifyUpdate();
    }

    public Icon renderCell(int row, int col) {
        return this.cells[row][col].getIcon();
    }

    private Integer[] getFirstCell() {
        Collections.shuffle(this.matrizUtils.getEntradas());
        return this.matrizUtils.getEntradas().get(0);
    }

    public int getCars() {
        return this.cars.size();
    }

    public void notifyStartButton(boolean status) {
        for (Observer observer : observers) {
            observer.changeStartButtonStatus(status);
        }
    }

    public void notifyUpdate() {
        for (Observer observer : observers) {
            observer.updateCarPosition();
        }
    }

    public void notifyEndButton(boolean status) {
        for (Observer observer : observers) {
            observer.changeEndButtonStatus(status);
        }
    }

    public void notifyCounter() {
        for (Observer observer : observers) {
            observer.changeCounter(this.getCars());
        }
    }

    public AbstractCell getCellAtPosition(int row, int col) {
        return cells[row][col];
    }
}
