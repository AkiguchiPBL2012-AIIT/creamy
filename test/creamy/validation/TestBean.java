/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package creamy.validation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


/**
 *
 * @author ATakahashi
 */
public class TestBean {
    @NotNull
    private String notNull;
    @Pattern(regexp="[0-9]+")
    private String numOnly;

    /**
     * @return the notNull
     */
    public String getNotNull() {
        return notNull;
    }

    /**
     * @param notNull the notNull to set
     */
    public void setNotNull(String notNull) {
        this.notNull = notNull;
    }

    /**
     * @return the numOnly
     */
    public String getNumOnly() {
        return numOnly;
    }

    /**
     * @param numOnly the numOnly to set
     */
    public void setNumOnly(String numOnly) {
        this.numOnly = numOnly;
    }

}
