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

import java.util.Collections;

public class Handler implements RequestHandler<Request, Response> {

    private static final Logger LOG = LogManager.getLogger(Handler.class);

    @Override
    public Response handleRequest(Request request, Context context) {
        Site site = request.getSite();
        DynamoDBClient client = DynamoDBClient.create();
        DynamoDbMapper dynamoDbMapper = new DynamoDbMapper(client);
        dynamoDbMapper.save(site);
        LOG.info("received " + " " + site);

        DynamoDbScanExpression scanExpression = new DynamoDbScanExpression();
        scanExpression.addFilterCondition("domain",
                Condition.builder()
                        .attributeValueList(AttributeValue.builder().s(site.getDomain()).build())
                        .comparisonOperator(ComparisonOperator.NE)
                        .build());

        PaginatedScanList<Site> sites = dynamoDbMapper.scan(Site.class, scanExpression);

        Engine engine = new Engine(site, sites);

        return Response.builder()
                .setStatusCode(200)
                .setObjectBody(engine.recommend(request.getInternal(), request.getExternal()))
                .setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & serverless"))
                .build();
    }

}
