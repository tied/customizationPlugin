package ru.amoroz.jira.customfields;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.RendererManager;
import com.atlassian.jira.issue.comments.Comment;
import com.atlassian.jira.issue.customfields.impl.CalculatedCFType;
import com.atlassian.jira.issue.fields.layout.field.FieldLayoutItem;
import com.atlassian.jira.issue.fields.layout.field.FieldLayoutManager;
import com.atlassian.jira.issue.fields.renderer.IssueRenderContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.atlassian.jira.issue.customfields.impl.FieldValidationException;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.fields.CustomField;

//@Scanned
public class LastComment extends CalculatedCFType {
    private static final Logger log = LoggerFactory.getLogger(LastComment.class);

//    for SortableCustomField interface only
//
//    @Override
//    public int compare(Object str1, Object str2, FieldConfig fieldConfig) {
//        return ((String) str1).compareTo(((String) str2));
//    }

    @Override
    public Object getValueFromIssue(CustomField customField, Issue issue) {
        Comment c = ComponentAccessor.getCommentManager().getLastComment(issue);

        // set numeric field size in field description, default 90
        int maxStringSize = 90;
        String description = customField.getDescription();
        if (description.length() > 0) {
            try {
                maxStringSize = Integer.parseInt(description);
            } catch (Exception e) {
                maxStringSize = 0;
                log.error(issue.getKey() + " ERROR setting getLastComment SIZE. User set description to: " + description);
            }
        }

        return (c != null && maxStringSize > 0) ? (c.getBody().length() > maxStringSize ? c.getBody().substring(0, maxStringSize) : c.getBody()) : "";
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
        //return super.isRenderable();
        return true;
    }
}