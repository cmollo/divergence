(ns divergence.system)

(defn as [entity k]
  (@entity k))

(defn move [{:keys [x-speed y-speed rot-speed] :as attrs} {ref :ref :as entity} entity-atom]
  (aset ref "rotation" (+ rot-speed (.-rotation ref)))
  (aset (.-position ref) "x" (mod (+ 0.5 (.-x (.-position ref))) 480))
  entity)

(defn move [entities]
  (doseq [e entities]
    (let [{{:keys [x-speed y-speed rot-speed] :as attrs} :velocity ref :ref} @e]
      (aset ref "rotation" (+ rot-speed (.-rotation ref)))
      (aset (.-position ref) "x" (mod (+ 0.5 (.-x (.-position ref))) 480)))))

(defn anchor-system [entities]
  (doseq [e entities]
    (let [{:keys [x y]} (@e :anchor) {ref :ref} @e]
      (aset (.-anchor ref) "x" x)
      (aset (.-anchor ref) "y" y))))

(defn scale-system [entities]
  (doseq [e entities]
    (let [{{:keys [x-scale y-scale rot-speed]} :scale ref :ref} @e]
      (aset (.-scale ref) "x" x-scale)
      (aset (.-scale ref) "y" y-scale))))

(defn position-setter [entities]
  (doseq [e entities]
    (let [{{:keys [x y rot]} :position ref :ref} @e]
      (aset (.-position ref) "x" x)
      (aset (.-position ref) "y" y)
      (aset ref "rotation" rot))))

(defn create-ref [entities]
  (doseq [e entities]
    (swap! e assoc :ref (js/PIXI.Sprite. (-> @e :sprite :texture)))))

(defn on-stage-system [entities]
  (doseq [e entities]
    (.addChild (@e :stage) ref)))

(defn run-entity-through-system [])


(comment

  ;; Playing around with some benchmarks

  (def stuff (atom {:something 0}))
  (def stuff2 #js {:something 0})

  (time
   (dotimes [_ 100000]
     (assoc-in {:something 0} [:something] 1)))

  (time
   (dotimes [_ 100000]
     (swap! stuff update-in [:something] inc)))

  (* (+ 5 20) 20)

  (time
   (dotimes [_ 10000]
     (swap! stuff identity)))

  (time
   (dotimes [_ 100000]
     (swap! stuff #(assoc % :something (inc (:something %))))))

  (time
   (let [k @stuff]
     (dotimes [_ 100000]
       (:something k))))

  (def foo {:something 0})

  (time
   (dotimes [_ 100000]
     (get foo :something)))

  (time
   (dotimes [_ 100000]
     (:something foo)))

  (time
   (dotimes [_ 100000]
     (foo :something)))

  (time
   (dotimes [_ 100000]
     (aset stuff2 "something" (inc (aget stuff2 "something")))))

  (time
   (dotimes [_ 10000000]
     (aget stuff2 "something")))

  (mapv atom (range 10))
  (def atom-list (mapv atom (range 10)))



  )
