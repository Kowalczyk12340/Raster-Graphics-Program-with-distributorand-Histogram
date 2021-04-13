import java.awt.*;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;

import org.jfree.ui.RefineryUtilities;

import java.lang.Math;
import java.util.Arrays;

public class GraphicsPanel extends JPanel
{
    protected BufferedImage bufferedImage;
    int minCzerwony = 255;
    int minZielony = 255;
    int minNiebieski = 255;
    int maxCzerwony = 0;
    int maxZielony = 0;
    int maxNiebieski = 0;

    public GraphicsPanel()
    {
        super();
        setSize(new Dimension(600,600));
        clear();
    }

    public void uploadGraphicsFile(String path)
    {
        File graphicsFile = new File(path);
        try {
            bufferedImage = ImageIO.read(graphicsFile);
            Dimension size = new Dimension(bufferedImage.getWidth(),bufferedImage.getHeight());
            if((int)size.getWidth()>800 || (int)size.getHeight()>800)
            {
                setPreferredSize(new Dimension(800, 800));
                setMaximumSize(new Dimension(800, 800));
            }
            else
            {
                setPreferredSize(size);
                setMaximumSize(size);
            }
            setBorder(BorderFactory.createLineBorder(Color.BLACK));
            repaint();
        }
        catch(IOException ex)
        {
            JOptionPane.showMessageDialog(null,"Błąd odczytu pliku: " + path);
            ex.printStackTrace();
        }
    }


    public void saveGraphicsFile(String path)
    {
        File graphicsFile = new File(path);
        try {
            if(bufferedImage != null)
            {
                if(!ImageIO.write(bufferedImage,path.substring(path.lastIndexOf('.') + 1),new File(path)))
                {
                    JOptionPane.showMessageDialog(null,"Nie udało się zapisać pliku w " + path);
                }
            }
            else {
                JOptionPane.showMessageDialog(null,"Brak obrazu do zapisania!");
            }
        }
        catch(IOException ex)
        {
            JOptionPane.showMessageDialog(null,"Nie udało się zapisać pliku w " + path);
        }
    }

    public void clear()
    {
        Graphics2D graphics2D = (Graphics2D) bufferedImage.getGraphics();
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(0,0,bufferedImage.getWidth(),bufferedImage.getHeight());
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        repaint();
    }

    public void setSize(Dimension dimension)
    {
        bufferedImage = new BufferedImage((int)dimension.getWidth(),(int)dimension.getHeight(),BufferedImage.TYPE_INT_RGB);
        setPreferredSize(dimension);
        setMaximumSize(dimension);
    }

    public void copy(BufferedImage input)
    {
        setSize(new Dimension(input.getWidth(),input.getHeight()));
        Color color;
        int red,green,blue;
        for(int i=0;i<input.getHeight();i++)
        {
            for(int j=0;j<input.getHeight();i++)
            {
                color = new Color(input.getRGB(i,j));
                red = color.getRed();
                green = color.getGreen();
                blue = color.getBlue();
                int average = (red + green + blue) / 3;
                //ustawianie koloru piksela w obiekcie BufferedImage
                bufferedImage.setRGB(i,j,new Color(average,average,average).getRGB());
            }
            repaint();
        }
    }


    public void avg(BufferedImage input){
        setSize(new Dimension(input.getWidth(),input.getHeight()));
        Color pobrany;
        int czerwony, zielony, niebieski;

        for(int i=0; i<input.getWidth(); i++){
            for(int j=0; j<input.getHeight(); j++){
                pobrany = new Color(input.getRGB(i,j));

                czerwony = pobrany.getRed();
                zielony = pobrany.getGreen();
                niebieski = pobrany.getBlue();
                int szary = (czerwony+zielony+niebieski)/3;
                bufferedImage.setRGB(i,j, new Color(szary,szary,szary).getRGB());
            }
        }
        repaint();
    }

    public void brightness(BufferedImage input, int k){
        setSize(new Dimension(input.getWidth(),input.getHeight()));
        Color pobrany;
        int czerwony, zielony, niebieski;

        for(int i=0; i<input.getWidth(); i++)
        {
            for(int j=0; j<input.getHeight(); j++){
                pobrany = new Color(input.getRGB(i,j));
                czerwony = pobrany.getRed();
                zielony = pobrany.getGreen();
                niebieski = pobrany.getBlue();
                //alfa = pobrany.getAlpha();

                czerwony+=k;
                zielony+=k;
                niebieski+=k;
                if(czerwony>255){
                    czerwony=255;
                }
                else if(czerwony<0){
                    czerwony=0;
                }
                if(zielony>255){
                    zielony=255;
                }
                else if(zielony<0){
                    zielony=0;
                }
                if(niebieski>255){
                    niebieski=255;
                }
                else if(niebieski<0){
                    niebieski=0;
                }
                bufferedImage.setRGB(i,j, new Color(czerwony,zielony,niebieski).getRGB());
            }
        }
        repaint();
    }

    public void contrast(BufferedImage input, double k)
    {
        setSize(new Dimension(input.getWidth(),input.getHeight()));
        Color pobrany;
        double czerwony, zielony, niebieski;

        for(int i=0; i<input.getWidth(); i++){
            for(int j=0; j<input.getHeight(); j++){
                pobrany = new Color(input.getRGB(i, j));

                czerwony = pobrany.getRed();
                zielony = pobrany.getGreen();
                niebieski = pobrany.getBlue();

                czerwony = (czerwony * k);
                zielony = (zielony * k);
                niebieski = (niebieski * k);

                if(czerwony>255){
                    czerwony = 255;
                }
                else if(czerwony<0){
                    czerwony = 0;
                }
                if(zielony>255){
                    zielony = 255;
                }
                else if(zielony<0){
                    zielony = 0;
                }
                if(niebieski>255){
                    niebieski = 255;
                }
                else if(niebieski<0){
                    niebieski = 0;
                }

                bufferedImage.setRGB(i, j, new Color((int)czerwony,(int)zielony,(int)niebieski).getRGB());
            }
        }
        repaint();
    }

