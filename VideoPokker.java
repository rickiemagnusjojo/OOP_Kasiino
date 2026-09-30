/**
 * VideoPokker
 * Selle mängu jaoks on rohkelt õnne vaja :D
 * Mängija saab viis kaarti, võib neist osa välja vahetada ning võidab sõltuvalt
 * lõplikust kaartide kombinatsioonist vastavalt võidutabelile.
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.Arrays;
import java.util.Scanner;

public class VideoPokker extends Mäng {

    private Kaardid kaardipakk = new Kaardid();

    @Override
    public void mängi(Mängija mängija, int bet) {

        kaardipakk.uusKaardipakk();
        List<String> kaardid = new ArrayList<>();
        System.out.print("Sinu kaardid |");
        for (int i = 0; i < 5; i++) {
            String kaart = kaardipakk.valiKaart();
            System.out.print("| " + kaart + " ");
            kaardid.add(kaart);
        }

        System.out.println("\nVali, millised kaardid välja vahetad. (1 2 3 4 5)");
        Scanner sc4 = new Scanner(System.in);
        Set<String> valikud = Set.of(sc4.nextLine().split(" "));
        for (String valik : valikud) {
            try {
                int i = Integer.parseInt(valik); // try blockis juhul kui parseInt annaks veateate
                if (i >= 1 && i <= 5) { kaardid.set(i-1, kaardipakk.valiKaart()); }
            } catch (Exception e) {}
        }

        System.out.print("Sinu kaardid |");
        for (String kaart : kaardid) { System.out.print("| " + kaart + " "); }
        System.out.println();

        int võit = kontrolliKaarte(kaardid);
        switch (võit) {
            case 800:
                System.out.print("Kuninglik mastirida! ");
                break;
            case 50:
                System.out.print("Mastirida! ");
                break;
            case 25:
                System.out.print("Nelik! ");
                break;
            case 9:
                System.out.print("Maja! ");
                break;
            case 6:
                System.out.print("Mast! ");
                break;
            case 4:
                System.out.print("Rida! ");
                break;
            case 3:
                System.out.print("Kolmik! ");
                break;
            case 2:
                System.out.print("Kaks paari! ");
                break;
            case 1:
                System.out.print("Paar poissi või parem! ");
                break;
        }
        if (võit == 0) {
            System.out.println("Kaotasid " + bet + " ühikut.");
            mängija.vähendaBilanssi(bet);
        }
        else if (võit == 1) { System.out.println("Said raha tagasi."); }
        else {
            int võidusumma = (võit - 1) * bet;
            System.out.println("Võitsid " + võidusumma + " ühikut!");
            mängija.suurendaBilanssi(võidusumma);
        }
    }

    private int kontrolliKaarte(List<String> kaardid) {
        List<String> mastid = new ArrayList<>();
        List<String> väärtused = new ArrayList<>();
        for (String kaart : kaardid) {
            String[] osad = kaart.split(" ");
            mastid.add(osad[0]);
            väärtused.add(osad[1]);
        }
        boolean onMast = Set.copyOf(mastid).size() == 1;
        Set<String> erinevadVäärtused = Set.copyOf(väärtused);
        int erinevateVäärtusteArv = erinevadVäärtused.size();
        boolean onRida = erinevateVäärtusteArv == 5 && kasOnRida(väärtused);
        if (onMast) {
            if (onRida) {
                if (väärtused.contains("Äss") && väärtused.contains("10")) return 800;
                return 50;
            }
            return 6;
        }
        if (onRida) return 4;
        int rohkeimSamasugusi = 0;
        switch (erinevateVäärtusteArv) {
            case 2:
                for (String väärtus : erinevadVäärtused) {
                    int samasugusi = Collections.frequency(väärtused, väärtus);
                    if (samasugusi > rohkeimSamasugusi) rohkeimSamasugusi = samasugusi;
                }
                if (rohkeimSamasugusi == 4) return 25;
                return 9;
            case 3:
                for (String väärtus : erinevadVäärtused) {
                    int samasugusi = Collections.frequency(väärtused, väärtus);
                    if (samasugusi > rohkeimSamasugusi) rohkeimSamasugusi = samasugusi;
                }
                if (rohkeimSamasugusi == 3) return 3;
                return 2;
            case 4:
                String esinevaimVäärtus = "";
                for (String väärtus : erinevadVäärtused) {
                    int samasugusi = Collections.frequency(väärtused, väärtus);
                    if (samasugusi > rohkeimSamasugusi) {
                        rohkeimSamasugusi = samasugusi;
                        esinevaimVäärtus = väärtus;
                    }
                }
                if (List.of("Äss", "Kuningas", "Emand", "Poiss").contains(esinevaimVäärtus)) return 1;
            default: return 0;
        }
    }

    private boolean kasOnRida(List<String> väärtused) {
        int järjestusesKaarte = 0;
        for (String väärtus : Kaardid.getVäärtused()) {
            if (väärtused.contains(väärtus)) {
                if (järjestusesKaarte == 4) return true;
                järjestusesKaarte += 1;
            }
            else {
                if (väärtus.equals("Kuningas")) järjestusesKaarte = 0; // juhul kui juhtub olla rida 5432A
                if (järjestusesKaarte != 0) return false;
            }
        }
        return järjestusesKaarte == 4 && väärtused.contains("Äss");
    }
}
