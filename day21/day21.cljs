(ns aoc2020.day21
  (:require
    [planck.core :as core]
    [clojure.set :refer [difference intersection]]
    [clojure.string :refer [join split]]))

(defn read-ingredients []
  (->>
    core/*in*
    core/line-seq
    (reduce
      (fn [[m all] line]
        (let [[ingredients allergens] (split line #" \(contains |\)")
              allergens (split allergens #", ")
              ingredients (set (split ingredients #" "))]
          (loop [[allergen & allergens] allergens
                 m m]
            (if allergen
              (recur allergens
                (if (contains? m allergen)
                  (update m allergen intersection ingredients)
                  (assoc m allergen ingredients)))
              [m (concat all ingredients)]))))
      [{} ()])))

(let [[allergens->ingredients ingredients] (read-ingredients)]

  (println "Number of occurences of non-allergic incredients:"
    (-> (reduce difference (set ingredients) (vals allergens->ingredients))
      (filter ingredients)
      count))

  (println "Ingredients alphabetically by their allergen:"
    (let [all->ingr
          (loop [as->is allergens->ingredients
                 a->i {}]
            (let [[as->is updated-all->ingr]
                  (loop [[allergen & rest-allergens] (keys as->is)]
                    (when-let [ingredients (as->is allergen)]
                      (if (= (count ingredients) 1)
                        [(reduce
                           (fn [as->is allergen] (update as->is allergen difference ingredients))
                           as->is
                           (keys as->is))
                         (assoc a->i allergen (first ingredients))]
                        (recur rest-allergens))))]
              (if updated-all->ingr
                (recur as->is updated-all->ingr)
                a->i)))]
      (->> all->ingr
        keys
        sort
        (map all->ingr)
        (join ",")))))