    public void negation(BufferedImage input)
    {
        setSize(new Dimension(input.getWidth(),input.getHeight()));
        Color pobrany;
        int red, green, blue;
        maxMin(input);

        for(int i=0; i<input.getWidth(); i++){
            for(int j=0; j<input.getHeight(); j++){
                pobrany = new Color(input.getRGB(i,j));
                red = maxCzerwony - pobrany.getRed();
                green = maxZielony - pobrany.getGreen();
                blue = maxNiebieski - pobrany.getBlue();

                bufferedImage.setRGB(i,j, new Color(red, green, blue).getRGB());
            }
        }
        repaint();
    }

    public void zerujMaxMin(){
        minCzerwony = 255;
        minZielony = 255;
        minNiebieski = 255;
        maxCzerwony = 0;
        maxZielony = 0;
        maxNiebieski = 0;
    }

    public void maxMin(BufferedImage input)
    {
        zerujMaxMin();
        Color pobrany;
        int czerwony, zielony, niebieski;

        for(int i=0; i<input.getWidth(); i++){
            for(int j=0; j<input.getHeight(); j++){
                pobrany = new Color(input.getRGB(i,j));
                czerwony = pobrany.getRed();
                zielony = pobrany.getGreen();
                niebieski = pobrany.getBlue();

                if(czerwony>maxCzerwony){
                    maxCzerwony = czerwony;
                }
                if(zielony>maxZielony){
                    maxZielony = zielony;
                }
                if(niebieski>maxNiebieski){
                    maxNiebieski = niebieski;
                }
                if(czerwony<minCzerwony){
                    minCzerwony = czerwony;
                }
                if(zielony<minZielony){
                    minZielony = zielony;
                }
                if(niebieski<minNiebieski){
                    minNiebieski = niebieski;
                }
            }
        }
    }

    public void oneOfThem(BufferedImage input, int color)
    {
        setSize(new Dimension(input.getWidth(),input.getHeight()));
        Color pobrany;

        if(color ==1){
            for(int i=0; i<input.getWidth(); i++){
                for(int j=0; j<input.getHeight(); j++){
                    pobrany = new Color(input.getRGB(i,j));
                    int szary = pobrany.getRed();
                    bufferedImage.setRGB(i,j, new Color(szary,szary,szary).getRGB());
                }
            }
        }
        else if(color ==2)
        {
            for(int i=0; i<input.getWidth(); i++){
                for(int j=0; j<input.getHeight(); j++){
                    pobrany = new Color(input.getRGB(i,j));
                    int szary = pobrany.getGreen();
                    bufferedImage.setRGB(i,j, new Color(szary,szary,szary).getRGB());
                }
            }
        }
        else{
            for(int i=0; i<input.getWidth(); i++){
                for(int j=0; j<input.getHeight(); j++){
                    pobrany = new Color(input.getRGB(i,j));
                    int szary = pobrany.getBlue();
                    bufferedImage.setRGB(i,j, new Color(szary,szary,szary).getRGB());
                }
            }
        }
        repaint();
    }

    public void modelYUV(BufferedImage input){
        setSize(new Dimension(input.getWidth(),input.getHeight()));
        Color pobrany;
        int red, green, blue;

        for(int i=0; i<input.getWidth(); i++){
            for(int j=0; j<input.getHeight(); j++){
                pobrany = new Color(input.getRGB(i,j));
                red = pobrany.getRed();
                green = pobrany.getGreen();
                blue = pobrany.getBlue();
                int szary = (int)((0.299 * red) + (0.587 * green) + (0.114 * blue));
                bufferedImage.setRGB(i,j, new Color(szary,szary,szary).getRGB());
            }
        }
        repaint();
    }

    public void rangeOfBrightness(BufferedImage input)
    {
        setSize(new Dimension(input.getWidth(),input.getHeight()));
        Color pobrany;
        Color pobranyL;
        int czerwony, zielony, niebieski;
        maxMin(input);

        for(int i=0; i<input.getWidth(); i++){
            for(int j=0; j<input.getHeight(); j++){
                pobrany = new Color(input.getRGB(i,j));

                czerwony = pobrany.getRed();
                zielony = pobrany.getGreen();
                niebieski = pobrany.getBlue();

                if((maxCzerwony - minCzerwony)==0){
                    czerwony = czerwony;
                }
                else{
                    czerwony = ((czerwony - minCzerwony)*255) / (maxCzerwony - minCzerwony);
                }
                if((maxZielony - minZielony)==0){
                    zielony = zielony;
                }
                else{
                    zielony = ((zielony - minZielony)*255) / (maxZielony - minZielony);
                }
                if((maxNiebieski - minNiebieski)==0){
                    niebieski = niebieski;
                }
                else{
                    niebieski = ((niebieski - minNiebieski)*255) / (maxNiebieski - minNiebieski);
                }

                if(czerwony>255){
                    czerwony = 255;
                }
                else if(czerwony<0){
                    czerwony = 0;
                }
                if(zielony>255){
                    zielony = 255;
                }
                else if(zielony<0){
                    zielony = 0;
                }
                if(niebieski>255){
                    niebieski = 255;
                }
                else if(niebieski<0){
                    niebieski = 0;
                }
                bufferedImage.setRGB(i,j, new Color(czerwony, zielony, niebieski).getRGB());
            }
        }
        repaint();
    }

