package ru.amoroz.jira.customfields;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.comments.Comment;
import com.atlassian.jira.issue.customfields.impl.CalculatedCFType;
import com.atlassian.jira.issue.customfields.impl.FieldValidationException;
import com.atlassian.jira.issue.fields.CustomField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LastCommentedUser extends CalculatedCFType {
    private static final Logger log = LoggerFactory.getLogger(LastCommentedUser.class);

//    for SortableCustomField interface only
//
//    @Override
//    public int compare(Object user1, Object user2, FieldConfig fieldConfig) {
//        return new ApplicationUserBestNameComparator().compare((ApplicationUser) user1, (ApplicationUser) user2);
//    }

    @Override
    public Object getValueFromIssue(CustomField customField, Issue issue) {
        Comment c = ComponentAccessor.getCommentManager().getLastComment(issue);
        return c == null ? "" : "[~" + c.getAuthorApplicationUser().getUsername() + "]";
    }

    @Override
    public String getStringFromSingularObject(Object o) {
        return o.toString();
    }

    @Override
    public Object getSingularObjectFromString(String s) throws FieldValidationException {
        return s;
    }

    @Override
    public boolean isRenderable() {
        return true;
    }
}