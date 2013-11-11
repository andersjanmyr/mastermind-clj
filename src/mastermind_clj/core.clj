
(ns mastermind-clj.core
  (:gen-class)
  (:require [clojure.data :refer [diff]]
             [clojure.set :refer [intersection]]))

(def symbols [:black :white :red :yellow :blue :green])

(defn array-to-map [arr]
  (let [pairs (map vector [:one :two :three :four]  arr)]
    (into {} pairs)))

(defn score [solution entry]
  (let [[a b both] (diff (array-to-map solution) (array-to-map entry))
        a (-> a vals set)
        b (-> b vals set)]
    {:black (count both)
     :white (count (intersection a b))}))

(defn random-row []
    (map (fn [x] (symbols (rand-int 6))) (range 0 4)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
