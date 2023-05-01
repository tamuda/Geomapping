import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
/**Creates Canvas and Renders The Components**/
public class Canvas extends JPanel {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    double canvasLength;
    double canvasWidth;
    int strokeWidth = 1;
    int counter=0;
    Timer timer;

    String fileName;
    public String finalDistance;
    Graph graph;
    int x1, x2, y1, y2;
    boolean showShortestPath =false;
    int globalCounter=0;
    boolean animate=false;
    LinkedList<Node> shortestPath;
    public Canvas(Graph g) {
        this.graph = g;
        showShortestPath =false;
    }
    /**Constructor For Canvas**/
    public Canvas(Graph g,List<Node> n,String file) {
        this.fileName =file;
        graph=g;
        shortestPath=(LinkedList<Node>) n;
        showShortestPath =true;
        timer = new Timer(300, new PathAnimationTimer());
        timer.start();

    }
    /**For the animating line along path**/
    public class PathAnimationTimer implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
            try {
                Node i=shortestPath.get(globalCounter);
                switch(fileName) {
                    case "ur.txt":
                        globalCounter=globalCounter+1;
                        break;
                    case "monroe.txt":
                        globalCounter=globalCounter+4;
                        break;
                    case "nys.txt":
                        globalCounter=globalCounter+100;
                        break;
                    default:
                        globalCounter++;
                }

                Node b=shortestPath.get(globalCounter);
                x1 = (int) generateX(i);

                y1 =(int) generateY(i);

                y2 =(int) generateY(b);
                x2 =(int) generateX(b);
                animate=true;
                repaint();
            }catch(Exception e) {
                timer.stop();
                animate=false;
                repaint();//called repeatedly to redraw line along path, thus animating it
            }
        }
    }
    /**
     * Generate X coordinates of nodes**/
    public double generateX(Node n) {
        double padding = 0.0;
        if(getWidth() > getHeight()) {
            padding = (getWidth() - getHeight())/2;
        }

        return ((n.longitude-graph.minLongitude)/(graph.maxLongitude - graph.minLongitude)* canvasWidth) + padding;
    }
    /**
     * Generate Y coordinates of nodes**/
    public double generateY(Node n) {
        double padding = 0.0;
        if(getHeight() > getWidth()) {
            padding = (getHeight() - getWidth())/2;
        }
        return (canvasLength - (n.lattitude-graph.minLatitude)/(graph.maxLatitude - graph.minLatitude)* canvasLength)+ padding;
    }


    @Override
    public void paintComponent(Graphics g) {

        canvasLength = getHeight();
        canvasWidth = getWidth();

        Graphics2D g2 = (Graphics2D) g;

        g.setColor(Color.WHITE);
        g2.fillRect(0,0,(int) canvasWidth,(int) canvasLength);


        for(String s:graph.vertices.keySet()) {
            Node node=graph.vertices.get(s);

            int x1= (int) generateX(node);

            int y1=(int) generateY(node);
            for(Node b:node.adjlist.keySet()) {
                int y2=(int) generateY(b);
                int x2=(int) generateX(b);

                g.setColor(Color.BLACK);
                g2.setStroke(new BasicStroke(strokeWidth));
                g.drawLine(x1, y1, x2, y2);
            }
        }

        if(showShortestPath) {
            int counter=0;
            Node initial = null,second = null;
            int y2 = 0,x2 = 0;
            int x1=0;
            int y1=0;

            if(shortestPath!=null) {
                for (Iterator<Node> i = shortestPath.iterator(); i.hasNext();) {
                    try {
                        if(counter==0) {
                            initial=(Node) i.next();

                        }else {
                            initial=second;
                        }
                        second=(Node) i.next();;
                        x1= (int) generateX(initial);

                        y1=(int) generateY(initial);
                        if(counter==shortestPath.size()/2) {
                            int centerx=x1;
                            int centery=y1;
                        }
                        y2=(int) generateY(second);
                        x2=(int) generateX(second);

                        g2.setColor(Color.red);
                        g2.setStroke(new BasicStroke(4));
                        g2.setStroke(new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 0, new float[] {5}, 0));
                        g2.drawLine(x1, y1, x2, y2);
                        counter++;

                    }catch(Exception e) {

                    }

                }

            }
        }

        if(animate==true) {
            do {

                g2.setColor(Color.red);
                g2.setStroke(new BasicStroke(4));
                g2.drawLine(x1, y1, x2, y2);
                animate=false;
            }while(animate==true);
        }
    }

}
