public class Naga extends Musuh implements BisaTerbang{
    public Naga() {
        super("Red Dragon", 100);
    }

    @Override
    public void serangPemain() {
        System.out.println(this.namamusuh + "Menyemburkan Api dari udara! Player -50 HP");
    }

    @Override
    public void suaraKhas(){
         System.out.println("BRUHHHHHHHH");
    }

    @Override
    public void lepasLandas() {
        System.out.println(this.namamusuh + " terbang tinggi! Sulit diserang");
        
    }

    @Override
    public void seranganUdara(){
        System.out.println(this.namamusuh + " Menyemburkan badai api! Pemain -80 HP");
    }
}


