(ns transportation-app.domain.order-test
  (:require [clojure.test :refer :all]
            [matcher-combinators.test :refer [match?]]
            [transportation-app.domain.order :as order]))


(deftest can-cancel-order?-test
  (testing "注文受付時の注文はキャンセル可能"
    (let [order {:status :注文受付}]
      (is (true? (order/can-cancel-order? order)))))
  (testing "配達業や割り当て済みの注文はキャンセル可能"
    (let [order {:status :配達業者割り当て済み}]
      (is (true? (order/can-cancel-order? order)))))
  (testing "配達中の注文はキャンセル不可"
    (let [order {:status :配達中}]
      (is (false? (order/can-cancel-order? order)))))
  (testing "配達完了の注文はキャンセル不可"
    (let [order {:status :配達完了}]
      (is (false? (order/can-cancel-order? order)))))
  (testing "キャンセル済みの注文はキャンセル不可"
    (let [order {:status :キャンセル}]
      (is (false? (order/can-cancel-order? order))))))

(deftest validate-order-test
  (testing "正常な注文はバリデーション成功"
    (let [order {:from-address "東京都渋谷区..."
                 :to-address "東京都新宿区..."
                 :package {:weight 5.0 :size {:width 30 :height 20 :depth 15}}
                 :desired-delivery-datetime #inst "2024-01-02T14:00:00Z"}]
      (is (match? {:valid true}
                  (order/validate-order order #inst "2024-01-01T10:00:00Z")))))
  (testing "配送元と配送先が同じ場合はエラー"
    (let [order {:from-address "東京都渋谷区..."
                 :to-address "東京都渋谷区..."
                 :package {:weight 5.0 :size {:width 30 :height 20 :depth 15}}
                 :desired-delivery-datetime #inst "2024-01-02T14:00:00Z"}]
      (is (match? {:valid false 
                   :errors ["配送元と配送先が同じ住所です"]}
                  (order/validate-order order #inst "2024-01-01T10:00:00Z")))))
  (testing "希望配達日時が過去の場合はエラー"
    (let [order {:from-address "東京都渋谷区..."
                 :to-address "東京都新宿区..."
                 :package {:weight 5.0 :size {:width 30 :height 20 :depth 15}}
                 :desired-delivery-datetime #inst "2024-01-01T09:00:00Z"}]
      (is (match? {:valid false
                   :errors ["希望配達日時は現在時刻より未来である必要があります"]}
                  (order/validate-order order #inst "2024-01-01T10:00:00Z"))))))

