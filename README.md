# Android_Kt
코틀린 공부하기

- 패캠 30개 프로젝트로 배우는 Android 앱 개발 with Kotlin 초격차 패키지 Online.

## Null Safe
    1. Scope Function
     * 코드를 좀 더 읽기 쉽게 만들어 주는 함수 
        (1) let - it
            * null 이 아닌 객체에서 람다를 실행할 때
            * let 에 들어오는 객체는 null일 수 없음
            * orEmpty 문 사용 가능 -> 받는 객체가 null 인 경우는 let에 들어온 함수 자체가 null 일 때에만 들어옴
        (2) run - this
            * with 함수와 비슷한 특징을 가지지만 확장 함수는 아님
        (3) with - this
            * 초기화 시 여러가지의 함수를 실행 할 때 with 문을 사용해 묶어서 사용할 수 있음
        (4) apply - this
            * 객체의 확장 함수 - 초기화 할 때 주로 사용
            * 내부에서 this 로 접근 가능
            * this 는 생략 가능
        (5) also - it
            * value 로 값을 받을 수 있지만 it 으로 생략이 가능함
            * 디버깅 용으로 보통 사용을 많이 함

    2. Data Class
        * model
        * copy, toString, hashcode, getter, setter 등등이 자동으로 생성됨

    3. Lambda Expression
        * 함수형 언어에 가깝기 때문에 사용이 편리함

    4. lateinit, lazy init
        * nullSafe 한 코드를 사용하기 위해 non-null 로 선언해야 할 때
        * 추후 초기화하는 코드
        * lazy init 은 사용하기 전까지 값이 할당되지 않음
