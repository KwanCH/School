import java.util.Iterator;
class Lenkeliste<T> implements Liste<T>{
    // Her ligger klassen for nodene som skal holde kontroll på elementene i listen
    class Node{
        Node neste = null;
        T data;
        Node(T x){
            data = x;
        }
    }

    class LenkelisteIterator implements Iterator<T>{
        private int pos;
        private Lenkeliste<T> liste;
        
        public LenkelisteIterator(Lenkeliste<T> liste){
            this.liste = liste;
        }

        @Override
        public boolean hasNext(){
            return pos < liste.stoerrelse();
        }

        @Override
        public T next(){
            return liste.hent(pos++);
        }
    }
    public Iterator<T> iterator(){
        return new LenkelisteIterator(this);
    }

    Node start;    
// her sjekker jeg størrelsen på listen
    public int stoerrelse(){
        Node p = start;
        int n = 0;
        while (p != null){
            n++;
            p = p.neste;
        }
        return n;
    }
// Her legger jeg til elementer på gitt posisjon
// Dersom elmementet skal legges på en godkjent posisjon så utføres en av if testene
// iforhold til hvor elementet skal være hen, hvis ikke så kastes metoden    
    public void leggTil(int pos, T x){
        if (pos >= 0 && pos <= stoerrelse()){
            Node ny = new Node(x);
            if (pos == 0){
                ny.neste = start;
                start = ny;
            }
            else if (pos == stoerrelse()-1){
                this.leggTil(x);
            }
            else{
                Node p = start;
                for(int i = 0; i < pos-1; i++){
                    p = p.neste;
                }
                ny.neste = p.neste;
                p.neste = ny;
        }
        }
        else{
            throw new UgyldigListeIndeks(pos-1);
        }
    }
// Her legger jeg til elementer bakerst
    public void leggTil(T x){
        Node ny = new Node(x);
        if (start == null){
            start = ny;
        }
        else{
            Node p = start;
            while(p.neste != null){
                p = p.neste;
            }
            p.neste = ny;
        }
    }
    // Her overskriver jeg elementer og passer på at posisjonen er innenfor området  
    public void sett(int pos, T x) {
        if (start == null){
            throw new UgyldigListeIndeks(pos-1);
        }
        if (pos < 0){
            throw new UgyldigListeIndeks(pos-1);
        }
        if (pos >= this.stoerrelse()){
            throw new UgyldigListeIndeks(pos-1);
        }
        Node p = start;
        for (int i = 0; i < pos; i++){
            p = p.neste;
        }
        p.data = x;             
    }
// her henter jeg et element fra en gitt posisjon
    public T hent(int pos) {
        if (start == null){
            throw new UgyldigListeIndeks(pos-1);
        }
        if (pos < 0){
            throw new UgyldigListeIndeks(pos-1);
        }
        if (pos >= this.stoerrelse()){
            throw new UgyldigListeIndeks(pos-1);
        }
        Node p = start;
        for(int i = 0; i < pos; i++){
            p = p.neste;
        }
        return p.data;
    }
    // her fjerner jeg elementer fra en gitt posisjon og bruker en tilsvarende måte for if testene som i leggtil metoden
    public T fjern(int pos){
        if (start == null){
            throw new UgyldigListeIndeks(pos-1);
        }
        if (pos < 0){
            throw new UgyldigListeIndeks(pos-1);
        }
        if (pos >= this.stoerrelse()){
            throw new UgyldigListeIndeks(pos-1);
        }
        Node p = start;
        if (pos == 0){
            return this.fjern();
        }
        else if (pos == stoerrelse()-1){
            for (int i = 0; i < pos-1; i++){
                p = p.neste;
            }
            Node q = p.neste;
            p.neste = null;
            return q.data;
        }
        else{
            for(int i = 0; i < pos-1; i++){
                p = p.neste;
            }
            Node q = p.neste;
            p.neste = p.neste.neste;
            return q.data;  
        }
    }
    // her fjerner jeg på slutten av listen
    public T fjern(){
        if (start == null){
            throw new UgyldigListeIndeks(-1);
        }
        Node p = start;
        if (p.neste == null){
            start = null;
            return p.data;
        }
        else{
            start = p.neste;
            return p.data;
        }
        
        
    }
}