import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class run {
    //Lists to store the information
    private static ArrayList<String> Deployables = new ArrayList<>();
    private static ArrayList<String> Heists = new ArrayList<>();
    private static ArrayList<String> PrimaryWeapons = new ArrayList<>();
    private static ArrayList<String> SecondaryWeapons = new ArrayList<>();
    private static ArrayList<String> ThrowableWeapons = new ArrayList<>();
    //keeps track of the selected items currently
    private static String selectedDeployable;
    private static String selectedHeist;
    private static String selectedPrimary;
    private static String selectedSecondary;
    private static String selectedThrowable;
    //keeps a reference to the panel and the frame
    private static JFrame frame = new JFrame();
    private static JPanel currentPanel = new JPanel();
    //tracks where the stealth missions start in the file and if the user wants them included
    private static int StealthIndexStart = Integer.MAX_VALUE;
    private static boolean stealth = false;

    public static void main(String[] args){
        //sets up the frame
        frame.setTitle("Payday 2 Roulette");
        frame.setSize(300, 300);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //populates the lists and randomizes the first time for the users
        populateLists();
        randomizeAll();
        //adds the panel to the frame
        addPanel(createPanel());
    }

    private static void populateLists(){ //reads the files and populates the various lists
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
    }//creates the panel and the basic UI

    private static void randomizePrimary(){
        selectedPrimary = PrimaryWeapons.get(randomInt(PrimaryWeapons.size()));
        addPanel(createPanel());
    }//randomises the selection for the primary weapon

    private static void randomizeSecondary(){
        selectedSecondary = SecondaryWeapons.get(randomInt(SecondaryWeapons.size()));
        addPanel(createPanel());
    }//randomises the selection for the secondary weapon

    private static void randomizeThrowable(){
        selectedThrowable = ThrowableWeapons.get(randomInt(ThrowableWeapons.size()));
        addPanel(createPanel());
    }//randomises the selection for the throwable weapon

    private static void randomizeHeist(){
        int randInt;
        do {
            randInt = randomInt(Heists.size());
        }while (!((randInt != StealthIndexStart && stealth) || (randInt < StealthIndexStart && !stealth)));
        selectedHeist = Heists.get(randInt);
        addPanel(createPanel());
    }//randomises the selection for the heist

    private static void randomizeDeployable(){
        selectedDeployable = Deployables.get(randomInt(Deployables.size()));
        addPanel(createPanel());
    }//randomises the selection for the deployable item

    private static void randomizeAll(){
        randomizeHeist();
        randomizePrimary();
        randomizeSecondary();
        randomizeThrowable();
        randomizeDeployable();
        addPanel(createPanel());
    }//randomises the selection for the all of the options

    private static int randomInt(int max){
        Random random = new Random();
        return random.nextInt(max);
    }//creates a random int from 0 to the max number

    private static void addPanel(JPanel panel){
        if(currentPanel != null){
            frame.remove(currentPanel); //keeps track of the old panel to remove it on updating the frame
        }
        currentPanel = panel;
        frame.add(panel);
        frame.repaint();
        frame.setVisible(true);
    }//adds the panel to the frame and updates the reference to the current panel.
}