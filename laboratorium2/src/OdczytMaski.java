import java.util.*;
import java.io.*;

public class OdczytMaski
{
    public static int[][] uploadMaskFilter(String nazwa)
    {
        Scanner scanner = null;
        int tab[][] = new int[0][0];
        try {
            scanner =new Scanner(new File(nazwa));
        }catch(java.io.FileNotFoundException e){
            System.out.println("Nie znaleziono pliku!");
        }

        try {
            //String r1=scanner.nextLine();
            int lW= scanner.nextInt();
            int lK= scanner.nextInt();
            int Wspolczynnik = scanner.nextInt();
            tab= new int[lW+1][lK];

            for (int i = 0; i <tab.length-1; i++)
            {
                for (int j = 0; j <tab[i].length; j++)
                {
                    tab[i][j]= scanner.nextInt();

                }

            }
            tab[lW][0] = Wspolczynnik;
            scanner.close();
        }catch (java.lang.RuntimeException e){
            System.out.println("Blad odczytu z pliku!");
            e.printStackTrace();

        }
        return tab;
    }


    public String wypiszTAb(int tab[][])
    {
        for (int i = 0; i <tab.length; i++)
        {
            for (int j = 0; j <tab[i].length; j++)
            {

                System.out.print(tab[i][j]+"   ");

            }
            System.out.print("\n");
        }
        return "Ola";
    }
}
