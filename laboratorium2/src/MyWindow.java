import java.awt.*;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MyWindow extends JFrame implements ActionListener
{
    GraphicsPanel left = new GraphicsPanel();
    GraphicsPanel right = new GraphicsPanel();

    MenuWindow menu = new MenuWindow();

    WindowDialogBrightness dialogBrightness;
    WindowDialogContrast dialogContrast;
    String pathToFile;

    public MyWindow() throws HeadlessException
    {
        super("Marcin Kowalczyk - Histogramy Grafika Komputerowa Laboratorium 2");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new FlowLayout(FlowLayout.CENTER, 2, 2));
        setJMenuBar(menu);
        add(left);
        add(right);
        setListenerEvents();
        matchToContent();
        setVisible(true);
    }

    private void setListenerEvents()
    {
        menu.openFile.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));
        menu.openFile.addActionListener(this);
        menu.saveFile.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
        menu.saveFile.addActionListener(this);
        menu.finish.setAccelerator(KeyStroke.getKeyStroke("ctrl X"));
        menu.finish.addActionListener(this);
        menu.leftClear.setAccelerator(KeyStroke.getKeyStroke("ctrl D"));
        menu.leftClear.addActionListener(this);
        menu.leftCopy.setAccelerator(KeyStroke.getKeyStroke("ctrl C"));
        menu.leftCopy.addActionListener(this);

        menu.negation.addActionListener(this);
        menu.YUW.addActionListener(this);
        menu.Red.addActionListener(this);
        menu.Green.addActionListener(this);
        menu.Blue.addActionListener(this);
        menu.average.addActionListener(this);
        menu.brightnessAndContrast.addActionListener(this);
        menu.brightness.addActionListener(this);
        menu.contrast.addActionListener(this);
        menu.rangeBrightness.addActionListener(this);
        menu.rangeBrightness.addActionListener(this);

        menu.histogramChartP.addActionListener(this);
        menu.distributorRight.addActionListener(this);
        menu.distributorComponentYRight.addActionListener(this);

        menu.histogramChart.addActionListener(this);
        //menu.histogramDyskretnyR.addActionListener(this);
        menu.dystrybuanta.addActionListener(this);
        menu.distributorEvenly.addActionListener(this);
        menu.distributorDark.addActionListener(this);
        menu.distributorLight.addActionListener(this);
        menu.distributorContrasting.addActionListener(this);
        menu.distributorComponentY.addActionListener(this);
        menu.distributorComponentPainting.addActionListener(this);
        menu.distributorComponentYDark.addActionListener(this);
        menu.distributorComponentYLight.addActionListener(this);
        menu.distributorComponentYContrasting.addActionListener(this);

        menu.filtry.addActionListener(this);
        menu.wczytajMaske.addActionListener(this);
        menu.filtrMedianowy.addActionListener(this);
        menu.gradientProsty.addActionListener(this);
        menu.gradientProstyZProgiem.addActionListener(this);

        menu.rightClear.setAccelerator(KeyStroke.getKeyStroke("ctrl H"));
        menu.rightClear.addActionListener(this);
        menu.authorDescription.setAccelerator(KeyStroke.getKeyStroke("ctrl T"));
        menu.authorDescription.addActionListener(this);
        menu.programDescription.setAccelerator(KeyStroke.getKeyStroke("ctrl P"));
        menu.programDescription.addActionListener(this);
    }

    //Obs??uga Zdarze??
    @Override
    public void actionPerformed(ActionEvent ev)
    {
        String label = ev.getActionCommand();
        Object source = ev.getSource();
        if(label.equals("Otw??rz Plik"))
        {
            openFile();
        }
        else if(label.equals("Zapisz Plik"))
        {
            saveFile();
        }
        else if(label.equals("Zako??cz"))
        {
            int answer = JOptionPane.showConfirmDialog(null, "Czy napewno chcesz wyj???? z programu?", "Pytanie", JOptionPane.YES_NO_CANCEL_OPTION);//ta metoda zwraca int
            if(answer == JOptionPane.YES_OPTION)
                dispose();
            else if(answer == JOptionPane.NO_OPTION)
                JOptionPane.showMessageDialog(null, "Nie opuszczasz programu! Tak trzyma??! :)");
            else if(answer == JOptionPane.CLOSED_OPTION)
                JOptionPane.showConfirmDialog(null, "Opuszczasz program :(", "Tytu??", JOptionPane.WARNING_MESSAGE);
        }
        else if(label.equals("Wyczy???? Lewy"))
        {
            left.clear();
        }
        else if(label.equals("Kopiuj"))
        {
            int width = right.bufferedImage.getWidth();
            int height = right.bufferedImage.getHeight();
            right.copy(left.bufferedImage);
            if(width != right.bufferedImage.getWidth() || height != right.bufferedImage.getHeight())
            {
                matchToContent();
            }
        }
        else if(label.equals("Wyczy???? Prawy"))
        {
            right.clear();
        }
        else if(label.equals("Model YUW"))
        {
            int w = right.bufferedImage.getWidth();
            int h = right.bufferedImage.getHeight();
            right.modelYUV(left.bufferedImage);
            matchSize(right, w, h);
        }
        else if(label.equals("Negacja"))
        {
            int w = right.bufferedImage.getWidth();
            int h = right.bufferedImage.getHeight();
            right.negation(left.bufferedImage);
            matchSize(right, w, h);
        }
        else if(label.equals("R"))
        {
            int w = right.bufferedImage.getWidth();
            int h = right.bufferedImage.getHeight();
            right.oneOfThem(left.bufferedImage, 1);
            matchSize(right, w, h);
        }
        else if(label.equals("G"))
        {
            int w = right.bufferedImage.getWidth();
            int h = right.bufferedImage.getHeight();
            right.oneOfThem(left.bufferedImage, 2);
            matchSize(right, w, h);
        }
        else if(label.equals("B"))
        {
            int w = right.bufferedImage.getWidth();
            int h = right.bufferedImage.getHeight();
            right.oneOfThem(left.bufferedImage, 3);
            matchSize(right, w, h);
        }
        else if(label.equals("Srednia"))
        {
            int w = right.bufferedImage.getWidth();
            int h = right.bufferedImage.getHeight();
            right.avg(left.bufferedImage);
            matchSize(right, w, h);
        }
        else if(label.equals("Jasno????"))
        {
            if(dialogBrightness == null){
                dialogBrightness = new WindowDialogBrightness(this);
            }

            dialogBrightness.setVisible(true);
            dialogBrightness.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            if(dialogBrightness.getOK()){
                int w = right.bufferedImage.getWidth();
                int h = right.bufferedImage.getHeight();
                right.brightness(left.bufferedImage, dialogBrightness.getValue());
                matchSize(right, w, h);
            }
        }
        else if(label.equals("Kontrast"))
        {
            if(dialogContrast == null)
            {
                dialogContrast = new WindowDialogContrast(this);
            }
            dialogContrast.setVisible(true);
            dialogContrast.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            if(dialogContrast.getOK())
            {
                int w = right.bufferedImage.getWidth();
                int h = right.bufferedImage.getHeight();
                right.contrast(left.bufferedImage, dialogContrast.getValue());

                matchSize(right, w, h);
            }
        }
        else if(label.equals("Zakres Jasno??ci"))
        {
            int w = right.bufferedImage.getWidth();
            int h = right.bufferedImage.getHeight();
            right.rangeOfBrightness(left.bufferedImage);
            matchSize(right,w,h);
        }
        else if(label.equals("Wyczy???? prawy"))
        {
            right.clear();
        }
        else if(label.equals("Histogram")){
            left.histogramDyskretny(left.bufferedImage);
        }
        else if(label.equals("Histogram prawy")){
            right.histogramDyskretny(right.bufferedImage);
        }
        //else if(label.equals("Histogram dyskretny")){
        //    lewy.histogramDyskretny(lewy.plotno);
        //}
        else if(label.equals("Dystrybuanta")){
            left.dystrybuanta(left.bufferedImage);
        }
        else if(label.equals("Dystrybuanta prawy")){
            right.dystrybuanta(right.bufferedImage);
        }
        else if(label.equals("Dystrybuanta wyr??wnana")){
            int w = right.bufferedImage.getWidth();
            int h = right.bufferedImage.getHeight();

            right.dystrybuantaWyrownana(left.bufferedImage);

            matchSize(right, w, h);
        }
        else if(label.equals("Dystrybuanta ??ciemniaj??ca")){
            int w = right.bufferedImage.getWidth();
            int h = right.bufferedImage.getHeight();

            right.dystrybuantaSciemniajaca(left.bufferedImage);

            matchSize(right, w, h);
        }
        else if(label.equals("Dystrybuanta rozja??niaj??ca")){
            int w = right.bufferedImage.getWidth();
            int h = right.bufferedImage.getHeight();

            right.dystrybuantaRozjasniajaca(left.bufferedImage);

            matchSize(right, w, h);
        }
        else if(label.equals("Dystrybuanta kontrastuj??ca")){
            int w = right.bufferedImage.getWidth();
            int h = right.bufferedImage.getHeight();

            right.dystrybuantaKontrastujaca(left.bufferedImage);

            matchSize(right, w, h);
        }
        else if(label.equals("Dystrybuanta sk??adowa Y")){
            right.dystrybuantaSkladowaY(left.bufferedImage);
        }
        else if(label.equals("Dystrybuanta sk??adowa Y prawy")){
            right.dystrybuantaSkladowaY(right.bufferedImage);
        }
        else if(label.equals("Dystrybuanta sk??adowa Y obraz")){
            int w = right.bufferedImage.getWidth();
            int h = right.bufferedImage.getHeight();

            right.dystrybuantaSkladowaYObraz(left.bufferedImage, "wyr??wnana");

            matchSize(right, w, h);
        }
        else if(label.equals("Dystrybuanta sk??adowa Y ??ciemniaj??ca")){
            int w = right.bufferedImage.getWidth();
            int h = right.bufferedImage.getHeight();

            right.dystrybuantaSkladowaYObraz(left.bufferedImage, "??ciemniaj??ca");

            matchSize(right, w, h);
        }
        else if(label.equals("Dystrybuanta sk??adowa Y rozja??niaj??ca")){
            int w = right.bufferedImage.getWidth();
            int h = right.bufferedImage.getHeight();

            right.dystrybuantaSkladowaYObraz(left.bufferedImage, "rozja??niaj??ca");

            matchSize(right, w, h);
        }
        else if(label.equals("Dystrybuanta sk??adowa Y kontrastuj??ca")){
            int w = right.bufferedImage.getWidth();
            int h = right.bufferedImage.getHeight();

            right.dystrybuantaSkladowaYObraz(left.bufferedImage, "kontrastuj??ca");

            matchSize(right, w, h);
        }
        else if(label.equals("Wczytaj Maske"))
        {
            int w = right.bufferedImage.getWidth();
            int h = right.bufferedImage.getHeight();

            right.wczytajMaske(left.bufferedImage, openMask());
            if(w != right.bufferedImage.getWidth() || h != right.bufferedImage.getHeight())
                matchToContent();
        }
        else if(label.equals("Filtr Medianowy"))
        {
            int w = right.bufferedImage.getWidth();
            int h = right.bufferedImage.getHeight();

            right.filtrMedianowy(left.bufferedImage);
            if(w != right.bufferedImage.getWidth() || h != right.bufferedImage.getHeight())
                matchToContent();
        }
        else if(label.equals("Gradient prosty"))
        {
            int w = right.bufferedImage.getWidth();
            int h = right.bufferedImage.getHeight();

            right.filtrProsty(left.bufferedImage);
            if(w != right.bufferedImage.getWidth() || h != right.bufferedImage.getHeight())
                matchToContent();
        }
        else if(label.equals("Gradient prosty z progiem"))
        {
            int w = right.bufferedImage.getWidth();
            int h = right.bufferedImage.getHeight();

            right.filtrProstyZProgiem(left.bufferedImage);
            if(w != right.bufferedImage.getWidth() || h != right.bufferedImage.getHeight())
                matchToContent();
        }
        else if(label.equals("O Programie"))
        {
            JOptionPane.showMessageDialog(this, "Program okienkowy demonstruje wykorzystanie biblioteki \n javax.swing i java.awt do stworzenia prostego panelu do wstawiania zdj???? z nadawaniem im filtr??w", "Opis Aplikacji", JOptionPane.INFORMATION_MESSAGE );
        }

        else if(label.equals("O Autorze"))
        {
            JOptionPane.showMessageDialog(this, "Autor : Marcin Kowalczyk - wi??cej informacji na stronie internetowej gingercoder.com", "Opis Autora", JOptionPane.INFORMATION_MESSAGE );
        }
    }

    //akcja w przypadku wyboru "otw??rz plik z menu"
    private int[][] openMask()
    {
        //okno dialogowe do wyboru pliku graficznego
        JFileChooser otworz= new JFileChooser();
        //zdefiniowanie filtra dla wybranych typu plik??w
        FileNameExtensionFilter filtr = new FileNameExtensionFilter("txt", "txt");
        //ustawienie filtra dla JFileChooser
        otworz.setFileFilter(filtr);
        //wy??wietlenie okna dialogowego wyboru pliku
        int wynik = otworz.showOpenDialog(this);
        //analiza rezultatu zwr??conego przez okno dialogowe
        int tab[][] = new int[0][0];
        if (wynik == JFileChooser.APPROVE_OPTION)
        {
            //wy??uskanie ??cie??ki do wybranego pliku
            pathToFile = otworz.getSelectedFile().getPath();
            OdczytMaski od = new OdczytMaski();
            tab = od.uploadMaskFilter(pathToFile);
        }
        return tab;
    }

    private void openFile()
    {
        JFileChooser open = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & BMP & PNG Images","jpg","bmp","png");
        open.setFileFilter(filter);
        int score = open.showOpenDialog(this);
        if(score == JFileChooser.APPROVE_OPTION)
        {
            pathToFile = open.getSelectedFile().getPath();
            int width = left.bufferedImage.getWidth();
            int height = left.bufferedImage.getHeight();
            left.uploadGraphicsFile(pathToFile);
            if(width != left.bufferedImage.getWidth() || height != left.bufferedImage.getHeight())
                matchToContent();
        }
    }

    private void saveFile()
    {
        JFileChooser save;
        if(pathToFile != null)
        {
            save = new JFileChooser(pathToFile);
        }
        else {
            save = new JFileChooser();
        }
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & BMP & PNG Images","jpg","bmp","png");
        save.setFileFilter(filter);
        int score = save.showSaveDialog(this);
        if(score == JFileChooser.APPROVE_OPTION)
        {
            pathToFile = save.getSelectedFile().getPath();
            right.saveGraphicsFile(pathToFile);
        }
    }

    public void matchSize(GraphicsPanel input, int w, int h){
        if(w != input.bufferedImage.getWidth() || h != input.bufferedImage.getHeight()){
            if(input.bufferedImage.getWidth()>=600 || input.bufferedImage.getHeight()>=600){
                input.setPreferredSize(new Dimension(600, 600));
                input.setMaximumSize(new Dimension(600, 600));
            }
            matchToContent();
        }
    }

    private void matchToContent()
    {
        pack();
        setLocationRelativeTo(null);
    }
}
