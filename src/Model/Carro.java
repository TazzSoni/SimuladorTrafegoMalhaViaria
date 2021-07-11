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
    private boolean outOfRoad = false;
    private final TelaController telaController;

    private AbstractCell cell;
    private AbstractCell nextCell = new Semaforo(0, 0, 0);

    public Carro(TelaController telaController) {
        this.telaController = telaController;
        setSpeed();
        System.out.println("Velocidade: " + speed);
    }

    @Override
    public void run() {
        super.run();

        while (!outOfRoad) {
            try {
                Thread.currentThread().sleep(speed);
            } catch (InterruptedException e) {
                e.getStackTrace();
            }

            if (checkLastCell()) {
                outOfRoad = true;
                telaController.updateCarCount(this);
            } else if (nextCell.isStopCell()) {
                verifyIntersection();
            } else {
                moveCar();
            }
        }

        cell.reset();
        telaController.notifyUpdate();
    }

    //O verifica cruzamento é uma região crítica. Não pode acontecer de o carro ver o espaço vazio à sua frente,
    // e aí perder o processador por um instante, e quando o carro for tentar se mover o espaço na verdade não está vazio.
    private void verifyIntersection() {
        List<AbstractCell> intersectionExits = new ArrayList<>();
        List<List<AbstractCell>> pathToAllExits = new ArrayList<>();
        List<AbstractCell> currentPathing = new ArrayList<>();

        AbstractCell cell = nextCell;

        // For responsável por passar pelas 4 células do cruzamento
        for (int i = 0; i < 4; i++) {
            int moveType = cell.getMoveType();
            currentPathing.add(cell);

            switch (moveType) {
                case 9:
                    intersectionExits.add(telaController.getCellAtPosition(cell.getRow(), cell.getColumn() + 1));
                    pathToAllExits.add(new ArrayList<>(currentPathing));
                    break;
                case 10:
                    intersectionExits.add(telaController.getCellAtPosition(cell.getRow() - 1, cell.getColumn()));
                    pathToAllExits.add(new ArrayList<>(currentPathing));
                    break;
                case 11:
                    intersectionExits.add(telaController.getCellAtPosition(cell.getRow() + 1, cell.getColumn()));
                    pathToAllExits.add(new ArrayList<>(currentPathing));
                    break;
                case 12:
                    intersectionExits.add(telaController.getCellAtPosition(cell.getRow(), cell.getColumn() - 1));
                    pathToAllExits.add(new ArrayList<>(currentPathing));
                    break;
            }
            cell = getNextCell(cell);
        }

        System.out.println(telaController.getThreadMethodType());
        checkPathAndMove(pathToAllExits, intersectionExits);
    }

    private void checkPathAndMove(List<List<AbstractCell>> pathToAllExits, List<AbstractCell> intersectionExits) {
        List<AbstractCell> acquiredCells = new ArrayList<>();
        boolean allCellsAcquired = false;
        List<AbstractCell> pathToExit;

        do {
            int chosenExit = new Random().nextInt(intersectionExits.size());
            pathToExit = pathToAllExits.get(chosenExit);
            pathToExit.add(intersectionExits.get(chosenExit));

            for (AbstractCell c : pathToExit) {
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

            if (pathToExit.size() == acquiredCells.size())
                allCellsAcquired = true;
            else
                pathToExit.remove(intersectionExits.get(chosenExit));
        } while (!allCellsAcquired);

        for (AbstractCell c : pathToExit) {
            moveCarToIntersectionExit(c);

            if (c != pathToExit.get(pathToExit.size() - 1)) {
                try {
                    Thread.currentThread().sleep(speed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean checkLastCell() {
        return cell.isLastCell();
    }

    private void moveCar() {
        AbstractCell c = getNextCell(cell);
        c.setCar(this);
        this.setColumn(c.getColumn());
        this.setRow(c.getRow());
        if (!c.isLastCell())
            this.nextCell = getNextCell(c);

        cell.reset();
        cell = c;
        refreshView();
    }

    private void moveCarToIntersectionExit(AbstractCell c) {
        this.setColumn(c.getColumn());
        this.setRow(c.getRow());
        this.nextCell = getNextCell(c);
        cell.reset();
        cell = c;

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

    public AbstractCell getCell() {
        return cell;
    }

    public void setCell(AbstractCell cell) {
        this.cell = cell;
    }

    public void setOutOfRoad(boolean outOfRoad) {
        this.outOfRoad = outOfRoad;
    }

    public boolean setFirstPosition(Integer row, Integer col) {
        AbstractCell cell = telaController.getCellAtPosition(row, col);
        cell.setCar(this);
        this.setCell(cell);

        setRow(row);
        setColumn(col);
        return true;
    }

    // Para este método precisaremos colocar as posições nas célular. Ele retorna a próxima célula em relação a uma célula qualquer,
    // e vai ser usado para mapearmos o cruzamento sem precisar mover o carro..
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
        telaController.updateRoadView(this);
    }
}
