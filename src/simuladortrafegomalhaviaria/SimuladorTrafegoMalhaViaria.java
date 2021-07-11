package simuladortrafegomalhaviaria;

import View.Tabela;
import java.awt.EventQueue;
import java.io.FileNotFoundException;
import java.io.IOException;
import View.Tela;

public class SimuladorTrafegoMalhaViaria {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
     
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                Tela tela;
                try {
                    tela = new Tela();
                    tela.setVisible(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }
    
}
