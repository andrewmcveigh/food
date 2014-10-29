(ns user
  (:require
   [clojure.tools.namespace.repl :as ns.repl]
   [com.stuartsierra.component :as component]
   [component.jetty :as jetty]
   [food.config :as config]
   [food.data.core :as data]
   [food.http.routes :as routes]
   [refdb.core :as db]))

(defn create-system [config-options]
  (let [{:keys [host port]} config-options]
    (component/system-map
      :collections (data/->Collections (db/db-spec {:path "data"} :food))
      :config (config/->Config)
      :app (component/using (routes/->App) [:collections :config])
      :server (component/using (jetty/web-server port) [:app]))))

(def system nil)

(defn init []
  (alter-var-root #'system
    (constantly (create-system {:port 8080}))))

(defn start []
  (alter-var-root #'system component/start))

(defn stop []
  (alter-var-root #'system
    (fn [s] (when s (component/stop s)))))

(defn go []
  (init)
  (start))

(defn reset []
  (stop)
  (ns.repl/refresh :after 'user/go))
