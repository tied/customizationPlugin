package ru.amoroz.jira.customfields;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.RendererManager;
import com.atlassian.jira.issue.comments.Comment;
import com.atlassian.jira.issue.comments.CommentManager;
import com.atlassian.jira.issue.comparator.ApplicationUserBestNameComparator;
import com.atlassian.jira.issue.customfields.impl.CalculatedCFType;
import com.atlassian.jira.issue.fields.layout.field.FieldLayoutManager;
import com.atlassian.jira.issue.fields.renderer.IssueRenderContext;
import com.atlassian.jira.issue.fields.renderer.JiraRendererPlugin;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.util.NaturalOrderStringComparator;
import com.atlassian.plugin.spring.scanner.annotation.component.Scanned;
import com.atlassian.plugin.spring.scanner.annotation.imports.ComponentImport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.atlassian.jira.issue.customfields.impl.TextCFType;
import com.atlassian.jira.issue.customfields.manager.GenericConfigManager;
import com.atlassian.jira.issue.customfields.persistence.CustomFieldValuePersister;
import com.atlassian.jira.issue.customfields.impl.FieldValidationException;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.issue.fields.config.FieldConfig;
import com.atlassian.jira.issue.fields.layout.field.FieldLayoutItem;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

//@Scanned
public class LastCommentCustomField extends CalculatedCFType {
    private static final Logger log = LoggerFactory.getLogger(LastCommentCustomField.class);

//    for SortableCustomField interface only
//
//    @Override
//    public int compare(Object str1, Object str2, FieldConfig fieldConfig) {
//        return ((String) str1).compareTo(((String) str2));
//    }

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