/**
 * Blackjack
 * Reegleid ilmselt liiga palju, et selles koodijupis seletada kõike,
 * aga peamine põhimõte on see, et mängija saab 2 kaarti ja diiler saab 2 kaarti.
 * Mängija saab siis kas juurde võtta või lasta diileril hakata võtma.
 * Üle 21 - bust ehk kaotus
 */

import java.util.Scanner;

public class Blackjack extends Mäng {

    private Kaardid kaardipakk = new Kaardid();

    public void mängi(Mängija mängija, int bet) {

        String kaart = "";
        String diileriTeineKaart = "";

        int mängijaMaxSumma = 0;
        int diileriMaxSumma = 0;
        int mängijaSumma = 0;
        int diileriSumma = 0;

        int mängijaÄssad = 0;
        int diileriÄssad = 0;

        boolean mängijaBlackjack = false;
        boolean diileriBlackjack = false;

        boolean splitVõimalik = false;
        boolean splitTehtud = false;
        int splitMaxSumma = 0;
        int splitSumma = 0;
        int splitÄssad = 0;
        int splitBet = 0;

        boolean mängijaLõhki = false;
        boolean splitLõhki = false;

        kaardipakk.uusKaardipakk();

        //Mängija kaartide valimine
        System.out.print("Sinu kaardid: ");

        kaart = kaardipakk.valiKaart();
        System.out.print(kaart + ", ");
        int esimeseKaardiVäärtus = kaardipakk.kaardiVäärtus(kaart);

        if (kaart.contains("Äss")) mängijaÄssad++;
        mängijaMaxSumma += esimeseKaardiVäärtus;

        kaart = kaardipakk.valiKaart();
        System.out.println(kaart);
        int teiseKaardiVäärtus = kaardipakk.kaardiVäärtus(kaart);

        if (kaart.contains("Äss")) mängijaÄssad++;
        mängijaMaxSumma += teiseKaardiVäärtus;

        mängijaSumma = arvutaSumma(mängijaMaxSumma, mängijaÄssad);
        System.out.println("Sinu summa on: " + mängijaSumma);

        if (esimeseKaardiVäärtus == teiseKaardiVäärtus && mängija.getBilanss() >= 2 * bet) {
            splitVõimalik = true;
        }

        if (mängijaSumma == 21) {
            mängijaBlackjack = true;
        }

        // Diileri kaartide valimine
        System.out.print("Diileri kaardid: ");

        kaart = kaardipakk.valiKaart();
        System.out.println(kaart);

        if (kaart.contains("Äss")) diileriÄssad++;
        diileriMaxSumma += kaardipakk.kaardiVäärtus(kaart);

        diileriTeineKaart = kaardipakk.valiKaart();
        System.out.println("Teine kaart ei ole hetkel teada");

        if (diileriTeineKaart.contains("Äss")) diileriÄssad++;
        diileriMaxSumma += kaardipakk.kaardiVäärtus(diileriTeineKaart);

        diileriSumma = arvutaSumma(diileriMaxSumma, diileriÄssad);

        if (diileriSumma == 21) {
            diileriBlackjack = true;
        }

        if (mängijaBlackjack) {
            System.out.println("Diileri teine kaart: " + diileriTeineKaart);
            if (diileriBlackjack) {
                System.out.println("Sinul ja diileril oli blackjack. Push. Raha tagasi.");
            }
            else {
                int blackjackVõit = bet * 3 / 2;
                System.out.println("Blackjack! Võitsid " + blackjackVõit + " ühikut!");
                mängija.suurendaBilanssi(blackjackVõit);
            }
            return; // lõppeb varakult
        }

        Scanner sc3 = new Scanner(System.in);
	    System.out.print("Tehke valik:\n1 = HIT, 2 = STAND");
        if (mängija.getBilanss() >= 2 * bet) { System.out.print(", 3 = DOUBLE"); }
        if (splitVõimalik) { System.out.print(", 4 = SPLIT"); }
        System.out.println();
        int valik = sc3.nextInt();
        while (valik == 1) {
            kaart = kaardipakk.valiKaart();
            System.out.println("Kaart: " + kaart);
            if (kaart.contains("Äss")) mängijaÄssad++;
            mängijaMaxSumma += kaardipakk.kaardiVäärtus(kaart);
            mängijaSumma = arvutaSumma(mängijaMaxSumma, mängijaÄssad);

            System.out.println("Teie summa: " + mängijaSumma);

            if (mängijaSumma > 21) {
                System.out.println("Läksid lõhki! Kaotasid " + bet + " ühikut.");
                mängija.vähendaBilanssi(bet);
                return; // lõpetab mängimise KOHE
            }

            if (mängijaSumma == 21) {
                valik = 0;
                break;
            }

            System.out.println("Tehke valik:\n1 = HIT, 2 = STAND");
            valik = sc3.nextInt();
            if (valik == 2) break;
        }

        if (valik == 3) {
            bet *= 2;
            kaart = kaardipakk.valiKaart();
            System.out.println("Kaart: " + kaart);
            if (kaart.contains("Äss")) mängijaÄssad++;
            mängijaMaxSumma += kaardipakk.kaardiVäärtus(kaart);
            mängijaSumma = arvutaSumma(mängijaMaxSumma, mängijaÄssad);

            System.out.println("Teie summa: " + mängijaSumma);

            if (mängijaSumma > 21) {
                System.out.println("Läksid lõhki! Kaotasid " + bet + " ühikut.");
                mängija.vähendaBilanssi(bet);
                return; // lõpetab mängimise KOHE
            }
        }

        if (valik == 4) {
            System.out.println("Jagasid kaardid kaheks.\nEsimene paar");
            splitTehtud = true;
            splitBet = bet;
            mängijaMaxSumma /= 2;
            mängijaÄssad /= 2;
            splitMaxSumma = mängijaMaxSumma;
            splitÄssad = mängijaÄssad;

            kaart = kaardipakk.valiKaart();
            System.out.println("Kaart: " + kaart);
            if (kaart.contains("Äss")) mängijaÄssad++;
            mängijaMaxSumma += kaardipakk.kaardiVäärtus(kaart);
            mängijaSumma = arvutaSumma(mängijaMaxSumma, mängijaÄssad);

            System.out.println("Teie summa: " + mängijaSumma);

            if (mängijaSumma < 21) {
                System.out.print("Tehke valik:\n1 = HIT, 2 = STAND");
                if (mängija.getBilanss() >= 3 * bet) { System.out.print(", 3 = DOUBLE"); }
                System.out.println();
                valik = sc3.nextInt();
                while (valik == 1) {
                    kaart = kaardipakk.valiKaart();
                    System.out.println("Kaart: " + kaart);
                    if (kaart.contains("Äss")) mängijaÄssad++;
                    mängijaMaxSumma += kaardipakk.kaardiVäärtus(kaart);
                    mängijaSumma = arvutaSumma(mängijaMaxSumma, mängijaÄssad);

                    System.out.println("Teie summa: " + mängijaSumma);

                    if (mängijaSumma > 21) {
                        System.out.println("Läks lõhki! Kaotasid " + bet + " ühikut.");
                        mängija.vähendaBilanssi(bet);
                        mängijaLõhki = true;
                        break;
                    }

                    if (mängijaSumma == 21) {
                        valik = 0;
                        break;
                    }

                    System.out.println("Tehke valik:\n1 = HIT, 2 = STAND");
                    valik = sc3.nextInt();
                    if (valik == 2) break;
                }

                if (valik == 3) {
                    bet *= 2;
                    kaart = kaardipakk.valiKaart();
                    System.out.println("Kaart: " + kaart);
                    if (kaart.contains("Äss")) mängijaÄssad++;
                    mängijaMaxSumma += kaardipakk.kaardiVäärtus(kaart);
                    mängijaSumma = arvutaSumma(mängijaMaxSumma, mängijaÄssad);

                    System.out.println("Teie summa: " + mängijaSumma);

                    if (mängijaSumma > 21) {
                        System.out.println("Läks lõhki! Kaotasid " + bet + " ühikut.");
                        mängija.vähendaBilanssi(bet);
                        mängijaLõhki = true;
                    }
                }
            }

            System.out.println("Teine paar");

            kaart = kaardipakk.valiKaart();
            System.out.println("Kaart: " + kaart);
            if (kaart.contains("Äss")) splitÄssad++;
            splitMaxSumma += kaardipakk.kaardiVäärtus(kaart);
            splitSumma = arvutaSumma(splitMaxSumma, splitÄssad);

            System.out.println("Teie summa: " + splitSumma);

            if (splitSumma < 21) {
                System.out.print("Tehke valik:\n1 = HIT, 2 = STAND");
                if (mängija.getBilanss() >= bet + 2 * splitBet) { System.out.print(", 3 = DOUBLE"); }
                System.out.println();
                valik = sc3.nextInt();
                while (valik == 1) {
                    kaart = kaardipakk.valiKaart();
                    System.out.println("Kaart: " + kaart);
                    if (kaart.contains("Äss")) splitÄssad++;
                    splitMaxSumma += kaardipakk.kaardiVäärtus(kaart);
                    splitSumma = arvutaSumma(splitMaxSumma, splitÄssad);

                    System.out.println("Teie summa: " + splitSumma);

                    if (splitSumma > 21) {
                        System.out.println("Läks lõhki! Kaotasid " + splitBet + " ühikut.");
                        mängija.vähendaBilanssi(splitBet);
                        splitLõhki = true;
                        break;
                    }

                    if (splitSumma == 21) {
                        valik = 0;
                        break;
                    }

                    System.out.println("Tehke valik:\n1 = HIT, 2 = STAND");
                    valik = sc3.nextInt();
                    if (valik == 2) break;
                }

                if (valik == 3) {
                    splitBet *= 2;
                    kaart = kaardipakk.valiKaart();
                    System.out.println("Kaart: " + kaart);
                    if (kaart.contains("Äss")) splitÄssad++;
                    splitMaxSumma += kaardipakk.kaardiVäärtus(kaart);
                    splitSumma = arvutaSumma(splitMaxSumma, splitÄssad);

                    System.out.println("Teie summa: " + splitSumma);

                    if (splitSumma > 21) {
                        System.out.println("Läks lõhki! Kaotasid " + splitBet + " ühikut.");
                        mängija.vähendaBilanssi(splitBet);
                        splitLõhki = true;
                    }
                }
            }
            
        }

        if (mängijaLõhki && splitLõhki) return;

        System.out.println("Diileri teine kaart: " + diileriTeineKaart);

        if (diileriBlackjack) {
            System.out.println("Diileril oli blackjack. Kaotasid " + (bet+splitBet) + " ühikut.");
            return;
        }

        while (diileriSumma < 17) {
            kaart = kaardipakk.valiKaart();
            System.out.println("Diiler võtab: " + kaart);
            if (kaart.contains("Äss")) diileriÄssad++;

            diileriMaxSumma += kaardipakk.kaardiVäärtus(kaart);
            diileriSumma = arvutaSumma(diileriMaxSumma, diileriÄssad);
        }

        if (splitTehtud) { System.out.println("Teie summad: " + mängijaSumma + "; " + splitSumma); }
        else { System.out.println("Teie summa: " + mängijaSumma); }
        System.out.println("Diileri summa: " + diileriSumma);

        if (!mängijaLõhki) {
            if (splitTehtud) { System.out.println("Esimene paar"); }
            if (diileriSumma > 21) {
                System.out.println("Diiler läks lõhki! Võitsid " + bet + " ühikut!");
                mängija.suurendaBilanssi(bet);
            }
            else if (mängijaSumma > diileriSumma) {
                System.out.println("Võitsid " + bet + " ühikut!");
                mängija.suurendaBilanssi(bet);
            }
            else if (mängijaSumma < diileriSumma) {
                System.out.println("Kaotasid " + bet + " ühikut.");
                mängija.vähendaBilanssi(bet);
            }
            else {
                System.out.println("Push. Raha tagasi.");
            }
        }
        if (splitTehtud && !splitLõhki) {
            System.out.println("Teine paar");
            if (diileriSumma > 21) {
                System.out.println("Diiler läks lõhki! Võitsid " + splitBet + " ühikut!");
                mängija.suurendaBilanssi(splitBet);
            }
            else if (splitSumma > diileriSumma) {
                System.out.println("Võitsid " + splitBet + " ühikut!");
                mängija.suurendaBilanssi(splitBet);
            }
            else if (splitSumma < diileriSumma) {
                System.out.println("Kaotasid " + splitBet + " ühikut.");
                mängija.vähendaBilanssi(splitBet);
            }
            else {
                System.out.println("Push. Raha tagasi.");
            }
        }
    }

    private int arvutaSumma(int summa, int ässad) {

        while (summa > 21 && ässad > 0){
            summa -= 10;
            ässad--;
        }

        return summa;
    }
}

