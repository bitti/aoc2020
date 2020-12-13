
(ns aoc2020.day03
  (:require [planck.core :as core]))

(defn read-map []
  (->>
    core/*in*
    core/line-seq
    (mapv vec)))

(defn count-slope [xd yd tree-map]
  (let [width (count (first tree-map))
        height (count tree-map)]
    (loop [x 0
           y 0
           trees 0]
      (if (>= y height)
        trees
        (recur (mod (+ x xd) width) (+ y yd)
          (if (= (get-in tree-map [y x]) \#)
            (inc trees)
            trees))))))

(let [tree-map (read-map)]
  ;; Part 1
  (println "Number of trees encountered:"
    (count-slope 3 1 tree-map))

  ;; Part 2
  (println "Product of number of trees on all slopes:"
    (reduce * (map (fn [[xd yd]] (count-slope xd yd tree-map))
                [[1 1] [3 1] [5 1] [7 1] [1 2]]))))
