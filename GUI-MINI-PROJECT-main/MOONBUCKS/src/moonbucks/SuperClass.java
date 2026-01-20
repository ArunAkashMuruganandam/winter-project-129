package moonbucks;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class SuperClass {
    // HARGA FOOD (Mesti sama ejaan dengan moonbucks.java)
    public double pPollosClassic = 7.00;
    public double pFamilyBucket = 25.00;
    public double pPollosTenders = 9.00;
    public double pTacoRancheros = 8.00;
    public double pEnchiladaRancheros = 8.00;
    public double pHuevosRancheros = 8.00;

    // HARGA DRINKS
    public double pHorchataSoda = 5.00;
    public double pSouthwesternTea = 4.00;
    public double pOrangeJuice = 7.00;
    public double pSpiceCurls = 4.00;
    public double pPimentoSandwich = 5.00;
    public double pLaloTacos = 9.00;

    // TAX RATE (Walaupun kita guna flat 0.60, biarkan ada variable ni untuk OOP)
    public double TaxRate = 0.60;

    // Method Exit
    private JFrame frame;
    public void iExitSystem(){
        frame = new JFrame ("Exit");
        if(JOptionPane.showConfirmDialog(frame,"CONFIRM TO EXIT","Ordering System",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_NO_OPTION){
            System.exit(0);
        }
    }
}