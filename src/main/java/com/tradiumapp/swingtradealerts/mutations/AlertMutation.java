package com.tradiumapp.swingtradealerts.mutations;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.mongodb.client.result.UpdateResult;
import com.tradiumapp.swingtradealerts.models.Alert;
import com.tradiumapp.swingtradealerts.models.AlertStatus;
import com.tradiumapp.swingtradealerts.models.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

@Component
public class AlertMutation implements GraphQLMutationResolver {
    Logger logger = LoggerFactory.getLogger(AlertMutation.class);

    @Autowired
    MongoTemplate mongoTemplate;

    public Response addAlert(final Alert alert) {
        alert.status = AlertStatus.Off;
        mongoTemplate.save(alert);

        logger.info("Alert for {} added successfully.", alert.symbol);
        return new Response(true, "Alert save successful.");
    }

    public Response updateAlert(final Alert alert) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(alert.id));

        Update update = new Update();
        update.set("title", alert.title);
        update.set("conditions", alert.conditions);

        UpdateResult result = mongoTemplate.updateFirst(query, update, Alert.class);
        Boolean success = result.getModifiedCount() == 1;

        if (success) {
            logger.info("Alert for {} updated successfully.", alert.symbol);
            return new Response(true, "Alert updated successfully.");
        } else {
            return new Response(false, "Alert update failed.");
        }
    }
}
