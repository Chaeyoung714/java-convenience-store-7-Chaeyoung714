# java-convenience-store-precourse

# 🤵 편의점 🏪

## 사용 안내

> 안녕하세요! 저희 W편의점을 이용해주셔서 감사합니다.
> <br>현재 다양한 할인도 가능하니 편하게 구경하고 구매해주세요.
> <br>저희 편의점 이용 방법을 안내드리겠습니다.

### 1. 장바구니에 구매하실 상품을 담아주세요.
구매하고 싶은 상품과 수량을 입력해주세요.<br>

상품 목록과 재고는 아래와 같은 형식으로 확인하실 수 있습니다.
```
- 콜라 1,000원 10개 탄산2+1
- 콜라 1,000원 10개
- 사이다 1,000원 8개 탄산2+1
- 사이다 1,000원 7개
- 오렌지주스 1,800원 9개 MD추천상품
- 오렌지주스 1,800원 재고 없음
- 탄산수 1,200원 5개 탄산2+1
- 탄산수 1,200원 재고 없음
- 물 500원 10개
...
```
상품 옆에 행사 할인 명이 쓰여 있는 상품은 현재 행사 할인 진행 중입니다!

구매하고 싶은 상품과 수량은 아래와 같이 입력해주시면 장바구니에 추가해드려요.
```
[콜라-10],[사이다-3]
```
> 참고사항
> - 상품 목록에 있는 상품만 입력 부탁드립니다.
> - 구매 수량은 재고 수량 이하로 입력 부탁드립니다.
> - 각 상품은 대괄호([]) 안에 담아주시고, 이름과 구매 수량은 하이픈(-)으로 구분해주세요. 각 상품은 쉼표(,)로 구분 부탁드립니다. <br> 이외의 형식으로 입력 시 입력에 실패합니다.
> - 구매 내역 입력에 실패하셔도 다시 입력 가능합니다.

<br>

### 2. 프로모션 증정품 제공!
저희 매장엔 기간제 행사 할인 상시 진행 중입니다.<br> 구매하시는 상품 중 프로모션 혜택 적용이 가능한 상품은 자동 적용해드립니다.<br>
<br>
현재 매장에서 진행 가능한 프로모션 행사입니다.<br> 날짜에 따라 실제 진행중인 프로모션은 이 중 일부일 수 있다는 점 참고 부탁드립니다.
> `탄산2+1` : 2개 구매 시 1개 무료<br>
> `MD추천상품` : 1개 구매 시 1개 무료 <br>
> `반짝할인` : 1개 구매 시 1개 무료


<br>프로모션 재고가 소진될 경우 일반 정가로 제공해드리니 유의 부탁드립니다.
> 참고사항
> - 프로모션 재고 소진 기준은 결제 상품 개수와 증정품 개수를 합한 수량이 프로모션 재고를 넘으면 소진입니다.
>   - `2+1 프로모션의 재고가 5개일 때 6개를 구매하시면 프로모션은 3개에만 적용되고, 나머지 2개는 정가로 결제됩니다.`
> - 프로모션 재고 소진일 경우 일반 정가 상품으로 구매하실지 여쭤봅니다.<br> 정가 결제를 원하시면 `Y`, 그렇지 않으면 `N`을 입력해주세요.
>   - 프로모션 재고가 소진된 상태에서 구매하시면 정가 상품 결제로 확인되어 정가 결제 의사를 묻지 않습니다.

<br> 무료 증정 기준에 맞지 않는 수량으로 구매하실 시에도 기준에 맞지 않는 수량은 정가 결제됩니다.

>참고사항 
>- 단, 입력하신 구매 내역 기준 증정품 무료 추가 가능할 시 추가로 여쭤봅니다. <br> 증정품 추가를 원하시면 `Y`, 그렇지 않으면 `N`을 입력해주세요.

<br>

### 3. 멤버십 할인 제공!
저희 매장을 이용해주시는 모든 고객분들을 대상으로 `30`% 할인 제공 중입니다!

멤버십 할인은 프로모션 적용에 포함된 전체 구매 금액을 제외한 금액을 대상으로 적용됩니다.
<br> `5개 구매 중 2+1 프로모션이 적용되면, 2+1이 적용된 3개를 제외한 2개에만 멤버십이 적용됩니다. `

