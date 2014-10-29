(ns food.http.resource.core
  (:require
   [liberator.core :refer [defresource]]))

(def defaults
  {:available-media-types #{"text/html"}})

(defresource root defaults
  :handle-ok (fn [_] "Hello World!"))
