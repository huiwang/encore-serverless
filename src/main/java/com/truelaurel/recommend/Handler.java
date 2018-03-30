package com.truelaurel.recommend;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.amazon.awssdk.services.dynamodb.DynamoDBClient;
import software.amazon.awssdk.services.dynamodb.datamodeling.DynamoDbMapper;
import software.amazon.awssdk.services.dynamodb.datamodeling.DynamoDbScanExpression;
import software.amazon.awssdk.services.dynamodb.datamodeling.PaginatedScanList;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.ComparisonOperator;
import software.amazon.awssdk.services.dynamodb.model.Condition;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Handler implements RequestHandler<Request, Response> {

    private static final Logger LOG = LogManager.getLogger(Handler.class);

    @Override
    public Response handleRequest(Request request, Context context) {
        LOG.info("received request " + request);
        if (request.getInternal() < 1) {
            throw new IllegalArgumentException("internal link must be greater or equal to 1. internal=" + request.getInternal());
        }
        if (request.getExternal() < 0) {
            throw new IllegalArgumentException("external link must be positive. external=" + request.getExternal());
        }
        Site site = request.getSite();
        DynamoDBClient client = DynamoDBClient.create();
        DynamoDbMapper dynamoDbMapper = new DynamoDbMapper(client);
        dynamoDbMapper.save(site);

        DynamoDbScanExpression scanExpression = new DynamoDbScanExpression();
        scanExpression.addFilterCondition("domain",
                Condition.builder()
                        .attributeValueList(AttributeValue.builder().s(site.getDomain()).build())
                        .comparisonOperator(ComparisonOperator.NE)
                        .build());

        PaginatedScanList<Site> sites = dynamoDbMapper.scan(Site.class, scanExpression);

        Engine engine = new Engine(site, sites);

        Map<Post, List<Post>> postMap = engine.recommend(request.getInternal(), request.getExternal());
        Map<String, List<Link>> result = convertToResult(postMap);
        return Response.builder()
                .setStatusCode(200)
                .setObjectBody(result)
                .build();
    }

    private Map<String, List<Link>> convertToResult(Map<Post, List<Post>> postMap) {
        return postMap.entrySet().stream().collect(Collectors.toMap(
                    e -> e.getKey().getPermalink(),
                    e -> e.getValue().stream().map(p -> new Link(p.getTitle(), p.getPermalink())).collect(Collectors.toList())));
    }

}
