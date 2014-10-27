(defproject com.andrewmcveigh/food "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2371"]
                 [ring/ring-core "1.3.1"]
                 [ring/ring-defaults "0.1.2"]
                 [liberator "0.12.2"]
                 [bidi "1.10.5"]
                 [com.stuartsierra/component "0.2.2"]
                 [com.andrewmcveigh/jetty-component "0.1.1"]
                 [com.andrewmcveigh/refdb "0.5.1"]]
  :plugins [[com.andrewmcveigh/lein-auto-release "0.1.1"]]
  :profiles {:dev {:source-paths ["dev"]}}
  :source-paths ["src/clj"])