    public int[] tableHistogram(BufferedImage input,int color)
    {
        int table[] = new int[256];
        for(int i=0;i<256;i++)
        {
            table[i] = 0;
        }
        Color pobrany;
        int value;
        if(color == 1)
        {
            for(int i=0;i<input.getWidth();i++)
            {
                for(int j=0;j<input.getHeight();j++)
                {
                    pobrany = new Color(input.getRGB(i,j));
                    value = pobrany.getRed();
                    table[value]++;
                }
            }
        }
        else if(color == 2)
        {
            for(int i=0;i<input.getWidth();i++)
            {
                for(int j=0;j<input.getHeight();j++)
                {
                    pobrany = new Color(input.getRGB(i,j));
                    value = pobrany.getGreen();
                    table[value]++;
                }
            }
        }
        else if(color==3){
            for(int i=0; i<input.getWidth(); i++){
                for(int j=0; j<input.getHeight(); j++){
                    pobrany = new Color(input.getRGB(i,j));
                    value = pobrany.getBlue();
                    table[value]++;
                }
            }
        }
        return table;
    }

    public void histogram(BufferedImage wejscie){
        int tablicaR[] = tableHistogram(wejscie, 1);
        int tablicaG[] = tableHistogram(wejscie, 2);
        int tablicaB[] = tableHistogram(wejscie, 3);

        HistogramWykres hist = new HistogramWykres("Histogram", tablicaR, tablicaG, tablicaB);

        hist.pack();
        RefineryUtilities.centerFrameOnScreen(hist);
        hist.setVisible(true);
    }

    public void histogramDyskretny(BufferedImage wejscie){
        int tablicaR[] = tableHistogram(wejscie, 1);
        int tablicaG[] = tableHistogram(wejscie, 2);
        int tablicaB[] = tableHistogram(wejscie, 3);

        double mianownik = wejscie.getWidth()*wejscie.getHeight();
        double histogramDyskretnyR[] = histogramTablica(tablicaR, mianownik);
        double histogramDyskretnyG[] = histogramTablica(tablicaG, mianownik);
        double histogramDyskretnyB[] = histogramTablica(tablicaB, mianownik);

        HistogramWykres hist = new HistogramWykres("Histogram",histogramDyskretnyR, histogramDyskretnyG, histogramDyskretnyB);

        hist.pack();
        RefineryUtilities.centerFrameOnScreen(hist);
        hist.setVisible(true);
    }

    public double[] histogramTablica(int[] tab, double mianownik){
        double[] tablica = new double[256];
        for(int i=0; i<256; i++){
            tablica[i]=tab[i]/mianownik;
        }
        return tablica;
    }

    public void dystrybuanta(BufferedImage wejscie){
        int tablicaR[] = tableHistogram(wejscie, 1);
        int tablicaG[] = tableHistogram(wejscie, 2);
        int tablicaB[] = tableHistogram(wejscie, 3);

        double mianownik = wejscie.getWidth()*wejscie.getHeight();
        double histogramDyskretnyR[] = histogramTablica(tablicaR, mianownik);
        double histogramDyskretnyG[] = histogramTablica(tablicaG, mianownik);
        double histogramDyskretnyB[] = histogramTablica(tablicaB, mianownik);

        histogramDyskretnyR = dystrybuantaTablica(histogramDyskretnyR);
        histogramDyskretnyG = dystrybuantaTablica(histogramDyskretnyG);
        histogramDyskretnyB = dystrybuantaTablica(histogramDyskretnyB);

        HistogramWykres hist = new HistogramWykres("Dystrybuanta", histogramDyskretnyR, histogramDyskretnyG, histogramDyskretnyB);

        hist.pack();
        RefineryUtilities.centerFrameOnScreen(hist);
        hist.setVisible(true);
    }

    public double[] dystrybuantaTablica(double[] tab){
        double[] tablica = new double[256];
        for(int i=0; i<256; i++){
            tablica[i] = tab[i];
        }
        for(int i=1; i<256; i++){
            tablica[i]+=tablica[i-1];
        }
        return tablica;
    }

    public double[] tablicaPrzejsc(double[] tab){
        double[] tablica = new double[256];
        for(int i=0; i<256; i++){
            tablica[i] = tab[i];
            if(tablica[i]>1){//if(tablica[i]>255){
                tablica[i] = 1;
            }
            else if(tablica[i]<0){
                tablica[i] = 0;
            }
        }
        return tablica;
    }

    public int[] tablicaPrzejscDystrybuanta(double[] tab1, double[] tab2){
        int[] tablica = new int[256];
        boolean wstawiono = false;
        for(int i=0; i<256; i++){
            for(int j=0; j<256; j++){
                if(tab1[i] <= tab2[j] && !wstawiono){
                    tablica[i] = j;
                    wstawiono = true;
                    break;
                }
                wstawiono = false;
            }
            if(tablica[i]>255){//if(tablica[i]>255){
                tablica[i] = 255;
            }
            else if(tablica[i]<0){
                tablica[i] = 0;
            }
        }
        return tablica;
    }

