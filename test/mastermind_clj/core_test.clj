(ns mastermind-clj.core-test
  (:require [clojure.test :refer :all]
            [mastermind-clj.core :refer :all]))

(deftest test-count-correct
  (testing "all correct"
    (let [solution "1234"
          entry "1234"]
         (is (= (count-correct solution entry) 4))))
  (testing "one correct"
    (let [solution "1234"
          entry "1111"]
         (is (= (count-correct solution entry) 1)))))

(deftest test-count-wrongplace
  (testing "one wrongplace"
    (let [solution "1234"
          entry "5551"]
         (is (= (count-wrongplace solution entry) 1))))
  (testing "one wrongplace one correct"
    (let [solution "1134"
          entry "1551"]
         (is (= (count-wrongplace solution entry) 2))))
  (testing "two wrongplace one correct"
    (let [solution "3114"
          entry "1551"]
         (is (= (count-wrongplace solution entry) 2)))))

(deftest test-count-correct-wrongplace
  (testing "one correct one wrong"
    (let [solution "1134"
          entry "1551"]
         (is (= (count-correct-wrongplace solution entry) [1 1])))))

