package javastuff.beans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.function.Predicate;

@RequestScoped
@ManagedBean(name = "validator")
public class ValidatorBean implements Serializable {
    public void validateX(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        validateBigDecimal(
                component, (BigDecimal) value,
                // -5 < x < 5
                number -> number.abs().compareTo( BigDecimal.valueOf(5) ) < 0
        );
    }

    public void validateY(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        validateBigDecimal(
                component, (BigDecimal) value,
                // -3 < y < 5
                number -> number.compareTo( BigDecimal.valueOf(-3) ) > 0 && number.compareTo(BigDecimal.valueOf(5)) < 0
        );
    }

    public void validateR(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        validateBigDecimal(
                component, (BigDecimal) value,
                // 1 < r < 4
                number -> number.compareTo(BigDecimal.valueOf(1)) > 0 && number.compareTo(BigDecimal.valueOf(4)) < 0
        );
    }


    private void validateBigDecimal(UIComponent component, BigDecimal value, Predicate<BigDecimal> validateCondition) throws ValidatorException {
        if (!validateCondition.test(value)) {
            Object summary = component.getAttributes().get("validatorMessage");
            FacesMessage message = new FacesMessage(
                    summary == null ? "Ошибка валидации" : summary.toString(),
                    "Число не входит в ОДЗ."
            );
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }
}
