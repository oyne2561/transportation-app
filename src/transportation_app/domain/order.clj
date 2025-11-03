(ns transportation-app.domain.order)

(defn can-cancel-order? [order]
  (let [status (:status order)]
    (contains? #{:注文受付 :配達業者割り当て済み} status)))

(defn validate-order [order current-datetime]
  (let [errors (atom [])]
    (when (= (:from-address order) (:to-address order))
      (swap! errors conj "配送元と配送先が同じ住所です"))
    (when (.before (:desired-delivery-datetime order) current-datetime)
      (swap! errors conj "希望配達日時は現在時刻より未来である必要があります"))
    (when (> (:weight (:package order)) 30.0)
      (swap! errors conj "荷物の重量は30kg以下である必要があります"))
    (if (empty? @errors)
      {:valid true}
      {:valid false :errors @errors})))
