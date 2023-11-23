# JPA Lock 테스트
## 비관적락 (LockModeType.PESSIMISTIC_WRITE)
반드시 충돌이 발생할것이라고 가정하여 로직을 사용한다

## 낙관적락 (LockModeType.OPTIMISTIC)
일단은 충돌이 발생하지 않을 것이라고 가정하고 로직을 실행

이후 jpa @Version 기능을 통해 충돌이 발생했을때 facade 패턴을 활용하여 충돌이 발생하지 않을때까지 로직을 다시 실행한다.
