class HvitRute extends Rute{

    public HvitRute(int rad, int kolonne, Labyrint labyrint){
        super(kolonne, rad, labyrint);
    }

    @Override
    public char tilTegn(){
        return '.';
    }
    
    @Override
    public boolean hvitRute(){
        return true;
    }
}