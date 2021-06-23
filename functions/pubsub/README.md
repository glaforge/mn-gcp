## Micronaut 3.0.0-M2 Documentation

- [User Guide](https://docs.micronaut.io/3.0.0-M2/guide/index.html)
- [API Reference](https://docs.micronaut.io/3.0.0-M2/api/index.html)
- [Configuration Reference](https://docs.micronaut.io/3.0.0-M2/guide/configurationreference.html)
- [Micronaut Guides](https://guides.micronaut.io/index.html)
---

# Micronaut and Google Cloud Function

## Running the function locally

```cmd
./gradlew runFunction
```

And visit http://localhost:8080/webhook

## Deploying the function

First build the function with:

```bash
$ ./gradlew clean shadowJar
```

Then `cd` into the `build/libs` directory (deployment has to be done from the location where the JAR lives):

```bash
$ cd build/libs
```

Now run:

```bash
$ gcloud functions deploy webhook --entry-point io.micronaut.gcp.function.http.HttpFunction --runtime java11 --trigger-http
```

Choose unauthenticated access if you don't need auth.

To obtain the trigger URL do the following:

```bash
$ YOUR_HTTP_TRIGGER_URL=$(gcloud functions describe webhook --format='value(httpsTrigger.url)')
```

You can then use this variable to test the function invocation:

```bash
$ curl -i $YOUR_HTTP_TRIGGER_URL/webhook
```
## Feature google-cloud-function documentation

- [Micronaut Google Cloud Function Support documentation](https://micronaut-projects.github.io/micronaut-gcp/latest/guide/index.html#simpleFunctions)

## Feature google-cloud-function-http documentation

- [Micronaut Google Cloud Function Support documentation](https://micronaut-projects.github.io/micronaut-gcp/latest/guide/index.html#httpFunctions)

