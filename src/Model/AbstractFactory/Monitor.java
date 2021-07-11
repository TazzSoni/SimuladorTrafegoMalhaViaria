package Model.AbstractFactory;

import Model.Estrada;
import Model.Carro;

import javax.swing.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Monitor extends AbstractCell {

    private final Lock lockCell = new ReentrantLock();

    public Monitor(int moveType, int row, int column) {
        this.stopCell = false;
        this.lastCell = false;
        this.row = row;
        this.column = column;
        this.moveType = moveType;
        this.icon = new ImageIcon(Estrada.getRoadType(moveType));
    }

    public int getMoveType() {
        return moveType;
    }

    public Carro getCar() {
        return carro;
    }

    public boolean setCarToIntersection(Carro c) {
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

    public void setCar(Carro c) {
        lockCell.lock();
        this.carro = c;
    }

    public void reset() {
        this.setIcon(new ImageIcon(Estrada.getRoadType(moveType)));
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
