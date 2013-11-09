(ns mastermind-clj.core-test
  (:require [midje.sweet :refer :all]
            [clojure.test :refer :all]
            [mastermind-clj.core :refer :all]))

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
  (fact "4 correct"
    (let [solution [:red :yellow :white :white]
          entry [:white :white :white :blue]]
      (score solution entry) => (exactly {:black 1 :white 1}))))

