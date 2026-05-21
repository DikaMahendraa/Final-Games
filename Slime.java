public class Slime extends Musuh implements Loot{
    public Slime() {
        super("Slime Asam", 50);
    }

    @Override
    public void serangPemain() {
        System.out.println(this.namamusuh + "Melompat dan menyiram cairan asam! Player -15 HP");
    }
     @Override
    public void suaraKhas(){
         System.out.println("SWUSHHHH");
    }

    @Override
    public void jatuhkanItem(){
        System.out.println(this.namamusuh + "Menjatuhkan Item");
    }
}
