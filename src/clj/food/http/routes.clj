(ns food.http.routes
  (:require
   [bidi.bidi :as b]
   [com.stuartsierra.component :as component]
   [food.config :as config]
   [food.http.resource.core :as resource]
   [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(def catch-all
  [[#".*" :default]])

(def app-routes
  [["" :root]])

(def routes
  ["/" (concat app-routes catch-all)])

(defn match [resource-ns x]
  (resolve (symbol (str resource-ns "." (or (namespace x) "core")) (name x))))

(defn make-handlers [db-spec]
  (let [p (promise)
        overrides {}]
    @(deliver p #(if (keyword? %)
                   (or (overrides %)
                       (match "food.http.resource" %))
                   %))))

(defrecord App []
  component/Lifecycle
  (start [{{:keys [db-spec]} :collections
           {:keys [atom file]} :config :as component}]
    (println "Starting app...")
    (letfn [(wrap-app [handler]
              (fn [request]
                (handler (assoc request
                           :config (config/config atom file)
                           :routes routes
                           :db-spec db-spec))))]
      (assoc component
        :handler (-> (b/make-handler routes (make-handlers db-spec))
                     (wrap-app)
                     (wrap-defaults site-defaults)))))
  (stop [component]
    (dissoc component :handler)))
