package com.truelaurel.recommend;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RecommendationHandler implements RequestHandler<Site, ApiGatewayResponse> {

    private static final Logger LOG = LogManager.getLogger(RecommendationHandler.class);

    @Override
    public ApiGatewayResponse handleRequest(Site site, Context context) {

        String tableName = System.getenv("DYNAMODB_TABLE");

        LOG.info("table " + " " + tableName);
        LOG.info("received " + " " + site);
        List<Recommendation> result = Collections.singletonList(new Recommendation("https://hui-wang.info/hello.html", Collections.singletonList(new Link("title", "https://hui-wnag.info/world.html"))));
        return ApiGatewayResponse.builder()
                .setStatusCode(200)
                .setObjectBody(result.stream().collect(Collectors.toMap(Recommendation::getPermalink, Recommendation::getLinks)))
                .setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & serverless"))
                .build();
    }

}
