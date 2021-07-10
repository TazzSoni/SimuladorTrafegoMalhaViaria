package Model.AbstractFactory;

import Model.Estrada;
import Model.Carro;

import javax.swing.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Semaforo extends AbstractCell {

    Semaphore mutex = new Semaphore(1);

    public Semaforo(int moveType, int row, int column) {
        this.stopCell = false;
        this.lastCell = false;
        this.row = row;
        this.column = column;
        this.tipoMovimento = moveType;
        this.icon = new ImageIcon(Estrada.getRoadType(moveType));
    }

    public int getTipoMovimento() {
        return tipoMovimento;
    }

    public Carro getCarro() {
        return carro;
    }

    public boolean setCarroInterseccao(Carro c) {
        try {
            if (mutex.tryAcquire(c.getSpeed(), TimeUnit.MILLISECONDS)) {
                this.carro = c;
                return true;
            }
            return false;
        } catch (InterruptedException e) {
            return false;
        }
    }

    public void setCarro(Carro c) {
        try {
            mutex.acquire();
            this.carro = c;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void reset() {
        this.setIcon(new ImageIcon(Estrada.getRoadType(tipoMovimento)));
        this.carro = null;
        mutex.release();
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    public boolean isStopCell() {
        return stopCell;
    }

    public void setStopCell(boolean stopCell) {
        this.stopCell = stopCell;
    }

    public boolean isLastCell() {
        return lastCell;
    }

    public void setLastCell(boolean lastCell) {
        this.lastCell = lastCell;
    }
}