    public void dystrybuantaWyrownana(BufferedImage wejscie){
        setSize(new Dimension(wejscie.getWidth(),wejscie.getHeight()));
        int tablicaR[] = tableHistogram(wejscie, 1);
        int tablicaG[] = tableHistogram(wejscie, 2);
        int tablicaB[] = tableHistogram(wejscie, 3);

        double mianownik = wejscie.getWidth()*wejscie.getHeight();
        double histogramDyskretnyR[] = histogramTablica(tablicaR, mianownik);
        double histogramDyskretnyG[] = histogramTablica(tablicaG, mianownik);
        double histogramDyskretnyB[] = histogramTablica(tablicaB, mianownik);

        histogramDyskretnyR = dystrybuantaTablica(histogramDyskretnyR);
        histogramDyskretnyG = dystrybuantaTablica(histogramDyskretnyG);
        histogramDyskretnyB = dystrybuantaTablica(histogramDyskretnyB);

        double tablicaPrzejscR[] = tablicaPrzejsc(histogramDyskretnyR);
        double tablicaPrzejscG[] = tablicaPrzejsc(histogramDyskretnyG);
        double tablicaPrzejscB[] = tablicaPrzejsc(histogramDyskretnyB);

        Color pobrany;
        float czerwony, zielony, niebieski;

        for(int i=0; i<wejscie.getWidth(); i++){
            for(int j=0; j<wejscie.getHeight(); j++){
                pobrany = new Color(wejscie.getRGB(i,j));
                czerwony = (float)tablicaPrzejscR[pobrany.getRed()];
                zielony = (float)tablicaPrzejscG[pobrany.getGreen()];
                niebieski = (float)tablicaPrzejscB[pobrany.getBlue()];

                bufferedImage.setRGB(i,j, new Color(czerwony, zielony, niebieski).getRGB());
            }
        }
        repaint();
    }

    public void dystrybuantaSciemniajaca(BufferedImage wejscie){
        setSize(new Dimension(wejscie.getWidth(),wejscie.getHeight()));

        double tablica[] = new double[256];
        for(int i=0;i<256;i++){
            //tablica[i] = Math.pow(1.01, i)/12.65;
            tablica[i] = Math.pow(1.01, i)/2.65;
            if(tablica[i]>1){
                tablica[i] = 1;
            }
            else if(tablica[i]<0){
                tablica[i] = 0;
            }
        }

        int tablicaR[] = tableHistogram(wejscie, 1);
        int tablicaG[] = tableHistogram(wejscie, 2);
        int tablicaB[] = tableHistogram(wejscie, 3);

        double mianownik = wejscie.getWidth()*wejscie.getHeight();
        double histogramDyskretnyR[] = histogramTablica(tablicaR, mianownik);
        double histogramDyskretnyG[] = histogramTablica(tablicaG, mianownik);
        double histogramDyskretnyB[] = histogramTablica(tablicaB, mianownik);

        histogramDyskretnyR = dystrybuantaTablica(histogramDyskretnyR);
        histogramDyskretnyG = dystrybuantaTablica(histogramDyskretnyG);
        histogramDyskretnyB = dystrybuantaTablica(histogramDyskretnyB);

        double tablicaPrzejscR[] = tablicaPrzejsc(histogramDyskretnyR);
        double tablicaPrzejscG[] = tablicaPrzejsc(histogramDyskretnyG);
        double tablicaPrzejscB[] = tablicaPrzejsc(histogramDyskretnyB);

        int[] tablicaPrzejscRD = tablicaPrzejscDystrybuanta(tablicaPrzejscR, tablica);
        int[] tablicaPrzejscGD = tablicaPrzejscDystrybuanta(tablicaPrzejscG, tablica);
        int[] tablicaPrzejscBD = tablicaPrzejscDystrybuanta(tablicaPrzejscB, tablica);

        Color pobrany;
        int czerwony, zielony, niebieski;

        for(int i=0; i<wejscie.getWidth(); i++){
            for(int j=0; j<wejscie.getHeight(); j++){
                pobrany = new Color(wejscie.getRGB(i,j));
                czerwony = tablicaPrzejscRD[pobrany.getRed()];
                zielony = tablicaPrzejscGD[pobrany.getGreen()];
                niebieski = tablicaPrzejscBD[pobrany.getBlue()];

                bufferedImage.setRGB(i,j, new Color(czerwony, zielony, niebieski).getRGB());
            }
        }
        repaint();
    }

    public void dystrybuantaRozjasniajaca(BufferedImage input){
        setSize(new Dimension(input.getWidth(), input.getHeight()));

        double tablica[] = new double[256];
        //tablica[0] = 0;
        for(int i=0;i<256;i++){
            //tablica[i] = Math.log10(i+5)/2.65;
            tablica[i] = Math.pow(i, 0.5)/15.7132;
            if(tablica[i]>1){
                tablica[i] = 1;
            }
            else if(tablica[i]<0){
                tablica[i] = 0;
            }
        }
        int tablicaR[] = tableHistogram(input, 1);
        int tablicaG[] = tableHistogram(input, 2);
        int tablicaB[] = tableHistogram(input, 3);

        double mianownik = input.getWidth()* input.getHeight();
        double histogramDyskretnyR[] = histogramTablica(tablicaR, mianownik);
        double histogramDyskretnyG[] = histogramTablica(tablicaG, mianownik);
        double histogramDyskretnyB[] = histogramTablica(tablicaB, mianownik);

        histogramDyskretnyR = dystrybuantaTablica(histogramDyskretnyR);
        histogramDyskretnyG = dystrybuantaTablica(histogramDyskretnyG);
        histogramDyskretnyB = dystrybuantaTablica(histogramDyskretnyB);

        double tablicaPrzejscR[] = tablicaPrzejsc(histogramDyskretnyR);
        double tablicaPrzejscG[] = tablicaPrzejsc(histogramDyskretnyG);
        double tablicaPrzejscB[] = tablicaPrzejsc(histogramDyskretnyB);

        int[] tablicaPrzejscRD = tablicaPrzejscDystrybuanta(tablicaPrzejscR, tablica);
        int[] tablicaPrzejscGD = tablicaPrzejscDystrybuanta(tablicaPrzejscG, tablica);
        int[] tablicaPrzejscBD = tablicaPrzejscDystrybuanta(tablicaPrzejscB, tablica);

        Color pobrany;
        int czerwony, zielony, niebieski;

        for(int i = 0; i< input.getWidth(); i++){
            for(int j = 0; j< input.getHeight(); j++){
                pobrany = new Color(input.getRGB(i,j));
                czerwony = tablicaPrzejscRD[pobrany.getRed()];
                zielony = tablicaPrzejscGD[pobrany.getGreen()];
                niebieski = tablicaPrzejscBD[pobrany.getBlue()];

                bufferedImage.setRGB(i,j, new Color(czerwony, zielony, niebieski).getRGB());
            }
        }
        repaint();
    }

