package Controller.Observer; 

public interface Observer {
    
    public void updateCarPosition();
    public void changeStartButtonStatus(boolean status);
    public void changeEndButtonStatus(boolean status);
    public void changeCounter(int value);
}
