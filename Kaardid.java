/**
 * Kaardid
 * Selle klassi abil loome kaardipaki, et ei peaks eraldi
 * meetodit tegema mängu klassidesse. Olulised meetodid uusKaardipakk(), mis teeb uue
 * kaardipaki, kaardiVäärtus, mis arvutab Blackjacki kontekstis parameetrina antud
 * kaardi väärtuse. valiKaart(), mis segab paki ja valib suvalise kaardi.
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Kaardid {


    private List<String> kaardipakk;
    private static String[] mastid = new String[]{"Risti", "Poti", "Ruudu", "Ärtu"};
    private static String[] väärtused = new String[]{"Äss", "Kuningas", "Emand", "Poiss", "10", "9", "8", "7", "6", "5", "4", "3", "2"};

    public static String[] getVäärtused() { return väärtused; }

    public Kaardid() {
        kaardipakk = new ArrayList<>();
    }

    public void uusKaardipakk(){
        kaardipakk.clear();
        for (String s : mastid){
            for (String n : väärtused){
                kaardipakk.add(s + " " + n);
            }
        }
        Collections.shuffle(kaardipakk);
    }

    public int kaardiVäärtus(String kaart){
        String[] tükid = kaart.split(" ");
        String väärtus = tükid[1];

        switch(väärtus) {
            case "Äss":
                return 11;
            case "Kuningas":
            case "Emand":
            case "Poiss":
                return 10;
            default:
                return Integer.parseInt(väärtus);
        }
    }

    public String valiKaart(){
        // remove() tagastab ka kaardi väärtuse
        String valitudKaart = kaardipakk.remove(0);
        return valitudKaart;
    }
}
