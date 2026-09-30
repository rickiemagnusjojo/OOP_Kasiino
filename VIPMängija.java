/**
 * VIPMängija
 * Nagu nimest näha, siis VIP versioon mängija klassist. VIP saab lisaks võidule
 * ka boonuse võimaluse, mis kahekordistab võidu (kasSaabBoonuse())
 */
public class VIPMängija extends Mängija{


    public VIPMängija(String nimi, int sünniaasta, int bilanss) {
        super(nimi,sünniaasta,bilanss);
    }

    public boolean kasSaabBoonuse(){
        return Math.random() < 0.5; // 50% võimalus saada boonust
    }

    @Override
    public void suurendaBilanssi(int voit){
        boolean boonus = kasSaabBoonuse();
        if (boonus == true){
            System.out.println("Said boonuse! Võit kahekordistatakse " + (voit * 2) + " peale!");
            super.suurendaBilanssi(voit * 2);
        }
        else{
            System.out.println("Seekord boonust ei saanud...");
            super.suurendaBilanssi(voit);
        }

    }

    @Override
    public String toString(){
        return super.toString() + "; Staatus: [*VIP*]";
    }
}
