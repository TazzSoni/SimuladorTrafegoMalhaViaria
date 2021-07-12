package Model;

import Controller.TelaController;
import Model.AbstractFactory.AbstractCell;
import Model.AbstractFactory.Semaforo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Carro extends Thread {

    private int row;
    private int column;
    private long speed;
    private boolean saiuPista = false;
    private final TelaController telaController;

    private AbstractCell celula;
    private AbstractCell nextCell = new Semaforo(0, 0, 0);

    public Carro(TelaController telaController) {
        this.telaController = telaController;
        setSpeed();
    }

    @Override
    public void run() {
        super.run();

        while (!saiuPista) {
            try {
                Thread.currentThread().sleep(speed);
            } catch (InterruptedException e) {
                e.getStackTrace();
            }
            if (checkLastCell()) {
                saiuPista = true;
                telaController.updateCarCount(this);
            } else if (nextCell.isStopCell()) {
                verificaCruzamentos();
            } else {
                moveCarro();
            }
        }

        celula.reset();
        telaController.notifyUpdate();
    }

    //Verifica se cruzamento é uma região crítica.
    private void verificaCruzamentos() {
        List<AbstractCell> saidaCruzamentos = new ArrayList<>();
        List<List<AbstractCell>> caminhoTodasSaidas = new ArrayList<>();
        List<AbstractCell> caminhoAtual = new ArrayList<>();

        AbstractCell cell = nextCell;

        // Verifica o cruzamento
        for (int i = 0; i < 4; i++) {
            int moveType = cell.getMoveType();
            caminhoAtual.add(cell);

            switch (moveType) {
                case 9:
                    saidaCruzamentos.add(telaController.getCellAtPosition(cell.getRow(), cell.getColumn() + 1));
                    caminhoTodasSaidas.add(new ArrayList<>(caminhoAtual));
                    break;
                case 10:
                    saidaCruzamentos.add(telaController.getCellAtPosition(cell.getRow() - 1, cell.getColumn()));
                    caminhoTodasSaidas.add(new ArrayList<>(caminhoAtual));
                    break;
                case 11:
                    saidaCruzamentos.add(telaController.getCellAtPosition(cell.getRow() + 1, cell.getColumn()));
                    caminhoTodasSaidas.add(new ArrayList<>(caminhoAtual));
                    break;
                case 12:
                    saidaCruzamentos.add(telaController.getCellAtPosition(cell.getRow(), cell.getColumn() - 1));
                    caminhoTodasSaidas.add(new ArrayList<>(caminhoAtual));
                    break;
            }
            cell = getNextCell(cell);
        }
        verificaCaminhoEMove(caminhoTodasSaidas, saidaCruzamentos);
    }

    private void verificaCaminhoEMove(List<List<AbstractCell>> caminhoTodasSaidas, List<AbstractCell> saidaCruzamentos) {
        List<AbstractCell> acquiredCells = new ArrayList<>();
        boolean allCellsAcquired = false;
        List<AbstractCell> caminhoSaida;

        do {
            int chosenExit = new Random().nextInt(saidaCruzamentos.size());
            caminhoSaida = caminhoTodasSaidas.get(chosenExit);
            caminhoSaida.add(saidaCruzamentos.get(chosenExit));

            for (AbstractCell c : caminhoSaida) {
                if (c.setCarToIntersection(this)) {
                    acquiredCells.add(c);
                } else {
                    for (AbstractCell acquiredCell : acquiredCells) {
                        acquiredCell.reset();
                    }
                    acquiredCells = new ArrayList<>();
                    try {
                        Thread.currentThread().sleep(speed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }

            if (caminhoSaida.size() == acquiredCells.size())
                allCellsAcquired = true;
            else
                caminhoSaida.remove(saidaCruzamentos.get(chosenExit));
        } while (!allCellsAcquired);

        for (AbstractCell c : caminhoSaida) {
            moveCarroSaidaIntersec(c);

            if (c != caminhoSaida.get(caminhoSaida.size() - 1)) {
                try {
                    Thread.currentThread().sleep(speed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean checkLastCell() {
        return celula.isLastCell();
    }

    private void moveCarro() {
        AbstractCell c = getNextCell(celula);
        c.setCar(this);
        this.setColumn(c.getColumn());
        this.setRow(c.getRow());
        if (!c.isLastCell())
            this.nextCell = getNextCell(c);

        celula.reset();
        celula = c;
        refreshView();
    }

    private void moveCarroSaidaIntersec(AbstractCell c) {
        this.setColumn(c.getColumn());
        this.setRow(c.getRow());
        this.nextCell = getNextCell(c);
        celula.reset();
        celula = c;

        refreshView();
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

    public void setSpeed() {
        this.speed = Double.valueOf((Math.random() * (1500 - 500)) + 500).longValue();
    }

    public long getSpeed() { //timesleep para a thread
        return speed;
    }

    public AbstractCell getCelula() {
        return celula;
    }

    public void setCelula(AbstractCell celula) {
        this.celula = celula;
    }

    public void setSaiuPista(boolean saiuPista) {
        this.saiuPista = saiuPista;
    }

    public boolean setFirstPosition(Integer row, Integer col) {
        AbstractCell cell = telaController.getCellAtPosition(row, col);
        cell.setCar(this);
        this.setCelula(cell);

        setRow(row);
        setColumn(col);
        return true;
    }

    // M�todo para mapear os cruzamentos sem mover o carro retornando a c�lula adjacente
    private AbstractCell getNextCell(AbstractCell cell) {
        int moveType;

        if (cell.getMoveType() > 4 && cell.getMoveType() <= 8) {
            moveType = cell.getMoveType() - 4;
        } else if (cell.getMoveType() > 8) {
            switch (cell.getMoveType()) {
                case 9:
                    moveType = 1;
                    break;
                case 10:
                    moveType = 4;
                    break;
                case 11:
                    moveType = 2;
                    break;
                case 12:
                    moveType = 3;
                    break;
                default:
                    moveType = 0;
            }
        } else {
            moveType = cell.getMoveType();
        }

        switch (moveType) {
            case 1:
                this.nextCell = telaController.getCellAtPosition(cell.getRow() - 1, cell.getColumn());
                break;
            case 2:
                this.nextCell = telaController.getCellAtPosition(cell.getRow(), cell.getColumn() + 1);
                break;
            case 3:
                this.nextCell = telaController.getCellAtPosition(cell.getRow() + 1, cell.getColumn());
                break;
            case 4:
                this.nextCell = telaController.getCellAtPosition(cell.getRow(), cell.getColumn() - 1);
                break;
            default:
                break;
        }

        return nextCell;
    }

    public void refreshView() {
        telaController.atualizaViewPista(this);
    }
}
