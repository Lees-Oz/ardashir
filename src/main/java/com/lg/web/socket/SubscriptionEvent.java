package com.lg.web.socket;

public class SubscriptionEvent {
    private String $o;
    private String $v;
    private int $c;
    private int $p;
    private String $causedBy;

    public SubscriptionEvent() {
    }

    public SubscriptionEvent(String $o, String $v, int $c, int $p, String $causedBy) {
        this.$o = $o;
        this.$v = $v;
        this.$c = $c;
        this.$p = $p;
        this.$causedBy = $causedBy;
    }

    public String get$o() {
        return $o;
    }

    public String get$v() {
        return $v;
    }

    public int get$c() {
        return $c;
    }

    public int get$p() {
        return $p;
    }

    public String get$causedBy() {
        return $causedBy;
    }
}
