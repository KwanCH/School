import javafx.scene.control.Button;
public abstract class Rute extends Button{
    protected int kolonne;
    protected int rad;
    Labyrint labyrint;
    Rute nord, sor, vest, ost;
    protected static Liste<String> utveier;
    

    public Rute(int rad, int kolonne, Labyrint labyrint){
        this.kolonne = kolonne;
        this.rad = rad;
        this.labyrint = labyrint;
    }


    static Rute lagRuter(char chars, int rad, int kolonne, Labyrint labyrint){
        if(chars == '#'){
            return new SortRute(rad, kolonne, labyrint);
        } else if(chars == '.'){
            if(rad == 0 || rad == labyrint.rader - 1 || kolonne == 0 || kolonne == labyrint.kolonne -1){
                return new Aapning(rad, kolonne, labyrint);
            }else{
                return new HvitRute(rad, kolonne, labyrint);
            }
        } else{
            throw new RuntimeException("Ukjent tegn: " + chars);
        }
    }

    public void gaa(String vei, Rute rute){
        if(!(hvitRute())){
            return;
        } else if(aapning()){
            vei += toString();
            labyrint.utveier.leggTil(vei);
        } else {
            vei += toString() + "---->";
            if(nord != rute){
                nord.gaa(vei,this);
            }
            if(sor != rute){
                sor.gaa(vei, this);
            }
            if(vest != rute){
                vest.gaa(vei,this);
            }
            if(ost != rute){
                ost.gaa(vei,this);
            }
        }
        
    }
    
    public Liste<String> finnUtvei(){
        utveier = new Lenkeliste<String>();
        String s = "";
        this.gaa(s,this);
        return utveier;
    }

    public abstract char tilTegn();

    @Override
    public String toString(){
        return "(" + kolonne + ", " + rad + ")";
    }


    public boolean aapning(){
        return false;
    }

    public boolean hvitRute(){
        return false;
    }


}  