import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
/**
 * Main class
 */
public class StreetMap {
    static double length = 900;
    static double width = 900;
    static List<Node> num;
    static String startOn;
    static JLabel dist=null;
    static JPanel jPanel;
    static JLabel startL ;
    static JLabel endL;
    static String firstLocation;
    static String secondLocation;
    static Graph g;
    static String file;
    /**main method**/
    public static void main(String[] args) {
        file = args[0];
        g = new Graph(file);
        if(args[1].equals("--show")) {
            try {
                if (args[2].equals("--directions")) {//if cmd has format --show --directions
                    firstLocation= args[3];
                    secondLocation=args[4];
                    num = g.findShortest(firstLocation, secondLocation);//find paths between given points
                    drawFrame();
                    if(num!=null) {//to print out path
                        System.out.println("Path: " + num.toString());
                    }
                }
            }catch(Exception e) {}//to not go into above lines if cmd did not write anything beyond --show
            if(args.length==2) {//if cmd just wrote --show
                drawFrame();//draw map without any paths drawn
            }
        } else if(args[1].equals("--directions")){// if cmd wrote directions before show or just directions
            try {
                if (args[2].equals("--show")) {// show written after directions
                    firstLocation= args[3];
                    secondLocation=args[4];
                    num = g.findShortest(firstLocation, secondLocation);
                    drawFrame();
                    if(num!=null) {
                        System.out.println("Path: " + num.toString());
                    }
                } else {//directions written without show, though it makes no difference to what happends
                    firstLocation= args[2];
                    secondLocation=args[3];
                    num = g.findShortest(firstLocation, secondLocation);
                    drawFrame();
                    if(num!=null) {
                        System.out.println("Path: " + num.toString());
                    }
                }
            }catch(Exception e) {}
        }
    }
    /**
     Draws the frame of the map
     */

    /** This method draws the JFrame and calls everything to draw path and output distance in miles. */
    public static void drawFrame() {
        // Set title of the JFrame to the name of the file
        String title = (file.substring(0, file.length() - 4)).toUpperCase(Locale.ROOT);
        JFrame frame = new JFrame("Street Map of " + title);

        // Create text fields and button for finding location
        JButton findLocation = new JButton("Get Route");
        findLocation.setBackground(Color.black);
        findLocation.setOpaque(true);
        JTextField startField = new JTextField("Point A", 4);
        JTextField endField = new JTextField("Point B", 4);

        JPanel topPanel = new JPanel();
        topPanel.add(startField);
        topPanel.add(endField);
        topPanel.add(findLocation);
        topPanel.setLayout(new FlowLayout());
        topPanel.setBackground(Color.black);

        // Add text fields and button to the top of the JFrame
        frame.add(topPanel, BorderLayout.NORTH);

        // Create canvas for drawing the graph and path
        Canvas canvas = new Canvas(g, num, file);
        canvas.setBackground(Color.GREEN);

        // ActionListener for the "Find" button to search for shortest path
        findLocation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String startLocation = startField.getText(); // Get starting node from left text field
                String endLocation = endField.getText(); // Get ending node from right text field

                // Find shortest path between start and end locations
                num = g.findShortest(startLocation, endLocation);
                canvas.shortestPath = (LinkedList<Node>) num;
                canvas.animate = false;
                canvas.timer.restart();
                canvas.repaint();

                // Output distance and path information
                if (num != null) {
                    if (num.get(num.size()-1) == null) {
                        startOn = "DNE";
                        dist.setText("Distance(miles): " + startOn);
                        startL.setText("Point A : " + startLocation);
                        endL.setText("Point B: " + endLocation);
                        findLocation.setBackground(Color.red);
                    } else {
                        DecimalFormat df = new DecimalFormat("#.###");
                        startOn = df.format(num.get(num.size()-1).distToSource);
                        dist.setText("Distance(miles) : " + startOn);
                        startL.setText("Point A : " + startLocation);
                        endL.setText("Point B : " + endLocation);
                        findLocation.setBackground(Color.green);
                    }
                    System.out.println("Path: " + num.toString());
                }
            }
        });

        // Create panel for displaying distance and path information
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.setBackground(Color.white);

        // Create labels for displaying distance and start/end locations
        DecimalFormat df = new DecimalFormat("#.###");
        dist = null;
        if (num != null) {
            if (num.get(num.size()-1) == null) {
                startOn = "DNE";
                dist = new JLabel("Distance(miles): " + startOn);
            } else {
                startOn = df.format(num.get(num.size()-1).distToSource);
                dist = new JLabel("Distance(miles): " + startOn);
            }
            bottomPanel.add(dist);
        } else {
            dist = new JLabel("Distance(miles): " + "DNE");
            bottomPanel.add(dist);
        }
        JLabel space = new JLabel("  ");
        startL = new JLabel("Point A: " + firstLocation);
        endL = new JLabel("Point B: " + secondLocation);
        bottomPanel.add(startL);
        bottomPanel.add(space);
        bottomPanel.add(endL);
        bottomPanel.add(space);

// Add distance and location labels to the bottom of the JFrame
        frame.add(bottomPanel, BorderLayout.SOUTH);

// Add canvas to the center of the JFrame
        frame.add(canvas, BorderLayout.CENTER);

// Set the close operation, size, and resizable property of the JFrame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 600);
        frame.setResizable(true);

// Set the JFrame to be visible
        frame.setVisible(true);

}
}

