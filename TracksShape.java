import java.awt.*;
import java.util.*;
import java.awt.Color;
import java.awt.geom.*;
import javax.swing.*;

public class TracksShape implements Moveable
{
    private int xx;
    private int yy;
    private int width;
    int n;

    //variables for the position of each train
    private  double[] theta = new double[20]; //at most 20 trains
    private  double[] y = new double[20];

    //variables for the track limits (the center of the track on the right, bottom, left, and top, based on 40 pixel track width)
    double[] xmax = new double[20];
    double[] ymax = new double[20];
    double[] xmin = new double[20];
    double[] ymin = new double[20];

    //colors for tracks (hexadecimal format)
    Color[] cc = {Color.decode("#ADFFFA"), Color.decode("#FFADE1"), Color.decode("#F2FFAD"), Color.decode("#FFE0AD"),
            Color.decode("#ADB4FF"), Color.decode("#ADFFB0"), Color.decode("#FFA998"), Color.decode("#A9FA6D"),
            Color.decode("#ADFFFA"), Color.decode("#FFADE1"), Color.decode("#F2FFAD"), Color.decode("#FFE0AD"),
            Color.decode("#ADB4FF"), Color.decode("#ADFFB0"), Color.decode("#FFA998"), Color.decode("#E9FA6D")};

    //constructor
    public TracksShape(int x0, int y0, int w)
    {
        xx = x0;
        yy = y0;
        width = w;
        n = width/40;  //number of tracks, each 40 pixels wide

// trackLimits
// for train on track i, give coordinates of the center of the track (and of the train)
// when train is on right, left, top, or bottom section of track i

        for(int i= 0; i<= n-1; i++){
            int IR = 20*i;
	    int OR = 20*(i+1);
	    int Ri = 20*i+10;
	    double Ci = 300 + Ri*Math.cos(theta[i]);
	    double Ai = 300 + Ri*Math.cos(theta[i]);
	    double Bi = 300 + Ri*Math.sin(theta[i]);
	    xmin[i]=Ai;
	    ymax[i]=Bi;
	    
        }


        //each train starts from the lower left corner of its track
        for(int i=0; i<= n-1; i=i+1){
            theta[i] = xmin[i];
            y[i]=  ymax[i];
        }

    }//end constructor

//implementation of Moveable

     public void translate(int dx, int dy)
    {
        int delx = dx; //horizontal increment or decrement (depending on current position)
        int dely = dy; //vertical displacement
        //position clockwise trains
        for (int i=0; i<= n-1; i= i+2){
            theta[i] = theta[i] - (i*2)*Math.PI/300;
        } //for

// position counter-clockwise trains
        for (int i=1; i<= n-1; i= i+2){
             theta[i] = theta[i] + (i*2)*Math.PI/300;
        } //for

    }



    public void draw(Graphics2D g2)
    {
        //paint tracks via superimposed shrinking rectangles
        for(int i= 0; i<=n-1; i++){
            Ellipse2D.Double rect = new Ellipse2D.Double(20*i, 20*i,width-(40*i),width-(40*i));
            g2.setColor(cc[i]);
            g2.fill(rect);
        }

        Random gen = new Random();

        //paint trains
        for (int i=0; i<= n-1; i= i+1){
            Color c = new Color(Math.abs(gen.nextInt()) % 255, Math.abs(gen.nextInt()) % 255, Math.abs(gen.nextInt()) % 255);
            g2.setColor(c);
            Ellipse2D.Double ball = new Ellipse2D.Double(theta[i], y[i], 20, 20);
            g2.fill(ball);
            g2.draw(ball);
        }

    } //end draw

}

