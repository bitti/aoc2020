(ns aoc2020.day02
  (:require [planck.core :as core]))

(defn read-passdb []
  (->>
    core/*in*
    core/line-seq))

(defn count-valid-passwords [passdb pred]
  (reduce
    (fn [valid-count line]
      (let [[_ min max c pass] (re-matches #"(\d+)-(\d+) (\w): (\w+)" line)]
        (if (pred (js/parseInt max) (js/parseInt min) (first c) pass)
          (inc valid-count)
          valid-count)))
    0
    passdb))

(let [passdb (read-passdb)]

  (println "Number of valid passwords by old policies:"
    (count-valid-passwords passdb
      (fn [max min c pass]
        (let [f ((frequencies pass) c)]
          (and f (<= min f max))))))

  (println "Number of valid passwords by new policies:"
    (count-valid-passwords passdb
      (fn [max min c pass]
        (let [c1 (get pass (dec min))
              c2 (get pass (dec max))]
          (not= (= c c1) (= c c2)))))))

