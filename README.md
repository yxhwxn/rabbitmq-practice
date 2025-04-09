**모든 실습은 [RabbitMQ Get Started 섹션](https://www.rabbitmq.com/tutorials/tutorial-one-java)을 참고하여 진행합니다.**
</br>

---

> Step01: Publisher - Consumer 단순 패턴 구현

* Receiver - 메시지 소비자(Consumer)
* Sender - 메시지 발행자(Publisher)
* Receiver는 RabbitMQ와 연결, 메시지를 전송하는 처리 수행
* Sender는 메시지 큐로부터 퍼블리셔(Receiver)가 적재한 메시지를 소비하는 처리 수행

---

> Step02: Worker Queue 실습

* 작업(Task)을 작업 큐(Work queue)에 스케줄링하는 프로그램
* 문자열값이 어떤 복잡한 작업이라고 가정할 때,
  * ex. Hello...이라고 하면
  * .(dot) 하나는 1초가 소요되는 작업이라고 가정
    * 따라서 Hello...는 3초가 걸리는 작업이라고 가정
        * 실행 인자값(args) 활용해서 Hello...과 같은 값 입력
</br>


* Worker를 여러개 실행시키고 NewTask를 전송하면
  * 여러개의 Worker가 작업을 나누어 처리하는 모습을 확인할 수 있음

---

> Step03: Publisher - Subscribe 구조 기반 Event 메시지 브로커

<img width="192" alt="Image" src="https://github.com/user-attachments/assets/434cb320-14bc-4f1b-abcd-a80893423e94" />

- Exchange
  - Publisher가 보낸 메시지를 소비자(Consumer)에게 어떻게 전달할지 결정하는 라우팅 허브
    - 종류
      - fanout: 모든 소비자에게 메시지를 전달
      - direct: 라우팅 키가 정확히 일치하는 큐에만 전달
      - topic: 라우팅 키의 패턴 매칭
        ```
        *.orange.*
        *.*.rabbit
        lazy.#.rabbit
        ```

_✅ Step03에서는 `fanout` exchange를 사용하여 pub-sub 구조를 구현한다._
</br>

```
  - EmitLog: 메시지를 fanout 타입의 logs exchange에 발행
  - ReceiveLogs: logs exchange에 바인딩된 큐에서 메시지를 수신
  
  즉, 메시지를 발행하면 모든 구독자에게 동일한 메시지를 브로드캐스팅하는 구조
```

- 실행방법
  1. ReceiveLogs를 여러 개 실행(각각 다른 터미널에서 실행)
  2. EmitLog 실행(메시지 전송)
  3. fanout exchange로 설정을 해두었기 때문에, 모든 큐가 브로드캐스팅 되는 지 확인

### 정리

| 구분       | 의미                              |
|-----------|---------------------------------|
| `RabbitMQ`   | 메시지 브로커 (전체 시스템)                |
| `Exchange`   | 메시지를 큐에 분배하는 중간 라우팅 허브          |
| `fanout`     | Exchange의 타입(전파 방식)             |
| `logs`       | Exchange의 이름 (네이밍은 개발자가 임의로 정함) |
