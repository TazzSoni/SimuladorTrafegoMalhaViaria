package Model.AbstractFactory;

import Model.Estrada;
import Model.Carro;

import javax.swing.*;

public abstract class AbstractCell {

    protected boolean stopCell;
    protected boolean lastCell;

    protected int row;
    protected int column;

    protected int moveType;
    protected Icon icon;
    protected Carro carro;

    public abstract int getMoveType();

    public abstract Carro getCar();

    public abstract boolean setCarToIntersection(Carro c);

    public abstract void setCar(Carro c);

    public abstract void reset();

    public abstract int getRow();

    public abstract void setRow(int row);

    public abstract int getColumn();

    public abstract void setColumn(int column);

    public abstract Icon getIcon();

    public abstract void setIcon(ImageIcon icon);

    public abstract boolean isStopCell();

    public abstract void setStopCell(boolean stopCell);

    public abstract boolean isLastCell();

    public abstract void setLastCell(boolean lastCell);

}
