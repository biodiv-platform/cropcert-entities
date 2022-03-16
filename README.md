# Cropcert Entities

Entities API for Cropcert

![Build + SDK](https://github.com/strandls/cropcert-entities/workflows/Build%20+%20SDK/badge.svg?branch=master)

## 🚀 Quick start

```sh
mvn clean                   # Clean application
mvn install                 # Build application WAR and generate OpenAPI SDK
sh @ci/pre-configure-m2.sh  # Creates/Updates Maven `settings.xml` (If using local artificatory please set values in shellfile first)
mvn deploy                  # Deploys WAR to artifactory
```

## Building OpenAPI SDK

```sh
@ci/pre-configure-sdk.sh    # Updated `pom.xml` with artificatory configuration
cd target/sdk && mvn deploy # Deploys SDK to artifactory
```
