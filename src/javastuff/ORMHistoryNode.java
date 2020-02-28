package javastuff;

import javastuff.beans.HistoryNode;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity(name = "history")
public class ORMHistoryNode {
    @Id
    @GeneratedValue
    private long id;

    @Column(precision = 25, scale = 20)
    private BigDecimal x;
    @Column(precision = 25, scale = 20)
    private BigDecimal y;
    @Column(precision = 25, scale = 20)
    private BigDecimal r;
    @Column
    private int result;

    public ORMHistoryNode() {
    }

    public ORMHistoryNode(HistoryNode node) {
        this.x = node.getX();
        this.y = node.getY();
        this.r = node.getR();
        this.result = node.isHit() ? 1 : 0;
    }

    public BigDecimal getX() {
        return x;
    }

    public BigDecimal getY() {
        return y;
    }

    public BigDecimal getR() {
        return r;
    }

    public int getResult() {
        return result;
    }
}
