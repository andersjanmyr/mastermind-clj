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
                 [org.clojure/clojure "1.6.0"]
                 [midje "1.6.3"]
                 [org.clojure/core.logic "0.8.8"]
                 [org.clojure/math.combinatorics "0.0.8"]
                 [compojure "1.1.9"]
                 [ring "1.3.1"]
                 [ring-basic-authentication "1.0.5"]
                 [environ "1.0.0"]
                 [com.cemerick/drawbridge "0.0.6"]
                 ]
  :min-lein-version "2.0.0"
  :jvm-opts ["-Xmx1g"])

