package com.truelaurel.recommend;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.amazon.awssdk.http.HttpStatusCodes;
import software.amazon.awssdk.services.dynamodb.DynamoDBClient;
import software.amazon.awssdk.services.dynamodb.datamodeling.DynamoDbMapper;
import software.amazon.awssdk.services.dynamodb.datamodeling.DynamoDbScanExpression;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.ComparisonOperator;
import software.amazon.awssdk.services.dynamodb.model.Condition;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Handler implements RequestHandler<Map<String, Object>, Response> {

    private static final Logger LOG = LogManager.getLogger(Handler.class);

    @Override
    public Response handleRequest(Map<String, Object> input, Context context) {
        try {
            LOG.info("received input " + input);
            return Response.builder()
                    .setStatusCode(HttpStatusCodes.OK)
                    .setObjectBody(doHandleRequest(input))
                    .build();
        } catch (Exception e) {
            LOG.error("Unable to handle recommendation request", e);
            return Response.builder()
                    .setStatusCode(HttpStatusCodes.INTERNAL_SERVER_ERROR)
                    .setObjectBody(e.getMessage())
                    .build();
        }
    }

    private Map<String, List<Link>> doHandleRequest(Map<String, Object> input) throws IOException {
        Request request = readRequest(input);
        Validator.validateRequest(request);

        DynamoDBClient client = DynamoDBClient.create();
        DynamoDbMapper dynamoDbMapper = new DynamoDbMapper(client);

        LOG.info("Start save current site");
        Site site = request.getSite();
        dynamoDbMapper.save(site);
        LOG.info("End save current site");

        LOG.info("Start load all sites");
        List<Site> sites = loadExternalSites(site, dynamoDbMapper);
        LOG.info("End load all sites");

        Engine engine = new Engine(site, sites);
        LOG.info("Start recommend");
        Map<Post, List<Post>> postMap = engine.recommend(request.getInternal(), request.getExternal());
        LOG.info("End recommend");
        return convertToResult(postMap);
    }

    private List<Site> loadExternalSites(Site site, DynamoDbMapper dynamoDbMapper) {
        DynamoDbScanExpression scanExpression = new DynamoDbScanExpression();
        scanExpression.addFilterCondition("domain",
                Condition.builder()
                        .attributeValueList(AttributeValue.builder().s(site.getDomain()).build())
                        .comparisonOperator(ComparisonOperator.NE)
                        .build());

        return dynamoDbMapper.scan(Site.class, scanExpression);
    }

    private Request readRequest(Map<String, Object> input) throws IOException {
        Object body = input.get("body");
        if (!(body instanceof String)) {
            throw new IllegalArgumentException("No body found in the input body=" + body);
        }
        return new ObjectMapper().readValue((String) body, Request.class);
    }

    private Map<String, List<Link>> convertToResult(Map<Post, List<Post>> postMap) {
        return postMap.entrySet().stream().collect(Collectors.toMap(
                e -> e.getKey().getPermalink(),
                e -> e.getValue().stream().map(p -> new Link(p.getTitle(), p.getPermalink())).collect(Collectors.toList())));
    }

}
