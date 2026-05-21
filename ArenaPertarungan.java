import java.util.Scanner;

public class ArenaPertarungan {

    public static void main(String[] arg) {

        Scanner input = new Scanner(System.in);

        Musuh[] gelombangMonster = new Musuh[3];
        gelombangMonster[0] = new Slime();
        gelombangMonster[1] = new Naga();
        gelombangMonster[2] = new Zombie();

        System.out.println("========================");
        System.out.println("WELCOME TO BATTLE ARENA");
        System.out.println("========================\n");

        boolean isBermain = true;

        while (isBermain) {

            System.out.println("\n--- STATUS MONSTER ---");

            for (int i = 0; i < gelombangMonster.length; i++) {

                if (gelombangMonster[i].healtpoint > 0) {

                    System.out.println(
                            (i + 1) + ". " +
                                    gelombangMonster[i].namamusuh +
                                    " (HP: " + gelombangMonster[i].healtpoint + ")");

                } else {

                    System.out.println(
                            (i + 1) + ". " +
                                    gelombangMonster[i].namamusuh +
                                    " [TEWAS]");
                }
            }

            System.out.println("4. Kabur dari pertarungan");
            System.out.print("\nPilih target monster (1-3) atau 4 untuk kabur: ");

            try {

                int pilihanTarget = input.nextInt();

                // Kabur
                if (pilihanTarget == 4) {

                    System.out.println("\nAnda melarikan diri dari Arena Pertarungan!");
                    isBermain = false;
                    continue;
                }

                // Validasi pilihan
                if (pilihanTarget < 1 || pilihanTarget > 3) {

                    System.out.println("Pilihan tidak valid!");
                    continue;
                }

                int indeksMonster = pilihanTarget - 1;

                // Cek monster mati
                if (gelombangMonster[indeksMonster].healtpoint <= 0) {

                    throw new TargetMatiExeception(
                            "Tindakan ilegal: Monster sudah mati!");
                }

                // Input damage
                System.out.print("Masukkan kekuatan serangan (10-100): ");
                int power = input.nextInt();

                // Validasi power
                if (power < 10 || power > 100) {

                    throw new SeranganTidakValidException(
                            "Kekuatan serangan harus di antara 10 sampai 100!");
                }

                System.out.println("\n>>> HASIL SERANGAN <<<");

                gelombangMonster[indeksMonster].terimaDamage(power);

                // Jika monster mati
                if (gelombangMonster[indeksMonster].healtpoint <= 0) {

                    System.out.println(
                            gelombangMonster[indeksMonster].namamusuh +
                                    " telah dikalahkan!");

                    // Loot
                    if (gelombangMonster[indeksMonster] instanceof Loot) {

                        Loot lootMonster = (Loot) gelombangMonster[indeksMonster];

                        lootMonster.jatuhkanItem();
                    }
                }

                // Giliran monster menyerang balik
                System.out.println("\n<<< GILIRAN MONSTER MEMBALAS >>>");

                for (int i = 0; i < gelombangMonster.length; i++) {

                    if (gelombangMonster[i].healtpoint > 0) {

                        Musuh monsterAktif = gelombangMonster[i];

                        monsterAktif.suaraKhas();

                        if (monsterAktif instanceof BisaTerbang) {

                            System.out.println(
                                    "[PERINGATAN! SERANGAN UDARA TERDETEKSI]");

                            BisaTerbang monsterTerbang = (BisaTerbang) monsterAktif;

                            monsterTerbang.lepasLandas();
                            monsterTerbang.seranganUdara();

                        } else {

                            monsterAktif.serangPemain();
                        }
                    }
                }

                // Cek semua monster mati
                boolean semuaMati = true;

                for (int i = 0; i < gelombangMonster.length; i++) {

                    if (gelombangMonster[i].healtpoint > 0) {

                        semuaMati = false;
                        break;
                    }
                }

                if (semuaMati) {

                    System.out.println(
                            "\nSELAMAT! Anda telah mengalahkan semua monster!");

                    isBermain = false;
                }

                System.out.println("\n--------------------------------");

            } catch (java.util.InputMismatchException e) {

                System.out.println("ERROR INPUT: Anda harus memasukkan ANGKA!");
                input.nextLine();

            } catch (TargetMatiExeception e) {

                System.out.println("KESALAHAN GAME: " + e.getMessage());

            } catch (SeranganTidakValidException e) {

                System.out.println("KESALAHAN GAME: " + e.getMessage());

            } catch (Exception e) {

                System.out.println("Terjadi kesalahan sistem: " + e.getMessage());
            }
        }

        input.close();

        System.out.println(
                "\nPermainan Berakhir. Sampai jumpa pada pertarungan berikutnya!");
    }
}