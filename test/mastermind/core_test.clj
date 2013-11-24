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
      (score solution entry) => [:black :white :white nil]))
  (fact "4 correct"
    (let [solution [:red :yellow :white :blue]
          entry [:red :yellow :white :blue]]
      (score solution entry) => [:black :black :black :black]))
  (fact "1 black 1 white correct"
    (let [solution [:red :yellow :white :white]
          entry [:white :white :white :blue]]
      (score solution entry) => [:black :white nil nil] )))

(facts "about random-row"
  (fact "generates four symbols"
    (let [row (random-row)]
      (count row) => (exactly 4))))

(facts "about combos"
  (fact "two white and one black"
    (count (combos [:black :white :white nil])) => 12)
  (fact "vector"
    ((combos [:black :white :white nil]) 1) => [:black :white nil :white]))

(facts "about rows"
  (fact "1296 rows")
    (let [result
          (run* [q]
            (fresh [a b c d]
              (rows [a b c d])))]
      (count result) => 1296))

(facts "about nil-constraint"
  (fact "625 rows")
    (let [row [:red :green :blue :yellow]
          result
          (run* [q]
            (fresh [a b c d]
              (rows [ a b c d])
              (nil-constraint [a b c d] row 0)
                   ))]
      (count result) => 625))

(facts "about white-constraint"
  (fact "540 rows")
    (let [row [:red :green :blue :yellow]
          result
          (run* [q]
            (fresh [a b c d]
              (rows [ a b c d])
              (white-constraint [a b c d] row 0)
                   ))]
      (count result) => 540))

(facts "about black-constraint"
  (fact " rows")
    (let [row [:red :green :blue :yellow]
          result
          (run* [q]
            (fresh [a b c d]
              (rows [ a b c d])
              (black-constraint [a b c d] row 0)
                   ))]
      (count result) => 216))

(facts "about constraints"
  (fact "black black black black"
    (let [results
          (constraints [ { :row [:red :blue :green :yellow]
                           :score [:black :black :black :black] }])]
      (first results) => [:red :blue :green :yellow]
      (count results) => 1))
  (fact "white white white white"
    (let [results
          (constraints [ { :row [:red :blue :green :yellow]
                           :score [:white :white :white :white] }])]
      (first results) => [:yellow :red :blue :green]
      (count results) => 9))
  (fact "a whole game"
    (let [results
          (constraints [
                        { :row [:red :green :blue :yellow]
                           :score [:white nil nil :white]}
                        { :row [:green :blue :black :white]
                           :score [:white nil nil :white]}
                        { :row [:blue :red :white :blue]
                           :score [:white nil nil :white]}
                        { :row [:white :yellow :red :black]
                           :score [:white :white :white :black]}
                        ] )]
      results => '([:white :black :yellow :red])
      (count results) => 1)))


(facts "about solve"
  (fact "another game"
    (let [solution [:red :green :red :blue]
          result (solve solution)]
      (print result)
      (:row (last result)) => solution)))

