package Utils;

import Controller.TelaController;

public class CarrosUtils extends Thread {

    TelaController telaController = TelaController.getInstance();
    private int qtdCarros;
    private int timer;

    public void setQtdCarros(int qtdCarros) {
        this.qtdCarros = qtdCarros;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public int getTimer() {
        return timer * 1000;
    }

    @Override
    public void run() {
        for (int i = 0; i < qtdCarros; i++) {
            if (telaController.isStopped()) {
                break;
            }
            telaController.start();
            try {
                Thread.currentThread().sleep(getTimer());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
