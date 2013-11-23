
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

(defn rows [vars]
  (membero (vars 0) symbols)
  (membero (vars 1) symbols)
  (membero (vars 2) symbols)
  (membero (vars 3) symbols))

(defn nil-constraint [vars row pos]
  (let [val (row pos)]
    (!= (vars 1) val)
    (!= (vars 1) val)
    (!= (vars 2) val)
    (!= (vars 3) val)))

(defn black-constraint [vars row pos]
    (== (vars pos) (row pos)))


(def cols [0 1 2 3])

(defn white-constraint [vars row pos]
  (let [val (row pos)]
    (!= (vars pos) val)
    (conde
      [(== (vars (mod (+ pos 1) 4)) val)]
      [(== (vars (mod (+ pos 2) 4)) val)]
      [(== (vars (mod (+ pos 3) 4)) val)])))

(defn constraint [vars row score pos]
  (case (score pos)
    nil (nil-constraint vars row pos)
    :black (white-constraint vars row pos)
    :white (white-constraint vars row pos)))

(defn constraints [row score]
  (run* [q]
    (fresh [a b c d]
        (== q [a b c d])
        (rows [a b c d])
        (constraint [a b c d] row score 0)
        (constraint [a b c d] row score 1)
        (constraint [a b c d] row score 2)
        (constraint [a b c d] row score 3))))

(defn constraints-multi [pairs]
  (run* [q]
    (fresh [a b c d]
      (== q [a b c d])
      (apply constraints (pairs 2)))))



(defn white [row n]
  (+ 1 2))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
