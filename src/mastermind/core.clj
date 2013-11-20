
(ns mastermind.core
  (:gen-class)
  (:require [clojure.data :refer [diff]]
             [clojure.core.logic :refer :all]
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

(defn rows []
  (run* [q]
    (fresh [a b c d]
      (== q [a b c d])
      (membero a symbols)
      (membero b symbols)
      (membero c symbols)
      (membero d symbols))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
