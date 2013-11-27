(defproject mastermind "0.1.0-SNAPSHOT"
  :description "A mastermind solver"
  :url "http://mastermind.herokuapp.com"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :profiles {:dev {:plugins [
                             [lein-midje "3.1.1"]
                             [lein-environ "0.4.0" :hooks true]
                             ]}
             :production {:env {:production true}}}
  :dependencies [
                 [org.clojure/clojure "1.5.1"]
                 [midje "1.5.1"]
                 [org.clojure/core.logic "0.8.5"]
                 [org.clojure/math.combinatorics "0.0.7"]
                 [compojure "1.1.1"]
                 [ring/ring-jetty-adapter "1.1.0"]
                 [ring/ring-devel "1.1.0"]
                 [ring-basic-authentication "1.0.1"]
                 [environ "0.4.0"]
                 [com.cemerick/drawbridge "0.0.6"]
                 ]
  :min-lein-version "2.0.0")

