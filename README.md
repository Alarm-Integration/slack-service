# Slack Service

> Slack App 을 등록하고, 알림을 전송할 수 있는 프로젝트  
> [Slack App 을 등록하는 방법](https://gitlab.gabia.com/mentoring/newbie/2021.05/mentoring-alarm/common/-/wikis/%EA%B8%B0%EC%88%A0-%EB%AC%B8%EC%84%9C/App/Slack) 참고
 
<br>

## Eureka 등록

> 현재 Eureka 가 띄워진 곳은 gcloud(239, 240)  
> 서로 사설 IP로 통신하기 위해 각각 사설 IP를 알고 있어야 함.

#### yml 설정 파일

```yml
eureka:
  instance:
    prefer-ip-address: true
    ip-address: [HOST_IP]
  client:
    register-with-eureka: true
    fetch-registry: true
    service-url:
      defaultZone: http://[GCLOUD_239_사설_IP]:8761/eureka, http://GCLOUD_240_사설_IP:8761/eureka
```

- `eureka.instance.ip-address`: Slack-Service 가 Eureka 에 등록할(Endpoint) IP 작성  
- `eureka.client.service-url.defaultZone`: Eureka 가 띄워져 있는 IP 작성

<br>

## .gitlab-ci.yml 추가 설정

> Slack instance 를 띄우고자하는 gcloud 서버마다 [gitlab runner 등록](https://gitlab.gabia.com/mentoring/newbie/2021.05/mentoring-alarm/playground/slack-service/-/settings/ci_cd)  
> 본 서비스는 gcloud(238, 239, 240) 서버에 띄우고자 하며, 3개 등록 되어있음

.gitlab-ci.yml 파일 설정

```yml
...
g238_deploy:
  stage: deploy
  tags:
    - g238
  script:
    - docker pull $DOCKER_HUB_ID/$IMAGE_NAME
    - "{ docker stop $CONTAINER_NAME; docker rm $CONTAINER_NAME; } || true"
    - "docker run -d -p $SLACK_SERVICE_PORT:$SLACK_SERVICE_PORT \
    --name $CONTAINER_NAME \
    -e HOST_IP=$HOST_238_IP \
    -e CONFIG_SERVER_URL=$CONFIG_SERVER_URL \
    $DOCKER_HUB_ID/$IMAGE_NAME"

g239_deploy:
  stage: deploy
  tags:
    - g239
  script:
    - docker pull $DOCKER_HUB_ID/$IMAGE_NAME
    - "{ docker stop $CONTAINER_NAME; docker rm $CONTAINER_NAME; } || true"
    - "docker run -d -p $SLACK_SERVICE_PORT:$SLACK_SERVICE_PORT \
    --name $CONTAINER_NAME \
    -e HOST_IP=$HOST_239_IP \
    -e CONFIG_SERVER_URL=$CONFIG_SERVER_URL \
    $DOCKER_HUB_ID/$IMAGE_NAME"

g240_deploy:
  stage: deploy
  tags:
    - g240
  script:
    - docker pull $DOCKER_HUB_ID/$IMAGE_NAME
    - "{ docker stop $CONTAINER_NAME; docker rm $CONTAINER_NAME; } || true"
    - "docker run -d -p $SLACK_SERVICE_PORT:$SLACK_SERVICE_PORT \
    --name $CONTAINER_NAME \
    -e HOST_IP=$HOST_240_IP \
    -e CONFIG_SERVER_URL=$CONFIG_SERVER_URL \
    $DOCKER_HUB_ID/$IMAGE_NAME"
```

- 사전에 gcloud (238, 239, 240) 서버에 gitlab runner 를 등록 할 때, tag 를 입력하고 runner 를 생성해야 함   
- `$HOST_238_IP`, `$HOST_239_IP`, `$HOST_240_IP` 정보를 gitlab runner secret variable 로 주입 받음

### gitlab runner tag 등록방법

> [Gitlab runner tag Docs](https://docs.gitlab.com/ee/ci/yaml/index.html#tags)

```
$ gitlab-runner register -n \
      --url https://gitlab.gabia.com/ \
      --registration-token [TOKEN] \
      --executor docker \
      --description "gitlab-runner on 240" \
      --tag-list "g240" \
      --docker-image "docker:19.03.12" \
      --docker-volumes /var/run/docker.sock:/var/run/docker.sock
```

- `--tag-list` 옵션을 사용해서 tag 를 붙임