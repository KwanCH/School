class SortRute extends Rute{
    
    public SortRute(int rad, int kolonne, Labyrint labyrint){
        super(kolonne, rad, labyrint);
    }
    
    
    @Override
    public char tilTegn(){
        return '#';
    }
}