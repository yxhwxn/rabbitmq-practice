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
