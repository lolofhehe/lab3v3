package javastuff.beans;

import javastuff.HistoryDataAccess;
import javastuff.ORMHistoryNode;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@ManagedBean(name = "history")
public class HistoryBean implements Serializable {
    private HistoryNode node;
    private int scrollPage;

    private boolean scrollableLeft;
    private boolean scrollableRight;
    private boolean submitted;

    public HistoryNode getNode() {
        return node;
    }

    public void setNode(HistoryNode node) {
        this.node = node;
    }

    public HistoryBean() {
        node = new HistoryNode();
        scrollPage = 0;
        submitted = false;
    }

    private List<HistoryNode> getNodeList() {
        try (HistoryDataAccess access = new HistoryDataAccess()) {
            return access.getNodes().stream().map(HistoryNode::new).collect(Collectors.toList());
        }
    }

    public List<HistoryNode> getReversedNodeList() {
        List<HistoryNode> temp = getNodeList();
        Collections.reverse(temp);

        scrollableLeft = scrollPage > 0;
        scrollableRight = (scrollPage + 1) * 20 < temp.size();

        return temp.subList(
                scrollPage * 20,
                scrollableRight ? (scrollPage + 1) * 20 : temp.size()
        );
    }

    public void scrollLeft() {
        if (scrollableLeft) scrollPage--;
    }

    public void scrollRight() {
        if (scrollableRight) scrollPage++;
    }

    public int getScrollPage() {
        return scrollPage;
    }

    public boolean isScrollableLeft() {
        return scrollableLeft;
    }

    public boolean isScrollableRight() {
        return scrollableRight;
    }

    public boolean isSubmitted() {
        return submitted;
    }

    public void addNode() {
        if (node.printHit()) {
            try(HistoryDataAccess access = new HistoryDataAccess()) {
                access.addNode( new ORMHistoryNode(node) );
            }
            node = new HistoryNode();
            scrollPage = 0;
            submitted = true;
        }
    }
}
