;; 顧客 (Customer)
{:customer-id "CUST-001"
 :name "山田太郎"
 :address "東京都渋谷区..."
 :phone "03-1234-5678"
 :email "yamada@example.com"}

;; 注文 (Order)
{:order-id "ORD-001"
 :customer-id "CUST-001"
 :order-datetime #inst "2024-01-01T10:00:00Z"
 :from-address "東京都渋谷区..."
 :to-address "東京都渋谷区..."
 :package {:size {:width 30 :height 20 :depth 15} ; cm
           :weight 5.0 ; kg
           :description "書籍"}
 :status :注文受付 ; :注文受付, :配達業者割当て済み, :配達中, :配達完了, :キャンセル
 :desired-delivery-datetime #inst "2024-01-02T14:00:00Z"}

;; 配達業者 (Driver)
{:driver-id "DRV-001"
 :name "佐藤次郎"
 :phone "090-1234-5678"
 :status :待機中 ; :待機中 :配達中, :休憩中
 :assigned-area "東京都23区"}

;; 配達 (Delivery)
{:delivery-id "DEL-001"
 :order-id "ORD-001"
 :driver-id "DRV-001"
 :start-datetime nil ;配達開始前はnil
 :complete-date nil ;配達完了前はnil
 :status :未割り当て ; ;未割り当て, :割当て済み, :配達中, :配達完了, :配達失敗
 :proof nil ;配達証明 (署名データなど)
 }