    public void dystrybuantaKontrastujaca(BufferedImage input){
        setSize(new Dimension(input.getWidth(), input.getHeight()));

        double tablica[] = new double[256];
        //tablica[0] = 0;
        for(int i=0;i<256;i++){
            tablica[i] = (Math.atan((i-128)/20.0)+1.6)/2.5;
            if(tablica[i]>1){
                tablica[i] = 1;
            }
            else if(tablica[i]<0){
                tablica[i] = 0;
            }
        }
        int tablicaR[] = tableHistogram(input, 1);
        int tablicaG[] = tableHistogram(input, 2);
        int tablicaB[] = tableHistogram(input, 3);

        double mianownik = input.getWidth()* input.getHeight();
        double histogramDyskretnyR[] = histogramTablica(tablicaR, mianownik);
        double histogramDyskretnyG[] = histogramTablica(tablicaG, mianownik);
        double histogramDyskretnyB[] = histogramTablica(tablicaB, mianownik);

        histogramDyskretnyR = dystrybuantaTablica(histogramDyskretnyR);
        histogramDyskretnyG = dystrybuantaTablica(histogramDyskretnyG);
        histogramDyskretnyB = dystrybuantaTablica(histogramDyskretnyB);

        double tablicaPrzejscR[] = tablicaPrzejsc(histogramDyskretnyR);
        double tablicaPrzejscG[] = tablicaPrzejsc(histogramDyskretnyG);
        double tablicaPrzejscB[] = tablicaPrzejsc(histogramDyskretnyB);

        int[] tablicaPrzejscRD = tablicaPrzejscDystrybuanta(tablicaPrzejscR, tablica);
        int[] tablicaPrzejscGD = tablicaPrzejscDystrybuanta(tablicaPrzejscG, tablica);
        int[] tablicaPrzejscBD = tablicaPrzejscDystrybuanta(tablicaPrzejscB, tablica);

        Color pobrany;
        int czerwony, zielony, niebieski;

        for(int i = 0; i< input.getWidth(); i++){
            for(int j = 0; j< input.getHeight(); j++){
                pobrany = new Color(input.getRGB(i,j));
                czerwony = tablicaPrzejscRD[pobrany.getRed()];
                zielony = tablicaPrzejscGD[pobrany.getGreen()];
                niebieski = tablicaPrzejscBD[pobrany.getBlue()];

                bufferedImage.setRGB(i,j, new Color(czerwony, zielony, niebieski).getRGB());
            }
        }
        repaint();
    }

    public int[] histogramSkladowaYTablica(BufferedImage wejscie){
        setSize(new Dimension(wejscie.getWidth(),wejscie.getHeight()));
        Color pobrany;
        int czerwony, zielony, niebieski;

        int tablica[] = new int[256];
        for(int i=0;i<256;i++){
            tablica[i]=0;
        }

        for(int i=0; i<wejscie.getWidth(); i++){
            for(int j=0; j<wejscie.getHeight(); j++){
                pobrany = new Color(wejscie.getRGB(i,j));
                czerwony = pobrany.getRed();
                zielony = pobrany.getGreen();
                niebieski = pobrany.getBlue();
                int y = (int)((0.299 * czerwony) + (0.587 * zielony) + (0.114 * niebieski));
                tablica[y]++;
            }
        }
        return tablica;
    }

    public void dystrybuantaSkladowaY(BufferedImage wejscie){
        int[] tablica = histogramSkladowaYTablica(wejscie);

        double mianownik = wejscie.getWidth()*wejscie.getHeight();
        double histogramDyskretny[] = histogramTablica(tablica, mianownik);

        histogramDyskretny = dystrybuantaTablica(histogramDyskretny);

        HistogramWykres hist = new HistogramWykres("Dystrybuanta Y", histogramDyskretny);

        hist.pack();
        RefineryUtilities.centerFrameOnScreen(hist);
        hist.setVisible(true);
    }

