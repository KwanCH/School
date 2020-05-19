class Aapning extends HvitRute{

    public Aapning(int rad, int kolonne, Labyrint labyrint){
        super(kolonne, rad, labyrint);
    }

    @Override
    public char tilTegn(){
        return '.';
    }

    @Override
    public boolean aapning(){
        return true;
    }
}