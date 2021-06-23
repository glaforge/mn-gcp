# mn-gcp

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

## Cloud Run

Enable Cloud Run, Cloud Build, configure Docker, build with Jib:

```
gcloud services enable run.googleapis.com
gcloud services enable cloudbuild.googleapis.com
gcloud auth configure-docker
./gradlew jib

```