
(ns mastermind.core
  (:refer-clojure :exclude [== distinct])
  (:require [clojure.data :refer [diff]]
            [clojure.core.logic :refer :all]
            [clojure.core.logic.fd :refer [distinct]]
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

(defn nil-constraint [vars row pos]
  (let [val (row pos)]
    (conde
      [(!= (vars 0) val)
        (!= (vars 1) val)
        (!= (vars 2) val)
        (!= (vars 3) val)])))

(defn black-constraint [vars row pos]
    (== (vars pos) (row pos)))


(def cols [0 1 2 3])

(defn white-constraint [vars row pos]
  (let [val (row pos)]
    (conde
      [(!= (vars pos) val)
       (conde
         [(== (vars (mod (+ pos 1) 4)) val)]
         [(== (vars (mod (+ pos 2) 4)) val)]
         [(== (vars (mod (+ pos 3) 4)) val)])])))

(defn constraint [vars row score pos]
  (case (score pos)
    nil (nil-constraint vars row pos)
    :black (black-constraint vars row pos)
    :white (white-constraint vars row pos)
    :nada (== 1 2)))

(defn rows [vars]
  (conde
    [(membero (vars 0) symbols)
    (membero (vars 1) symbols)
    (membero (vars 2) symbols)
    (membero (vars 3) symbols)]))

(defn constraints-one [vars row score]
  (conde
    [(constraint vars row score 0)
      (constraint vars row score 1)
      (constraint vars row score 2)
      (constraint vars row score 3)]))

(defn combos [list]
  (vec (permutations list)))

(defn vector-or-empty [v i]
  (if (< i (count v))
    (vec (v i))
    [:nada :nada :nada :nada]))

(defn constraints-perm [vars entry]
  (let
    [row (:row entry)
     score (:score entry)
     scores (combos score)]
      (conde
        [(constraints-one vars row (vector-or-empty scores 0))]
        [(constraints-one vars row (vector-or-empty scores 1))]
        [(constraints-one vars row (vector-or-empty scores 2))]
        [(constraints-one vars row (vector-or-empty scores 3))]
        [(constraints-one vars row (vector-or-empty scores 4))]
        [(constraints-one vars row (vector-or-empty scores 5))]
        [(constraints-one vars row (vector-or-empty scores 6))]
        [(constraints-one vars row (vector-or-empty scores 7))]
        [(constraints-one vars row (vector-or-empty scores 8))]
        [(constraints-one vars row (vector-or-empty scores 9))]
        [(constraints-one vars row (vector-or-empty scores 10))]
        [(constraints-one vars row (vector-or-empty scores 11))])))


(defn constraints-perm-maybe [vars v i]
  (if (< i (count v))
    (constraints-perm vars (v i))
    (== 1 1)))

(defn constraints [entries]
  (run* [q]
    (fresh [a b c d]
      (== q [a b c d])
      (rows [a b c d])
      (constraints-perm-maybe [a b c d] entries 0)
      (constraints-perm-maybe [a b c d] entries 1)
      (constraints-perm-maybe [a b c d] entries 2)
      (constraints-perm-maybe [a b c d] entries 3)
      (constraints-perm-maybe [a b c d] entries 4)
      (constraints-perm-maybe [a b c d] entries 5)
      (constraints-perm-maybe [a b c d] entries 6)
      (constraints-perm-maybe [a b c d] entries 7)
      (constraints-perm-maybe [a b c d] entries 8)
      (constraints-perm-maybe [a b c d] entries 9))))


(defn guess [entries]
  (rand-nth (constraints entries)))

(defn white [row n]
  (+ 1 2))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
