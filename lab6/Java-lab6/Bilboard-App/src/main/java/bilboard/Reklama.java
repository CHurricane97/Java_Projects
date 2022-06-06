package bilboard;

import java.time.Duration;

public class Reklama {
    public int id;
    public String tekstReklamy;
    public Duration pozostaloSec;

    public Reklama(int id, String tekstReklamy, Duration pozostaloSec) {
        this.id = id;
        this.tekstReklamy = tekstReklamy;
        this.pozostaloSec = pozostaloSec;
    }

    public String getTekstReklamy() {
        return tekstReklamy;
    }

    public Duration getPozostaloSec() {
        return pozostaloSec;
    }
}
