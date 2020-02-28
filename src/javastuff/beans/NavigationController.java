package javastuff.beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "navigationController", eager = true)
@RequestScoped
public class NavigationController implements Serializable {

    public String moveToMain() {
        return "main";
    }

    public String moveToIndex() {
        return "index";
    }
}
