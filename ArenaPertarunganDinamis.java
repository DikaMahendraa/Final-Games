import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;

public class ArenaPertarunganDinamis {

    public static void main(String[] arg) {

        Scanner input = new Scanner(System.in);

        // Musuh[] gelombangMonster = new Musuh[3];
        // gelombangMonster[0] = new Slime();
        // gelombangMonster[1] = new Naga();
        // gelombangMonster[2] = new Zombie();
        
        ArrayList<Musuh> gelombangMonster = new ArrayList<>();
        gelombangMonster.add(new Slime());
        gelombangMonster.add (new Naga());
        gelombangMonster.add(new Slime());
        gelombangMonster.add(new Zombie());

        System.out.println("========================");
        System.out.println("WELCOME TO BATTLE ARENA");
        System.out.println("========================\n");

        boolean isBermain = true;

        while (isBermain && !gelombangMonster.isEmpty()) {

            System.out.println("\n--- STATUS MONSTER ---");

            for (int i = 0; i < gelombangMonster.size(); i++) {
                Musuh m = gelombangMonster.get(i);
                System.out.println((i + 1) + "."+ m.namamusuh+ "(Hp: "+ m.healtpoint + ")");
            }
            System.out.println("-------------------");
            
            System.out.println("8. [SAVE GAME] Simpan progres pertarungan");
            System.out.println("9. [LOAD GAME] Muat progres sebelumnya");
            System.out.println("0. Kabur dari pertarungan");
            System.out.println("\n Pilih target Monster(1-"+ gelombangMonster.size() + ") atau aksi lainnya:");
            // System.out.println("4. Kabur dari pertarungan");
            // System.out.print("\nPilih target monster (1-3) atau 4 untuk kabur: ");

            try{

                int pilihanTarget = input.nextInt();

                // Kabur
                if (pilihanTarget == 0) {

                    System.out.println("\nAnda melarikan diri dari Arena Pertarungan!");
                    isBermain = false;
                    continue;
                }

                else if (pilihanTarget == 8){
                    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("savegame_rpg.dat"))){
                        oos.writeObject(gelombangMonster);
                        System.out.println(">>> BERHASIL: Game telah disimpan! <<<");

                    }catch (IOException e){
                        System.out.println(">>> GAGAL:  Terjadi kesalahan saat menyimpan game." + e.getMessage());
                    }
                    continue;
                }
                else if (pilihanTarget == 9 ){
                    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("savegame_rpg.dat"))){
                        gelombangMonster = (ArrayList<Musuh>) ois.readObject();
                        System.out.println(">>> BERHASIL: Game berhasil di muat! <<<");
                    }catch (FileNotFoundException e){
                        System.out.println (" >>>  GAGAL: File save Game belum ada. Silahkan Save Game terlebih dahulu!");
                    }catch (IOException | ClassNotFoundException e){
                        System.out.println(">>> GAGAL:  Terjadi kesalahan saat membaca file save. "+ e.getMessage());
                    }
                    continue;
                }

                // Validasi pilihan
                if (pilihanTarget < 1 || pilihanTarget > gelombangMonster.size()) {

                    System.out.println("Pilihan tidak valid!");
                    continue;
                }

                int indeksMonster = pilihanTarget - 1;
                Musuh target = gelombangMonster.get(indeksMonster);

                // Input damage
                System.out.print("Masukkan kekuatan serangan (10-100): ");
                int power = input.nextInt();

                // Validasi power
                if (power < 10 || power > 100) {

                    throw new SeranganTidakValidException(
                            "Kekuatan serangan harus di antara 10 sampai 100!");
                }

                System.out.println("\n>>> HASIL SERANGAN <<<");
                target.terimaDamage(power);
                if(target.healtpoint <= 0) {
                    System.out.println(target.namamusuh+ "hancur menjadi debu!");
                    if (target instanceof Loot){
                        Loot loot = (Loot) target;
                        loot.jatuhkanItem();
                    }
                    gelombangMonster.remove(indeksMonster);
                }
                // gelombangMonster[indeksMonster].terimaDamage(power);

                // // Jika monster mati
                // if (gelombangMonster[indeksMonster].healtpoint <= 0) {

                //     System.out.println(
                //             gelombangMonster[indeksMonster].namamusuh +
                //                     " telah dikalahkan!");

                //     // Loot
                //     if (gelombangMonster[indeksMonster] instanceof Loot) {

                //         Loot lootMonster = (Loot) gelombangMonster[indeksMonster];

                //         lootMonster.jatuhkanItem();
                //     }
                // }
            
                // Giliran monster menyerang balik
                System.out.println("\n<<< GILIRAN MONSTER MEMBALAS >>>");

                for (int i = 0; i < gelombangMonster.size(); i++) {
                    Musuh monsterAktif = gelombangMonster.get(indeksMonster);
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

                // Cek semua monster mati
                boolean semuaMati = true;

                for (int i = 0; i < gelombangMonster.size(); i++) {
                    if (gelombangMonster.get(i).healtpoint > 0) {
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
        }  catch (Exception e) {

                System.out.println("Terjadi kesalahan sistem, coba lagi!");
                input.nextLine();
                continue;
            }
    }
    
        input.close();

        System.out.println(
                "\nPermainan Berakhir. Sampai jumpa pada pertarungan berikutnya!");
    }
}
