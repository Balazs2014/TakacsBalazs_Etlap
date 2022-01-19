package hu.csepel.etlap;

public class Etel {
    private String nev;
    private String leairas;
    private int ar;

    public Etel(String nev, String leairas, int ar) {
        this.nev = nev;
        this.leairas = leairas;
        this.ar = ar;
    }

    public String getNev() {
        return nev;
    }

    public String getLeairas() {
        return leairas;
    }

    public int getAr() {
        return ar;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public void setLeairas(String leairas) {
        this.leairas = leairas;
    }

    public void setAr(int ar) {
        this.ar = ar;
    }
}
