(ns transportation-app.domain.order-test
  (:require [clojure.test :refer :all]
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
      (is (false? (order/can-cancel-order? order)))))
  )

