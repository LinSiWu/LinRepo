# Incident API DEMO
## Introduction
It is a demo for incident api. Api interfaces are list as follows:

- <strong><em> GET /incident/{id} </em></strong>

get an incident by id

path params:
```block
{id} in path param
```

output:
```json
{
  "code": 0,
  "message": "success",
  "data": {
    "incidentId": 1,
    "status": 1, // 0=PROCESSING, 1=FINISH
    "creator": "Lin",
    "level": 1, // 0=LOW, 1=HIGH
    "info": "This is an Incident.",
    "createTime": "2024-12-20 00:00:00",
    "updateTime": "2024-12-20 00:00:00"
  }
}
```




- <strong><em> POST /incident/search </em></strong>

page search incidents list by condition

input body:
```json
{
    "incidentIds": [1,2],
    "statuses": [0,1], // 0=PROCESSING, 1=FINISH
    "searchCreator": "someone",
    "searchInfo": "something",
    "levels": [0,1], // 0=LOW, 1=HIGH
    "pageSize": 10,
    "currentPage": 1
}
```

output:
```json
{
    "code": 0,
    "message": "success",
    "data": {
        "data": [
            {
              "incidentId": 1,
              "status": 1, // 0=PROCESSING, 1=FINISH
              "creator": "Lin",
              "level": 1, // 0=LOW, 1=HIGH
              "info": "This is an Incident.",
              "createTime": "2024-12-20 00:00:00",
              "updateTime": "2024-12-20 00:00:00"
            }
        ],
        "total": 1,
        "size": 10,
        "current": 1
    }
}
```

- <strong><em> POST /incident </em></strong>

create an incident

input body:
```json
{
  "info": "This is an incident",
  "level": 1, // 0=LOW, 1=HIGH
  "status": 1, // 0=PROCESSING, 1=FINISH
  "creator": "LIN"
}
```
output format:
```json
{
  "code": 0,
  "message": "success",
  "data": 1
}
```

-  <strong><em> PUT /incident/{id} </em></strong>

update incident

path params:
```block
{id} in path param
```

body input:
```json
{
  "info": "info after update",
  "status": 1, // 0=PROCESSING, 1=FINISH
  "level": 1, // 0=LOW, 1=HIGH
  "creator": "someone"
}
```
output:
```json
{
  "code": 0,
  "message": "success",
  "data": true
}
```

- <strong><em> DELETE /incident/{id} </em></strong>

delete incident by id

path params:
```block
{id} in path param
```

output:
```json
{
    "code": 0,
    "message": "success",
    "data": true
}
```

## External Dependencies
This Demo use Java 17 and external dependencies are list as follows:
1. MySQL : database
2. Redis : cache
3. MyBatis-Plus : MyBatis framework
4. fastjson : json library
5. Juint : test framework
6. spring boot : spring-based application bootstrap framework
7. spring mvc : spring-based application mvc framework
 
## Docker Images
1. mysql : docker search register.liberx.info/mysql
2. redis : docker search register.liberx.info/redis
3. this demo image : run docker-compose.yml by docker-compose tool