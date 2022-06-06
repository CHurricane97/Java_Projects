package Classes;

public class StatusPliku {
    public String nazwa;
    public int status;


    public StatusPliku(String nazwa, int status) {
        this.nazwa = nazwa;
        this.status = status; //0-del 1-same 2-not same 3-added
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
