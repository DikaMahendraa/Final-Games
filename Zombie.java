public class Zombie extends Musuh implements Loot {
    public Zombie() {
        super("Clickers", 150);
    }

    @Override
    public void serangPemain() {
        System.out.println(this.namamusuh + "Mengejar dan memakan! Player -20 HP");
    }
     @Override
    public void suaraKhas(){
         System.out.println("ARGHHHHHH");
    }

    @Override
    public void jatuhkanItem(){
        System.out.println(this.namamusuh + "menjatuhkan barang");
    }
}
