가계부 시스템
=============
이 프로젝트는, 여러 기기에서 가계부를 사용할 수 있도록 하기 위해 Spring 5 기반으로 만들어진 서버단 어플리케이션 입니다.

## 주요기능

1. 자산종류 설정(은행계좌,현금 등)
2. 각 자산별 수입과 지출 기록
3. 총 수입과 지출기록
4. 각 자산간의 변환(현금을 자신의 계좌에 입금하는 등)

## REST API
모든 요청은 HTTP 헤더에 AuthToken 이라는 인증토큰을 포함해야 동작합니다.

### /book/{YYYY-mm}
1. GET : YYYY-mm 인 가계부를 가져온다.
2. POST : YYYY-mm 인 가계부를 생성한다. 이미 존재한다면 실패한다.

### /accounts/{aid}
1. GET : 식별자가 aid인 account를 가져온다.
2. PUT : 식별자가 aid인 account를 수정한다.
3. DELETE : 식별자가 aid인 account를 삭제한다.

### /accounts
1. POST : 새 account를 생성한다. 

### /account/{type}/{YYYY-mm}
{type} 은 Income 혹은 Expenditure 입니다.
1. GET : YYYY-mm가계부에서 accountType이 {type}인 account들을 가져옵니다.

### /accounts/conversion/{YYYY-mm}
1. GET : YYYY-mm 가계부에서 conversionaccount를 모두 가져옵니다.
2. POST : YYYY-mm 가계부에 새 conversionaccount를 생성합니다.

### /assets/{name}
1. GET : assetName이 {name}인 asset를 가져옵니다.
2. POST : assetName이 {name}인 asset를 생성합니다.

### /assets
1. GET : 모든 asset을 가져옵니다.

### /categories/{type}
{type} 은 Income 혹은 Expenditure 입니다.
1. GET : type이 {type}인 category들을 가져옵니다.

### /categories/{type}/{name}
1. GET : type이 {type}이고 name이 {name}인 category를 가져옵니다.
2. POST : type이 {type}이고 name이 {name}인 category를 생성합니다.

