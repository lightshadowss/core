package com.dotmarketing.portlets.rules.conditionlet;

import com.dotmarketing.cms.factories.PublicCompanyFactory;
import com.dotmarketing.util.Logger;
import com.liferay.portal.language.LanguageException;
import com.liferay.portal.language.LanguageUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

public abstract class Conditionlet implements Serializable {

    private static final long serialVersionUID = -8179010054316951177L;

    private String languageId;

    /**
     * This method looks for the name in the language.properties
     * file using property "com.my.classname.name" If that is not there it will return the value
     * set in the getName() method.
     *
     * @return the name in the language.properties, if exists, value of getName() if not.
     */
    public String getLocalizedName() {
        String val = null;
        try {
            String key = this.getClass().getCanonicalName() + ".name";
            val = LanguageUtil.get(PublicCompanyFactory.getDefaultCompanyId(), PublicCompanyFactory.getDefaultCompany().getLocale(), key);
            if (val != null &&! key.equals(val)) {
                return val;
            }
        } catch (LanguageException e) {
            Logger.error(this.getClass(), e.getMessage(), e);
        }
        return getName();
    }

    /**
     * Returns the human readable name for this Conditionlet
     *
     * @return the name of this Conditionlet
     */
    protected abstract String getName();

    /**
     * Returns a Map object whoose keys are the operators' names and values are the operators' labels (for presentation)
     * @return a Map of operators' names and labels
     */
    public abstract Set<Comparison> getComparisons();


    /**
     * Performs a validation to the given inputValues(s), determined by the given operator
     * @param comparison the selected comparison in the condition
     * @param inputValues the given values in the condition
     * @return the result of whether the given values are valid or not, determined by the given operator
     */
    public abstract ValidationResults validate(Comparison comparison, Set<ConditionletInputValue> inputValues);

    protected abstract ValidationResult validate(Comparison comparison, ConditionletInputValue inputValue);

    /**
     * Returns a {@link ConditionletInput} containing all the information and/or data needed to build the input for a Condition,
     * determined by the given operator
     * @param comparison the name of the selected operator in the condition
     * @return
     */
    public abstract Set<ConditionletInput> getInputs(Comparison comparison);

    public abstract boolean evaluate(Comparison comparison, HttpServletRequest request);

    public String getLanguageId() {
        return languageId;
    }

    public void setLanguageId(String languageId) {
        this.languageId = languageId;
    }
}