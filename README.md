# How to Test

Use `serverless info` to find the function url and then execute the following command
```
curl -d "@data/request1.json" -X POST https://your-url
```