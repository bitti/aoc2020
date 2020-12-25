(ns day25)

(defn transform [subj value]
  (mod (* value subj) 20201227))

(defn find-loop-size [k]
  (loop [n 0
         v 1]
    (if (= v k)
      n
      (recur (inc n) (transform 7 v)))))

(let [[pubkey1 pubkey2] (map js/parseInt *command-line-args*)]
  (->> pubkey1
    (iterate (partial transform pubkey1))
    (take (find-loop-size pubkey2))
    last
    (println "The encryption key is")))