멤버십 할인을 원하시면 `Y`, 원하지 않으시면 `N`을 입력해주세요.

> 참고사항
> - 멤버십 2번 이상 적용은 불가능합니다.
> - 멤버십 혜택 금액 최대 한도는 `8000`원입니다.

<br>

### 4. 결제 후 영수증
결제가 완료되면 결제 내역은 영수증으로 제공됩니다.

영수증은 아래와 같이 제공됩니다.

```
==============W 편의점================
상품명		수량	금액
콜라		3 	3,000
에너지바 		5 	10,000
=============증	정===============
콜라		1
====================================
총구매액		8	13,000
행사할인			-1,000
멤버십할인			-3,000
내실돈			 9,000
```

<br>

### 5. 저희 편의점이 마음에 드셨나요? 그럼 다시 이용해주세요!
구매 후 이어서 추가 구매가 가능합니다.<br>
추가 구매를 원하시면 `Y`, 그렇지 않으면 `N`을 입력해주세요.

> 참고 사항
> - 잘못된 값 입력 시 재입력 가능합니다.

<br>

---

## 기능 구현 목록

| 완료여부 | 구분 | 주기능                        | 주기능정의                                               | 세부기능                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     | 예외흐름                                                                                                                                                                                                                                                                                      |
| --- | --- |----------------------------|-----------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
|  | **1. 편의점 등록** | **1.1 편의점 행사할인 등록**        | 제공된 행사할인 목록을 등록한다.                                  | - 제공된 행사할인 파일을 받아와 각각의 이름, 구매개수, 증정개수, 행사 시작 날짜, 종료 날짜를 저장한다. <br> - 1.2로 간다.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            | - 중복된 이름의 행사할인이 있으면 예외를 발생한다. <br>[예외 메세지] `[SYSTEM] 매장 내 오류로 서비스 이용이 일시 중단되었습니다. 이용에 불편을 드려 죄송합니다.`                                                                                                                                                                                      |
|  |  | **1.2 편의점 상품 등록**          | 제공된 상품 목록을 등록한다.                                    | - 제공된 상품 파일을 받아와 각각의 이름, 가격, 양, 제공 중인 행사 할인(선택)을 저장한다.  <br> - 2.1로 간다.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  | - 중복된 이름의 상품이 있으면 예외를 발생한다. <br>- 하나의 제품에 두개 이상의 프로모션이 적용되었다면 예외를 발생한다.  <br>  [예외메세지] `[SYSTEM] 매장 내 오류로 서비스 이용이 일시 중단되었습니다. 이용에 불편을 드려 죄송합니다.`                                                                                                                                          |
  |  | **2. 구매할 상품 주문 등록** | **2.1 현재 보유한 재고 정보 출력**    | 환영인사와 함께 현재 보유하고 있는 재고 정보를 출력한다.                    | - `안녕하세요. W편의점입니다. 현재 보유하고 있는 상품입니다.` 문구를 출력한다. <br>- 보유한 모든 제품의 이름, 가격, 남아있는 수량, 진행중인 행사 할인 목록을 출력한다. <br>- 이때, 가격은 천단위씩 `,` 로 끊는다. <br>- 이때, 행사 할인은 오늘의 날짜에 진행 중인 것만 표시한다. <br>- 이때, 남은 수량이 0이면 `재고 없음`으로 출력한다. <br>- 진행중인 행사 할인이 없다면 아무것도 출력하지 않는다. 이때 같은 상품에 대해 프로모션가 제품과 정가 제품을 따로 출력하되, 프로모션 제품을 먼저 출력하고 바로 다음에 정가 제품을 출력한다. <br> - 2.2로 간다.                                                                                                                                                                                                                                       |                                                                                                                                                                                                                                                                                           |
  |  |  | **2.2 구매할 상품의 이름과 개수 입력**  | 사용자가 구매할 상품 각각의 이름과 개수를 입력한다.                       | - `구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])` 안내 문구 출력 후 입력을 받는다.  <br>- 2.3로 간다.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      | - 이때, 아래와 같은 잘못된 형태를 입력하면 예외 메세지를 출력하고 2.2를 다시 시작한다. <br>1. 빈 값을 입력할 때 <br>2. `[ - ], [ - ],…`의 형태가 아닐 때<br> 3. 입력값 중간에 띄어쓰기가 있을 때 <br>[예외 메세지] `[ERROR] 올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요.`                                                                                                |
   |  |  | **2.3 구매할 상품의 이름과 개수 등록**  | 구매할 상품의 이름과 개수 목록을 등록한다.                            | - 구매할 상품의 이름이 등록된 상품의 이름인지 확인하고, 구매내역에 등록한다. <br>- 구매할 상품의 개수가 양의 정수인지 확인하고, 구매내역에 등록한다. <br>- 3.1로 간다.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  | - 이때, 아래와 같은 잘못된 값을 입력하면 예외 메세지를 출력하고 2.2로 돌아간다. <br>1. 상품의 이름이 등록된 이름이 아닐 때  <br>2. 구매 개수가 양의 정수가 아닐 때 <br>[예외메세지] `[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.`                                                                                         |
   |  | **3. 재고 확인** | **3.1 구매할 상품의 재고 확인**      | 구매할 상품의 재고가 있는지 확인한다.                               | - 모든 구매 상품에 대해 구매 수량보다 재고 수량이 많거나 같은지 확인한다. <br> - 4.1로 간다.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              | - 이때, 재고 수량이 모자란 상품이 있다면 예외 메세지를 출력하고 2.2를 다시 시작한다.                                                                                                                                                                                <br>[예외 메세지] `[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.` |
   |  | **4. 행사 할인 적용** | **4.1 구매할 상품의 행사 할인 여부 확인** | 구매할 상품이 행사 할인을 진행중인지 확인한다.                          | - 행사 할인이 진행중인지 확인한다. <br>- 행사 할인이 진행중이면 4.3로, 진행중이지 않으면 4.6로 간다. <br> - 모든 구매 상품에 대해 4.1을 반복한다.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          |                                                                                                                                                                                                                                                                                           |
  |  |  | **4.2 행사할인 상품 계산**         | 행사 할인이 진행중인 상품을 계산한다.                               | - 행사할인 상품으로 먼저 계산한 뒤, 행사할인 상품이 모두 소진되면 비행사할인 상품으로 계산을 한다.                                                                                                                                                                                                                                                                                                                    <br>- 구매할 수량이 현재 행사할인 상품 재고보다 많으면 4.3로 간다. 그렇지 않으면 4.4로 간다.                                                                                                                                                 |                                                                                                                                                                              |
  |  |  | **4.3 행사할인 상품 재고 부족**      | 행사 할인 상품 재고가 부족할 시, 정가 결제 의사를 물어보고 계산한다.            | - `현재 {상품명} {수량}개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)` 문구 출력 후 입력을 받는다. 이때 수량에는 `(사용자가 구매할 전체 수량) - (행사 할인이 적용된 수량)`이 들어간다. <br>- 사용자가 구매를 하겠다고 하면, 4.3.a로 간다.<br>- 구매를 하지 않겠다고 하면, 4.3.b로 간다.                                                                                                                                                                                                                                                                                                                                                                                      | - 이때, 사용자 입력이 `Y`나 `N`이 아니라면, 예외메세지를 출력하고 4.4를 다시 시작한다. <br>[예외메세지] `[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.`                                                                                                                                                                                      |
  |  |  |                            | a. 부족한 행사 할인 상품 수량은 정가로 계산한다.                       | - 행사할인 적용이 불가능한 수량은 모두 정가로 계산한다. <br> (`ex. 2+1 행사에서 6개를 결제했는데 행사 할인 재고가 4개라면, 3개는 행사할인을 적용하고 3개는 정가로 결제한다.`) <br>- 사용자가 무료로 받은 행사할인 증정품의 종류와 수량을 저장한다. <br> - 4.6로 간다.                                                                                                                                                                                                                                                                                                                                                                                                                  |                                                                                                                                                                              |
  |  |  |                            | b. 부족한 행사 할인 상품 수량만큼 제외하고 계산한다.                     | - 행사할인 적용이 불가능한 수량은 제외하고 계산한다. <br>(`ex. 2+1 행사에서 6개를 결제했는데 행사 할인 재고가 4개라면, 3개만 결제한다.`) <br>- 사용자가 무료로 받은 행사할인 증정품의 종류와 수량을 저장한다. <br> - 4.6로 간다.                                                                                                                                                                                                                                                                                                                                                                                                                                        |                                                                                                                                                                               |
  |  |  | **4.4 증정품 1개 더 가져올 수 있는지 확인** | 행사 할인 무료 증정품을 추가할 수 있는지 확인한다.                       | - 행사 할인 기준보다 1개를 덜 가져와서 행사 할인 적용을 받지 못할 때 (`ex. 2+1 행사에서 2개만 구매할 때`), 4.5로 간다.                                                                                                                                                                                                                                                                                                                                                             <br>- 그렇지 않으면 4.6로 간다.                                                                                                                        |                                                                                                                                                                                                                                                                                           |
  |  |  | **4.5 증정품 1개 추가**          | 증정품 1개를 더 가져오면 행사 할인을 적용할 수 있을 때, 추가 의사를 물어보고 계산한다. | - 증정품을 1개 더 가져왔을 때 행사 할인을 적용할 수 있는지 행사 할인 상품 재고를 확인한다. 재고가 모자라면 4.6로 간다. <br>(ex. 2+1 행사에서 5개를 결제했다면, 행사 할인 재고가 6개 이상인지 확인하고, 그렇지 않으면 4.6로 간다.) <br>- `현재 {상품명}은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)` 문구 출력 후 입력을 받는다. <br>- 사용자가 추가하겠다고 하면, 4.5.a로 간다.<br> - 사용자가 추가하지 않겠다고 하면, 4.6으로 간다.                                                                                                                                                                                                                                                                                           |   - 이때, 사용자 입력이 `Y`나 `N`이 아니라면, 예외메세지를 출력하고 5.1을 다시 시작한다. <br>[예외메세지] `[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.`                                                                                                                                                                   |
  |  |  |                            | a. 증정품 1개를 추가하여 계산한다.                               | - 구매 수량에 무료 증정품 1개를 추가하여 계산한다. <br>- 사용자가 무료로 받은 행사할인 증정품의 종류와 수량을 저장한다. 추가한 증정품 1개도 포함한다. <br> - 4.6로 간다.                                                                                                                                                                                                                                                                                                                                                                                                                                                                               |                                                                                                                                                              |
  |  |  |                            | b. 증정품을 추가하지 않고 계산한다.                               | - 구매 수량을 그대로 하여 계산한다. <br>- 사용자가 무료로 받은 행사할인 증정품의 종류와 수량을 저장한다. 이때, 추가하지 않은 증정품은 미포함한다. <br> - 4.6로 간다.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  |                                                                                                                                                                            |
  |  |  | **4.6 최종 재고 차감**           | 구매 수량만큼 상품 재고를 차감한다.                                | - 계산 시 결제된 수량만큼 행사할인 상품의 재고를 차감한다. <br>- 이때 행사할인을 받을 수 있는 수량은 행사할인을 적용하고, 나머지는 정가결제한다. <br>(`ex. 2+1 행사에서 4개 혹은 5개를 결제했다면, 3개는 행사할인을 적용하고 1개 혹은 2개는 정가결제한다.`) <br>- 사용자가 무료로 받은 행사할인 증정품의 종류와 수량을 저장한다. <br> - 5.1로 간다.                                                                                                                                                                                                                                                                                                                                                                  | - 이때, 재고 수량이 모자란 상품이 있다면 예외 메세지를 출력하고 2.2를 다시 시작한다. <br>[예외 메세지] `[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.`                                                                                                                                                                            |
  |  | **5. 멤버십 할인 적용** | **5.1 멤버십 할인 여부 확인**       | 사용자가 멤버십 할인 여부를 입력한다.                               | - `멤버십 할인을 받으시겠습니까? (Y/N)` 문구 출력 후 입력을 받는다. <br>- 사용자가 할인을 받겠다고 하면, 5.2로 간다. <br>- 사용자가 할인을 받지 않는다고 하면, 5.3로 간다.                                                                                                                                                                                                                                                                                                                                                                                                                                                                         | - 이때, 사용자 입력이 `Y`나 `N`이 아니라면, 예외메세지를 출력하고 5.1을 다시 시작한다. <br>[예외메세지] `[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.`                                                                                                                                                                                      |
  |  |  | **5.2 멤버십 할인 적용**          | 멤버십 할인이 적용되는 금액을 계산한다.                              | - 프로모션 적용이 안 된 금액에 대해 멤버십 할인을 적용하고, 할인된 양을 계산한다. <br>- 멤버십은 행사할인 미적용 금액의 30%를 할인받는다. <br>(`ex. 콜라 5개를 샀을 때 3개는 2+1 행사로 샀다면, 2개에 대해서 멤버십 할인을 받는다.`) <br>- 할인 금액이 8000원을 넘기면, 8000원만 할인받는다. <br> - 5.3로 간다.                                                                                                                                                                                                                                                                                                                                                                                |                                                                                                                                                                                                                                                                                           |
  |  |  | **5.3 최종 결제 금액 계산**        | 행사할인 및 멤버십할인이 적용된 최종 결제 금액을 계산한다.                   | - 최종 결제 금액을 계산한다.                                                                                                                                                                                                                                                                                                                                                                                                                                                <br> - 최종 결제 금액은 `(구매한 상품의 총 금액) - (행사할인으로 할인된 금액) - (멤버십할인으로 할인된 금액)` 이다. <br> - 6.1로 간다.                              |                                                                                                                                                                                                                                                                                           |
  |  | **6. 영수증 출력** | **6.1 구매 상품 내역 출력**        | 구매 상품 내역을 출력한다.                                     | - 구분선을 출력하고, 구매한 상품별 이름, 수량, 금액을 출력한다. <br>- 이때, 구매한 상품에는 증정품도 포함된다. <br>- 이때, 금액은 `(정가 금액) * (구매 수량)` 으로 구한다. <br>- 이때, 금액과 수량은 천단위씩 `,` 로 끊는다. <br> - 6.2로 간다.                                                                                                                                                                                                                                                                                                                                                                                                                         |                                                                                                                                                                                                                                                                                           |
  |  |  | **6.2 증정 상품 내역 출력**        | 증정 상품 내역을 출력한다.                                     | - 구분선을 출력하고, 무료로 받은 증정품의 이름, 수량을 출력한다.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       <br>- 이때, 수량은 천단위씩 `,` 로 끊는다.<br> - 6.3로 간다. |                                                                                                                                                                                                                                                                                           |
  |  |  | **6.3 금액 정보 출력**           | 금액 정보를 출력한다.                                        | - 구분선을 출력하고, 총 구매액, 행사 할인액, 멤버십 할인액, 최종 결제 금액을 출력한다. <br>- 이때, 총 구매액은 구매한 상품의 `(정가 금액) * (구매 수량)` 의 합산으로 구한다. <br>- 이때, 행사 할인액과 멤버십 할인액은 음수로 출력한다. <br>- 이때, 모든 금액은 천단위씩 `,` 로 끊는다. <br> - 7.1로 간다.                                                                                                                                                                                                                                                                                                                                                                                      |                                                                                                                                                                                                                                                                                           |
  |  | **7. 재구매 또는 구매 완료** | **7.1 재구매 의사 확인**          | 사용자가 재구매 혹은 구매 완료 여부를 입력한다.                         | - `감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)` 문구 출력 후 입력을 받는다. <br>- 사용자가 다른 상품이 있다고 하면, 2.1로 간다. <br>- 사용자가 없다고 하면, 프로그램을 종료한다.                                                                                                                                                                                                                                                                                                                                                                                                                                                                  | - 이때, 사용자 입력이 `Y`나 `N`이 아니라면, 예외메세지를 출력하고 7.1을 다시 시작한다. <br>[예외메세지] `[ERROR] 잘못된 입력입니다. 다시 입력해 주세요.`                                                                                                                                                                                      |