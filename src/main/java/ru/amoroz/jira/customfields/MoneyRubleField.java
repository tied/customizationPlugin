package ru.amoroz.jira.customfields;

import com.atlassian.jira.issue.customfields.impl.AbstractSingleFieldType;
import com.atlassian.jira.issue.customfields.impl.FieldValidationException;
import com.atlassian.jira.issue.customfields.manager.GenericConfigManager;
import com.atlassian.jira.issue.customfields.persistence.CustomFieldValuePersister;
import com.atlassian.jira.issue.customfields.persistence.PersistenceFieldType;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

@Scanned
public class MoneyRubleField extends AbstractSingleFieldType<BigDecimal> {
    private static final Logger log = LoggerFactory.getLogger(MoneyRubleField.class);

    public MoneyRubleField(@ComponentImport CustomFieldValuePersister customFieldValuePersister, @ComponentImport GenericConfigManager genericConfigManager) {
        super(customFieldValuePersister, genericConfigManager);
    }

    @Override
    protected BigDecimal getObjectFromDbValue(final Object databaseValue) throws FieldValidationException {
        return getSingularObjectFromString((String) databaseValue);
    }

    @Override
    protected Object getDbValueFromObject(final BigDecimal customFieldObject) {
        return getStringFromSingularObject(customFieldObject);
    }

    @Override
    public String getStringFromSingularObject(final BigDecimal singularObject) {
        if (singularObject == null) return null;
        else return singularObject.toString();
    }

    @Override
    public BigDecimal getSingularObjectFromString(final String s) throws FieldValidationException {
        if (s == null) return null;

        try {
            final BigDecimal decimal = new BigDecimal(s);

            if (decimal.scale() > 2) throw new FieldValidationException("Maximum of 2 decimal places are allowed.");

            return decimal.setScale(2);
        } catch (NumberFormatException e) {
            throw new FieldValidationException("Not a valid number.");
        }
    }

    @Override
    protected PersistenceFieldType getDatabaseType() {
        return PersistenceFieldType.TYPE_LIMITED_TEXT;
    }
}