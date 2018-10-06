import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class run {
    private static ArrayList<String> Deployables = new ArrayList<>();
    private static ArrayList<String> Heists = new ArrayList<>();
    private static ArrayList<String> PrimaryWeapons = new ArrayList<>();
    private static ArrayList<String> SecondaryWeapons = new ArrayList<>();
    private static ArrayList<String> ThrowableWeapons = new ArrayList<>();

    private static String selectedDeployable;
    private static String selectedHeist;
    private static String selectedPrimary;
    private static String selectedSecondary;
    private static String selectedThrowable;

    private static JFrame frame = new JFrame();
    private static JPanel oldPanel = new JPanel();

    private static int StealthIndexStart;
    private static boolean stealth = false;

    public static void main(String[] args){
        frame.setTitle("Payday 2 Roulette");
        frame.setSize(300, 300);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        populateLists();
        randomizeAll();

        addPanel(createPanel());
    }

    private static void populateLists(){
        try {
            String deployablesLocation = "Deployables.txt";
            BufferedReader br = new BufferedReader(new FileReader(new File(deployablesLocation)));
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                Deployables.add(currentLine);
            }
            String heistsLocation = "Heists.txt";
            br = new BufferedReader(new FileReader(new File(heistsLocation)));
            int i = 1;
            while ((currentLine = br.readLine()) != null) {
                if (!currentLine.equals("------")) {
                    Heists.add(currentLine);
                } else {
                    StealthIndexStart = i;
                }
                i++;
            }

            String primaryWeapLocation = "PrimaryWeapons.txt";
            br = new BufferedReader(new FileReader(new File(primaryWeapLocation)));
            while ((currentLine = br.readLine()) != null) {
                PrimaryWeapons.add(currentLine);
            }
            String secondaryWeapLocation = "SecondaryWeapons.txt";
            br = new BufferedReader(new FileReader(new File(secondaryWeapLocation)));
            while ((currentLine = br.readLine()) != null) {
                SecondaryWeapons.add(currentLine);
            }
            String throwableWeapLocation = "ThrowableWeapons.txt";
            br = new BufferedReader(new FileReader(new File(throwableWeapLocation)));
            while ((currentLine = br.readLine()) != null) {
                ThrowableWeapons.add(currentLine);
            }
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
    }

    private static JPanel createPanel(){
        JPanel panel = new JPanel();
        panel.setBackground(Color.GRAY);
        Container con = new Container();
        con.setLayout(new BoxLayout(con, BoxLayout.Y_AXIS));

        JRadioButton stealthButton = new JRadioButton("Stealth?", stealth);
        stealthButton.addActionListener(e -> stealth = !stealth);
        stealthButton.setBackground(Color.GRAY);
        con.add(stealthButton);

        JTextArea heist = new JTextArea();
        heist.setText(selectedHeist);
        heist.setRows(1);
        con.add(heist);

        JButton heistButton = new JButton("New Heist");
        heistButton.addActionListener(e -> randomizeHeist());
        con.add(heistButton);

        JTextArea primaryWeapons = new JTextArea();
        primaryWeapons.setText(selectedPrimary);
        primaryWeapons.setRows(1);
        con.add(primaryWeapons);

        JButton primaryButton = new JButton("New Primary");
        primaryButton.addActionListener(e -> randomizePrimary());
        con.add(primaryButton);

        JTextArea secondaryWeapons = new JTextArea();
        secondaryWeapons.setRows(1);
        secondaryWeapons.setText(selectedSecondary);
        con.add(secondaryWeapons);

        JButton secondaryButton = new JButton("New Secondary");
        secondaryButton.addActionListener(e -> randomizeSecondary());
        con.add(secondaryButton);

        JTextArea throwable = new JTextArea();
        throwable.setText(selectedThrowable);
        throwable.setRows(1);
        con.add(throwable);

        JButton throwableButton = new JButton("New Throwable");
        throwableButton.addActionListener(e -> randomizeThrowable());
        con.add(throwableButton);

        JTextArea deployable = new JTextArea();
        deployable.setRows(1);
        deployable.setText(selectedDeployable);
        con.add(deployable);

        JButton deployableButton = new JButton("New Deployable");
        deployableButton.addActionListener(e -> randomizeDeployable());
        con.add(deployableButton);

        JButton randomzie = new JButton("Randomize all");
        randomzie.addActionListener(e -> randomizeAll());
        con.add(randomzie);

        panel.add(con);
        return panel;
    }

    private static void randomizePrimary(){
        selectedPrimary = PrimaryWeapons.get(randomInt(PrimaryWeapons.size()));
        addPanel(createPanel());
    }

    private static void randomizeSecondary(){
        selectedSecondary = SecondaryWeapons.get(randomInt(SecondaryWeapons.size()));
        addPanel(createPanel());
    }

    private static void randomizeThrowable(){
        selectedThrowable = ThrowableWeapons.get(randomInt(ThrowableWeapons.size()));
        addPanel(createPanel());
    }

    private static void randomizeHeist(){
        int randInt;
        do {
            randInt = randomInt(Heists.size());
        }while (!((randInt != StealthIndexStart && stealth) || (randInt < StealthIndexStart && !stealth)));
        selectedHeist = Heists.get(randInt);
        addPanel(createPanel());
    }

    private static void randomizeDeployable(){
        selectedDeployable = Deployables.get(randomInt(Deployables.size()));
        addPanel(createPanel());
    }

    private static void randomizeAll(){
        randomizeHeist();
        randomizePrimary();
        randomizeSecondary();
        randomizeThrowable();
        randomizeDeployable();
        addPanel(createPanel());
    }

    private static int randomInt(int max){
        Random random = new Random();
        return random.nextInt(max);
    }

    private static void addPanel(JPanel panel){
        if(oldPanel != null){
            frame.remove(oldPanel); //keeps track of the old panel to remove it on updating the frame
        }
        oldPanel = panel;
        frame.add(panel);
        frame.repaint();
        frame.setVisible(true);
    }
}