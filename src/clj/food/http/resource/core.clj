(ns food.http.resource.core
  (:require
   [liberator.core :refer [defresource]]))

(def defaults
  {:available-media-types #{"text/html"}
   :handle-not-found (fn [_] "Not found")})

(defresource root defaults
  :handle-ok (fn [_] "Hello World!"))

(defresource default defaults
  :exists? false)
