(ns mastermind.web-test
  (:require [midje.sweet :refer :all]
            [mastermind.web :refer :all]))

(facts "about GET"
  (fact "gets the initial page"
    (+ 2 2) => 4))
