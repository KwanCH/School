import java.io.*;
import java.util.*;

public class Labyrint{
    public Rute[][] ruter;
    int kolonne, rader;
    Lenkeliste<String> utveier;

    private Labyrint(Rute[][] ruter, int rader, int kolonne){
        this.ruter = ruter;
        this.kolonne = kolonne;
        this.rader = rader;
    }

    static Labyrint lesFraFil(File fil) throws FileNotFoundException{
        Scanner scanner = new Scanner(fil);
        String [] str = scanner.nextLine().split(" ");
        int antR = Integer.parseInt(str[0]);
        int antK = Integer.parseInt(str[1]);
        Rute[][] ruter = new Rute[antR][antK];
        Labyrint labyrint = new Labyrint(ruter, antR, antK);
        //For-løkke som setter in ulike type ruter-objekter i forhold til tegn innlest i filen i rute-arrayet.
        for(int rad = 0; rad < antR; rad++){
            char [] chars = scanner.nextLine().toCharArray();

            for(int kol = 0; kol < antK; kol++){
                ruter[rad][kol] = Rute.lagRuter(chars[kol], rad, kol, labyrint);
            }
        }

        for(int rad = 1; rad < antR-1; rad++){
            for(int kol = 1; kol < antK-1; kol++){
                ruter[rad][kol].nord = ruter[rad-1][kol];
                ruter[rad][kol].sor = ruter[rad+1][kol];
                ruter[rad][kol].vest = ruter[rad][kol-1];
                ruter[rad][kol].ost = ruter[rad][kol+1];
            }
        }
        System.out.println(labyrint.toString());
        scanner.close();
        return labyrint;

    }

    public Liste<String> finnUtveiFra(int kol, int rad){
        utveier = new Lenkeliste<String>();
        ruter[rad][kol].finnUtvei();
        return utveier;
    }

    public Rute[][] getBrett(){
        return ruter;
    }

    public int getKol(){
        return kolonne;
    }

    public int getRad(){
        return rader;
    }
    public Liste<String> getUtvei(){
        return utveier;
    }


    @Override
    public String toString(){
        String brett = "";
        for(Rute[] rad : ruter){
            for(Rute rute : rad){
                brett += rute.tilTegn();
            }
            brett += "\n";
        }
        return brett;
    }
}