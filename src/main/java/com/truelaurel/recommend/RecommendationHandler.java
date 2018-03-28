package com.truelaurel.recommend;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RecommendationHandler implements RequestHandler<Site, ApiGatewayResponse> {

    private static final Logger LOG = Logger.getLogger(RecommendationHandler.class);

    @Override
    public ApiGatewayResponse handleRequest(Site site, Context context) {
        BasicConfigurator.configure();

        LOG.info("received " + " " + site);
        List<Recommendation> result = Collections.singletonList(new Recommendation("https://hui-wang.info/hello.html", Collections.singletonList(new Link("title", "https://hui-wnag.info/world.html"))));
        return ApiGatewayResponse.builder()
                .setStatusCode(200)
                .setObjectBody(result.stream().collect(Collectors.toMap(Recommendation::getPermalink, Recommendation::getLinks)))
                .setHeaders(Collections.singletonMap("X-Powered-By", "AWS Lambda & serverless"))
                .build();
    }

}
