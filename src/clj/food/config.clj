(ns food.config
  (:require
   [clojure.edn :as edn]
   [clojure.java.io :as io]
   [com.stuartsierra.component :as component]))

(defn load-config [f]
  (with-open [reader (java.io.PushbackReader. (io/reader f))]
    (loop [form {}]
      (if-let [form (edn/read {:eof nil} reader)]
        (recur form)
        form))))

(defn config [config-atom file]
  (let [modified (.lastModified file)]
    (if (or (nil? @config-atom) (> modified (:modified @config-atom)))
      (do
        (swap! config-atom assoc :config (load-config file))
        (swap! config-atom assoc :file file)
        (swap! config-atom assoc :modified modified)
        (:config @config-atom))
      (:config @config-atom))))

(defrecord Config []
  component/Lifecycle
  (start [component]
    (println "Loading config...")
    (let [file (or (io/file (System/getProperty "config.path"))
                   (try (io/file (io/resource "config"))
                        (catch IllegalArgumentException _)))]
      (assert file "Config file must exist, and be a file")
      (assoc component
        :atom (atom {})
        :file (io/file file "app.clj"))))
  (stop [component] component))
