package Controller.Observer;

public interface Observer {

    public void qtdCarros(int value);

    public void setOnOffStartButton(boolean status);

    public void setOnOffStopButton(boolean status);

    public void atualizaPosicaoCarros();
}