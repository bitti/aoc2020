(ns aoc2020.day01
  (:require [planck.core :as core]))

(defn read-expenses []
  (->>
    core/*in*
    core/line-seq
    (map #(js/parseInt %))
    set))

(defn find-pair [sum expenses]
  (reduce
    (fn [e s]
      (when-let [p (expenses (- sum s))]
        (reduced [s p])))
    0
    expenses))

(let [expenses (read-expenses)]
  (println "Pair found:" (apply * (find-pair 2020 expenses)))

  (println "Triple found:"
    (loop [expense (first expenses)
           expenses (disj expenses expense)]
      (if-let [pair (find-pair (- 2020 expense) expenses)]
        (apply * expense pair)
        (recur (first expenses) (disj expenses (first expenses)))))))
