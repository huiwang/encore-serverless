# Deploy
Run the following command to generate the shaded jar and deploy the lambda to cloud provider
```mvn clean package; serverless deploy```

# Test
Use `serverless info` to find the function url and then execute the following command
```
curl -d "@data/request1.json" -X POST https://your-url
```

# Online debug

```serverless logs -f recommend```