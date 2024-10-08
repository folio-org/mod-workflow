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

### Issue tracker

See project [FOLIO](https://issues.folio.org/browse/FOLIO)
at the [FOLIO issue tracker](https://dev.folio.org/guidelines/issue-tracker/).