    public void dystrybuantaSkladowaYObraz(BufferedImage wejscie, String rodzaj){
        setSize(new Dimension(wejscie.getWidth(),wejscie.getHeight()));
        int[] tablica = histogramSkladowaYTablica(wejscie);

        double mianownik = wejscie.getWidth()*wejscie.getHeight();
        double histogramDyskretny[] = histogramTablica(tablica, mianownik);

        histogramDyskretny = dystrybuantaTablica(histogramDyskretny);

        double tablicaPrzejsc[] = new double[256];
        for(int i=0;i<256;i++){
            tablicaPrzejsc[i] = 0;
        }
        if(rodzaj=="wyrównana"){
            tablicaPrzejsc = tablicaPrzejsc(histogramDyskretny);
        }
        else if(rodzaj=="ściemniająca"){
            for(int i=0;i<256;i++){
                tablicaPrzejsc[i] = Math.pow(1.01, i)/12.65;
                if(tablica[i]>1){
                    tablica[i] = 1;
                }
                else if(tablica[i]<0){
                    tablica[i] = 0;
                }
            }
        }
        else if(rodzaj=="rozjaśniająca"){
            for(int i=0;i<256;i++){
                //tablicaPrzejsc[i] = Math.log10(i+10)/2.5;
                tablicaPrzejsc[i] = Math.pow(i, 0.5)/16.1;
                if(tablica[i]>1){
                    tablica[i] = 1;
                }
                else if(tablica[i]<0){
                    tablica[i] = 0;
                }
            }
        }
        else if(rodzaj=="kontrastująca"){
            for(int i=0;i<256;i++){
                tablicaPrzejsc[i] = (Math.atan((i-128)/20.0)+1.6)/3.2;
                if(tablica[i]>1){
                    tablica[i] = 1;
                }
                else if(tablica[i]<0){
                    tablica[i] = 0;
                }
            }
        }
        else{
            tablicaPrzejsc = tablicaPrzejsc(histogramDyskretny);
        }

        Color pobrany;
        int czerwony, zielony, niebieski;
        double y, u, v;

        for(int i=0; i<wejscie.getWidth(); i++){
            for(int j=0; j<wejscie.getHeight(); j++){
                pobrany = new Color(wejscie.getRGB(i,j));

                y = 0.299*pobrany.getRed() + 0.587*pobrany.getGreen() + 0.114*pobrany.getBlue();
                u = 0.492*pobrany.getBlue() - 0.492*y;
                v = 0.877*pobrany.getRed() - 0.877*y;

                y = 256*tablicaPrzejsc[(int)y];

                czerwony = (int)(y+1.140*v);
                zielony = (int)(y-0.395*u-0.581*v);
                niebieski = (int)(y+2.032*u);

                //System.out.print(czerwony+"; ");

                if(czerwony>255){
                    czerwony = 255;
                }
                else if(czerwony<0){
                    czerwony = 0;
                }
                if(zielony>255){
                    zielony = 255;
                }
                else if(zielony<0){
                    zielony = 0;
                }
                if(niebieski>255){
                    niebieski = 255;
                }
                else if(niebieski<0){
                    niebieski = 0;
                }

                bufferedImage.setRGB(i,j, new Color(czerwony, zielony, niebieski).getRGB());
            }
        }
        repaint();
    }

    public void getTableYUW(BufferedImage input)
    {
        setSize(new Dimension(input.getWidth(), input.getHeight()));
        double czerw = 0, ziel = 0, nieb = 0;
        double Y, U=0, V=0, R, G, B , Yprim=0;
        for(int i = 1; i< input.getWidth()-1; i++){
            for(int j = 1; j< input.getHeight()-1; j++){
                Color ci1 = new Color(input.getRGB(i,j));
                czerw = ci1.getRed();
                ziel = ci1.getGreen();
                nieb = ci1.getBlue();
                Y = (int)((0.299 * czerw) + (0.587 * ziel) + (0.114 * nieb));
                U = (int)((-0.147 * czerw) + (-0.289 * ziel) + (0.437 * nieb));
                V = (int)((0.615 * czerw) + (-0.515 * ziel) + (-0.100 * nieb));
                R = (int)(1.000*Y + 1.140*V);
                G = (int)(1.000*Y + (-0.394*U) + (-0.587*V));
                B = (int)(1.000*Y + 2.028*U);
                czerw = R;
                ziel = G;
                nieb =B;
                if(czerw<0){
                    czerw = 0;
                }
                if(czerw>255){
                    czerw = 255;
                }
                if(ziel<0){
                    ziel = 0;
                }
                if(ziel>255){
                    ziel = 255;
                }
                if(nieb<0){
                    nieb = 0;
                }
                if(nieb>255){
                    nieb = 255;
                }
                bufferedImage.setRGB(i,j,new Color((int)czerw,(int)ziel,(int)nieb).getRGB());
            }
        }
        repaint();
    }

    public void wczytajMaske(BufferedImage wejscie, int tab[][])
    {
        String[] opcja = {"RGB", "YUV"};
        int opcjaWInt = JOptionPane.showOptionDialog(null,"Wybierz model koloru, w których chcesz filtrować:"
                ,"Wybór modelu", 0, JOptionPane.QUESTION_MESSAGE, null, opcja, 0);
        int wspolczynnik = tab[tab.length-1][0];
        int roz = -1;
        //wprowadzenie maski
        for(int i=0; i<tab.length-1; i++){
            roz = tab[i].length;
        }
        int maska[][] = new int[tab.length-1][roz];
        for(int i=0; i<tab.length-1; i++){
            for(int j=0; j<tab[i].length; j++)
            {
                maska[i][j] = tab[i][j];
            }
        }

        setSize(new Dimension(wejscie.getWidth(),wejscie.getHeight()));
        Color ci;
        double czerw, ziel, nieb;
        double Y, U=0, V=0, R, G, B , Yprim=0;
        Color tablica[][] = new Color[3][3];
        for(int i=1; i<wejscie.getWidth()-1; i++){
            for(int j=1; j<wejscie.getHeight()-1; j++)
            {
                czerw = 0;
                ziel = 0;
                nieb = 0;
                tablica[0][0] = new Color(wejscie.getRGB(i-1,j-1));
                tablica[0][1] = new Color(wejscie.getRGB(i-1,j));
                tablica[0][2] = new Color(wejscie.getRGB(i-1,j+1));
                tablica[1][0] = new Color(wejscie.getRGB(i,j-1));
                tablica[1][1] = new Color(wejscie.getRGB(i,j));
                tablica[1][2] = new Color(wejscie.getRGB(i,j+1));
                tablica[2][0] = new Color(wejscie.getRGB(i+1,j-1));
                tablica[2][1] = new Color(wejscie.getRGB(i+1,j));
                tablica[2][2] = new Color(wejscie.getRGB(i+1,j+1));
                for(int k=0;k<3;k++){
                    for(int l=0;l<3;l++){
                        if(opcjaWInt == 0)//rgb
                        {
                            czerw += maska[k][l] * tablica[k][l].getRed();
                            ziel += maska[k][l] * tablica[k][l].getGreen();
                            nieb += maska[k][l] * tablica[k][l].getBlue();
                        }
                        else
                        {
                            czerw = tablica[k][l].getRed();
                            ziel = tablica[k][l].getGreen();
                            nieb = tablica[k][l].getBlue();
                            Y = (int)((0.299 * czerw) + (0.587 * ziel) + (0.114 * nieb));
                            Yprim += Y * maska[k][l];
                        }
                    }
                }
                if(opcjaWInt == 0){
                    czerw /= wspolczynnik;
                    ziel /= wspolczynnik;
                    nieb /= wspolczynnik;
                }
                else
                {
                    Yprim /= wspolczynnik;
                    czerw = tablica[1][1].getRed();
                    ziel = tablica[1][1].getGreen();
                    nieb = tablica[1][1].getBlue();
                    Y = (int)((0.299 * czerw) + (0.587 * ziel) + (0.114 * nieb));
                    U = (int)((-0.147 * czerw) + (-0.289 * ziel) + (0.437 * nieb));
                    V = (int)((0.615 * czerw) + (-0.515 * ziel) + (-0.100 * nieb));
                    R = (int)((1.000*Yprim + 1.140*V));
                    G = (int)(1.000*Yprim + (-0.394*U) + (-0.587*V));
                    B = (int)(1.000*Yprim + 2.028*U);
                    czerw =R;
                    ziel = G;
                    nieb = B;
                    Yprim = 0;
                }
                if(czerw<0){
                    czerw = 0;
                }
                if(czerw>255){
                    czerw = 255;
                }
                if(ziel<0){
                    ziel = 0;
                }
                if(ziel>255){
                    ziel = 255;
                }
                if(nieb<0){
                    nieb = 0;
                }
                if(nieb>255){
                    nieb = 255;
                }
                bufferedImage.setRGB(i,j,new Color((int)czerw,(int)ziel,(int)nieb).getRGB());
            }
        }

        repaint();
    }

