package Model.AbstractFactory;

import Model.Estrada;
import Model.Carro;

import javax.swing.*;

public abstract class AbstractCell {

    protected boolean stopCell;
    protected boolean lastCell;

    protected int row;
    protected int column;

    protected int tipoMovimento;
    protected Icon icon;
    protected Carro carro;

    public abstract int getTipoMovimento();

    public abstract Carro getCarro();

    public abstract boolean setCarroInterseccao(Carro c);

    public abstract void setCarro(Carro c);

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
