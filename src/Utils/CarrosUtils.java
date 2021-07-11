package Utils;

import Controller.TelaController;

public class CarrosUtils extends Thread {

    TelaController telaController = TelaController.getInstance();
    private int qtdCarros;
    private int tempo;

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
        for (int i = 0; i < qtdCarros; i++) {
            if (telaController.isStopped()) {
                break;
            }
            telaController.start();
            try {
                Thread.currentThread().sleep(getTempo());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
