
(ns mastermind.core
  (:refer-clojure :exclude [==])
  (:require [clojure.data :refer [diff]]
            [clojure.core.logic :refer :all]
            [clojure.math.combinatorics :refer [permutations]]
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

(defn without [v pos]
  (vec (concat (subvec v 0 pos) (subvec v (+ pos 1)))))

(defn without-list [v list]
  (let [[res _ _] (diff (set v) (set list))]
    (vec res)))

(defn black-in-pos [q row pos]
  (fresh [a b c]
    (== q [(row pos) a b c])
    (membero a (without-list symbols (without row pos)))
    (membero b (without-list symbols (without row pos)))
    (membero c (without-list symbols (without row pos)))))

(defn black [row n]
  (run* [q]
    (conde
      [(black-in-pos q row 0)]
      [(black-in-pos q row 1)]
      [(black-in-pos q row 2)]
      [(black-in-pos q row 3)])))

(defn combos [list]
  (permutations list))

(defn possible-value [value ])

(defn possible-values [row score]
  (map row score))

(defn nil-constraint [q row pos]
  (membero q (without-list symbols (without row pos))))

(defn black-constraint [q row pos]
  (== q (row pos)))

(defn white-constraint [q row pos]
  (membero q (without-list symbols (without row pos))))

(defn constraints [row score]
  (run* [q]
    (fresh [a b c d]
      (== q [a b c d])
      (black-constraint a row 0)
      (white-constraint b row 1)
      (white-constraint c row 2)
      (nil-constraint d row 3))))


(defn white [row n]
  (+ 1 2))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
