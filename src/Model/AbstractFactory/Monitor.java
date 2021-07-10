package Model.AbstractFactory;

import Model.AbstractFactory.AbstractCell;
import Model.Carro;
import Model.Estrada;

import javax.swing.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor extends AbstractCell {

    private final Lock lockCell = new ReentrantLock();

    public Monitor(int tipoMovimento, int row, int column) {
        this.stopCell = false;
        this.lastCell = false;
        this.row = row;
        this.column = column;
        this.tipoMovimento = tipoMovimento;
        this.icon = new ImageIcon(Estrada.getRoadType(tipoMovimento));
    }

    public int getTipoMovimento() {
        return tipoMovimento;
    }

    public Carro getCarro() {
        return carro;
    }

    public boolean setCarroInterseccao(Carro c) {
        try {
            if (lockCell.tryLock(c.getSpeed(), TimeUnit.MILLISECONDS)) {
                this.carro = c;
                return true;
            }
            return false;
        } catch (InterruptedException e) {
            return false;
        }
    }

    public void setCarro(Carro c) {
        lockCell.lock();
        this.carro = c;
    }

    public void reset() {
        this.setIcon(new ImageIcon(Estrada.getRoadType(tipoMovimento)));
        this.carro = null;
        lockCell.unlock();
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
