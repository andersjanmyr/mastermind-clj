
(ns mastermind-clj.core
  (:gen-class))

(use '[clojure.string :only (replace-first)])

(defn count-correct
  "Counts the number of correct inputs"
  [solution entry]
  (count (filter identity (map = solution entry))))

(defn count-wrongplace
  "Counts the number of inputs in the wrong place"
  [solution entry]
  (let [ xs (reduce (fn [seed c] (replace-first seed c "X")) entry solution)]
    (count (filter (fn [x] (= x \X)) xs))) )

(defn count-correct-wrongplace [solution entry]
  (let [correct (count-correct solution entry)
        wrong (count-wrongplace solution entry)]
    [correct (- wrong correct)]))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
