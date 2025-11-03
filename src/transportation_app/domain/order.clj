(ns transportation-app.domain.order)

(defn can-cancel-order? [order]
  (let [status (:status order)]
    (contains? #{:注文受付 :配達業者割り当て済み} status)))