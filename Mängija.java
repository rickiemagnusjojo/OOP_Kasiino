/**
 * Mängija
 * Eesmärk: mängija väärtuste määramine ja nende muutmine,
 * mängija info väljastamise määramine, mida on näha ka meetodi nimedest.
 *
 */
public class Mängija {

    private String nimi;
    private int sünniaasta;
    private int bilanss;
    private int võidud;
    private int kaotused;

    public Mängija(String nimi, int sünniaasta, int bilanss) {
        this.nimi = nimi;
        this.sünniaasta = sünniaasta;
        this.bilanss = bilanss;
        this.võidud = 0;
        this.kaotused = 0;
    }

    public String getNimi() {
        return nimi;
    }

    public int getSünniaasta() {
        return sünniaasta;
    }

    public int getBilanss() {
        return bilanss;
    }


    public int getVõidud() {
        return võidud;
    }

    public int getKaotused() {
        return kaotused;
    }


    public void suurendaBilanssi(int võit){

        bilanss += võit;
        võidud += võit;
    }

    public void vähendaBilanssi(int kaotus){
        bilanss -= kaotus;
        kaotused += kaotus;
    }


    public String toString(){
        return "Mängija nimi: " + nimi + ", vanus: " + (2026-sünniaasta) +
                ", võidud: " + võidud + " ühikut, kaotused: " + kaotused + " ühikut. Bilanss: " +
                bilanss;
    }
}
