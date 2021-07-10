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

    private AbstractCell celula;
    private AbstractCell proximaCelula = new Semaforo(0, 0, 0);

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
                telaController.updateCarroContador(this);
            } else if (proximaCelula.isStopCell()) {
                verifyIntersection();
            } else {
                moveCar();
            }
        }

        celula.reset();
        telaController.notificarPosicaoCarros();
    }

    //O verifica cruzamento é uma região crítica. Não pode acontecer de o carro ver o espaço vazio à sua frente,
    // e aí perder o processador por um instante, e quando o carro for tentar se mover o espaço na verdade não está vazio.
    private void verifyIntersection() {
        List<AbstractCell> intersectionExits = new ArrayList<>();
        List<List<AbstractCell>> pathToAllExits = new ArrayList<>();
        List<AbstractCell> currentPathing = new ArrayList<>();

        AbstractCell cell = proximaCelula;

        // For responsável por passar pelas 4 células do cruzamento
        for (int i = 0; i < 4; i++) {
            int tipoMovimento = cell.getTipoMovimento();
            currentPathing.add(cell);

            switch (tipoMovimento) {
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

        System.out.println(telaController.getTipoThread());
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
                if (c.setCarroInterseccao(this)) {
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
        return celula.isLastCell();
    }

    private void moveCar() {
        AbstractCell c = getNextCell(celula);
        c.setCarro(this);
        this.setColumn(c.getColumn());
        this.setRow(c.getRow());
        if (!c.isLastCell())
            this.proximaCelula = getNextCell(c);

        celula.reset();
        celula = c;
        refreshView();
    }

    private void moveCarToIntersectionExit(AbstractCell c) {
        this.setColumn(c.getColumn());
        this.setRow(c.getRow());
        this.proximaCelula = getNextCell(c);
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

    public void setOutOfRoad(boolean outOfRoad) {
        this.outOfRoad = outOfRoad;
    }

    public boolean setFirstPosition(Integer row, Integer col) {
        AbstractCell cell = telaController.getCellAtPosition(row, col);
        cell.setCarro(this);
        this.setCelula(cell);

        setRow(row);
        setColumn(col);
        return true;
    }

    // Para este método precisaremos colocar as posições nas célular. Ele retorna a próxima célula em relação a uma célula qualquer,
    // e vai ser usado para mapearmos o cruzamento sem precisar mover o carro..
    private AbstractCell getNextCell(AbstractCell celula) {
        int tipoMovimento;

        if (celula.getTipoMovimento()> 4 && this.celula.getTipoMovimento() <= 8) {
            tipoMovimento = celula.getTipoMovimento() - 4;
        } else if (celula.getTipoMovimento() > 8) {
            switch (celula.getTipoMovimento()) {
                case 9:
                    tipoMovimento = 1;
                    break;
                case 10:
                    tipoMovimento = 4;
                    break;
                case 11:
                    tipoMovimento = 2;
                    break;
                case 12:
                    tipoMovimento = 3;
                    break;
                default:
                    tipoMovimento = 0;
            }
        } else {
            tipoMovimento = this.celula.getTipoMovimento();
        }

        switch (tipoMovimento) {
            case 1:
                this.proximaCelula = telaController.getCellAtPosition(this.celula.getRow() - 1, this.celula.getColumn());
                break;
            case 2:
                this.proximaCelula = telaController.getCellAtPosition(this.celula.getRow(), this.celula.getColumn() + 1);
                break;
            case 3:
                this.proximaCelula = telaController.getCellAtPosition(this.celula.getRow() + 1, this.celula.getColumn());
                break;
            case 4:
                this.proximaCelula = telaController.getCellAtPosition(this.celula.getRow(), this.celula.getColumn() - 1);
                break;
            default:
                break;
        }

        return proximaCelula;
    }

    public void refreshView() {
    	telaController.updateRoadView(this);
    }
}
