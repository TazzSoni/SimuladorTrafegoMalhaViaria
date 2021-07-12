package View;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import Controller.TelaController;
import Controller.Observer.Observer;

import java.awt.*;

public class Tabela extends JPanel implements Observer {


    class CellModel extends AbstractTableModel{

        @Override
        public int getRowCount() {
            return telaController.getMatrizUtils().getRows();
        }

        @Override
        public int getColumnCount() {
            return telaController.getMatrizUtils().getCols();
        }

        @Override
        public Object getValueAt(int row, int col) {
            return telaController.renderCell(row, col);
        }

    }

    class CellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table,
                                                       Object value,
                                                       boolean isSelected,
                                                       boolean hasFocus,
                                                       int row,
                                                       int col) {
            setIcon((ImageIcon) value);

            return this;
        }
    }

    private TelaController telaController;

    private CellModel cellModel;
    private JTable cellTable;


    public Tabela() {
        super();

        telaController = TelaController.getInstance();
        telaController.addObserver(this);

        cellModel = new CellModel();

        initComponents();

    }

    private void initComponents() {

        cellTable = new JTable();
        cellTable.setBackground(Color.white);
        cellTable.setModel(this.cellModel);
        for (int x = 0 ; x < cellTable.getColumnModel().getColumnCount(); x++) {
            cellTable.getColumnModel().getColumn(x).setWidth(35);
            cellTable.getColumnModel().getColumn(x).setMaxWidth(45);
        }
        cellTable.setRowHeight(32);
        cellTable.setShowGrid(false);

        cellTable.setDefaultRenderer(Object.class, new CellRenderer());

        add(cellTable);
    }

    @Override
    public void updateCarPosition() {
        updateUI();
    }

    @Override
    public void changeStartButtonStatus(boolean status) {}

    @Override
    public void changeEndButtonStatus(boolean status) {}

    @Override
    public void changeCounter(int value) {}
}
