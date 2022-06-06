package Classes;

public class Plik {
    public String nazwa;
    public String md5;

    public Plik(String nazwa, String md5) {
        this.nazwa = nazwa;
        this.md5 = md5;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }
}
