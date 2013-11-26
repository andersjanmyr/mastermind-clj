(defproject mastermind "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :profiles {:dev {:plugins [[lein-midje "3.1.1"]]}}
  :dependencies [
                 [org.clojure/clojure "1.5.1"]
                 [midje "1.5.1"]
                 [org.clojure/core.logic "0.8.5"]
                 [org.clojure/math.combinatorics "0.0.7"]
                 ])
