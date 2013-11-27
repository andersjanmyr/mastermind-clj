
(ns mastermind.core
  (:refer-clojure :exclude [== distinct])
  (:require [clojure.data :refer [diff]]
            [clojure.core.logic :refer :all]
            [clojure.core.logic.fd :refer [distinct]]
            [clojure.math.combinatorics :refer [permutations]]
            [clojure.set :refer [intersection]]))

(def symbols [:black :white :red :yellow :blue :green])

(defn array-to-map [arr]
  "Converts a 4 element array to a map with the keys :one :two :three :four"
  (let [pairs (map vector [:one :two :three :four]  arr)]
    (into {} pairs)))

(defn score-map-to-vec [score-map]
  "Converts map {:white list :black list} to [:black :white nil nil]"
  (let [{bc :black wc :white} score-map
        whites (repeat wc :white)
        blacks (repeat bc :black)
        nils (repeat (- 4 wc bc) nil)]
    (vec (concat blacks whites nils))))


(defn score [solution entry]
  "Calculates the score of of an entry [:red :white :yellow :blue]"
  (let [[a b both] (diff (array-to-map solution) (array-to-map entry))
        a (-> a vals set)
        b (-> b vals set)]
    (score-map-to-vec {:black (count both)
                       :white (count (intersection a b))})))

(defn random-row []
  "Creates a random row [:red :white :yellow :blue]"
  (vec (map (fn [x] (symbols (rand-int 6))) (range 0 4))))

(defn nil-constraint [[a b c d] row pos]
  "Constraint for a missing mark"
  (let [val (row pos)]
    (conde
      [(!= a val)
       (!= b val)
       (!= c val)
       (!= d val)])))

(defn black-constraint [vars row pos]
  "Constraint for a black mark"
  (== (vars pos) (row pos)))


(def cols [0 1 2 3])

(defn white-constraint [vars row pos]
  "Constraint for a white mark"
  (let [val (row pos)]
    (conde
      [(!= (vars pos) val)
       (conde
         [(== (vars (mod (+ pos 1) 4)) val)]
         [(== (vars (mod (+ pos 2) 4)) val)]
         [(== (vars (mod (+ pos 3) 4)) val)])])))

(defn constraint [vars row score pos]
  "Constraint for nil :white or :black"
  (case (score pos)
    nil (nil-constraint vars row pos)
    :black (black-constraint vars row pos)
    :white (white-constraint vars row pos)
    :nada (== 1 2))) ; TODO :nada needs to be removed

(defn rows [[a b c d]]
  (conde
    [(membero a symbols)
     (membero b symbols)
     (membero c symbols)
     (membero d symbols)]))

(defn constraints-one [vars row score]
  (conde
    [(constraint vars row score 0)
     (constraint vars row score 1)
     (constraint vars row score 2)
     (constraint vars row score 3)]))

(defn combos [list]
  (vec (permutations list)))

; TODO this should not be needed
(defn vector-or-empty [v i]
  "Return a dummy vector since I cannot recur over the scores"
  (if (< i (count v))
    (vec (v i))
    [:nada :nada :nada :nada])) ;TODO get rid of this hack

(defn constraints-perm [vars entry]
  (let
    [row (:row entry)
     score (:score entry)
     scores (combos score)]
      (conde
        ; TODO Replace this with recursion
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


; TODO this should not be needed
  "Return a dummy constraint since I cannot recur over the permutations"
(defn constraints-perm-maybe [vars v i]
  (if (< i (count v))
    (constraints-perm vars (v i))
    (== 1 1)))

(defn constraints [entries]
  "Create constraints for all permutations"
  (run* [q]
    (fresh [a b c d]
      (== q [a b c d])
      (rows [a b c d])
      ; TODO Replace this with recursion
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
  "Create a random guess based on constraints"
  (rand-nth (constraints entries)))

(defn solve-recur [row solution entries]
  (let [s (score solution row)
        next-entries (conj entries {:row row :score s})]
    (println (count entries))
    (if (= s [:black :black :black :black])
      next-entries
      (recur (guess next-entries) solution next-entries))))

(defn solve [solution]
  "Solve the solution by recurring guesses and scoring"
  (let [first-guess (random-row)]
    (solve-recur first-guess solution [])))


(defn -main
  "Solve the solution"
  [& args]
  (solve (first args)))
