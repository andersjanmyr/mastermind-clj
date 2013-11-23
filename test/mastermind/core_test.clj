(ns mastermind.core-test
  (:refer-clojure :exclude [==])
  (:require [midje.sweet :refer :all]
            [clojure.core.logic :refer [run* fresh]]
            [mastermind.core :refer :all]))

(facts "about array-to-map"
  (fact "converts to map"
    (array-to-map [:blue :green]) => { :one :blue :two :green }))

(facts "about score"
  (fact "1 correct, 2 wrong place"
    (let [solution [:red :yellow :white :blue]
          entry [:white :black :yellow :blue]]
      (score solution entry) => (exactly {:black 1 :white 2})))
  (fact "4 correct"
    (let [solution [:red :yellow :white :blue]
          entry [:red :yellow :white :blue]]
      (score solution entry) => (exactly {:black 4 :white 0})))
  (fact "1 black 1 white correct"
    (let [solution [:red :yellow :white :white]
          entry [:white :white :white :blue]]
      (score solution entry) => (exactly {:black 1 :white 1}))))

(facts "about random-row"
  (fact "generates four symbols"
    (let [row (random-row)]
      (count row) => (exactly 4)
      )))


(facts "about without"
  (fact ""
    (without [1 2 3 4] 1) => [1 3 4])
  (fact ""
    (without [1 2 3 4] 0) => [2 3 4]))

(facts "about without-list"
  (fact ""
    (without-list [1 2 3 4] [2 1]) => [3 4])
  (fact ""
    (without-list [1 2 3 4] [2 3]) => [1 4]))

(facts "about black"
  (fact "first"
    (first (black [:red :blue :white :yellow] 1)) => [:red :black :black :black])
  (fact "count"
    (count (black [:red :blue :white :yellow] 1)) => 108))

(facts "about white"
  (fact "first"
    (white [:red :blue :white :yellow] 1) => 3))

(facts "about combos"
  (fact "two white and one black"
    (count (combos [:black :white :white nil])) => 12)
  (fact "vector"
    ((combos [:black :white :white nil]) 1) => [:black :white nil :white]))

(facts "about possible-values"
  (fact "two white and one black"
    (count (combos [:black :white :white nil])) => 12))

(facts "about rows"
  (fact "1296 rows")
    (let [result
          (run* [q]
            (fresh [a b c d]
              (rows [a b c d])))]
      (count result) => (exactly 1296)))

(facts "about constraints"
  (fact "black black black black"
    (let [results
          (constraints [:red :blue :green :yellow]
                       [:black :black :black :black])]
      (first results) => [:red :blue :green :yellow]
      (count results) => 1))
  (fact "white white white white"
    (let [results
          (constraints [:red :blue :green :yellow]
                       [:white :white :white :white])]
      (first results) => [:yellow :red :blue :green]
      (count results) => 9))
  (fact "black nil nil nil"
    (let [results
          (constraints [:red :blue :green :yellow] [:black nil nil nil])]
      results => []
      (count results) => 104)))