    public void filtrMedianowy(BufferedImage wejscie)
    {
        String[] opcja = {"RGB", "YUV"};
        int opcjaWInt = JOptionPane.showOptionDialog(null,"Wybierz model koloru, w których chcesz filtrować:"
                ,"Wybór modelu", 0, JOptionPane.QUESTION_MESSAGE, null, opcja, 0);
        setSize(new Dimension(wejscie.getWidth(),wejscie.getHeight()));
        Color ci;
        double czerw = 0, ziel = 0, nieb = 0;
        double Y, U=0, V=0, R, G, B , Yprim=0, Uprim=0, Vprim=0;
        Color tablica[][] = new Color[3][3];
        int mczerw[] = new int[9];
        int mziel[] = new int[9];
        int mnieb[] = new int[9];
        for(int i=1; i<wejscie.getWidth()-1; i++)
            for(int j=1; j<wejscie.getHeight()-1; j++)
            {
                tablica[0][0] = new Color(wejscie.getRGB(i-1,j-1));
                tablica[0][1] = new Color(wejscie.getRGB(i-1,j));
                tablica[0][2] = new Color(wejscie.getRGB(i-1,j+1));
                tablica[1][0] = new Color(wejscie.getRGB(i,j-1));
                tablica[1][1] = new Color(wejscie.getRGB(i,j));
                tablica[1][2] = new Color(wejscie.getRGB(i,j+1));
                tablica[2][0] = new Color(wejscie.getRGB(i+1,j-1));
                tablica[2][1] = new Color(wejscie.getRGB(i+1,j));
                tablica[2][2] = new Color(wejscie.getRGB(i+1,j+1));

                int t=0;
                for(int p=0; p<3; p++){
                    for( int o=0; o<3; o++){
                        mczerw[t]=tablica[p][o].getRed();
                        mziel[t]=tablica[p][o].getGreen();
                        mnieb[t]=tablica[p][o].getBlue();
                        t++;
                    }
                }
                Arrays.sort(mczerw);
                Arrays.sort(mziel);
                Arrays.sort(mnieb);
                if(opcjaWInt == 0){
                    czerw = mczerw[4];
                    ziel = mziel[4];
                    nieb = mnieb[4];
                }else{

                    czerw = mczerw[4];
                    ziel = mziel[4];
                    nieb = mnieb[4];
                    Y = (int)((0.299 * czerw) + (0.587 * ziel) + (0.114 * nieb));
                    U = (int)((-0.147 * czerw) + (-0.289 * ziel) + (0.437 * nieb));
                    V = (int)((0.615 * czerw) + (-0.515 * ziel) + (-0.100 * nieb));
                    R = (int)((1.000*Y + 1.140*V));
                    G = (int)(1.000*Y + (-0.394*U) + (-0.587*V));
                    B = (int)(1.000*Y + 2.028*U);
                    czerw = R;
                    ziel = G;
                    nieb = B;

                }
                if(czerw<0){
                    czerw = 0;
                }
                if(czerw>255){
                    czerw = 255;
                }
                if(ziel<0){
                    ziel = 0;
                }
                if(ziel>255){
                    ziel = 255;
                }
                if(nieb<0){
                    nieb = 0;
                }
                if(nieb>255){
                    nieb = 255;
                }
                bufferedImage.setRGB(i,j,new Color((int)czerw,(int)ziel,(int)nieb).getRGB());
            }
        repaint();
    }

