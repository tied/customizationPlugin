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

public class LastUserCommentedCFType extends CalculatedCFType {

    @Override
    public int compare(Object o, Object t1, FieldConfig fieldConfig) {
        return new ApplicationUserBestNameComparator().compare((ApplicationUser) o, (ApplicationUser) t1);
    }

    @Override
    public Object getValueFromIssue(CustomField customField, Issue issue) {
        Comment c = ComponentAccessor.getCommentManager().getLastComment(issue);
        return c == null ? "" : (c.getBody().length() > 90 ? c.getBody().substring(0, 90) : c.getBody());
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