import org.jfree.chart.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.*;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class HistogramWykres extends JFrame
{
    double max;
    public HistogramWykres(String s, int tabR[], int tabG[], int tabB[]){
        super(s);

        JPanel jpanel = stworzPanel(s, tabR, tabG, tabB, "c");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jpanel.setPreferredSize(new java.awt.Dimension(640, 480));
        add(jpanel);
    }

    public JPanel stworzPanel(String titleChart, int tabR[], int tabG[], int tabB[], String color){
        JFreeChart wykres = ChartFactory.createScatterPlot(titleChart, "l", "h(l)", daneXYTrzyTabele(tabR, tabG, tabB), PlotOrientation.VERTICAL, true, true, false);

        Shape dots = new Ellipse2D.Double(0,0,2,2);

        XYPlot xyPlot = (XYPlot) wykres.getPlot();
        XYItemRenderer renderer = xyPlot.getRenderer();
        renderer.setSeriesShape(0, dots);
        renderer.setSeriesPaint(0, Color.red);
        renderer.setSeriesShape(1, dots);
        renderer.setSeriesPaint(1, Color.green);
        renderer.setSeriesShape(2, dots);
        renderer.setSeriesPaint(2, Color.blue);

        return new ChartPanel(wykres);
    }

    public XYDataset daneXYTrzyTabele(int[] tabR, int[] tabG, int[] tabB){
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries seria1 = new XYSeries("Red");
        XYSeries seria2 = new XYSeries("Green");
        XYSeries seria3 = new XYSeries("Blue");
        for(int i=0; i<tabR.length; i++){
            seria1.add(i, tabR[i]);
            seria2.add(i, tabG[i]);
            seria3.add(i, tabB[i]);
        }
        seria1.add(-1, -1);
        dataset.addSeries(seria1);
        dataset.addSeries(seria2);
        dataset.addSeries(seria3);
        return dataset;
    }

    public HistogramWykres(String s, double tabR[], double tabG[], double tabB[]){
        super(s);

        JPanel jpanel = stworzPanel(s, tabR, tabG, tabB, "c");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jpanel.setPreferredSize(new java.awt.Dimension(640, 480));
        add(jpanel);
    }

    public JPanel stworzPanel(String tytulWykres, double tabR[], double tabG[], double tabB[], String kolor){
        JFreeChart wykres = ChartFactory.createScatterPlot(tytulWykres, "l", "h(l)", daneXYTrzyTabele(tabR, tabG, tabB), PlotOrientation.VERTICAL, true, true, false);

        Shape dots = new Ellipse2D.Double(0,0,2,2);

        XYPlot xyPlot = (XYPlot) wykres.getPlot();
        XYItemRenderer renderer = xyPlot.getRenderer();
        renderer.setSeriesShape(0, dots);
        renderer.setSeriesPaint(0, Color.red);
        renderer.setSeriesShape(1, dots);
        renderer.setSeriesPaint(1, Color.green);
        renderer.setSeriesShape(2, dots);
        renderer.setSeriesPaint(2, Color.blue);

        NumberAxis domain = (NumberAxis) xyPlot.getDomainAxis();
        domain.setRange(-5.0, 260.00);
        domain.setTickUnit(new NumberTickUnit(10));
        domain.setVerticalTickLabels(true);
        NumberAxis range = (NumberAxis) xyPlot.getRangeAxis();
        range.setRange(-0.005, max+0.005);
        range.setTickUnit(new NumberTickUnit(max/10));

        return new ChartPanel(wykres);
    }

    public XYDataset daneXYTrzyTabele(double[] tabR, double[] tabG, double[] tabB){
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries seria1 = new XYSeries("Red");
        XYSeries seria2 = new XYSeries("Green");
        XYSeries seria3 = new XYSeries("Blue");

        double maxR = 0;
        double maxG = 0;
        double maxB = 0;

        for(int i=0; i<tabR.length; i++){
            seria1.add(i, tabR[i]);
            seria2.add(i, tabG[i]);
            seria3.add(i, tabB[i]);

            if(tabR[i]>maxR){
                maxR = tabR[i];
            }
            if(tabG[i]>maxG){
                maxG = tabG[i];
            }
            if(tabB[i]>maxB){
                maxB = tabB[i];
            }
        }

        if(maxR>maxG){
            if(maxR>maxB){
                max = maxR;
            }
            else{
                max = maxB;
            }
        }
        else if(maxG>maxB){
            max = maxG;
        }
        else{
            max = maxB;
        }

        //seria1.add(-1, -0.000001);
        dataset.addSeries(seria1);
        dataset.addSeries(seria2);
        dataset.addSeries(seria3);
        return dataset;
    }

    public HistogramWykres(String s, double tab[]){
        super(s);

        JPanel jpanel = stworzPanel(s, tab);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jpanel.setPreferredSize(new java.awt.Dimension(640, 480));
        add(jpanel);
    }

    public JPanel stworzPanel(String tytulWykres, double tab[]){
        JFreeChart wykres = ChartFactory.createScatterPlot(tytulWykres, "l", "h(l)", daneXYTabela(tab), PlotOrientation.VERTICAL, true, true, false);

        Shape dots = new Ellipse2D.Double(0,0,2,2);

        XYPlot xyPlot = (XYPlot) wykres.getPlot();
        XYItemRenderer renderer = xyPlot.getRenderer();
        renderer.setSeriesShape(0, dots);
        renderer.setSeriesPaint(0, Color.darkGray);

        return new ChartPanel(wykres);
    }

    public XYDataset daneXYTabela(double[] tab){
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries seria1 = new XYSeries("Y");

        for(int i=0; i<tab.length; i++){
            seria1.add(i, tab[i]);
        }

        seria1.add(-1, -0.000001);
        dataset.addSeries(seria1);
        return dataset;
    }
}