    public void filtrProsty(BufferedImage wejscie)
    {
        String[] opcja = {"RGB", "YUV"};
        int opcjaWInt = JOptionPane.showOptionDialog(null,"Wybierz model koloru, w których chcesz filtrować:"
                ,"Wybór modelu", 0, JOptionPane.QUESTION_MESSAGE, null, opcja, 0);
        setSize(new Dimension(wejscie.getWidth(),wejscie.getHeight()));
        double czerw = 0, ziel = 0, nieb = 0;
        double Y, U=0, V=0, R, G, B , Yprim=0;
        for(int i=1; i<wejscie.getWidth()-1; i++)
            for(int j=1; j<wejscie.getHeight()-1; j++)
            {
                czerw = 0;
                ziel = 0;
                nieb = 0;
                Color ci1 = new Color(wejscie.getRGB(i,j));
                Color ci2 = new Color(wejscie.getRGB(i,j+1));
                Color ci3 = new Color(wejscie.getRGB(i+1,j));
                if(opcjaWInt == 0){
                    czerw = Math.sqrt(Math.pow(ci1.getRed()-ci2.getRed(),2)+Math.pow(ci1.getRed()-ci3.getRed(),2));
                    ziel = Math.sqrt(Math.pow(ci1.getGreen()-ci2.getGreen(),2)+Math.pow(ci1.getGreen()-ci3.getGreen(),2));
                    nieb = Math.sqrt(Math.pow(ci1.getBlue()-ci2.getBlue(),2)+Math.pow(ci1.getBlue()-ci3.getBlue(),2));
                }
                else
                {
                    Yprim =Math.sqrt(Math.pow(ci1.getRed()-ci2.getRed(),2)+Math.pow(ci1.getRed()-ci3.getRed(),2));
                    czerw = ci1.getRed();
                    ziel = ci1.getGreen();
                    nieb = ci1.getBlue();

                    Y = (int)((0.299 * czerw) + (0.587 * ziel) + (0.114 * nieb));
                    U = (int)((-0.147 * czerw) + (-0.289 * ziel) + (0.437 * nieb));
                    V = (int)((0.615 * czerw) + (-0.515 * ziel) + (-0.100 * nieb));
                    R = (int)((1.000*Yprim + 1.140*V));
                    G = (int)(1.000*Yprim + (-0.394*U) + (-0.587*V));
                    B = (int)(1.000*Yprim + 2.028*U);
                    czerw = R;
                    ziel = G;
                    nieb = B;

                }
                if(czerw<0){
                    czerw = 0;
                }
                if(czerw>255){
                    czerw = 255;
                }
                if(ziel<0){
                    ziel = 0;
                }
                if(ziel>255){
                    ziel = 255;
                }
                if(nieb<0){
                    nieb = 0;
                }
                if(nieb>255){
                    nieb = 255;
                }
                bufferedImage.setRGB(i,j,new Color((int)czerw,(int)ziel,(int)nieb).getRGB());
            }
        repaint();
    }

    public void filtrProstyZProgiem(BufferedImage wejscie)
    {
        String[] opcja = {"RGB", "YUV"};
        int opcjaWInt = JOptionPane.showOptionDialog(null,"Wybierz model koloru, w których chcesz filtrować:"
                ,"Wybór modelu", 0, JOptionPane.QUESTION_MESSAGE, null, opcja, 0);
        String wiad = JOptionPane.showInputDialog("Podaj próg od 0 do 255");
        int prog = Integer.valueOf(wiad);
        setSize(new Dimension(wejscie.getWidth(),wejscie.getHeight()));
        double czerw = 0, ziel = 0, nieb = 0;
        double Y, U=0, V=0, R, G, B , Yprim=0;
        for(int i=1; i<wejscie.getWidth()-1; i++)
            for(int j=1; j<wejscie.getHeight()-1; j++)
            {
                czerw = 0;
                ziel = 0;
                nieb = 0;
                Color ci1 = new Color(wejscie.getRGB(i,j));
                Color ci2 = new Color(wejscie.getRGB(i,j+1));
                Color ci3 = new Color(wejscie.getRGB(i+1,j));

                if(opcjaWInt == 0){
                    czerw = Math.sqrt(Math.pow(ci1.getRed()-ci2.getRed(),2)+Math.pow(ci1.getRed()-ci3.getRed(),2));
                    ziel = Math.sqrt(Math.pow(ci1.getGreen()-ci2.getGreen(),2)+Math.pow(ci1.getGreen()-ci3.getGreen(),2));
                    nieb = Math.sqrt(Math.pow(ci1.getBlue()-ci2.getBlue(),2)+Math.pow(ci1.getBlue()-ci3.getBlue(),2));
                }
                else
                {
                    Yprim =Math.sqrt(Math.pow(ci1.getRed()-ci2.getRed(),2)+Math.pow(ci1.getRed()-ci3.getRed(),2));
                    czerw = ci1.getRed();
                    ziel = ci1.getGreen();
                    nieb = ci1.getBlue();

                    Y = (int)((0.299 * czerw) + (0.587 * ziel) + (0.114 * nieb));
                    U = (int)((-0.147 * czerw) + (-0.289 * ziel) + (0.437 * nieb));
                    V = (int)((0.615 * czerw) + (-0.515 * ziel) + (-0.100 * nieb));
                    R = (int)((1.000*Yprim + 1.140*V));
                    G = (int)(1.000*Yprim + (-0.394*U) + (-0.587*V));
                    B = (int)(1.000*Yprim + 2.028*U);
                    czerw = R;
                    ziel = G;
                    nieb = B;

                }

                if(czerw<prog){
                    czerw = 0;
                }
                if(czerw>prog){
                    czerw = 255;
                }
                if(ziel<prog){
                    ziel = 0;
                }
                if(ziel>prog){
                    ziel = 255;
                }
                if(nieb<prog){
                    nieb = 0;
                }
                if(nieb>prog){
                    nieb = 255;
                }
                bufferedImage.setRGB(i,j,new Color((int)czerw,(int)ziel,(int)nieb).getRGB());
            }
        repaint();
    }

    @Override
    public void paintComponent(Graphics graphics)
    {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.drawImage(bufferedImage,0,0,this);
    }
}
