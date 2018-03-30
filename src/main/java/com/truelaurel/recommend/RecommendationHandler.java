package com.truelaurel.recommend;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.amazon.awssdk.services.dynamodb.DynamoDBClient;
import software.amazon.awssdk.services.dynamodb.datamodeling.DynamoDbMapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RecommendationHandler implements RequestHandler<Site, ApiGatewayResponse> {

    private static final Logger LOG = LogManager.getLogger(RecommendationHandler.class);

    @Override
    public ApiGatewayResponse handleRequest(Site site, Context context) {

        DynamoDBClient client = DynamoDBClient.create();
        DynamoDbMapper dynamoDbMapper = new DynamoDbMapper(client);
        dynamoDbMapper.save(site);
        LOG.info("received " + " " + site);
        LOG.info("read from db " + " " + dynamoDbMapper.load(Site.class, site.getDomain()));

        List<Recommendation> result = Collections.singletonList(new Recommendation("https://hui-wang.info/hello.html", Collections.singletonList(new Link("title", "https://hui-wnag.info/world.html"))));
        return ApiGatewayResponse.builder()
                .setStatusCode(200)
                .setObjectBody(result.stream().collect(Collectors.toMap(Recommendation::getPermalink, Recommendation::getLinks)))
                .setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & serverless"))
                .build();
    }

}
