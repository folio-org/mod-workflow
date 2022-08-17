# mod-workflow

Copyright (C) 2018-2022 The Open Library Foundation

This software is distributed under the terms of the Apache License, Version 2.0.
See the file ["LICENSE"](LICENSE) for more information.

## Introduction

Introduction ...

## Additional information

## Docker deployment

```
docker build -t folio/mod-workflow .
docker run -d -p 9001:9001 folio/mod-workflow
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

