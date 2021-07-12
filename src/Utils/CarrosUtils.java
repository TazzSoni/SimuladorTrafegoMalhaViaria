package Utils;

import Controller.TelaController;

public class CarrosUtils extends Thread {

    TelaController telaController = TelaController.getInstance();
    private int qtdCarros;
    private int tempo;
    private boolean stop = false;

    public void setQtdCarros(int qtdCarros) {
        this.qtdCarros = qtdCarros;
    }

    public void setTempo(int timer) {
        this.tempo = timer;
    }

    public int getTempo() {
        return tempo * 1000;
    }

    @Override
    public void run() {
        while (!stop) {
            System.out.println();
        int ver  = telaController.getCars();
            if (telaController.isStopped()) {
                break;
            }
            if (qtdCarros > ver) {
                telaController.start();
                try {
                    Thread.currentThread().sleep(getTempo());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
