(ns aoc2020.day12
  (:require [planck.core :as core]))

(defn read-instructions []
  (->>
    core/*in*
    core/line-seq
    (map (fn [line]
           (let [[_ i s] (re-matches #"(\w)(\d+)" line)]
             [(first i) (js/parseInt s)])))))

(defn rotate [i s [x y]]
  "Rotate `x`, `y` left or right by `s` degrees"
  (let [[e1 e2]
        (case i
          \R [- +]
          \L [+ -])]
    (loop [d s
           x x
           y y]
      (if (> d 0)
        (recur (- d 90) (e1 y) (e2 x))
        [x y]))))

(defn forward [s [x y] [xd yd]]
  "Move point `x`, `y` by `s` steps, whereas a step is defined by `xd` and `yd`"
  [(+ x (* s xd)) (+ y (* s yd))])

(defn move [i s [x y]]
  (case i
    \E [(+ x s) y]
    \S [x (+ y s)]
    \W [(- x s) y]
    \N [x (- y s)]))

(defn l1 [[x y]]
  "Manhatten distance (l1-norm) of point `x`, `y` to origin"
  (+ (Math/abs x) (Math/abs y)))

(let [instructions (read-instructions)]
  (println "Manhatten distance of ship by initial meaning of instructions"
    (l1 (first
          (reduce
            (fn [[pos dir] [i s]]
              (case i
                (\L \R) [pos (rotate i s dir)]
                \F [(forward s pos dir) dir]
                [(move i s pos) dir]))
            [[0 0] [1 0]]
            instructions))))

  (println "Manhatten distance of ship by corrected meaning of instructions"
    (l1 (first
          (reduce
            (fn [[pos dir] [i s]]
              (case i
                (\L \R) [pos (rotate i s dir)]
                \F [(forward s pos dir) dir]
                [pos (move i s dir)]))
            [[0 0] [10 -1]]
            instructions)))))
