(ns aoc2020.day10
  (:require [planck.core :as core]))

(defn read-adapters []
  (->>
    core/*in*
    core/line-seq
    (map #(js/parseInt %))
    sort))

(def count-arr
  (memoize
    (fn [input [next & adapters] output]
      (let [max-next (+ 3 input)]
        (cond
          (not next)
          (if (< max-next output) 0 1)

          (< max-next next)
          0

          (= max-next next)
          (count-arr next adapters output)

          (> max-next next)
          (+ (count-arr input adapters output)
             (count-arr next adapters output)))))))

(let [adapters (read-adapters)
      device (+ 3 (reduce max adapters))]

  (println "Product of number of 1 and 3 jolt differences when using all adapters:"
    (loop [current 0
           [next & adapters] (concat adapters (list device))
           d1 0
           d3 0]
      (if next
        (case (- next current)
          1 (recur next adapters (inc d1) d3)
          3 (recur next adapters d1 (inc d3))
          (recur next adapters d1 d3))
        (* d1 d3))))

  (println "Possible adapter arrangements:"
    (count-arr 0 adapters device)))
