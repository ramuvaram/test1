package com.demoorg.demo.workflow;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.workflow.WorkflowException;
import com.day.cq.workflow.WorkflowSession;
import com.day.cq.workflow.exec.WorkItem;
import com.day.cq.workflow.exec.WorkflowData;
import com.day.cq.workflow.exec.WorkflowProcess;
import com.day.cq.workflow.metadata.MetaDataMap;

/**
 * Custom workflow to update user profile information.
 */
@Component
@Service
public class UpdateUserProfile implements WorkflowProcess {

    /** the logger */
    private static final Logger log = LoggerFactory.getLogger(UpdateUserProfile.class);
    
    private static String PROP_NAME_AUTHORIZABLE_CATEGORY="cq:authorizableCategory";
    private static String PROP_VAL_AUTHORIZABLE_CATEGORY="mcm";
    private static final String TYPE_JCR_PATH = "JCR_PATH";

    public void execute(WorkItem item, WorkflowSession wfSession, MetaDataMap args) throws WorkflowException {
        log.info("Inside UpdateUserProfile execute");
        WorkflowData workflowData = item.getWorkflowData();
        if (workflowData.getPayloadType().equals(TYPE_JCR_PATH)) {
            String path = workflowData.getPayload().toString();
            log.info("Inside UpdateUserProfile execute  path "+path);
            try {
                if(path.length()>0){ 
                     Session session = wfSession.getSession();
                     Node node = (Node) session.getItem(path);
                     if(node!=null){
                         node.setProperty(PROP_NAME_AUTHORIZABLE_CATEGORY,PROP_VAL_AUTHORIZABLE_CATEGORY);
                         node.save();
                         session.save();
                     }
                 log.info("Inside UpdateUserProfile execute  node saved ");
                                                               }    
            } catch (RepositoryException e) {
                log.info("Inside UpdateUserProfile execute  node error "+e.getMessage());
                throw new WorkflowException(e.getMessage(), e);
            }
        }

    }
    
   }
 