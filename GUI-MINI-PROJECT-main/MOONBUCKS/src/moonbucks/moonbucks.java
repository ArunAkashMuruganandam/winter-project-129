package moonbucks;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.*;
import javax.swing.border.TitledBorder;

// Pastikan extends SuperClass untuk guna harga & tax rate
public class moonbucks extends javax.swing.JFrame {

    // --- Palette ---
    Color primaryColor = new Color(255, 87, 34);   // Deep Orange
    Color secondaryColor = new Color(54, 69, 79);  // Charcoal
    Color whiteColor = new Color(255, 255, 255);
    Color exitRed = new Color(220, 53, 69);

    // Fonts
    Font fontHeader = new Font("Impact", Font.PLAIN, 24);
    Font fontMenu = new Font("Arial", Font.BOLD, 16);
    Font fontBtn = new Font("Arial", Font.BOLD, 11);
    Font fontDisplay = new Font("Monospaced", Font.BOLD, 14);

    JTextField[] dFields = new JTextField[6];
    JTextField[] fFields = new JTextField[6];

    JTextField jtxtCOD = new JTextField("0.00"), jtxtCOF = new JTextField("0.00"),
            jtxtTax = new JTextField("0.00"), jtxtTotal = new JTextField("0.00");
    JTextArea jtxtReceipt = new JTextArea();

    public moonbucks() {
        setupLayout();
        this.setUndecorated(true);
        this.setSize(1200, 700); // Atau 1100, 650 jika skrin kecil
        this.setLocationRelativeTo(null);
    }

    private void setupLayout() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(secondaryColor);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // --- 1. TOP BAR ---
        JPanel topBar = new JPanel(new BorderLayout(20, 0));
        topBar.setOpaque(false);
        topBar.setPreferredSize(new Dimension(0, 150));

        JLabel lblLogo = new JLabel();
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("Screenshot_2026-01-13_140650-removebg-preview.png"));
            Image img = icon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            lblLogo.setText("[LOGO]"); lblLogo.setFont(new Font("Arial", Font.BOLD, 20));
            lblLogo.setForeground(primaryColor);
        }

        JPanel nameWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 35));
        nameWrapper.setOpaque(false);
        JLabel lblName = new JLabel("LOS POLLOS HERMANOS");
        lblName.setFont(new Font("Impact", Font.PLAIN, 60));
        lblName.setForeground(primaryColor);
        nameWrapper.add(lblName);

        JPanel exitWrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        exitWrapper.setOpaque(false);
        JButton btnExit = new JButton("X");
        btnExit.setPreferredSize(new Dimension(45, 40));
        btnExit.setBackground(exitRed);
        btnExit.setForeground(whiteColor);
        btnExit.setFocusPainted(false);
        btnExit.setBorder(null);

        // Guna method exit dari SuperClass
        btnExit.addActionListener(e -> {
            SuperClass sc = new SuperClass();
            sc.iExitSystem();
        });
        exitWrapper.add(btnExit);

        topBar.add(lblLogo, BorderLayout.WEST);
        topBar.add(nameWrapper, BorderLayout.CENTER);
        topBar.add(exitWrapper, BorderLayout.NORTH);

        mainPanel.add(topBar, BorderLayout.NORTH);

        // --- 2. MAIN GRID ---
        JPanel centerGrid = new JPanel(new GridBagLayout());
        centerGrid.setBackground(secondaryColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.weighty = 1.0;

        // COLUMN A: FOOD
        gbc.weightx = 0.30;
        JPanel pFood = createSection("MAIN DISHES");
        String[] fNames = {"Pollos Classic (RM 7.00)" , "Family Bucket (RM25.00)", "Pollos Tenders(RM 9.00)", "Taco Rancheros (RM 8.00)", "Enchilada Rancheros (RM 8.00)", "Huevos Rancheros (RM 8.00)"};
        for(int i=0; i<6; i++) { fFields[i] = new JTextField("0"); addStepper(pFood, fNames[i], fFields[i]); }
        centerGrid.add(pFood, gbc);

        // COLUMN B: DRINKS & SIDES
        gbc.weightx = 0.30;
        JPanel pDrink = createSection("DRINKS & SIDES");
        String[] dNames = {"Horchata Soda (RM5.00)", "Southwestern Tea (RM4.00)", "Orange Juice (RM7.00)", "Spice Curls (RM4.00)", "Pimento Sandwich (RM5.00)", "Lalo's Tacos (RM9.00)"};
        for(int i=0; i<6; i++) { dFields[i] = new JTextField("0"); addStepper(pDrink, dNames[i], dFields[i]); }
        centerGrid.add(pDrink, gbc);

        // COLUMN C: BUTTONS & COST DISPLAY
        gbc.weightx = 0.10;
        JPanel pMid = new JPanel(new GridLayout(2, 1, 0, 20));
        pMid.setOpaque(false);

        JPanel pBtns = new JPanel(new GridLayout(3, 1, 0, 10));
        pBtns.setOpaque(false);
        JButton bTotal = styleBtn("TOTAL"); JButton bRec = styleBtn("RECEIPT"); JButton bRes = styleBtn("RESET");
        pBtns.add(bTotal); pBtns.add(bRec); pBtns.add(bRes);

        JPanel pCosts = new JPanel(new GridLayout(8, 1, 0, 4));
        pCosts.setOpaque(false);
        pCosts.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(whiteColor), "TOTALS", TitledBorder.CENTER, 0, fontBtn, whiteColor));
        addLabel(pCosts, "DRINKS:"); addDisplayField(pCosts, jtxtCOD);
        addLabel(pCosts, "FOOD:"); addDisplayField(pCosts, jtxtCOF);
        addLabel(pCosts, "TAX:"); addDisplayField(pCosts, jtxtTax);
        addLabel(pCosts, "TOTAL:"); addDisplayField(pCosts, jtxtTotal);

        pMid.add(pBtns);
        pMid.add(pCosts);
        centerGrid.add(pMid, gbc);

        // COLUMN D: RECEIPT
