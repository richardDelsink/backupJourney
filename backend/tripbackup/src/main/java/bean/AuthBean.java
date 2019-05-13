package bean;

import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

@Named
@Dependent
public class AuthBean {

    public String doLogout() {
        ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false)).invalidate();

        return "user.xhtml?faces-redirect=true";
    }

    public boolean isLoggedIn() {
        return FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal() != null;
    }

    public String getPrincipalName() {
        if (isLoggedIn()) {
            return FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();
        } else {
            return "ANONYMOUS";
        }
    }

    public boolean isAdmin() {
        FacesContext fc = FacesContext.getCurrentInstance();
        return fc.getExternalContext().isUserInRole("AdminRole");
    }



}
