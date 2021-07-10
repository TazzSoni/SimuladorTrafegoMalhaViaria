package View;

import Controller.Observer.Observer;
import Controller.TelaController;
import java.awt.Color;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

public class Tabela extends JPanel implements Observer {

    class TableModel extends AbstractTableModel {

        @Override
        public int getRowCount() {
            return cont.getMatrizUtils().getRows();
        }

        @Override
        public int getColumnCount() {
            return cont.getMatrizUtils().getCols();
        }

        @Override
        public Object getValueAt(int row, int col) {
            return cont.renderCell(row, col);
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

    private TelaController cont;

    private TableModel tableModel;
    private JTable table;

    public Tabela() {
        super();

        cont = TelaController.getInstance();
        cont.attach(this);

        tableModel = new TableModel();

        initComponents();

    }

    private void initComponents() {

        table = new JTable();
        table.setBackground(Color.black);
        table.setModel(this.tableModel);
        for (int x = 0; x < table.getColumnModel().getColumnCount(); x++) {
            table.getColumnModel().getColumn(x).setWidth(35);
            table.getColumnModel().getColumn(x).setMaxWidth(45);
        }
        table.setRowHeight(32);
        table.setShowGrid(false);

        table.setDefaultRenderer(Object.class, new CellRenderer());

        add(table);
    }

    @Override
    public void qtdCarros(int value) {
        updateUI();
    }

    @Override
    public void setOnOffStartButton(boolean status) {
    }

    @Override
    public void setOnOffStopButton(boolean status) {
    }

    @Override
    public void atualizaPosicaoCarros() {
    }
}