// COLUMN D: RECEIPT
        gbc.weightx = 0.50; // <--- Besarkan nilai ini (Asal: 0.30)
        jtxtReceipt.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scroll = new JScrollPane(jtxtReceipt);
// Pastikan scrollpane dibenarkan untuk membesar
        scroll.setPreferredSize(new Dimension(105, 200));
        scroll.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(primaryColor), "RECEIPT", 0, 0, fontHeader, primaryColor));
        centerGrid.add(scroll, gbc);

        mainPanel.add(centerGrid, BorderLayout.CENTER);
        this.add(mainPanel);

        bTotal.addActionListener(e -> calculate());
        bRes.addActionListener(e -> reset());
        bRec.addActionListener(e -> generateReceipt());
    }

    private void addStepper(JPanel p, String name, JTextField f) {
        JPanel row = new JPanel(new GridBagLayout());
        row.setOpaque(false);
        GridBagConstraints rGbc = new GridBagConstraints();

        JLabel lbl = new JLabel(name);
        lbl.setForeground(whiteColor);
        lbl.setFont(fontMenu);

        rGbc.gridx = 0; rGbc.weightx = 1.0; rGbc.anchor = GridBagConstraints.WEST;
        row.add(lbl, rGbc);

        JPanel ctrl = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        ctrl.setOpaque(false);
        JButton m = new JButton("-"); JButton pl = new JButton("+");
        styleStep(m); styleStep(pl);
        f.setPreferredSize(new Dimension(40, 30)); f.setHorizontalAlignment(0); f.setEditable(false);
        f.setFont(fontMenu);

        m.addActionListener(e -> { int v = Integer.parseInt(f.getText()); if(v>0) f.setText(""+(v-1)); });
        pl.addActionListener(e -> { int v = Integer.parseInt(f.getText()); f.setText(""+(v+1)); });

        ctrl.add(m); ctrl.add(f); ctrl.add(pl);
        rGbc.gridx = 1; rGbc.weightx = 0; rGbc.anchor = GridBagConstraints.EAST;
        row.add(ctrl, rGbc);
        p.add(row);
    }

    private JPanel createSection(String title) {
        JPanel p = new JPanel(new GridLayout(6, 1, 0, 10));
        p.setBackground(secondaryColor);
        p.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(primaryColor), title, 0, 0, fontHeader, primaryColor));
        return p;
    }

    private JButton styleBtn(String t) {
        JButton b = new JButton(t);
        b.setBackground(primaryColor); b.setForeground(whiteColor);
        b.setFont(fontBtn); b.setPreferredSize(new Dimension(0, 35));
        b.setBorder(null); b.setFocusPainted(false);
        return b;
    }

    private void styleStep(JButton b) {
        b.setPreferredSize(new Dimension(35, 35));
        b.setBackground(primaryColor);
        b.setForeground(whiteColor);
        b.setFont(new Font("Arial", Font.BOLD, 20));
        b.setBorder(null);
    }

    private void addLabel(JPanel p, String t) { JLabel l = new JLabel(t); l.setFont(new Font("Arial", Font.BOLD, 10)); l.setForeground(whiteColor); p.add(l); }

    private void addDisplayField(JPanel p, JTextField f) {
        f.setEditable(false); f.setBackground(whiteColor); f.setForeground(Color.BLACK);
        f.setHorizontalAlignment(JTextField.RIGHT); f.setFont(fontDisplay); f.setBorder(null);
        p.add(f);
    }

    // --- UPDATED CALCULATE METHOD ---
    private void calculate() {
        SuperClass sc = new SuperClass(); // Panggil data harga dari SuperClass

        // Susun harga ikut turutan UI
        double[] fPrices = {
                sc.pPollosClassic, sc.pFamilyBucket, sc.pPollosTenders,
                sc.pTacoRancheros, sc.pEnchiladaRancheros, sc.pHuevosRancheros
        };

        double[] dPrices = {
                sc.pHorchataSoda, sc.pSouthwesternTea, sc.pOrangeJuice,
                sc.pSpiceCurls, sc.pPimentoSandwich, sc.pLaloTacos
        };

        double dTotal = 0, fTotal = 0;

        // Loop melalui textfield untuk dapat kuantiti dan darab harga
        for(int i = 0; i < dFields.length; i++) {
            dTotal += Integer.parseInt(dFields[i].getText()) * dPrices[i];
        }
        for(int i = 0; i < fFields.length; i++) {
            fTotal += Integer.parseInt(fFields[i].getText()) * fPrices[i];
        }

        double tax = (dTotal + fTotal) * sc.TaxRate;

        jtxtCOD.setText(String.format("%.2f", dTotal));
        jtxtCOF.setText(String.format("%.2f", fTotal));
        jtxtTax.setText(String.format("%.2f", tax));
        jtxtTotal.setText(String.format("%.2f", dTotal + fTotal + tax));
    }

    private void reset() {
        for(int i=0; i<6; i++) { dFields[i].setText("0"); fFields[i].setText("0"); }
        jtxtCOD.setText("0.00"); jtxtCOF.setText("0.00"); jtxtTax.setText("0.00"); jtxtTotal.setText("0.00"); jtxtReceipt.setText("");
    }

    private void generateReceipt() {
        jtxtReceipt.setText("\n    LOS POLLOS HERMANOS\n" + "==========================\n" +
                " Food:        RM " + jtxtCOF.getText() + "\n" +
                " Drinks:      RM " + jtxtCOD.getText() + "\n" +
                " Tax:         RM " + jtxtTax.getText() + "\n" +
                " TOTAL:       RM " + jtxtTotal.getText() + "\n" +
                "==========================\n" + "      THANK YOU!");
    }

    public static void main(String[] args) {
        new moonbucks().setVisible(true);
    }
}