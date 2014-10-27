(ns food.data.core
  (:require
   [com.stuartsierra.component :as component]
   [refdb.core :as db]))

(defrecord Collections [db-spec]
  component/Lifecycle
  (start [component]
    (db/init! db-spec)
    (assoc component :db-spec db-spec))
  (stop [component]
    (dissoc component :db-spec)))
