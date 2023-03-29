# Micronaut on Google Cloud

Samples showing how to deploy Micronaut code on Google Cloud

## Some setup

```
gcloud config set functions/region europe-west1
gcloud config set run/region europe-west1

gcloud services enable cloudbuild.googleapis.com
```

## Cloud Functions

### HTTP functions for webhooks

You can generate a template project with `mn` or `curl`:

```
mn create-app --build=gradle --jdk=11 --lang=java --test=spock --features=google-cloud-function mngcp.webhook

curl --location --request GET 'https://next.micronaut.io/create/default/mngcp.webhook?lang=JAVA&build=GRADLE&test=SPOCK&javaVersion=JDK_11&features=google-cloud-function' --output webhook.zip
```

Run the service locally and invoke it:

```
./gradlew run

curl -X POST http://localhost:8080/webhook -H "Content-Type: application/json" -d '{"test":123}'
http POST localhost:8080/webhook hello=1234
```

```
./gradlew shadowJar

gcloud functions deploy webhook --entry-point io.micronaut.gcp.function.http.HttpFunction --trigger-http --runtime java11 --memory 2048MB --region europe-west1 --allow-unauthenticated --source build/libs 
```

### Function for Pub/Sub messages

```
./gradlew shadowJar

gcloud functions deploy pubsub --entry-point mngcp.PubSubFunction --trigger-topic events --runtime java11 --memory 2048MB --region europe-west1 --source build/libs 
```

## App Engine

### Backend

Backend composed of a static part and a dynamic part.

You can serve the static part with:

```
python3 -m http.server 8080
```

Build and deploy both static and dynamic parts with:

```
cd appengine/backend

./gradlew shadowJar
./gradlew appEngineStage

gcloud app deploy build/staged-app/ -q
```

### With GraalVM

Bug with the version of GraalVM used by Micronaut 3.0.0-M1 and M2.
Must use Micronaut 2.5.6, by changing `gradle.properties`'s version.

```
./gradlew dockerBuildNative

# Run the container in another terminal
docker run -p 8080:8080 backend:latest --rm -it

# Copy the native binary application locally
# Find the container id currently running
docker cp c781798142e8:/app/application stage/

# Deploy app.yaml and application from a stage/ directory
gcloud app deploy stage
```

## Cloud Run

### Build with JIB and deploy

Enable Cloud Run, Cloud Build, configure Docker, build with Jib:

```
gcloud services enable run.googleapis.com
gcloud services enable cloudbuild.googleapis.com
gcloud auth configure-docker

./gradlew jib
gcloud run deploy dice --image gcr.io/chessnuts/livefeed --allow-unauthenticated
```

### Deploy a GraalVM native image

Reuse the "vegetable" backend deployed on App Engine, built inside a container, and deploy that container:

```
docker tag vegetable_backend:latest gcr.io/chessnuts/vegetable_backend
docker push gcr.io/chessnuts/vegetable_backend
gcloud run deploy vegetable-backend --image gcr.io/chessnuts/vegetable_backend --allow-unauthenticated
```
