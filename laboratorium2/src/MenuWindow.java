import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.JSeparator;

public class MenuWindow extends JMenuBar
{
    JMenu file = new JMenu("Plik");
    JMenuItem openFile = new JMenuItem("Otwórz Plik");
    JMenuItem saveFile = new JMenuItem("Zapisz Plik");
    JMenuItem finish = new JMenuItem("Zakończ");

    JMenuItem leftClear = new JMenuItem("Wyczyść Lewy");
    JMenuItem leftCopy = new JMenuItem("Kopiuj Lewy");

    JMenuItem rightClear = new JMenuItem("Wyczyść Prawy");

    JMenu RGB = new JMenu("Szaroodcieniowe Warianty");
    JMenuItem YUW = new JMenuItem("Model YUW");
    JMenuItem Red = new JMenuItem("R");
    JMenuItem Green = new JMenuItem("G");
    JMenuItem Blue = new JMenuItem("B");
    JMenuItem average = new JMenuItem("Srednia");

    JMenu changedImage = new JMenu("Przekształcenia");
    JMenu brightnessAndContrast = new JMenu("Jasność i kontrast");
    JMenuItem brightness = new JMenuItem("Jasność");
    JMenuItem contrast = new JMenuItem("Kontrast");
    JMenuItem negation = new JMenuItem("Negacja");
    JMenuItem rangeBrightness = new JMenuItem("Zakres Jasności");

    JMenu histogram = new JMenu("Histogram");
    JMenuItem histogramChart = new JMenuItem("Histogram");
    //JMenuItem histogramDyskretnyR = new JMenuItem("Histogram dyskretny");
    JMenuItem dystrybuanta = new JMenuItem("Dystrybuanta");

    JMenuItem distributorEvenly = new JMenuItem("Dystrybuanta wyrównana");
    JMenuItem distributorDark = new JMenuItem("Dystrybuanta ściemniająca");
    JMenuItem distributorLight = new JMenuItem("Dystrybuanta rozjaśniająca");
    JMenuItem distributorContrasting = new JMenuItem("Dystrybuanta kontrastująca");

    JMenuItem distributorComponentY = new JMenuItem("Dystrybuanta składowa Y");
    JMenuItem distributorComponentPainting = new JMenuItem("Dystrybuanta składowa Y obraz");
    JMenuItem distributorComponentYDark = new JMenuItem("Dystrybuanta składowa Y ściemniająca");
    JMenuItem distributorComponentYLight = new JMenuItem("Dystrybuanta składowa Y rozjaśniająca");
    JMenuItem distributorComponentYContrasting = new JMenuItem("Dystrybuanta składowa Y kontrastująca");

    JMenuItem histogramChartP = new JMenuItem("Histogram prawy");
    JMenuItem distributorRight = new JMenuItem("Dystrybuanta prawy");
    JMenuItem distributorComponentYRight = new JMenuItem("Dystrybuanta składowa Y prawy");

    JMenu filtry = new JMenu("Filtry");
    JMenuItem wczytajMaske = new JMenuItem("Wczytaj Maske");
    JMenuItem filtrMedianowy = new JMenuItem("Filtr Medianowy");
    JMenuItem gradientProsty = new JMenuItem("Gradient prosty");
    JMenuItem gradientProstyZProgiem = new JMenuItem("Gradient prosty z progiem");

    JMenu description = new JMenu("Opis");
    JMenuItem authorDescription = new JMenuItem("O Autorze");
    JMenuItem programDescription = new JMenuItem("O Programie");

    public MenuWindow(){
        file.add(openFile);
        file.add(saveFile);
        file.add(leftClear);
        file.add(leftCopy);
        file.add(rightClear);
        file.add(new JSeparator());
        file.add(finish);
        add(file);

        RGB.add(Red);
        RGB.add(Green);
        RGB.add(Blue);
        RGB.add(average);
        RGB.add(YUW);
        add(RGB);

        changedImage.add(negation);
        changedImage.add(brightnessAndContrast);
        brightnessAndContrast.add(brightness);
        brightnessAndContrast.add(contrast);
        changedImage.add(rangeBrightness);
        add(changedImage);

        histogram.add(histogramChart);
        //histogram.add(histogramDyskretnyR);
        histogram.add(dystrybuanta);
        histogram.add(new JSeparator());
        histogram.add(distributorEvenly);
        histogram.add(distributorDark);
        histogram.add(distributorLight);
        histogram.add(distributorContrasting);
        histogram.add(new JSeparator());
        histogram.add(distributorComponentY);
        histogram.add(distributorComponentPainting);
        histogram.add(distributorComponentYDark);
        histogram.add(distributorComponentYLight);
        histogram.add(distributorComponentYContrasting);
        add(histogram);

        filtry.add(wczytajMaske);
        filtry.add(filtrMedianowy);
        filtry.add(gradientProsty);
        filtry.add(gradientProstyZProgiem);
        add(filtry);

        description.add(authorDescription);
        description.add(programDescription);
        add(description);
    }
}
