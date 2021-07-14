package com.tradiumapp.swingtradealerts.controllers;

import graphql.ExecutionResult;
import graphql.GraphQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.tradiumapp.swingtradealerts.graphql_utilities.GraphQlUtility;

import java.io.IOException;

@RestController
public class MainController {

    private GraphQL graphQL;
    private GraphQlUtility graphQlUtility;
    Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    MainController(GraphQlUtility graphQlUtility) throws IOException {
        this.graphQlUtility = graphQlUtility;
        graphQL = graphQlUtility.createGraphQlObject();
    }

    @PostMapping(value = "/query")
    public ResponseEntity query(@RequestBody String query){
        logger.info("excuting query");

        ExecutionResult result = graphQL.execute(query);
        System.out.println("errors: "+result.getErrors());
        return ResponseEntity.ok(result.getData());
    }
}