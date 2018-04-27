package ru.amoroz.jira.customfields;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.comments.Comment;
import com.atlassian.jira.issue.comparator.ApplicationUserBestNameComparator;
import com.atlassian.jira.issue.customfields.SortableCustomField;
import com.atlassian.jira.issue.customfields.impl.AbstractCustomFieldType;
import com.atlassian.jira.issue.customfields.impl.CalculatedCFType;
import com.atlassian.jira.issue.customfields.impl.FieldValidationException;
import com.atlassian.jira.issue.customfields.view.CustomFieldParams;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.issue.fields.config.FieldConfig;
import com.atlassian.jira.user.ApplicationUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LastUserCommentedCFType extends CalculatedCFType {
    private static final Logger log = LoggerFactory.getLogger(LastUserCommentedCFType.class);

    @Override
    public int compare(Object str1, Object str2, FieldConfig fieldConfig) {
        return ((String) str1).compareTo(((String) str2));
    }

    @Override
    public Object getValueFromIssue(CustomField customField, Issue issue) {
        Comment c = ComponentAccessor.getCommentManager().getLastComment(issue);
        return c == null ? "" : c.getAuthorFullName();
    }

    @Override
    public String getStringFromSingularObject(Object o) {
        return o.toString();
    }

    @Override
    public Object getSingularObjectFromString(String s) throws FieldValidationException {
        return s;
    }
}