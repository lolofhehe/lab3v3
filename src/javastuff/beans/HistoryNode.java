package javastuff.beans;

import javastuff.ORMHistoryNode;

import java.io.Serializable;
import java.math.BigDecimal;

public class HistoryNode implements Serializable {
    private BigDecimal x;
    private BigDecimal y;
    private BigDecimal r;

    private boolean hit;

    public HistoryNode() {

    }

    public HistoryNode(ORMHistoryNode node) {
        this.x = node.getX();
        this.y = node.getY();
        this.r = node.getR();

        this.hit = node.getResult() == 1;
    }

    public BigDecimal getX() {
        return x;
    }

    public void setX(BigDecimal x) {
        this.x = x;
    }

    public BigDecimal getY() {
        return y;
    }

    public void setY(BigDecimal y) {
        this.y = y;
    }

    public BigDecimal getR() {
        return r;
    }

    public void setR(BigDecimal r) {
        this.r = r;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public boolean isHit() {
        return hit;
    }

    private boolean calculateHit(BigDecimal x, BigDecimal y, BigDecimal r) {
        if ( x.compareTo(BigDecimal.ZERO) > 0 && y.compareTo(BigDecimal.ZERO) < 0 ) // x > 0 and y < 0
            return false;

        if ( x.compareTo(BigDecimal.ZERO) > 0 && y.compareTo(BigDecimal.ZERO) > 0 ) // x > 0 and y > 0
            return x.pow(2).add( y.pow(2) ).compareTo( r.pow(2).divide(BigDecimal.valueOf(4)) ) <= 0; // x^2 + y^2 <= r^2/4

        if (x.compareTo(BigDecimal.ZERO) <= 0 && y.compareTo(BigDecimal.ZERO) > 0) // x <= 0
            return x.compareTo(r.divide(BigDecimal.valueOf(2).negate())) >= 0 && y.compareTo(r) <= 0; // x >= -r/2; y <= r

        return y.compareTo(x.negate().divide(BigDecimal.valueOf(2)).subtract(r.divide(BigDecimal.valueOf(2)))) >= 0; //y >= -x/2 - r/2
    }

    boolean printHit() {
        if (x == null || y == null || r == null) return false;
        this.hit = calculateHit( x, y, r );
        return true;
    }
}