# mod-workflow

Copyright (C) 2018-2022 The Open Library Foundation

This software is distributed under the terms of the Apache License, Version 2.0.
See the file ["LICENSE"](LICENSE) for more information.

## Introduction

This module provides a **Workflow** backend **FOLIO** module intended to help facilite and interact with one or more **Workflow Engines**.
A **Workflow Engine** is able to react to events generated in FOLIO and may also trigger events based on some schedule.
**Triggering Events** may be data flows or user interactions.

This module is de-coupled from the **Workflow Engine**.
A **Workflow Engine**, such as [mod-camunda](https://github.com/folio-org/mod-camunda/), provides the **Business Process Model and Notation** (**BPMN**) and performs the actual execution of a **Workflow**.

## Additional information

The [Workflow Documentation](docs/README.md) describes additional information on particular standards and other topics.

  - [Folio Workflow Zip (FWZ)](docs/fwz/README.md)

## Docker deployment

```
docker build -t folio/mod-workflow .
docker run -d -p 9001:8081 folio/mod-workflow
```

### Publish docker image

```
docker login [docker repo]
docker build -t [docker repo]/folio/mod-workflow:[version] .
docker push [docker repo]/folio/mod-workflow:[version]
```

### Environment variables:

| Name                           |       Default value       | Description                                                                                                                                                |
|:-------------------------------|:-------------------------:|:-----------------------------------------------------------------------------------------------------------------------------------------------------------|
| JAVA_OPTIONS                   | -XX:MaxRAMPercentage=75.0 | Java options                                                                                                                                               |
| DB_HOST                        |         postgres          | Postgres hostname                                                                                                                                          |
| DB_PORT                        |           5432            | Postgres port                                                                                                                                              |
| DB_USERNAME                    |        folio_admin        | Postgres username                                                                                                                                          |
| DB_PASSWORD                    |             -             | Postgres username password                                                                                                                                 |
| DB_DATABASE                    |       okapi_modules       | Postgres database name                                                                                                                                     |
| DB_QUERYTIMEOUT                |           60000           | Database query timeout.                                                                                                                                    |
| DB_CHARSET                     |           UTF-8           | Database charset.                                                                                                                                          |
| DB_MAXPOOLSIZE                 |             5             | Database max pool size.                                                                                                                                    |
| KAFKA_HOST                     |           kafka           | Kafka broker hostname                                                                                                                                      |
| KAFKA_PORT                     |           9092            | Kafka broker port                                                                                                                                          |
| KAFKA_SECURITY_PROTOCOL        |         PLAINTEXT         | Kafka security protocol used to communicate with brokers (SSL or PLAINTEXT)                                                                                |
| KAFKA_SSL_KEYSTORE_LOCATION    |             -             | The location of the Kafka key store file. This is optional for client and can be used for two-way authentication for client.                               |
| KAFKA_SSL_KEYSTORE_PASSWORD    |             -             | The store password for the Kafka key store file. This is optional for client and only needed if 'ssl.keystore.location' is configured.                     |
| KAFKA_SSL_TRUSTSTORE_LOCATION  |             -             | The location of the Kafka trust store file.                                                                                                                |
| KAFKA_SSL_TRUSTSTORE_PASSWORD  |             -             | The password for the Kafka trust store file. If a password is not set, trust store file configured will still be used, but integrity checking is disabled. |
| OKAPI_URL                      |     http://okapi:9130     | OKAPI URL used to login system user, required                                                                                                              |
| SERVER_PORT                    |           8081            | The port to listen on that must match the PortBindings.                                                                                                              |
| SERVER_SERVLET_CONTEXTPATH     |             /             | The context path, or base path, to host at.                                                                                                              |
| SPRING_FLYWAY_ENABLED          |           false           | Database migration support via Spring Flyway.                                                                                                              |
| SPRING_JPA_HIBERNATE_DDLAUTO   |           update          | Auto-configure database on startup.                                                                                                              |
| TENANT_DEFAULTTENANT           |           diku            | The name of the default tenant to use.                                                                                                              |
| TENANT_FORCETENANT             |           false           | Forcibly add or overwrite the tenant name using the default tenant.                                                                                                              |
| TENANT_INITIALIZEDEFAULTTENANT |           true            | Perform initial auto-creation of tenant in the DB (schema, tables, etc..).                                                                                                              |
| TENANT_RECREATEDEFAULTTENANT   |           false           | When TENANT_INITIALIZEDEFAULTTENANT is true and the DB already exists, then drop and re-create.                                                                                                              |


### Required Permissions
Institutional users should be granted the following permissions in order to use this workflow API:
- `workflow.actions.all`
- `workflow.events.all`
- `workflow.nodes.all`
- `workflow.nodes.item.post`
- `workflow.triggers.all`
- `workflow.tasks.all`
- `workflow.workflows.all`

### Issue tracker

See project [FOLIO](https://issues.folio.org/browse/FOLIO)
at the [FOLIO issue tracker](https://dev.folio.org/guidelines/issue-tracker/).

